package com.ywj.swiftbuy.webapp.controller;

import com.ywj.swiftbuy.model.GoodsDetailBean;
import com.ywj.swiftbuy.service.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @author ywj
 * 1.商品详情页
 * 2.加入购物车
 */
@Controller
@RequestMapping(value = "/api/goods")
public class GoodsDetailController {

    private static final Logger LOG = LoggerFactory.getLogger(GoodsDetailController.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private ReplyService replyService;

    /**
     * 访问这个接口打开商品详情页
     *
     * @param id
     * @param businessId
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public GoodsDetailBean getGoodsDetail(@RequestParam(value = "id", required = true) int id,
                                          @RequestParam(value = "businessId", required = true) int businessId) {
        if (id < 0)
            id = 1;
        if (businessId < 0)
            businessId = 1;
        GoodsDetailBean goodsDetailBean = new GoodsDetailBean();
        goodsDetailBean.setGoods(goodsService.getGoodsById(id));
        goodsDetailBean.setBusiness(businessService.get(businessId));
        goodsDetailBean.setReplyBeanList(replyService.getReplyBeanList(id));
        return goodsDetailBean;
    }
}
