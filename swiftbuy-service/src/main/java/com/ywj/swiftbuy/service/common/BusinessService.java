package com.ywj.swiftbuy.service.common;

import com.ywj.swiftbuy.admin.BusinessBean;

import java.util.List;

public interface BusinessService {

    List<Integer> getIdListByP(String province);

    boolean insert(BusinessBean business);

    BusinessBean get(int id);

    boolean update(BusinessBean business);

    void updateUid(int uid, int id);

    void updatePassword(int uid, String password);

    List<Integer> getIdListByPC(String province, String city);

    List<Integer> getIdListByPCC(String province, String city, String county);

    List<BusinessBean> getAllBusiness();

    List<BusinessBean> getBusinessListByAddress(String query);

    List<BusinessBean> getBusinessListByName(String query);

    List<Integer> getIdList(String name);
}
