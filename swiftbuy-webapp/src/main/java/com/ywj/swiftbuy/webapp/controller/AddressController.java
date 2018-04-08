package com.ywj.swiftbuy.webapp.controller;

import com.ywj.swiftbuy.model.APIResponse;
import com.ywj.swiftbuy.model.SuccessAPIResponse;
import com.ywj.swiftbuy.service.common.AddressIpService;
import com.ywj.swiftbuy.web.Address;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ywj
 * <p>
 * 1.获取定位地址
 * 2.获取某个城市的下属所有地区
 */
@Controller
@RequestMapping("/api/address")
public class AddressController {

    private static final Logger LOG = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressIpService addressIpService;

    @RequestMapping(value = "/getCurrentCity", method = RequestMethod.GET)
    @ResponseBody
    public APIResponse getCurrentIdCity() {
        try {
            String currentCity = addressIpService.getCurrentIdAddress();
            return new SuccessAPIResponse(currentCity);
        } catch (Exception e) {
            LOG.warn("time out in get address during");
            return new SuccessAPIResponse("北京市");
        }
    }

    @RequestMapping(value = "/getCountyList", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getCountyList(@RequestParam(value = "city", required = false, defaultValue = "北京市") String city) throws UnsupportedEncodingException {
        List<Address> countyList = addressIpService.getCountyList(city);
        if (CollectionUtils.isEmpty(countyList))
            return new ArrayList<>();
        return countyList.stream().map(x -> x.getCounty()).collect(Collectors.toList());
    }
}
