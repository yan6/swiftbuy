package com.ywj.swiftbuy.admin.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.ywj.swiftbuy.admin.CategoryBean;
import com.ywj.swiftbuy.model.APIResponse;
import com.ywj.swiftbuy.model.FailureAPIResponse;
import com.ywj.swiftbuy.model.JacksonViews;
import com.ywj.swiftbuy.model.SuccessAPIResponse;
import com.ywj.swiftbuy.service.common.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/admin/category")
public class CategoryAdminController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryAdminController.class);

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public APIResponse insert(@RequestParam(value = "name", required = true) String name,
                              @RequestParam(value = "description", required = true) String description,
                              @RequestParam(value = "image", required = false, defaultValue = "") String image) {
        if (categoryService.exist(name))
            return new FailureAPIResponse("这个分类存在");
        CategoryBean categoryBean = new CategoryBean(name, description, image);
        if (categoryService.insert(categoryBean))
            return new SuccessAPIResponse();
        return new FailureAPIResponse("分类存入失败");
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    @JsonView(JacksonViews.Admin.class)
    public APIResponse update(@RequestParam(value = "id", required = false, defaultValue = "-1") int id,
                              @RequestParam(value = "name", required = false, defaultValue = "") String name,
                              @RequestParam(value = "description", required = false, defaultValue = "") String description,
                              @RequestParam(value = "image", required = false, defaultValue = "") String image) {
        if (categoryService.exist(name))
            return new FailureAPIResponse("这个分类存在");
        CategoryBean categoryBean = new CategoryBean(name, description, image);
        if (categoryService.insert(categoryBean))
            return new SuccessAPIResponse();
        return new FailureAPIResponse("分类存入失败");
    }
}
