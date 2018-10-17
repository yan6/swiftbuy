package com.ywj.swiftbuy.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/test")
public class TestController {


    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    @ResponseBody
    public String testAPI() {
        return "success";
    }
}
