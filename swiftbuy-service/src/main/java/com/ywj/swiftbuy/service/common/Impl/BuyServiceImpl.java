package com.ywj.swiftbuy.service.common.Impl;

import com.ywj.swiftbuy.dao.model.tables.Buy;
import com.ywj.swiftbuy.dao.model.tables.records.BuyRecord;
import com.ywj.swiftbuy.model.BuyBean;
import com.ywj.swiftbuy.service.common.BuyService;
import com.ywj.swiftbuy.service.common.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BuyServiceImpl extends CommonService implements BuyService {

    private static final Logger LOG = LoggerFactory.getLogger(BuyServiceImpl.class);

    private static final Buy TABLE = Buy.BUY;

    //生成订单
    @Override
    public boolean insert(BuyBean buy) {
        BuyRecord record = objectToRecord(buy, BuyRecord.class);
        return insert(TABLE, record);
    }
}
