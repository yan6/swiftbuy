package com.ywj.swiftbuy.admin.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ywj.swiftbuy.admin.UserType;
import com.ywj.swiftbuy.model.*;
import com.ywj.swiftbuy.service.common.AccountService;
import com.ywj.swiftbuy.service.common.AddressIpService;
import com.ywj.swiftbuy.service.utils.Md5Utils;
import com.ywj.swiftbuy.service.utils.NextPageUrlGenerator;
import com.ywj.swiftbuy.service.utils.NextPageUrlUtils;
import com.ywj.swiftbuy.utils.ListUtils;
import com.ywj.swiftbuy.web.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台管理之用户管理
 * 1.新增用户
 * 2.根据各种方式检索用户并展示（分页展示）
 * 3.操作用户信息
 * 4.
 */
@Controller
@RequestMapping(value = "/admin/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private AddressIpService addressIpService;

    /**
     *  新增用户
     */
    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public APIResponse insert(@RequestParam(value = "username", required = false, defaultValue = "") String username,
                              @RequestParam(value = "password", required = false, defaultValue = "") String password,
                              @RequestParam(value = "phone", required = false, defaultValue = "") String phone,
                              @RequestParam(value = "userType", required = false, defaultValue = "") String userType) {
        User user = new User(username, Md5Utils.md5(password), phone);
        user.setUserType(UserType.NORMAL_USER.getValue());
        //重名
        if (accountService.insert(user) > 0)
            return new SuccessAPIResponse();
        else
            return new FailureAPIResponse();
    }

    /**
     * 修改用户信息，uid一定传过来
     * 修改 描述，电话，地址（不支持修改用户名和密码）
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public APIResponse update(@RequestBody User user) {
        user.setPassword(Md5Utils.md5(user.getPassword()));
        //重名
        if (accountService.update(user))
            return new SuccessAPIResponse();
        else
            return new FailureAPIResponse();
    }

    /**
     * 用户基本信息管理
     * 1.UID 2.REGISTER 3.USER_NAME
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public UserListItem getList(@RequestParam(value = "start", required = false, defaultValue = "0") int start,
                                @RequestParam(value = "num", required = false, defaultValue = "10") int num,
                                @RequestParam(value = "query", required = false, defaultValue = "") String query,
                                @RequestParam(value = "minDate", required = false, defaultValue = "") Date minDate,
                                @RequestParam(value = "maxDate", required = false, defaultValue = "") Date maxDate,
                                @RequestParam(value = "uid", required = false, defaultValue = "-1") int uid,
                                @RequestParam(value = "type", required = true) UserQueryType type) {
        List<User> userList = getUserList(query, minDate, maxDate, uid, type);
        if (CollectionUtils.isEmpty(userList))
            return null;
        UserListItem userListItem = new UserListItem();
        List<User> subList = ListUtils.getSubList(userList, start, num);
        userListItem.setUsers(subList);
        userListItem.setNextPageUrl(getNextPageUrl(start, num, query, userList.size()));
        return userListItem;
    }

    private String getNextPageUrl(int start, int num, String query, int total) {
        if ((start + num) >= total)
            return null;
        List<NextPageUrlGenerator.Parameter> parameterList = new ArrayList<>();
        parameterList.add(new NextPageUrlGenerator.Parameter("start", String.valueOf(start + num)));
        parameterList.add(new NextPageUrlGenerator.Parameter("num", String.valueOf(num)));
        parameterList.add(new NextPageUrlGenerator.Parameter("query", query));
        return NextPageUrlUtils.nextPageUrl(parameterList, "/admin/user/query", "localhost:8081");
    }

    private List<User> getUserList(String query, Date minDate, Date maxDate, int uid, UserQueryType type) {
        List<User> userList = new ArrayList<>();
        switch (type) {
            case USER_NAME:
                if (StringUtils.isBlank(query) || StringUtils.isEmpty(query))
                    return null;
                userList = accountService.getListByQuery(query);
                break;
            case REGISTER:
                if (minDate == null || maxDate == null || maxDate.getTime() < minDate.getTime())
                    return null;
                userList = accountService.getUserListByRegisterDate(minDate, maxDate);
                break;
            case UID:
                if (uid < 0)
                    return null;
                User user = accountService.getUser(uid);
                if (user == null || !StringUtils.equalsIgnoreCase(user.getUserType(), UserType.NORMAL_USER.getValue()))
                    return null;
                user.setAddress(addressIpService.getAddressStr(user.getAddressId()));
                userList.add(user);
                break;
            default:
                return null;
        }
        return userList;
    }

}
