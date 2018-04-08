package com.ywj.swiftbuy.service.common;

import com.ywj.swiftbuy.web.User;

import java.util.List;

public interface AccountService {

    boolean login(String username, String password);

    int getUidByUsername(String username);

    int insert(User user);

    boolean update(User user);

    User getUser(String username);

    boolean updateUsername(int uid, String username);

    User getUser(int uid);

    boolean updatePassword(int uid, String oldPassword, String newPassword);

    boolean forgetPassword(int uid, String newPassword, String userType);

    List<User> getListByQuery(String query);
}
