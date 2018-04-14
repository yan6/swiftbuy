package com.ywj.swiftbuy.admin.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ywj.swiftbuy.admin.BusinessBean;
import com.ywj.swiftbuy.model.*;
import com.ywj.swiftbuy.service.common.BusinessService;
import com.ywj.swiftbuy.service.utils.NextPageUrlGenerator;
import com.ywj.swiftbuy.service.utils.NextPageUrlUtils;
import com.ywj.swiftbuy.utils.ListUtils;
import com.ywj.swiftbuy.web.User;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ywj
 */
@Controller
@RequestMapping(value = "/admin/business")
public class BusinessAccountController {
    private static final Logger LOG = LoggerFactory.getLogger(BusinessAccountController.class);

    @Autowired
    private BusinessService businessService;

    /**
     * 新增商家
     *
     * @param business
     * @return
     */
    @RequestMapping(value = "/business/register", method = RequestMethod.POST)
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
                                    @RequestParam(value = "province", required = true) String province,
                                    @RequestParam(value = "city", required = true) String city,
                                    @RequestParam(value = "county", required = true) String county,
                                    @RequestParam(value = "password", required = true) String password,
                                    @RequestParam(value = "phone", required = true) String phone) {
        BusinessBean businessBean = new BusinessBean(name, description, city, county, phone, password, province);
        return businessService.insert(businessBean) ? new SuccessAPIResponse() :
                new FailureAPIResponse("注册商家已经存在");
    }

    /**
     * 修改商家信息
     *
     * @param business
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public APIResponse updateBusinessAccount(@RequestBody BusinessBean business) {
        return businessService.update(business) ? new SuccessAPIResponse() :
                new FailureAPIResponse("修改失败");
    }

    /**
     * 得到所有商家
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public BusinessListItem getAllBusiness(@RequestParam(value = "start", required = false, defaultValue = "0") int start,
                                           @RequestParam(value = "num", required = false, defaultValue = "10") int num) {
        List<BusinessBean> allBusiness = businessService.getAllBusiness();
        if (CollectionUtils.isEmpty(allBusiness))
            return null;
        BusinessListItem businessListItem = new BusinessListItem();
        List<BusinessBean> subList = ListUtils.getSubList(allBusiness, start, num);
        businessListItem.setBusinessList(subList);
        businessListItem.setNextPageUrl(getNextPageUrl(start, num, allBusiness.size()));
        return businessListItem;
    }

    /**
     * 检索商家 1名称(BUSINESS_NAME)，2地区(ADDRESS)
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public BusinessListItem getAllBusiness(@RequestParam(value = "start", required = false, defaultValue = "0") int start,
                                           @RequestParam(value = "num", required = false, defaultValue = "10") int num,
                                           @RequestParam(value = "query", required = true) String query,
                                           @RequestParam(value = "type", required = true) UserQueryType type) {
        List<BusinessBean> businessBeanList = new ArrayList<>();
        switch (type) {
            case BUSINESS_NAME:
                businessBeanList = businessService.getBusinessListByName(query);
                break;
            case ADDRESS:
                businessBeanList = businessService.getBusinessListByAddress(query);
                break;
            default:
                return null;
        }
        if (CollectionUtils.isEmpty(businessBeanList))
            return null;
        BusinessListItem businessListItem = new BusinessListItem();
        List<BusinessBean> subList = ListUtils.getSubList(businessBeanList, start, num);
        businessListItem.setBusinessList(subList);
        businessListItem.setNextPageUrl(getNextPageUrl(start, num, query, type, businessBeanList.size()));
        return businessListItem;
    }

    private String getNextPageUrl(int start, int num, int total) {
        if ((start + num) >= total)
            return null;
        List<NextPageUrlGenerator.Parameter> parameterList = new ArrayList<>();
        parameterList.add(new NextPageUrlGenerator.Parameter("start", String.valueOf(start + num)));
        parameterList.add(new NextPageUrlGenerator.Parameter("num", String.valueOf(num)));
        return NextPageUrlUtils.nextPageUrl(parameterList, "/admin/business", "localhost:8081");
    }

    private String getNextPageUrl(int start, int num, String query, UserQueryType type, int total) {
        if ((start + num) >= total)
            return null;
        List<NextPageUrlGenerator.Parameter> parameterList = new ArrayList<>();
        parameterList.add(new NextPageUrlGenerator.Parameter("start", String.valueOf(start + num)));
        parameterList.add(new NextPageUrlGenerator.Parameter("num", String.valueOf(num)));
        parameterList.add(new NextPageUrlGenerator.Parameter("query", query));
        parameterList.add(new NextPageUrlGenerator.Parameter("type", String.valueOf(type)));
        return NextPageUrlUtils.nextPageUrl(parameterList, "/admin/business/query", "localhost:8081");
    }

}
