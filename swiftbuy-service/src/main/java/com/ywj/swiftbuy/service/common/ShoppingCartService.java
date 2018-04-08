package com.ywj.swiftbuy.service.common;

import com.ywj.swiftbuy.bean.ShoppingCartBean;

import java.util.List;

public interface ShoppingCartService {

    boolean add(ShoppingCartBean shoppingCart);

    boolean updateStatus(int id);

    List<ShoppingCartBean> getListByUid(int uid);
}
