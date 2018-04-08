package com.ywj.swiftbuy.service.common.Impl;

import com.ywj.swiftbuy.bean.GoodsBean;
import com.ywj.swiftbuy.bean.Status;
import com.ywj.swiftbuy.dao.model.tables.Goods;
import com.ywj.swiftbuy.service.common.CommonService;
import com.ywj.swiftbuy.service.common.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl extends CommonService implements SearchService {

    private static final Logger LOG = LoggerFactory.getLogger(SearchServiceImpl.class);

    private static final Goods GOODS = Goods.GOODS;

    @Override
    public List<GoodsBean> searchGoods(String query) {
        query = "%" + query + "%";
        return select(GOODS, GOODS.STATUS.eq(Status.online.getValue())
                        .and(GOODS.NAME.like(query).or(GOODS.DESCRIPTION.eq(query))), GoodsBean.class);
    }
}
