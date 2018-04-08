package com.ywj.swiftbuy.admin.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ywj.swiftbuy.admin.BusinessBean;
import com.ywj.swiftbuy.model.APIResponse;
import com.ywj.swiftbuy.model.FailureAPIResponse;
import com.ywj.swiftbuy.model.JacksonViews;
import com.ywj.swiftbuy.model.SuccessAPIResponse;
import com.ywj.swiftbuy.service.common.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author ywj
 *
 */
@Controller
@RequestMapping(value = "/admin/business")
public class BusinessAccountController {
    private static final Logger LOG = LoggerFactory.getLogger(BusinessAccountController.class);

    @Autowired
    private BusinessService businessService;

    @RequestMapping(value = "/business/register", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public APIResponse register(@RequestBody BusinessBean business) {
        return businessService.insert(business) ? new SuccessAPIResponse() :
                new FailureAPIResponse("注册商家已经存在");
    }

    @RequestMapping(value = "/testRegister", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public APIResponse testRegister(@RequestParam(value = "name", required = true) String name,
                                    @RequestParam(value = "description", required = true) String description,
                                    @RequestParam(value = "city", required = true) String city,
                                    @RequestParam(value = "county", required = true) String county,
                                    @RequestParam(value = "password", required = true) String password,
                                    @RequestParam(value = "phone", required = true) String phone) {
        BusinessBean businessBean = new BusinessBean(name, description, city, county, phone, password);
        return businessService.insert(businessBean) ? new SuccessAPIResponse() :
                new FailureAPIResponse("注册商家已经存在");
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public APIResponse updateBusinessAccount(@RequestBody BusinessBean business) {
        return businessService.update(business) ? new SuccessAPIResponse() :
                new FailureAPIResponse("修改失败");
    }
}
