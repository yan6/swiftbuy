package com.ywj.swiftbuy.service.common.Impl;

import com.ywj.swiftbuy.admin.BusinessBean;
import com.ywj.swiftbuy.admin.UserType;
import com.ywj.swiftbuy.dao.model.tables.Business;
import com.ywj.swiftbuy.dao.model.tables.records.BusinessRecord;
import com.ywj.swiftbuy.service.common.AccountService;
import com.ywj.swiftbuy.service.common.AddressIpService;
import com.ywj.swiftbuy.service.common.BusinessService;
import com.ywj.swiftbuy.service.common.CommonService;
import com.ywj.swiftbuy.service.utils.Md5Utils;
import com.ywj.swiftbuy.web.User;
import org.jooq.Field;
import org.jooq.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ywj
 */

@Service
public class BusinessServiceImpl extends CommonService implements BusinessService {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessServiceImpl.class);

    private static final Business TABLE = Business.BUSINESS;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AddressIpService addressIpService;

    @Override
    public List<Integer> getIdList(String city, String county) {
        return select(TABLE,
                TABLE.ID,
                TABLE.CITY.eq(city).and(TABLE.COUNTY.eq(county)));
    }

    @Override
    public boolean update(BusinessBean business) {
        if (get(business.getId()) == null)
            return false;
        return update(TABLE, objectToRecord(business, BusinessRecord.class), TABLE.ID.eq(business.getId()));
    }

    @Override
    public void updateUid(int uid, int id) {
        updateField(TABLE, TABLE.UID, uid, TABLE.ID.eq(id));
    }

    @Override
    public void updatePassword(int uid, String password) {
        Map<Field<?>, Object> toUpdate = new HashMap<>();
        toUpdate.put(TABLE.UPDATE_TIME, new Date());
        toUpdate.put(TABLE.PASSWORD, password);
        update(TABLE, toUpdate, TABLE.UID.eq(uid));
    }

    @Override
    public BusinessBean get(int id) {
        return selectOneRecord(TABLE, TABLE.ID.eq(id), BusinessBean.class);
    }


    @Override
    public boolean insert(BusinessBean business) {
        if (accountService.getUidByUsername(business.getName()) > 0)
            return false;
        business.setPassword(Md5Utils.md5(business.getPassword()));
        BusinessRecord record = objectToRecord(business, BusinessRecord.class);
        Record record1 = insertWithReturning(TABLE, record, TABLE.ID);
        if (record1 != null) {
            //生成用户
            updateUid(accountService.insert(setUser(business)), record1.getValue(TABLE.ID));
            return true;
        }
        return false;
    }

    private User setUser(BusinessBean business) {
        User user = new User();
        user.setUsername(business.getName());
        user.setNickname(business.getName());
        user.setPassword(business.getPassword());
        user.setRegistDate(new Date());
        user.setUserType(UserType.BUSINESS.getValue());
        user.setDescription(business.getDescription());
        user.setPhone(business.getPhone());
        user.setAddressId(addressIpService.getId(business.getCity(), business.getCounty()));
        return user;
    }

}
