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
public class PopularGoodsBean {
    private int id;
    private int goodsId;
    private Date createTime;
    private int status;
    private int categoryId;
    private String description;
}
