package com.ywj.swiftbuy.constant;

public class RequestParameter {

    public enum ActionType {
        increase, decrease
    }

    public enum Strategy {
        newest, shareCount, playCount, createTime, manSort
    }

    public enum RankListStrategy {
        topCollection, topShare, topPlay, weekly, monthly, historical
    }

    public enum ReplyStrategy {
        date, likeCount
    }

    public static final String DATE = "date";

    public static final String NUM = "num";

    public static final String CATEGORY_NAME = "categoryName";

    public static final String CATEGORY_ID = "categoryId";

    public static final String START = "start";

    public static final String ID = "id";

    public static final String LAST_ID = "lastId";

    public static final String VIDEO_ID = "videoId";

    public static final String NAME = "name";

    public static final String ACTION = "action";

    public static final String STRATEGY = "strategy";

    public static final String DEADLINE_DATE = "deadLineDate";

    public static final String LIBRARY = "library";

    public static final String MODEL = "model";

    public static final String DEVICE_MODEL = "deviceModel";

    public static final String CHANNEL = "last_channel";

    public static final String FROM = "f";

    public static final String UDID_IOS = "u";

    public static final String UDID_ANDROID = "udid";

    public static final String UDID = "udid";

    public static final String UID = "uid";

    public static final String VERSION_CODE_IOS = "vc";

    public static final String VERSION_NAME_IOS = "v";

    public static final String VERSION_CODE_ANDROID = "vc";

    public static final String VERSION_NAME_ANDROID = "vn";

    public static final String SYSTEM_VERSION_CODE_ANDROID = "system_version_code";

    public static final String RANDOM_NUM = "random";

    public static final String TOTAL_NUM = "total";

    public static final String NEEDED = "needed";

    public static final String PHONE_NUMBER = "phoneNumber";

    public static final String ADDRESS = "address";

    public static final String WECHAT_NUMBER = "wechatNumber";

    public static final String TOKEN = "token";

    public static final String CAMPAIGN_ID = "campaignId";

    public static final String TYPE = "type";

    public static final String SOURCE_TYPE = "sourceType";

    public static final String ITEM_TYPE = "itemType";

    public static final String ITEM_ID = "itemId";

    public static final String ITEM_LIST = "itemList";

    public static final String IDENTITY = "identity";

    public static final String QUERY = "query";

    public static final String TAG = "tag";

    public static final String PGC_ID = "pgcId";

    public static final String PGC_NAME = "pgcName";

    public static final String DISTINCT = "distinct";

    public static final String VIDEO_IDS = "vids";

    public static final String TAG_ID = "tagId";

    public static final String UID_LIST = "uidList";

    public static final String TAG_LIST = "tagList";

    public static final String PAGINATION = "pagination";

    public static final String NEED_FILTER = "needFilter";

    public static final String REMOVE_ADS = "removeAds";

    public static final String NID = "nid";

    public static final String SHAREABLE = "shareable";

    public static final String ONLY_NORMAL_VIDEO = "onlyNormalVideo";

    public static final String USER_ID = "userId";

    public static final String USER_TYPE = "userType";

    public static final String FOLLOW = "follow";

    public static final String PAGE = "page";

    public static final String START_ID = "startId";

    public static final String LAST_START_ID = "lastStartId";

    public static final String TOP = "top";

    public static final String DESCRIPTION = "description";

    public static final String REPLY_ID = "replyId";

    public static final String REFRESH = "refresh";

    public static final String REASON = "reason";

    public static final String AD_WEB_PAGE_ID = "webPageId";

    public static final String DURATION = "duration";

    public static final String RESOURCE_TYPE = "resourceType";
}
