package com.ywj.swiftbuy.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ywj.swiftbuy.bean.GoodsBean;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 *
 * 商品列表
 */
@Data
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecentGoods {
    private List<GoodsBean> goodsBeans;
    private String nextPageUrl;

    public RecentGoods(List<GoodsBean> goodsBeans, String nextPageUrl) {
        this.goodsBeans = goodsBeans;
        this.nextPageUrl = nextPageUrl;
    }
}
