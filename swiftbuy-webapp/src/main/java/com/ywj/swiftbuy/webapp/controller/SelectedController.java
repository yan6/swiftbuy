package com.ywj.swiftbuy.webapp.controller;

import com.ywj.swiftbuy.bean.GoodsBean;
import com.ywj.swiftbuy.bean.SortStrategy;
import com.ywj.swiftbuy.model.RecentGoods;
import com.ywj.swiftbuy.service.common.*;
import com.ywj.swiftbuy.service.utils.NextPageUrlGenerator;
import com.ywj.swiftbuy.service.utils.NextPageUrlUtils;
import com.ywj.swiftbuy.utils.ListUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * 首页接口
 *
 * @author ywj
 * <p>
 * 1.直接访问首页  ：http://localhost:8080/selected
 * 2.通过登录 ：http://localhost:8080/swiftbuy/login.html
 * 3.通过注册：http://localhost:8080/swiftbuy/register.html
 * 这三种方式都是跳到首页
 * <p>
 * <p>
 * <p>
 * <p>
 * 1.首页之  默认商品列表或检索商品列表
 * 2.首页之  推荐商品列表
 * 3.首页之  热门商品列表
 */

@Controller
@RequestMapping(value = "/api/selected")
public class SelectedController {

    private static final Logger LOG = LoggerFactory.getLogger(SelectedController.class);

    @Autowired
    private BusinessService businessService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PopularGoodsService popularGoodsService;

    /**
     * 通过三级联动检索出商品列表    确定，跳转到另一页面  city,county,类型
     * 注：根据区域，排序策略选出num个商品
     * 如果不能分页的话，就不做
     * <p>
     * 1.商品类型必填
     * 2.省  省市，省市区
     */
    @RequestMapping(value = "/recentGoodsList", method = RequestMethod.GET)
    @ResponseBody
    public RecentGoods getRecentGoodsList(@RequestParam(value = "categoryName", required = false, defaultValue = "服装") String categoryName,
                                          @RequestParam(value = "province", required = false, defaultValue = "") String province,
                                          @RequestParam(value = "city", required = false, defaultValue = "") String city,
                                          @RequestParam(value = "county", required = false, defaultValue = "") String county,
                                          @RequestParam(value = "strategy", required = false, defaultValue = "date") SortStrategy strategy,
                                          @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                                          @RequestParam(value = "num", required = false, defaultValue = "6") int num) {
        //city，county定位到商家business_id   ,business_id,categoryName定位到商品
        //TODO city county categoryName缺少，就默认一个推荐列表
        RecentGoods recentGoods = new RecentGoods();
        List<Integer> businessIdList = getBusinessIdList(province, city, county);
        int categoryId = categoryService.getIdByName(categoryName);
        if (CollectionUtils.isEmpty(businessIdList) || categoryId < 1)
            return null;
        List<GoodsBean> totalGoods = goodsService.getGoodsListInCity(businessIdList, categoryId, strategy);
        if (CollectionUtils.isEmpty(totalGoods))
            return null;
        List<GoodsBean> subList = ListUtils.getSubList(totalGoods, start, num);
        recentGoods.setGoodsBeans(subList);
        recentGoods.setNextPageUrl(getNextPageUrl(city, county, categoryName, strategy, start, num, totalGoods.size()));
        return recentGoods;
    }

    /**
     * 默认第一位置展示的商品列表
     *
     * @return
     */
    @RequestMapping(value = "/defaultGoodsList", method = RequestMethod.GET)
    @ResponseBody
    public List<GoodsBean> defaultGoodsList(@RequestParam(value = "num", required = false, defaultValue = "8") int num) {
        return goodsService.getDefaultShowList(num);
    }

    /**
     * 第二位置
     * 推荐商品(num个)
     * 用户浏览过，买过，搜索过，各种历史数据推荐出商品，也可以根据用户做个性化推荐
     */
    @RequestMapping(value = "/recommendGoodsList", method = RequestMethod.GET)
    @ResponseBody
    public List<GoodsBean> recommendGoodsList(@RequestParam(value = "username", required = false, defaultValue = "") String username,
                                              @RequestParam(value = "num", required = false, defaultValue = "6") int num) {
        //TODO 默认值还需设定下
        if (StringUtils.isEmpty(username) || StringUtils.isBlank(username)) {
            //走大众推荐入口
            return historyService.getCommonRecommendList(num);
        } else {
            //走个性化推荐入口
            int uid = accountService.getUidByUsername(username);
            if (uid <= 0)
                return historyService.getCommonRecommendList(num);
            return historyService.getSpecialRecommendList(uid, num);
        }
    }


    /**
     * 第三位置
     * 热门商品
     * 购买量最多的商品num/2个，popular_goods(管理员设置)中num/2个，混合排序
     */
    @RequestMapping(value = "/hotGoodsList", method = RequestMethod.GET)
    @ResponseBody
    public List<GoodsBean> hotGoodsList(@RequestParam(value = "num", required = false, defaultValue = "6") int num) {
        int popularNum = num / 2;
        int commonNum = num - popularNum;
        List<Integer> popularGoodsId = popularGoodsService.getDefaultPopularGoodsId(popularNum);
        List<Integer> commonHistory = historyService.getCommonHistory(num);
        popularGoodsId.addAll(ListUtils.getSubListByIndex(commonHistory, 0, commonNum));
        Collections.shuffle(popularGoodsId, new Random(System.currentTimeMillis()));
        return goodsService.getGoodsBeanList(popularGoodsId);
    }

    private String getNextPageUrl(String city, String county, String categoryName, SortStrategy strategy, int start, int num, int total) {
        if (total <= (num + start))
            return null;
        List<NextPageUrlGenerator.Parameter> parameterList = new ArrayList<>();
        parameterList.add(new NextPageUrlGenerator.Parameter("city", city));
        parameterList.add(new NextPageUrlGenerator.Parameter("county", county));
        parameterList.add(new NextPageUrlGenerator.Parameter("categoryName", categoryName));
        parameterList.add(new NextPageUrlGenerator.Parameter("strategy", String.valueOf(strategy)));
        parameterList.add(new NextPageUrlGenerator.Parameter("start", String.valueOf(start + num)));
        parameterList.add(new NextPageUrlGenerator.Parameter("num", String.valueOf(num)));
        return NextPageUrlUtils.nextPageUrl(parameterList, "/api/selected/recentGoodsList", "localhost:8080");
    }

    private List<Integer> getBusinessIdList(String province, String city, String county) {
        if (StringUtils.isEmpty(province))
            return businessService.getIdListByP("北京市");
        if (StringUtils.isEmpty(city) && StringUtils.isEmpty(county))
            return businessService.getIdListByP(province);
        else if (getFourCity().contains(province))
            city = province;
        if (StringUtils.isEmpty(county))
            return businessService.getIdListByPC(province, city);
        return businessService.getIdListByPCC(province, city, county);
    }

    private List<String> getFourCity() {
        List<String> list = new ArrayList<>();
        list.add("北京市");
        list.add("天津市");
        list.add("上海市");
        list.add("重庆市");
        return list;
    }

}
