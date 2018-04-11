package com.ywj.swiftbuy.web;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ywj
 */

@Data
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address implements Serializable {
    private int id;
    private String city;
    private String county;
    private String province;
    private Date update_time;

    public Address(String city,String county,String province){
        this.city=city;
        this.county=county;
        this.province=province;
        this.update_time=new Date();
        return;
    }
}
