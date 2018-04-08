package com.ywj.swiftbuy.service.common;

import com.ywj.swiftbuy.bean.GoodsBean;
import com.ywj.swiftbuy.bean.HistoryBean;

import java.util.List;

public interface HistoryService {

    List<GoodsBean> getCommonRecommendList(int num);

    List<GoodsBean> getSpecialRecommendList(int uid, int num);

    List<Integer> getCommonHistory(int num);

    List<Integer> getGoodsIdList(int uid,int num);

    boolean insert(HistoryBean history);
}
