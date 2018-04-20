package com.ywj.swiftbuy.service.common.Impl;

import com.ywj.swiftbuy.bean.ShoppingCartBean;
import com.ywj.swiftbuy.bean.ShoppingCartStatus;
import com.ywj.swiftbuy.bean.SimpleShoppingCartBean;
import com.ywj.swiftbuy.dao.model.tables.ShoppingCart;
import com.ywj.swiftbuy.dao.model.tables.records.ShoppingCartRecord;
import com.ywj.swiftbuy.service.common.AccountService;
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

/**
 * @author ywj
 * 和购物车相关
 */

@Service
public class ShoppingCartServiceImpl extends CommonService implements ShoppingCartService {

    private static final Logger LOG = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

    private static final ShoppingCart TABLE = ShoppingCart.SHOPPING_CART;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private AccountService accountService;

    /**
     * 购物车中存在该商品，count+1,不存在则新增数据
     *
     * @param goodsId
     * @param username
     * @return
     */
    @Override
    public boolean add(int goodsId, String username) {
        int uid = accountService.getUidByUsername(username);
        boolean ifExist = exists(TABLE, TABLE.UID.eq(uid).and(TABLE.GOODS_ID.eq(goodsId)));
        if (!ifExist) {
            SimpleShoppingCartBean shoppingCartBean = new SimpleShoppingCartBean();
            shoppingCartBean.setUid(uid);
            shoppingCartBean.setGoodsId(goodsId);
            shoppingCartBean.setStatus(0);
            shoppingCartBean.setCreateTime(new Date());
            shoppingCartBean.setCount(1);
            ShoppingCartRecord record = objectToRecord(shoppingCartBean, ShoppingCartRecord.class);
            return insert(TABLE, record);
        }
        Map<Field<?>, Object> fields = new HashMap<>();
        fields.put(TABLE.UPDATE_TIME, new Date());
        fields.put(TABLE.COUNT, +1);
        return update(TABLE, fields, TABLE.UID.eq(uid).and(TABLE.GOODS_ID.eq(goodsId)));
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
