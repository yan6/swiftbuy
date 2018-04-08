package com.ywj.swiftbuy.service.common.Impl;

import com.ywj.swiftbuy.bean.GoodsBean;
import com.ywj.swiftbuy.bean.SortStrategy;
import com.ywj.swiftbuy.bean.Status;
import com.ywj.swiftbuy.dao.model.tables.Goods;
import com.ywj.swiftbuy.dao.model.tables.records.GoodsRecord;
import com.ywj.swiftbuy.service.common.CommonService;
import com.ywj.swiftbuy.service.common.GoodsService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ywj
 */

@Service
public class GoodsServiceImpl extends CommonService implements GoodsService {

    private static final Logger LOG = LoggerFactory.getLogger(GoodsServiceImpl.class);

    private static final Goods TABLE = Goods.GOODS;

    @Override
    public GoodsBean getGoodsById(int id) {
        GoodsRecord goodsRecord = selectOneRecord(TABLE, TABLE.ID.eq(id).and(TABLE.STATUS.eq(Status.online.getValue())));
        GoodsBean goodsBean = recordToObject(goodsRecord, GoodsBean.class);
        if (goodsRecord == null || goodsBean == null)
            return null;
        return goodsBean;
    }

    @Override
    public List<GoodsBean> getGoodsBeanList(List<Integer> idList) {
        return idList.stream().map(this::getGoodsById).collect(Collectors.toList());
    }

    /**
     * 默认上架时间排序，价格排序，销量排序，距离排序
     *
     * @param businessId
     * @param categoryId
     * @return
     */
    @Override
    public List<GoodsBean> getGoodsListByBusinessId(int businessId, int categoryId) {
        return select(TABLE,
                TABLE.BUSINESS_ID.eq(businessId).and(TABLE.CATEGORY_ID.eq(categoryId)),
                GoodsBean.class);
    }

    /**
     * 查询一个城市，某个分类下所有商品 按sortStrategy排序
     */
    @Override
    public List<GoodsBean> getGoodsListInCity(List<Integer> businessIdList, int categoryId, SortStrategy strategy) {
        //后面如果有空的话使用redis缓存
        List<GoodsBean> recentGoods = new ArrayList<>();
        businessIdList.stream().forEach(x -> {
            List<GoodsBean> oneBusinessGood = getGoodsListByBusinessId(x, categoryId);
            if (CollectionUtils.isNotEmpty(oneBusinessGood))
                recentGoods.addAll(oneBusinessGood);
        });

        switch (strategy) {
            case date:
                recentGoods.sort((o1, o2) -> o2.getUpdateTime().compareTo(o1.getUpdateTime()));
                break;
            case soldCount:
                break;
            case price:
                break;
            case range:
                break;
            default:
        }
        return recentGoods;
    }

    /**
     * 判断某个商家是否有改商品
     *
     * @return
     */
    @Override
    public boolean exist(int businessId, String name) {
        return exists(TABLE, TABLE.BUSINESS_ID.eq(businessId).and(TABLE.NAME.eq(name)));
    }

    @Override
    public boolean insert(GoodsBean goods) {
        GoodsRecord goodsRecord = objectToRecord(goods, GoodsRecord.class);
        return insert(TABLE, goodsRecord);
    }

    @Override
    public boolean update(GoodsBean goods) {
        GoodsRecord goodsRecord = objectToRecord(goods, GoodsRecord.class);
        return update(TABLE, goodsRecord, TABLE.ID.eq(goods.getId()));
    }

    @Override
    public List<GoodsBean> getDefaultGoods() {
        GoodsBean goodsBean = new GoodsBean();
        goodsBean.setImage("http://xxx");
        goodsBean.setName("JAVA讲义");
        goodsBean.setDescription("测试使用");
        goodsBean.setPriceBase(66);
        goodsBean.setRemainCount(1);
        goodsBean.setCategoryId(1);
        List<GoodsBean> list = new ArrayList<>();
        list.add(goodsBean);
        return list;
    }

    //+-1商品库存
    @Override
    public boolean updateRemainCount(int id, int count) {
        int remainCount = getRemainCount(id) + count;
        return updateField(TABLE,TABLE.REMAIN_COUNT,remainCount,TABLE.ID.eq(id));
    }

    //获取某件商品的库存
    @Override
    public int getRemainCount(int id) {
        Integer remainCount = selectOneValue(TABLE, TABLE.REMAIN_COUNT, TABLE.ID.eq(id));
        if (remainCount == null)
            return 0;
        else
            return remainCount;
    }
}
