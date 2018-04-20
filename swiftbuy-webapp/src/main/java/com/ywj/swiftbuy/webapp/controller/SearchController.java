package com.ywj.swiftbuy.webapp.controller;

import com.ywj.swiftbuy.bean.GoodsBean;
import com.ywj.swiftbuy.model.RecentGoods;
import com.ywj.swiftbuy.service.common.SearchService;
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
import java.util.List;

/**
 * @author ywj
 * 1.搜索接口
 */
@Controller
@RequestMapping(value = "/api/search")
public class SearchController {

    private static final Logger LOG = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;


    /**
     * 不做分页展示接口
     * @param query
     * @param start
     * @param num
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<GoodsBean> search(@RequestParam(value = "query", required = false, defaultValue = "") String query,
                              @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                              @RequestParam(value = "num", required = false, defaultValue = "10") int num) {
        if (StringUtils.isBlank(query))
            return null;
        List<GoodsBean> goodsBeanList = searchService.searchGoods(query);
        //记录搜索历史

        if (CollectionUtils.isEmpty(goodsBeanList))
            return null;
        List<GoodsBean> subList = ListUtils.getSubList(goodsBeanList, start, num);

        return subList;
    }

    @RequestMapping(value = "/searchCanPage", method = RequestMethod.GET)
    @ResponseBody
    public RecentGoods searchCanPage(@RequestParam(value = "query", required = false, defaultValue = "") String query,
                              @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                              @RequestParam(value = "num", required = false, defaultValue = "10") int num) {
        if (StringUtils.isBlank(query))
            return null;
        List<GoodsBean> goodsBeanList = searchService.searchGoods(query);
        if (CollectionUtils.isEmpty(goodsBeanList))
            return null;
        List<GoodsBean> subList = ListUtils.getSubList(goodsBeanList, start, num);
        String nextPageUrl = getNextPageUrl(query, start, num, goodsBeanList.size());
        return new RecentGoods(subList, nextPageUrl);
    }

    private String getNextPageUrl(String query, int start, int num, int total) {
        if ((start + num) >= total)
            return null;
        List<NextPageUrlGenerator.Parameter> parameterList = new ArrayList<>();
        parameterList.add(new NextPageUrlGenerator.Parameter("query", query));
        parameterList.add(new NextPageUrlGenerator.Parameter("start", String.valueOf(start + num)));
        parameterList.add(new NextPageUrlGenerator.Parameter("num", String.valueOf(num)));
        return NextPageUrlUtils.nextPageUrl(parameterList, "/api/search", "localhost:8080");
    }

}
