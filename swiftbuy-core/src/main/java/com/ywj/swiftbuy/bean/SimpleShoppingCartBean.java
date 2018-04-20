package com.ywj.swiftbuy.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleShoppingCartBean {
    private int id;
    private int uid;
    private int goodsId;
    private Date createTime;
    private Date updateTime;
    private int status;//shoppingCartStatus
    private int count;//购物车中商品数量
    private String username;//如果前端传uid不方便，则传username
}
