package com.ywj.swiftbuy.admin.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ywj.swiftbuy.admin.UserType;
import com.ywj.swiftbuy.model.JacksonViews;
import com.ywj.swiftbuy.service.admin.ToolService;
import com.ywj.swiftbuy.web.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author ywj
 * <p>
 * <p>
 * 登录地址：http://localhost:8081/swiftbuy/admin/index.html
 */
@Controller
@RequestMapping(value = "/admin")
public class ToolController {

    private static final Logger LOG = LoggerFactory.getLogger(ToolController.class);

    @Autowired
    private ToolService toolService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public String testRegister(@RequestParam(value = "username", required = true) String username,
                               @RequestParam(value = "password", required = true) String password,
                               @RequestParam(value = "phone", required = true) String phone) {

        System.out.println("username=" + username + "password=" + password + "phone=" + phone);
        User user = new User(username, password, phone);
        user.setUserType(UserType.NORMAL_USER.getValue());
        if (toolService.insert(user) > 0)
            return "success";
        else
            return "failure";

    }
}
