package com.ywj.swiftbuy.service.admin.Impl;

import com.ywj.swiftbuy.dao.model.tables.AdminAccount;
import com.ywj.swiftbuy.service.admin.AdminLoginService;
import com.ywj.swiftbuy.service.common.CommonService;
import com.ywj.swiftbuy.service.utils.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AdminLoginServiceImpl extends CommonService implements AdminLoginService {

    private static final Logger LOG = LoggerFactory.getLogger(AdminLoginServiceImpl.class);

    private static final AdminAccount TABLE = AdminAccount.ADMIN_ACCOUNT;

    @Override
    public boolean login(String name, String password) {
        password = Md5Utils.md5(password);
        return exists(TABLE,
                TABLE.NAME.eq(name).and(TABLE.PASSWORD.eq(password)));
    }


    public static void main(String args[]) {
        System.out.println("password=" + Md5Utils.md5("admin"));
    }
}
