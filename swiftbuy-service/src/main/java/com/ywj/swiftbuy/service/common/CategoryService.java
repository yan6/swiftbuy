package com.ywj.swiftbuy.service.common;

import com.ywj.swiftbuy.admin.CategoryBean;

public interface CategoryService {

    int getIdByName(String categoryName);

    boolean exist(String name);

    boolean insert(CategoryBean categoryBean);

    boolean update(CategoryBean categoryBean);
}
