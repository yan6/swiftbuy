package com.ywj.swiftbuy.webapp.controller;

import com.ywj.swiftbuy.bean.HistoryBean;
import com.ywj.swiftbuy.model.APIResponse;
import com.ywj.swiftbuy.model.BuyBean;
import com.ywj.swiftbuy.model.SuccessAPIResponse;
import com.ywj.swiftbuy.service.common.*;
import com.ywj.swiftbuy.thread.ThreadPool;
import com.ywj.swiftbuy.web.HistoryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ywj
 * 1.点击购买，生成订单->提交订单
 * 2.
 */
@Controller
@RequestMapping(value = "/api/buy")
public class BuyController {

    private static final Logger LOG = LoggerFactory.getLogger(BuyController.class);

    @Autowired
    private AddressIpService addressIpService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private BuyService buyService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private AccountService accountService;

    /**
     * 点击购买  弹出表单（商品id,收件人姓名，收件人电话，收件人地址，确认购买,用户uid）
     * 注：1.前端校验库存量
     */
    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    @ResponseBody
    public APIResponse buy(@RequestBody BuyBean buy) {
        //添加历史购买记录
        generateBuy(buy);
        //库存减1
        goodsService.updateRemainCount(buy.getGoodsId(), -1);
        //生成订单
        buyService.insert(buy);
        return new SuccessAPIResponse();
    }

    private void generateBuy(BuyBean buy) {
        ThreadPool.getInstance().exec(() -> {
            try {
                String city = addressIpService.getCurrentIdAddress();
                if (buy.getUid() <= 0 && buy.getBuyUsername() != null) {
                    int uidByUsername = accountService.getUidByUsername(buy.getBuyUsername());
                    if (uidByUsername<0)
                        return;
                    buy.setUid(uidByUsername);
                }
                HistoryBean historyBean = new HistoryBean(buy.getUid(), buy.getGoodsId(), city, HistoryType.buy.getValue());
                historyService.insert(historyBean);
            } catch (Exception e) {
                LOG.warn("get current address failure");
            }
        });
    }
}
