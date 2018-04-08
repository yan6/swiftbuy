package com.ywj.swiftbuy.admin.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ywj.swiftbuy.bean.GoodsBean;
import com.ywj.swiftbuy.model.APIResponse;
import com.ywj.swiftbuy.model.FailureAPIResponse;
import com.ywj.swiftbuy.model.JacksonViews;
import com.ywj.swiftbuy.model.SuccessAPIResponse;
import com.ywj.swiftbuy.service.common.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/admin")
public class GoodsAdminController {
    private static final Logger LOG = LoggerFactory.getLogger(GoodsAdminController.class);

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/goods/insert", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public APIResponse insert(@RequestBody GoodsBean goods) {
        if (goodsService.exist(goods.getBusinessId(), goods.getName()))
            return new FailureAPIResponse("商家已有该商品上架");
        if (goodsService.insert(goods))
            return new SuccessAPIResponse();
        return new FailureAPIResponse("上架失败，请检查商品信息");
    }

    @RequestMapping(value = "/goods/update", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public APIResponse update(@RequestBody GoodsBean goods) {
        if (goods.getId() <= 0)
            return new FailureAPIResponse("该商品不存在");
        if (goodsService.update(goods))
            return new SuccessAPIResponse();
        return new FailureAPIResponse();
    }
}
