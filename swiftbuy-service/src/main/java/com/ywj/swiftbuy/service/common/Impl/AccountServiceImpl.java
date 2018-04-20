package com.ywj.swiftbuy.service.common.Impl;

import com.ywj.swiftbuy.admin.UserType;
import com.ywj.swiftbuy.dao.model.tables.records.UserRecord;
import com.ywj.swiftbuy.service.common.AccountService;
import com.ywj.swiftbuy.service.common.AddressIpService;
import com.ywj.swiftbuy.service.common.BusinessService;
import com.ywj.swiftbuy.service.common.CommonService;
import com.ywj.swiftbuy.service.utils.Md5Utils;
import com.ywj.swiftbuy.web.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Field;
import org.jooq.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ywj
 * 和账号相关
 */

@Service
public class AccountServiceImpl extends CommonService implements AccountService {

    public static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

    private static final com.ywj.swiftbuy.dao.model.tables.User TABLE = com.ywj.swiftbuy.dao.model.tables.User.USER;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private AddressIpService addressIpService;

    @Override
    public boolean login(String username, String password) {
        password = Md5Utils.md5(password);
        return exists(TABLE, TABLE.USERNAME.eq(username).and(TABLE.PASSWORD.eq(password)));
    }

    @Override
    public User getUser(int uid) {
        return selectOneRecord(TABLE, TABLE.UID.eq(uid), User.class);
    }

    @Override
    public int getUidByUsername(String username) {
        Integer uid = selectOneValue(TABLE, TABLE.UID, TABLE.USERNAME.eq(username));
        if (uid == null)
            return -1;
        return uid;
    }

    @Override
    public int insert(User user) {
        Record record = insertWithReturning(TABLE, objectToRecord(user, UserRecord.class), TABLE.UID);
        if (record == null)
            return -1;
        return record.getValue(TABLE.UID);
    }

    @Override
    public boolean update(User user) {
        UserRecord record = objectToRecord(user, UserRecord.class);
        return update(TABLE, record, TABLE.UID.eq(user.getUid()));
    }

    @Override
    public boolean updateUsername(int uid, String username) {
        Map<Field<?>, Object> toUpdate = new HashMap<>();
        toUpdate.put(TABLE.USERNAME, username);
        toUpdate.put(TABLE.NICKNAME, username);
        return update(TABLE, toUpdate, TABLE.UID.eq(uid));
    }

    @Override
    public boolean updatePassword(int uid, String oldPassword, String newPassword) {
        oldPassword = Md5Utils.md5(oldPassword);
        User user = getUser(uid);
        if (user == null || !StringUtils.equals(user.getPassword(), oldPassword)) {
            LOG.info("oldPassword is failure");
            return false;
        }
        return forgetPassword(uid, newPassword, user.getUserType());

    }

    @Override
    public boolean forgetPassword(int uid, String newPassword, String userType) {
        newPassword = Md5Utils.md5(newPassword);
        if (StringUtils.equalsIgnoreCase(userType, UserType.BUSINESS.getValue())) {
            businessService.updatePassword(uid, newPassword);
        }
        Map<Field<?>, Object> toUpdate = new HashMap<>();
        toUpdate.put(TABLE.UPDATEDATE, new Date());
        toUpdate.put(TABLE.PASSWORD, newPassword);
        return update(TABLE, toUpdate, TABLE.UID.eq(uid));
    }

    @Override
    public User getUser(String username) {
        return selectOneRecord(TABLE, TABLE.USERNAME.eq(username), User.class);
    }

    @Override
    public List<User> getListByQuery(String query) {
        query = "%" + query + "%";
        List<User> list = select(TABLE, TABLE.USERNAME.eq(query), User.class);
        if (CollectionUtils.isEmpty(list))
            return new ArrayList<>();
        fillAddress(list);
        return list;
    }

    @Override
    public List<User> getUserListByRegisterDate(Date minDate, Date maxDate) {
        List<User> list = select(TABLE,
                TABLE.REGISTDATE.le(new Timestamp(maxDate.getTime())).and(TABLE.REGISTDATE.ge(new Timestamp(minDate.getTime()))),
                User.class);
        if (CollectionUtils.isEmpty(list))
            return new ArrayList<>();
        fillAddress(list);
        return list;
    }

    private void fillAddress(List<User> list) {
        list = list.stream()
                .filter(x -> StringUtils.equalsIgnoreCase(x.getUserType(), UserType.NORMAL_USER.getValue()))
                .collect(Collectors.toList());
        for (User user : list) {
            user.setAddress(addressIpService.getAddressStr(user.getAddressId()));
        }
    }
}
