package com.ywj.swiftbuy.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ywj.swiftbuy.bean.BuyStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuyBean {

    /**
     * setValue(0, id);
     * setValue(1, name);
     * setValue(2, phone);
     * setValue(3, address);
     * setValue(4, uid);
     * setValue(5, createTime);
     * setValue(6, status);
     * setValue(7, goodsId);
     */

    //点击购买  弹出表单（商品id,收件人姓名，收件人电话，购买用户uid）确认购买
    private int id;
    private int goodsId;  //商品id
    private String name;  //收货人姓名
    private String phone; //收货人电话
    private String address;//收货人地址
    private int uid;
    private Date createTime;
    private int status;
    private String buyUsername;  //购买者姓名

    public BuyBean(int goodsId, String name, String phone, String address, String buyUsername) {
        this.goodsId = goodsId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.buyUsername = buyUsername;
        this.createTime = new Date();
        this.status= BuyStatus.success.getValue();
    }

}
