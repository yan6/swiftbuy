package com.ywj.swiftbuy.admin.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ywj.swiftbuy.model.APIResponse;
import com.ywj.swiftbuy.model.FailureAPIResponse;
import com.ywj.swiftbuy.model.JacksonViews;
import com.ywj.swiftbuy.model.SuccessAPIResponse;
import com.ywj.swiftbuy.service.admin.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/admin/login")
public class LoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public APIResponse login(@RequestParam(value = "name",required = true)String name,
                             @RequestParam(value = "password",required = true)String password){
        return adminLoginService.login(name,password)?new SuccessAPIResponse():new FailureAPIResponse("用户名或密码错误");
    }


}
