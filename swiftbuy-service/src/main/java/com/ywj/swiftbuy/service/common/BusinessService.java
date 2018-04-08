package com.ywj.swiftbuy.service.common;

import com.ywj.swiftbuy.admin.BusinessBean;

import java.util.List;

public interface BusinessService {

    List<Integer> getIdList(String city, String county);

    boolean insert(BusinessBean business);

    BusinessBean get(int id);

    boolean update(BusinessBean business);

    void updateUid(int uid,int id);

    void updatePassword(int uid, String password);
}
