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
public class HistoryBean {
    private int id;
    private int uid;
    private Date date;
    private int goodsId;
    private String city;
    private int type;//historyType

    public HistoryBean(int uid,int goodsId,String city,int type){
        this.uid=uid;
        this.goodsId=goodsId;
        this.city=city;
        this.type=type;
        this.date=new Date();
        return;
    }
}
