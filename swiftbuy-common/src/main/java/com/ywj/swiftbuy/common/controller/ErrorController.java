package com.ywj.swiftbuy.common.controller;

import com.ywj.swiftbuy.common.model.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author ywj
 */

@Controller
@RequestMapping(value = "/error")
public class ErrorController {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorController.class);

    @RequestMapping(value = "/500", produces = "application/json;charset=utf-8")
    @ResponseBody
    public APIResponse response500(){
        return new APIResponse(500,"Internal Error");
    }

    @RequestMapping(value = "/404", produces = "application/json; charset=utf-8")
    @ResponseBody
    public APIResponse response404 () {
        return new APIResponse(404, "Resource Not Found");
    }

    @RequestMapping(value = "/400", produces = "application/json; charset=utf-8")
    @ResponseBody
    public APIResponse response400 () {
        return new APIResponse(400, "Bad Request");
    }

}
