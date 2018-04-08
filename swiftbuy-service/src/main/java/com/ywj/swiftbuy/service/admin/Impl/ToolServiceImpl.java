package com.ywj.swiftbuy.service.admin.Impl;

import com.ywj.swiftbuy.dao.model.tables.records.UserRecord;
import com.ywj.swiftbuy.service.admin.ToolService;
import com.ywj.swiftbuy.service.common.CommonService;
import com.ywj.swiftbuy.web.User;
import org.jooq.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ToolServiceImpl extends CommonService implements ToolService {

    private static final Logger LOG = LoggerFactory.getLogger(ToolServiceImpl.class);

    private static final com.ywj.swiftbuy.dao.model.tables.User USER = com.ywj.swiftbuy.dao.model.tables.User.USER;

    @Override
    public int insert(User user) {
        Record record = insertWithReturning(USER, objectToRecord(user, UserRecord.class), USER.UID);
        if (record==null)
            return -1;
        return record.getValue(USER.UID);
    }
}
