package com.ywj.swiftbuy.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
     setValue(0, id);
     setValue(1, name);
     setValue(2, phone);
     setValue(3, address);
     setValue(4, uid);
     setValue(5, createTime);
     setValue(6, status);
     setValue(7, goodsId);
     */

    //点击购买  弹出表单（商品id,收件人姓名，收件人电话，购买用户uid）确认购买
    private int id;
    private int goodsId;
    private String name;
    private String phone;
    private String address;
    private int uid;
    private Date createTime;
    private int status;
}
