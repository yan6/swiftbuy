package com.ywj.swiftbuy.service.common;

import com.ywj.swiftbuy.web.Address;

import java.util.List;

public interface AddressIpService {

    String getCurrentIdAddress() throws Exception;

    List<Address> getCountyList(String city);

    boolean insert(String city, String county, String province);

    boolean update(Address address);

    boolean exist(String city,String county);

    int getId(String city,String county);

    String getAddressStr(int id);
}
