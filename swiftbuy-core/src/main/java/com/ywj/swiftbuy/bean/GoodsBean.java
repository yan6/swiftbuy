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
public class GoodsBean {

    /**
     * setValue(0, id);
     * setValue(1, categoryId);
     * setValue(2, image);
     * setValue(3, name);
     * setValue(4, description);
     * setValue(5, priceBase);
     * setValue(6, updateTime);
     * setValue(7, remainCount);
     * setValue(8, status);
     * setValue(9, businessId);
     */
    private int id;
    private int categoryId;
    private String image;
    private String name;
    private String description;
    private int priceBase;
    private Date updateTime;
    private int remainCount;//剩余库存
    private int status; //物品状态
    private int businessId; //商家id

    public GoodsBean(int categoryId, String image, String name, String description, int priceBase, int remainCount, int status, int businessId) {
        this.categoryId = categoryId;
        this.image = image;
        this.name = name;
        this.description = description;
        this.priceBase = priceBase;
        this.updateTime = new Date();
        this.remainCount = remainCount;
        this.status = status;
        this.businessId = businessId;
        return;
    }

}
