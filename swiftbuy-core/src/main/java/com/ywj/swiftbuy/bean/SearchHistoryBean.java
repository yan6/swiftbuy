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
public class SearchHistoryBean {

    private int id;
    private String query;
    private Date date;
    private int count;
    private int goodsId;

    public SearchHistoryBean(String query,int goodsId){
        this.query=query;
        this.goodsId=goodsId;
        this.date=new Date();
        this.count=1;
    }
}
