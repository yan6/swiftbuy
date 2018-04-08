package com.ywj.swiftbuy.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ywj.swiftbuy.admin.BusinessBean;
import com.ywj.swiftbuy.bean.GoodsBean;
import com.ywj.swiftbuy.bean.ReplyBean;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoodsDetailBean {
    private GoodsBean goods;
    private BusinessBean business;
    private List<ReplyBean> replyBeanList;
}
