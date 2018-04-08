package com.ywj.swiftbuy.admin.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.ywj.swiftbuy.model.APIResponse;
import com.ywj.swiftbuy.model.FailureAPIResponse;
import com.ywj.swiftbuy.model.JacksonViews;
import com.ywj.swiftbuy.model.SuccessAPIResponse;
import com.ywj.swiftbuy.service.common.AddressIpService;
import com.ywj.swiftbuy.web.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/admin")
public class AddressAdminController {
    private static final Logger LOG = LoggerFactory.getLogger(AddressAdminController.class);

    @Autowired
    private AddressIpService addressIpService;

    @RequestMapping(value = "/address/insert", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public APIResponse insert(@RequestParam(value = "city", required = true) String city,
                              @RequestParam(value = "county", required = true) String county) {

        if (addressIpService.exist(city,county))
            return new FailureAPIResponse("城市已存在");
        addressIpService.insert(city, county);
        return new SuccessAPIResponse();
    }

    @RequestMapping(value = "/address/update", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public APIResponse update(@RequestParam(value = "city", required = true) String city,
                              @RequestParam(value = "county", required = true) String county) {
        if (addressIpService.update(new Address(city, county)))
            return new SuccessAPIResponse();
        return new FailureAPIResponse();
    }
}
