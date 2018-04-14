package com.ywj.swiftbuy.admin.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ywj.swiftbuy.admin.BusinessBean;
import com.ywj.swiftbuy.bean.GoodsBean;
import com.ywj.swiftbuy.model.*;
import com.ywj.swiftbuy.service.common.GoodsService;
import com.ywj.swiftbuy.service.utils.NextPageUrlGenerator;
import com.ywj.swiftbuy.service.utils.NextPageUrlUtils;
import com.ywj.swiftbuy.utils.ListUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/goods")
public class GoodsAdminController {
    private static final Logger LOG = LoggerFactory.getLogger(GoodsAdminController.class);

    @Autowired
    private GoodsService goodsService;

    /**
     * 新增商品
     * @param goods
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public APIResponse insert(@RequestBody GoodsBean goods) {
        if (goodsService.exist(goods.getBusinessId(), goods.getName()))
            return new FailureAPIResponse("商家已有该商品上架");
        if (goodsService.insert(goods))
            return new SuccessAPIResponse();
        return new FailureAPIResponse("上架失败，请检查商品信息");
    }


    @RequestMapping(value = "/testInsert", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public APIResponse testInsert(@RequestParam(value = "categoryId", required = true) int categoryId,
                                  @RequestParam(value = "image", required = false, defaultValue = "") String image,
                                  @RequestParam(value = "name", required = true) String name,
                                  @RequestParam(value = "description", required = false, defaultValue = "") String description,
                                  @RequestParam(value = "priceBase", required = true) int priceBase,
                                  @RequestParam(value = "remainCount", required = true) int remainCount,
                                  @RequestParam(value = "status", required = true) int stauts,
                                  @RequestParam(value = "businessId", required = true) int businessId) {
        if (goodsService.exist(businessId, name))
            return new FailureAPIResponse("商家已有该商品上架");
        GoodsBean goods = new GoodsBean(categoryId, image, name, description, priceBase, remainCount, stauts, businessId);
        if (goodsService.insert(goods))
            return new SuccessAPIResponse();
        return new FailureAPIResponse("上架失败，请检查商品信息");
    }

    /**
     * 修改商品信息
     * @param goods
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public APIResponse update(@RequestBody GoodsBean goods) {
        if (goods.getId() <= 0)
            return new FailureAPIResponse("该商品不存在");
        if (goodsService.update(goods))
            return new SuccessAPIResponse();
        return new FailureAPIResponse();
    }

    /**
     *  检索商品
     *  1.商品名称
     *  2.商家名称
     *  3.商品类型
     *  4.在线状态
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public RecentGoods get(@RequestParam(value = "start", required = false, defaultValue = "0") int start,
                           @RequestParam(value = "num", required = false, defaultValue = "10") int num,
                           @RequestParam(value = "query", required = true) String query,
                           @RequestParam(value = "type", required = true) UserQueryType type){
        List<GoodsBean> goodsBeans = new ArrayList<>();
        switch (type) {
            case BUSINESS_NAME:
                //商家名称
                break;
            case ADDRESS:
                break;
            case GOODS_NAME:
                break;
            default:
                return null;
        }
        if (CollectionUtils.isEmpty(goodsBeans))
            return null;
        RecentGoods recentGoods = new RecentGoods();
        List<GoodsBean> subList = ListUtils.getSubList(goodsBeans, start, num);
        recentGoods.setGoodsBeans(subList);
        recentGoods.setNextPageUrl(getNextPageUrl(start, num, query, type, goodsBeans.size()));
        return recentGoods;
    }

    private String getNextPageUrl(int start, int num, String query, UserQueryType type, int total) {
        if ((start + num) >= total)
            return null;
        List<NextPageUrlGenerator.Parameter> parameterList = new ArrayList<>();
        parameterList.add(new NextPageUrlGenerator.Parameter("start", String.valueOf(start + num)));
        parameterList.add(new NextPageUrlGenerator.Parameter("num", String.valueOf(num)));
        parameterList.add(new NextPageUrlGenerator.Parameter("query", query));
        parameterList.add(new NextPageUrlGenerator.Parameter("type", String.valueOf(type)));
        return NextPageUrlUtils.nextPageUrl(parameterList, "/admin/goods/query", "localhost:8081");
    }
}
