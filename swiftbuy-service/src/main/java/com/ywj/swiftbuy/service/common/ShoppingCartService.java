package com.ywj.swiftbuy.service.common;

import com.ywj.swiftbuy.bean.ShoppingCartBean;

import java.util.List;

public interface ShoppingCartService {

    boolean add(int goodsId, String username);

    boolean updateStatus(int id);

    List<ShoppingCartBean> getListByUid(int uid);
}
