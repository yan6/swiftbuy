package com.ywj.swiftbuy.admin.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ywj.swiftbuy.admin.UserType;
import com.ywj.swiftbuy.model.*;
import com.ywj.swiftbuy.service.common.AccountService;
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
import java.util.List;

/**
 * 后台管理之用户管理
 * 1.使用uid检索用户用户
 * 2.使用名称检索用户并展示（分页展示）
 * 3.编辑用户信息功能
 */
@Controller
@RequestMapping(value = "/admin/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AccountService accountService;

    //用户管理
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
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
     * 通过username模糊查询
     * 得到userList
     *
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public UserListItem getList(@RequestParam(value = "start", required = false, defaultValue = "0") int start,
                                @RequestParam(value = "num", required = false, defaultValue = "10") int num,
                                @RequestParam(value = "query", required = true) String query) {
        List<User> userList = accountService.getListByQuery(query);
        if (CollectionUtils.isEmpty(userList))
            return null;
        UserListItem userListItem = new UserListItem();
        List<User> subList = ListUtils.getSubList(userList, start, num);
        userListItem.setUsers(subList);
        userListItem.setNextPageUrl(getNextPageUrl(start, num, query, userList.size()));
        return userListItem;
    }

    /**
     * 通过uid检索单个用户
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "/oneUser", method = RequestMethod.GET)
    @ResponseBody
    public User getList(@RequestParam(value = "uid", required = true) int uid) {
        if (uid < 0)
            return null;
        User user = accountService.getUser(uid);
        if (user == null || !StringUtils.equalsIgnoreCase(user.getUserType(), UserType.NORMAL_USER.getValue()))
            return null;
        return user;
    }

    /**
     * 修改用户信息，uid一定传过来
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public APIResponse edit(@RequestBody User user) {
        return accountService.update(user) ? new SuccessAPIResponse() : new FailureAPIResponse("修改失败，此用户不能被修改");
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


}
