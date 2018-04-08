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
public class ReplyBean {
    private int id;
    private int uid;
    private int goodsId;
    private String content;
    private Date createTime;
    private int status;//是否被删除
}
