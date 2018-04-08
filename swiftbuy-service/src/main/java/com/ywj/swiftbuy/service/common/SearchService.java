package com.ywj.swiftbuy.service.common;

import com.ywj.swiftbuy.bean.GoodsBean;

import java.util.List;

public interface SearchService {

    List<GoodsBean> searchGoods(String query);
}
