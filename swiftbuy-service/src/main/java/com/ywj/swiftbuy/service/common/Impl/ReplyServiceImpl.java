package com.ywj.swiftbuy.service.common.Impl;

import com.ywj.swiftbuy.bean.ReplyBean;
import com.ywj.swiftbuy.bean.Status;
import com.ywj.swiftbuy.dao.model.tables.Reply;
import com.ywj.swiftbuy.service.common.CommonService;
import com.ywj.swiftbuy.service.common.ReplyService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReplyServiceImpl extends CommonService implements ReplyService {

    private static final Logger LOG = LoggerFactory.getLogger(ReplyServiceImpl.class);

    private static final Reply TABLE = Reply.REPLY;

    @Override
    public ReplyBean get(int id) {
        return selectOneRecord(TABLE,
                TABLE.ID.eq(id).and(TABLE.STATUS.eq(Status.online.getValue())),
                ReplyBean.class);
    }

    @Override
    public List<ReplyBean> getReplyBeanList(int goodsId) {
        List<ReplyBean> list = select(TABLE,
                TABLE.GOODS_ID.eq(goodsId).and(TABLE.STATUS.eq(Status.online.getValue())),
                TABLE.CREATE_TIME.desc(),
                ReplyBean.class);
        if (CollectionUtils.isEmpty(list))
            return new ArrayList<>();
        return list;
    }
}
