package com.ywj.swiftbuy.service.common.Impl;

import com.ywj.swiftbuy.bean.GoodsBean;
import com.ywj.swiftbuy.bean.HistoryBean;
import com.ywj.swiftbuy.dao.model.tables.History;
import com.ywj.swiftbuy.dao.model.tables.SearchHistory;
import com.ywj.swiftbuy.dao.model.tables.records.HistoryRecord;
import com.ywj.swiftbuy.service.common.CommonService;
import com.ywj.swiftbuy.service.common.GoodsService;
import com.ywj.swiftbuy.service.common.HistoryService;
import com.ywj.swiftbuy.utils.ListUtils;
import com.ywj.swiftbuy.web.HistoryType;
import org.apache.commons.collections.CollectionUtils;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ywj
 */
@Service
public class HistoryServiceImpl extends CommonService implements HistoryService {

    private static final Logger LOG = LoggerFactory.getLogger(HistoryServiceImpl.class);

    private static final History HISTORY = History.HISTORY;

    private static final SearchHistory SEARCH_HISTORY = SearchHistory.SEARCH_HISTORY;

    @Autowired
    private GoodsService goodsService;


    /**
     * 推荐商品列表聚合(大众推荐)
     * 策略是：取搜索历史前三的商品,如果没有暂时给出一个默认的
     * 所有用户数据：50%搜索+50%浏览过或买过
     *
     * @param num
     */
    @Override
    public List<GoodsBean> getCommonRecommendList(int num) {
        int num1 = num / 2;
        int num2 = num - num1;
        //在所有搜索过的商品，取前10
        List<Integer> goodsIdList = getJooqContext()
                .select()
                .from(SEARCH_HISTORY)
                .orderBy(SEARCH_HISTORY.COUNT.desc())
                .limit(20)
                .fetch(SEARCH_HISTORY.GOODS_ID);
        System.out.println("getRecommendList id=" + goodsIdList.toString());
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        if (CollectionUtils.isEmpty(goodsIdList)) {
            //例子中只有1个 TODO
            goodsBeanList = goodsService.getDefaultGoods();
        } else {
            //取8个
            goodsBeanList = goodsIdList.stream()
                    .filter(Objects::nonNull)
                    .map(x -> goodsService.getGoodsById(x))
                    .filter(Objects::nonNull)
                    .limit(num1)
                    .collect(Collectors.toList());
        }
        goodsBeanList.addAll(commonHistoryList(num2));
        return goodsBeanList;
    }

    @Override
    public List<GoodsBean> getSpecialRecommendList(int uid, int num) {
        //根据该用户的购买历史或浏览历史，续接所有用户的购买数据
        List<Integer> goodsIdList = getGoodsIdList(uid, num);
        if (CollectionUtils.isEmpty(goodsIdList))
            return new ArrayList<>();
        return goodsService.getGoodsBeanList(goodsIdList);
    }

    @Override
    public List<Integer> getGoodsIdList(int uid, int num) {
        List<Integer> goodsIdList = getJooqContext()
                .select()
                .from(HISTORY)
                .where(HISTORY.UID.eq(uid))
                .orderBy(HISTORY.DATE.desc())
                .limit(num)
                .fetch(HISTORY.GOODS_ID);
        if (CollectionUtils.isEmpty(goodsIdList)) {
            return getCommonHistory(num);
        } else if (goodsIdList.size() < num) {
            List<Integer> subList = ListUtils.getSubList(getCommonHistory(0), 0, num - goodsIdList.size());
            if (CollectionUtils.isEmpty(subList))
                return goodsIdList;
            goodsIdList.addAll(subList);
            Collections.shuffle(goodsIdList, new Random(System.currentTimeMillis()));
            return goodsIdList;
        }
        return goodsIdList;
    }

    public boolean insert(HistoryBean history) {
        HistoryRecord record = objectToRecord(history, HistoryRecord.class);
        return insert(HISTORY, record);
    }

    /**
     * 商品购买量前num名
     *
     * @param num
     * @return
     */
    @Override
    public List<Integer> getCommonHistory(int num) {
        Result<Record> result = getJooqContext()
                .select(HISTORY.GOODS_ID.count().as("total"))
                .select(HISTORY.GOODS_ID.as("goods_id"))
                .select(HISTORY.TYPE)
                .from(HISTORY)
                .where(HISTORY.TYPE.eq(HistoryType.buy.getValue()))
                .groupBy(HISTORY.GOODS_ID)
                .orderBy(HISTORY.GOODS_ID.count().desc())
                .limit(num)
                .fetch();
        return generateGoodsList(result);
    }

    private List<Integer> generateGoodsList(Result<Record> result) {
        List<Integer> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(result)) {
            list.add(1);
            list.add(2);
            list.add(3);
            list.add(4);
            list.add(5);
            list.add(6);
            list.add(7);
            list.add(8);
        }
        for (Record record : result) {
            int goods_id = record.getValue("goods_id", Integer.class);
            list.add(goods_id);
        }
        return list;
    }


    /**
     * 排名前7的商品在所有用户浏览记录中
     *
     * @return
     */
    private List<GoodsBean> commonHistoryList(int num) {
        Result<Record> result = getJooqContext()
                .select(HISTORY.GOODS_ID.count().as("total"))
                .select(HISTORY.GOODS_ID.as("goods_id"))
                .from(HISTORY)
                .where(HISTORY.TYPE.eq(HistoryType.view.getValue()))
                .groupBy(HISTORY.GOODS_ID)
                .orderBy(HISTORY.GOODS_ID.count().desc())
                .limit(20)
                .fetch();
        return generateGoodsBean(generateGoodsList(result), num);
    }

    private List<GoodsBean> generateGoodsBean(List<Integer> goodsIdList, int num) {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        if (CollectionUtils.isEmpty(goodsIdList)) {
            //例子中只有1个
            goodsBeanList = goodsService.getDefaultGoods();
        } else {
            //取4个
            goodsBeanList = goodsIdList.stream()
                    .filter(Objects::nonNull)
                    .map(x -> goodsService.getGoodsById(x))
                    .filter(Objects::nonNull)
                    .limit(num)
                    .collect(Collectors.toList());
        }
        return goodsBeanList;
    }
}
