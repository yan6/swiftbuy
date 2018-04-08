package com.ywj.swiftbuy.service.common;

import com.ywj.swiftbuy.bean.ReplyBean;

import java.util.List;

public interface ReplyService {
    ReplyBean get(int id);

    List<ReplyBean> getReplyBeanList(int goodsId);
}
