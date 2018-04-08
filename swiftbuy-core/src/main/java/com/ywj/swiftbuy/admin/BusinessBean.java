package com.ywj.swiftbuy.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessBean {
    private int id;
    private int uid;
    private String name;
    private String description;
    private Date createTime;
    private Date updateTime;
    private String city;
    private String county;
    private String phone;
    private String password;
    private String icon;

    public BusinessBean(String name,String description,String city,String county,String phone,String password){
        this.name=name;
        this.description=description;
        this.city=city;
        this.county=county;
        this.phone=phone;
        this.password=password;
        this.createTime=new Date();
        this.updateTime=new Date();
        return;
    }
}
