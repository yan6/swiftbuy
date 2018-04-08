package com.ywj.swiftbuy.service.common.Impl;

import com.ywj.swiftbuy.bean.PopularGoodsBean;
import com.ywj.swiftbuy.bean.Status;
import com.ywj.swiftbuy.dao.model.tables.PopularGoods;
import com.ywj.swiftbuy.service.common.CommonService;
import com.ywj.swiftbuy.service.common.PopularGoodsService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PopularGoodsServiceImpl extends CommonService implements PopularGoodsService {

    private static final Logger LOG = LoggerFactory.getLogger(PopularGoodsServiceImpl.class);

    private static final PopularGoods TABLE = PopularGoods.POPULAR_GOODS;

    /**
     * 管理员自定义一批流行商品
     */



    @Override
    public List<Integer> getDefaultPopularGoodsId(int num) {
        List<PopularGoodsBean> list = select(TABLE, TABLE.STATUS.eq(Status.online.getValue()), TABLE.CREATE_TIME.desc(), 4, PopularGoodsBean.class);
        if (CollectionUtils.isEmpty(list))
            return new ArrayList<>();
        return list.stream()
                .filter(Objects::nonNull)
                .map(x -> {
                    return x.getGoodsId();
                }).limit(num).collect(Collectors.toList());
    }
}
