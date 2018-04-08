package com.ywj.swiftbuy.webapp.controller;


import com.ywj.swiftbuy.admin.UserType;
import com.ywj.swiftbuy.model.APIResponse;
import com.ywj.swiftbuy.model.FailureAPIResponse;
import com.ywj.swiftbuy.model.SuccessAPIResponse;
import com.ywj.swiftbuy.service.common.AccountService;
import com.ywj.swiftbuy.service.utils.Md5Utils;
import com.ywj.swiftbuy.web.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author ywj
 * <p>
 * 1.登录注册接口
 * 2.个人主页接口
 * 3.修改名称（账号名）
 * 4.修改密码
 * 5.找回密码
 */
@Controller
@RequestMapping("/api/account")
public class AccountController {

    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public User login(@RequestParam(value = "username", required = true) String username,
                      @RequestParam(value = "password", required = true) String password) {
        User user = new User();
        if (accountService.login(username, password)) {
            user.setUsername(username);
            return user;
        } else {
            return null;
        }
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public APIResponse register(@RequestParam(value = "username", required = true) String username,
                                @RequestParam(value = "password", required = true) String password,
                                @RequestParam(value = "phone", required = true) String phone) {
        User user = new User(username, Md5Utils.md5(password), phone);
        user.setUserType(UserType.NORMAL_USER.getValue());
        //重名
        if (accountService.insert(user) > 0)
            return new SuccessAPIResponse();
        else
            return new FailureAPIResponse();
    }

    //用户主页
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    @ResponseBody
    public User getUserHomePage(@RequestParam(value = "username", required = true) String username) {
        //如果返回是个空，那么提示改用户被管理员删除
        return accountService.getUser(username);
    }

    //编辑个人信息
    //密码不让直接
    @RequestMapping(value = "/editUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public APIResponse editUserInfo(@RequestBody User user) {
        return accountService.update(user) ? new SuccessAPIResponse() : new FailureAPIResponse("修改失败，请联系管理员");
    }

    //修改名称
    @RequestMapping(value = "/editName", method = RequestMethod.POST)
    @ResponseBody
    public APIResponse editName(@RequestParam(value = "uid", required = true) int uid,
                                @RequestParam(value = "username", required = true) String username) {
        return accountService.updateUsername(uid, username) ? new SuccessAPIResponse() : new FailureAPIResponse("修改名称失败");
    }

    //修改密码
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public APIResponse updatePassword(@RequestParam(value = "uid", required = true) int uid,
                                      @RequestParam(value = "oldPassword", required = true) String oldPassword,
                                      @RequestParam(value = "newPassword", required = true) String newPassword) {
        return accountService.updatePassword(uid, oldPassword, newPassword) ? new SuccessAPIResponse() : new FailureAPIResponse("原密码错误");
    }

    //忘记密码，找回
    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    @ResponseBody
    public APIResponse forgetPassword(@RequestParam(value = "uid", required = true) int uid,
                                      @RequestParam(value = "newPassword", required = true) String newPassword) {
        User user = accountService.getUser(uid);
        if (user == null || user.getUserType() == null) {
            return new FailureAPIResponse("此用户不存在，请先注册");
        }
        return accountService.forgetPassword(uid, newPassword, user.getUserType()) ? new SuccessAPIResponse() : new FailureAPIResponse("用户不存在");
    }


}
