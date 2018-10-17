package com.ywj.swiftbuy.service.testcode;

import com.ywj.swiftbuy.dao.model.tables.TestCode;
import com.ywj.swiftbuy.service.common.CommonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestCodeServiceImpl extends CommonService implements TestCodeService {
    private static final TestCode TABLE = TestCode.TEST_CODE;

    @Override
    @Transactional
    public boolean update() {
        return updateField(TABLE, TABLE.AGE, 26, TABLE.ID.eq(1));
    }
}
