package com.ywj.swiftbuy.service.common.Impl;

import com.ywj.swiftbuy.admin.CategoryBean;
import com.ywj.swiftbuy.dao.model.tables.Category;
import com.ywj.swiftbuy.dao.model.tables.records.CategoryRecord;
import com.ywj.swiftbuy.service.common.CategoryService;
import com.ywj.swiftbuy.service.common.CommonService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends CommonService implements CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private static final Category TABLE = Category.CATEGORY;

    @Override
    public int getIdByName(String categoryName) {
        return selectOneValue(TABLE,
                TABLE.ID,
                TABLE.NAME.eq(categoryName));
    }

    @Override
    public boolean exist(String name) {
        return exists(TABLE, TABLE.NAME.eq(name));
    }

    @Override
    public boolean insert(CategoryBean categoryBean) {
        CategoryRecord record = objectToRecord(categoryBean, CategoryRecord.class);
        return insert(TABLE, record);
    }

    @Override
    public boolean update(CategoryBean categoryBean) {
        if (!exist(categoryBean.getName())&& StringUtils.isNotEmpty(categoryBean.getName())&&StringUtils.isNotEmpty(categoryBean.getDescription())) {
            return insert(categoryBean);
        } else {
            CategoryRecord record = objectToRecord(categoryBean, CategoryRecord.class);
            return update(TABLE, record, TABLE.ID.eq(categoryBean.getId()));
        }
    }

}
