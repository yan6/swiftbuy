package com.ywj.swiftbuy.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoodsBuyBean implements Serializable {
    private int id;
    private int goodsId;
    private int uid;
    private Date createTime;
    private int businessId;
    private boolean ifBuy;
}
