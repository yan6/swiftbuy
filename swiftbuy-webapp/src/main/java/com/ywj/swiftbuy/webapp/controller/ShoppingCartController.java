package com.ywj.swiftbuy.webapp.controller;

import com.ywj.swiftbuy.bean.ShoppingCartBean;
import com.ywj.swiftbuy.model.APIResponse;
import com.ywj.swiftbuy.model.FailureAPIResponse;
import com.ywj.swiftbuy.model.SuccessAPIResponse;
import com.ywj.swiftbuy.service.common.AccountService;
import com.ywj.swiftbuy.service.common.ShoppingCartService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ywj
 * 购物车
 * 1.添加商品进购物车
 * 2.从购物车删除商品
 * 3.购物车列表(uid,username两种方式获得)
 */

@Controller
@RequestMapping(value = "/api/shoppingCart")
public class ShoppingCartController {

    private static final Logger LOG = LoggerFactory.getLogger(ShoppingCartController.class);

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private AccountService accountService;

    //加入购物车
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    public APIResponse add(@RequestParam(value = "goodsId", required = true) int goodsId,
                           @RequestParam(value = "username", required = true) String username) {
        //如果存在只修改状态
        return shoppingCartService.add(goodsId, username) ? new SuccessAPIResponse() : new FailureAPIResponse("加入购物车失败");
    }

    //从购物车中删除该商品
    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    @ResponseBody
    public APIResponse remove(@RequestParam(value = "id", required = true) int id) {
        //shoppingCart表的id
        return shoppingCartService.updateStatus(id) ? new SuccessAPIResponse() : new FailureAPIResponse("id不存在");
    }

    //获取购物车列表,username方式
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<ShoppingCartBean> getList(@RequestParam(value = "username", required = true) String username) {
        //订单id
        int uid = accountService.getUidByUsername(username);
        if (uid <= 0)
            return null;
        List<ShoppingCartBean> list = shoppingCartService.getListByUid(uid);
        if (CollectionUtils.isEmpty(list))
            return null;
        return list;
    }

    //获取购物车列表,uid方式
    @RequestMapping(value = "/shopCartList", method = RequestMethod.GET)
    @ResponseBody
    public List<ShoppingCartBean> getList(@RequestParam(value = "uid", required = true) int uid) {
        //订单id
        List<ShoppingCartBean> list = shoppingCartService.getListByUid(uid);
        if (CollectionUtils.isEmpty(list))
            return null;
        return list;
    }
}
