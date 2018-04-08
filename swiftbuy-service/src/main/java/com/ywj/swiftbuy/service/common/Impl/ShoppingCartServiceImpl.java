package com.ywj.swiftbuy.service.common.Impl;

import com.ywj.swiftbuy.bean.ShoppingCartBean;
import com.ywj.swiftbuy.bean.ShoppingCartStatus;
import com.ywj.swiftbuy.dao.model.tables.ShoppingCart;
import com.ywj.swiftbuy.dao.model.tables.records.ShoppingCartRecord;
import com.ywj.swiftbuy.service.common.CommonService;
import com.ywj.swiftbuy.service.common.GoodsService;
import com.ywj.swiftbuy.service.common.ShoppingCartService;
import org.apache.commons.collections.CollectionUtils;
import org.jooq.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShoppingCartServiceImpl extends CommonService implements ShoppingCartService {

    private static final Logger LOG = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

    private static final ShoppingCart TABLE = ShoppingCart.SHOPPING_CART;

    @Autowired
    private GoodsService goodsService;

    /**
     * 购物车中存在该商品，count+1,不存在则新增数据
     *
     * @param shoppingCart
     * @return
     */
    @Override
    public boolean add(ShoppingCartBean shoppingCart) {
        ShoppingCartBean shoppingCartBean = selectOneRecord(TABLE,
                TABLE.UID.eq(shoppingCart.getUid()).and(TABLE.GOODS_ID.eq(shoppingCart.getGoodsId())).and(TABLE.STATUS.eq(ShoppingCartStatus.ONLINE.getValue())),
                ShoppingCartBean.class);
        if (shoppingCartBean == null)
            return insert(TABLE, objectToRecord(shoppingCart, ShoppingCartRecord.class));
        Map<Field<?>, Object> fields = new HashMap<>();
        fields.put(TABLE.UPDATE_TIME, new Date());
        fields.put(TABLE.COUNT, shoppingCartBean.getCount() + 1);
        return update(TABLE, fields, TABLE.ID.eq(shoppingCartBean.getId()));
    }

    /**
     * 从购物车中移除
     */
    @Override
    public boolean updateStatus(int id) {
        return updateField(TABLE,
                TABLE.STATUS, ShoppingCartStatus.DELETE.getValue(),
                TABLE.ID.eq(id));
    }

    /**
     * 获取一个用户的购物列表
     */
    @Override
    public List<ShoppingCartBean> getListByUid(int uid) {
        List<ShoppingCartBean> list = select(TABLE,
                TABLE.UID.eq(uid).and(TABLE.STATUS.eq(ShoppingCartStatus.ONLINE.getValue())),
                TABLE.CREATE_TIME.desc(),
                ShoppingCartBean.class);
        if (CollectionUtils.isEmpty(list))
            return new ArrayList<>();
        //TODO 这个地方可能会有null问题
        list.stream().filter(Objects::nonNull)
                .forEach(x -> x.setGoods(goodsService.getGoodsById(x.getGoodsId())));
        return list;
    }
}
