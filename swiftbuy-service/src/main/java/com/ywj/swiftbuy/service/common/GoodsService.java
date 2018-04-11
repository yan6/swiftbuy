package com.ywj.swiftbuy.service.common;

import com.ywj.swiftbuy.bean.GoodsBean;
import com.ywj.swiftbuy.bean.SortStrategy;

import java.util.List;

public interface GoodsService {

    List<GoodsBean> getGoodsListByBusinessId(int businessId, int categoryId);

    List<GoodsBean> getGoodsListInCity(List<Integer> businessIdList, int categoryId, SortStrategy strategy);

    List<GoodsBean> getDefaultGoods();

    GoodsBean getGoodsById(int id);

    boolean exist(int businessId,String name);

    boolean insert(GoodsBean goods);

    boolean update(GoodsBean goods);

    List<GoodsBean> getGoodsBeanList(List<Integer> idList);

    int getRemainCount(int id);

    boolean updateRemainCount(int id, int count);

    List<GoodsBean> getDefaultShowList(int num);
}
