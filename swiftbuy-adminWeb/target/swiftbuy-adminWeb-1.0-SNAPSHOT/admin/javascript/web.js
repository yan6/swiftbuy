'use strict';
var swiftbuy = angular.module('swiftbuy', ['ng', 'ngRoute', 'ui.sortable', 'swiftbuyServices', 'swiftbuyControllers', 'commonDirectives']);
swiftbuy.//constant('API_HOST', "http://localhost:7000").
constant('baseInfo', {
    pageSize: 5,
    baseUrl: "/api/admin",
    //baseUrl: "http://10.19.25.12:8080/baobab-admin/api/admin",
    // listType
    typeAdTrack: "adTracks",
    typeAdTrackAdvancing: "adTrackAdvancing",
    typeVideo: "videos",
    typeIssue: "issues",
    typeTopic: "specialTopics",
    typeLightTopic: "lightTopics",
    typeAuthor: "authors",
    typeCard: "cards",
    typeFeedCard: "feedCards",
    typeCardGroupConfig: "cardGroupConfigs",
    typeVideoTag: "videoTags",
    typeTag: "tags",
    typeAdControl: "adControls",
    typeCommonConfig: "commonConfig",
    typeCandidate: "candidates",
    typeForums: "forums",
    typeReply: "replies",
    typeDialog: "dialog",
    typeStartPageVideoAd: "startPageVideoAd",
    typeProfilePageAd: "profilePageAd",

    //constTypes
    constTypeCategory: "categories",
    constTypeProvider: "providers",
    constTypeAuthor: "authors",
    constTypeTag: "tags",

    // videoLibrary

    //upload
    uploadVideoApi: "tools/uploadVideo",
    uploadImageApi: "tools/uploadImage",
    uploadAndBlurImageApi: "tools/blurImage",

    issueListApi: "/api/admin/issues/calendar",
    // todo

    videoListApi: "/api/admin/getVideoBeanList",
    saveVideoApi: '/api/admin/saveVideoInfo',
    editVideoApi: "/api/admin/editVideoInfo",
    getVideoApi: "/api/admin/getVideoInfo",
    saveIssueApi: "/api/admin/saveIssue",
    saveCategoryApi: "/api/admin/saveCategory",
    saveOrderApi: "/api/admin/saveOrder",
    getCategoryListApi: "/api/admin/getCategoryList",
    getProviderListApi: "/api/admin/getProviderList",
    getAuthorListApi: "/api/admin/authors/all",
    changeVideoStatusApi: "/api/admin/changeVideoStatus",
    deleteCategoryApi: "/api/admin/deleteCategory",
    getDownloadTaskListApi: "/api/admin/getDownloadTaskList",
    getDownloadTaskApi: "/api/admin/getDownloadTask",
    addDownloadTaskApi: "/api/admin/addDownloadTask",
    getConfigsApi: "/api/v1/configs",
    saveConfigsApi: "/api/admin/saveConfig",
    shortenUrlApi: "/api/admin/shortenUrl",
    generateWebActionUrlApi: "/api/admin/tools/generateWebActionUrl",
    saveAdApi: "/api/admin/saveAd",
    searchVideoApi: "/api/admin/videos/search",
    searchCandidateApi: "/api/admin/candidates/search",
    getShareConfigListApi: "/api/admin/getShareConfigList",
    saveShareConfigApi: "/api/admin/saveShareConfig",
    getWebPageInfoListApi: "/api/admin/getWebPageInfoList",
    saveWebPageInfoApi: "/api/admin/saveWebPageInfo",
    getExperimentApi: "/api/admin/getExperiment",
    addExperimentApi: "/api/admin/addExperiment",
    updateExperimentApi: "/api/admin/updateExperiment",
    getVideoTagListApi: "/api/admin/videoTags/video",
    getTagVideoListApi: "/api/admin/videoTags/tag",
    checkSourceUrlRepeatingApi: "/api/admin/videos/sourceUrlRepeating",
    registerApi:"/api/admin/register",

    issueTypeList: [
        {key: 'morning', name: '早报', issueTime: "09:00"},
        {key: 'evening', name: '晚报', issueTime: "21:00"},
        {key: 'other', name: '其他', issueTime: "21:00"}
    ],
    publishStatusList: [
        {key: 'OFFLINE', name: '已下线', disabled: true},
        {key: 'UNREADY', name: '未发布', disabled: true},
        {key: 'READY', name: '已发布,未到上线时间', disabled: true},
        {key: 'ONLINE', name: '已上线', disabled: true}
    ],
    videoStatusList: [
        {key: 'OFFLINE', name: '下线'},
        {key: 'DEFAULT', name: '未上线'},
        {key: 'ONLINE', name: '上线'}
    ],
    filterVideoStatusList: [
        {key: "", name: ""},
        {key: 'OFFLINE', name: '下线'},
        {key: 'DEFAULT', name: '未上线'},
        {key: 'ONLINE', name: '上线'}
    ],
    filterCandidateStatusList: [
        {key: "", name: ""},
        {key: 'NEW', name: '最新条目'},
        {key: 'PASSED', name: '不符合'},
        {key: 'TO_FORUM', name: '入选讨论'}
    ],
    filterForumStatusList: [
        {key: "", name: ""},
        {key: 'NEW', name: '最新条目'},
        {key: 'PASSED', name: '不符合'},
        {key: 'PICKED', name: '入选精选'}
    ],
    filterTranslateStatusList: [
        {key: "", name: ""},
        {key: 'NO_NEED_TRANSLATE', name: '不需要'},
        {key: 'ADDED_TASK', name: '翻译中'},
        {key: 'TRANSLATED', name: '翻译完'}
    ],
    filterUploadStatusList: [
        {key: "", name: ""},
        {key: 'NO_NEED_UPLOAD', name: '不需要'},
        {key: 'NEED_UPLOAD', name: '自动下载'},
        {key: 'UPLOAD_FAILED', name: '失败'},
        {key: 'UPLOAD_SUCCEED', name: '成功'}
    ],
    boolList: [
        {value: true, name: '是'},
        {value: false, name: '否'}
    ],
    filterBoolList: [
        {key: "", name: ""},
        {key: true, name: '是'},
        {key: false, name: '否'}
    ],
    pageTypeList: [
        {type: 'VIDEO_DETAIL', name: '视频详情页'},
        {type: 'SPECIAL_TOPIC', name: '专题页'},
        {type: 'DEFAULT', name: '普通网页'},
        {type: 'RECOMMEND_PAGE', name: '探索页'},
        {type: 'REPLY_PAGE', name: '评论页'},
        {type: 'PGC_PAGE', name: 'PGC页'},
        {type: 'TAG_PAGE', name: 'Tag页'},
        {type: 'PANORAMIC_VIDEO_DETAIL', name: '360全景视频详情页'}
    ],
    sharePlatformList: [
        {platform: 'OTHERS', name: '其他'},
        {platform: 'WECHAT_MOMENTS', name: '微信朋友圈'},
        {platform: 'WECHAT_FRIENDS', name: '微信好友'},
        {platform: 'QQ', name: 'QQ'},
        {platform: 'WEIBO', name: '微博'}
    ],
    adLocationList: [
        {key: 'OTHERS', name: '其他'},
        {key: 'START_PAGE_AD', name: '启动屏'},
        {key: 'CAMPAIGN', name: 'Feed 页 banner'},
        {key: 'VIDEO_APP_DETAIL', name: 'app 的视频详情页'},
        {key: 'VIDEO_WEB_DETAIL', name: '视频的 web detail 页'},
        {key: 'WEB_PAGE', name: '网页'},
        {key: 'VIDEO_APP_DETAIL_SHARE', name: '视频分享'},
        {key: 'VIDEO_APP_DETAIL_FAVORITE', name: '视频收藏'},
        {key: 'CARD', name: '卡片'},
        {key: 'LIGHT_TOPIC', name: '轻专题'},
        {key: 'START_PAGE_VIDEO_AD', name: '启动屏开屏视频'}
    ],
    libraryStatusList: [
        {key: "NONE", name: "无"},
        {key: "DEFAULT", name: "二级内容"},
        {key: "DAILY", name: "每日精选"},
        {key: "EXTRA", name: "号外"}
    ],
    filterLibraryStatusList: [
        {key: "", name: ""},
        {key: "NONE", name: "无"},
        {key: "DEFAULT", name: "二级内容"},
        {key: "DAILY", name: "每日精选"},
        {key: "EXTRA", name: "号外"}
    ],
    infoStatusList: [
        {key: 'NOT_CONFIRMED', name: '未确认'},
        {key: 'CONFIRMED', name: '已确认'}
    ],
    filterInfoStatusList: [
        {key: "", name: ""},
        {key: 'NOT_CONFIRMED', name: '未确认'},
        {key: 'CONFIRMED', name: '已确认'}
    ],

    adTrackOrganizationList: [
        {key: 'AD_MASTER', name: 'AdMaster'},
        {key: 'MIAO_ZHEN', name: '秒针'},
        {key: 'SIZMEK', name: 'sizmek'},
        {key: 'DOUBLE_CLICK', name: 'DoubleClick'},
        {key: 'GUO_SHUANG', name: 'guoshuang'}
    ],
    adTrackUrlStatusList: [
        {key: 'HIDE', name: 'HIDE'},
        {key: 'SHOW', name: 'SHOW'}
    ],

    baseStatus: [
        {key: 'HIDE', name: 'HIDE'},
        {key: 'SHOW', name: 'SHOW'}
    ],

    experimentStatusList: [
        {status: 'ready', name: '预上线'},
        {status: 'online', name: '进行中'},
        {status: 'finished', name: '完成'}
    ],

    statusList: [
        {key: 'deleted', name: '已删除'},
        {key: 'ready', name: '未上线'},
        {key: 'online', name: '上线'},
        {key: 'finished', name: '完成'}
    ],
    statusListUpperCase: [
        {key: 'DELETED', name: '已删除'},
        {key: 'READY', name: '未上线'},
        {key: 'ONLINE', name: '上线'},
        {key: 'FINISHED', name: '完成'}
    ],
    candidateStatusList: [
        {key: 'NEW', name: '最新条目'},
        {key: 'PASSED', name: '不符合'},
        {key: 'TO_FORUM', name: '入选讨论'}
    ],
    forumStatusList: [
        {key: 'NEW', name: '最新条目'},
        {key: 'PASSED', name: '不符合'},
        {key: 'PICKED', name: '入选精选'}
    ],
    translateStatusList: [
        {key: 'NO_NEED_TRANSLATE', name: '不需要'},
        {key: 'ADDED_TASK', name: '翻译中'},
        {key: 'TRANSLATED', name: '翻译完'}
    ],
    uploadStatusList: [
        {key: 'NO_NEED_UPLOAD', name: '不需要'},
        {key: 'NEED_UPLOAD', name: '自动下载'},
        {key: 'UPLOAD_FAILED', name: '失败'},
        {key: 'UPLOAD_SUCCEED', name: '成功'}
    ],
    cardTypeList: [
        {key: 'unknown', name: '未知'},
        {key: 'banner1', name: 'banner1'},
        {key: 'banner2', name: 'banner2'}
    ],

    feedCardTypeList: [
        {key: 'unknown', name: '未知'},
        {key: 'feed_top', name: 'feed_top'},
        {key: 'feed_fixed', name: 'feed_fixed'},
        {key: 'category', name: 'category'},
        {key: 'feed_new', name: 'feed_new'},
        {key: 'feed_recommend', name: 'feed_recommend'},
        {key: 'feed_second', name: 'feed_second'}
    ],

    tagStatusList: [
        {key: 'HIDDEN', name: '隐藏'},
        {key: 'SHOW', name: '显示'}
    ],

    videoTypeList: [
        {key: 'NORMAL', name: '普通视频'},
        {key: 'PANORAMIC', name: '360°全景视频'}
    ],

    cardGroupLocationList: [
        {key: 'DISCOVERY_PAGE', name: '发现 tab'},
        {key: 'FEED_PAGE', name: 'Feed tab'},
        {key: 'PGC_PAGE', name: 'Pgc tab'},
        {key: 'DISCOVERY_PAGE_SECOND', name: '发现 tab 第二组 banner'}
    ],

    adVideoLocationList: [
        {key: 'feed', name: 'feed 页'},
        {key: 'category', name: '分类视频列表'},
        {key: 'tag', name: 'tag 视频列表'},
        {key: 'author', name: '作者视频列表'},
        {key: 'rankList', name: '排行榜'},
        {key: 'other', name: '其他'}
    ],

    animateTypeList: [
        {key: 'leftToRight', name: '从左至右'},
        {key: 'rightToLeft', name: '从右至左'},
        {key: 'topToBottom', name: '从上至下'},
        {key: 'bottomToTop', name: '从下至上'}
    ],

    videoAdTypeList: [
        {key: 'OPENING', name: '开屏视频'},
        {key: 'FEED', name: '首页 feed 流自动播放'}
    ],

    forumItemInfos: [
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'title', name: '名称', showType: 'link'},
        {key: 'srcUrl', name: '原地址'},
        {
            key: 'uploadStatus', name: '上传CDN状态', status: [
            {key: 'NO_NEED_UPLOAD', name: '不需要'},
            {key: 'NEED_UPLOAD', name: '自动下载'},
            {key: 'UPLOAD_FAILED', name: '失败'},
            {key: 'UPLOAD_SUCCEED', name: '成功'}
        ], showType: 'select', editType: 'select-not-required'
        },
        {key: 'categoryId', name: '分类', status: "categories", editType: "select"},
        {key: 'coverUrl', name: 'Cover 图', showType: 'image', editType: 'not-required'},
        {key: 'note', name: '讨论理由', editType: 'not-required'},
        {key: 'cdnUrl', name: 'cdn地址', showType: 'videoPlay', editType: 'not-required'},

        {
            key: 'status', name: '状态', status: [
            {key: 'NEW', name: '最新条目'},
            {key: 'PASSED', name: '不符合'},
            {key: 'PICKED', name: '入选精选'}
        ], showType: 'select', editType: 'select-not-required'
        },
        {
            key: 'translateStatus', name: '翻译状态', status: [
            {key: 'NO_NEED_TRANSLATE', name: '不需要'},
            {key: 'ADDED_TASK', name: '翻译中'},
            {key: 'TRANSLATED', name: '翻译完'}
        ], showType: 'select', editType: 'select-not-required'
        },
        {key: 'candidateId', name: '候选库ID', editType: 'not-required'}
    ]
});

swiftbuy.run(function ($rootScope) {
    $rootScope.currentController = '';
    $rootScope.$on('$routeChangeSuccess', function ($event, route) {
        $rootScope.currentController = route.controller;
    });
});
swiftbuy.config(['$routeProvider', "$httpProvider", function ($routeProvider, $httpProvider) {
    $routeProvider.when('/contentTab', {
        templateUrl: 'templates/contentTab.html',
        controller: 'contentTabCtrl'
    });
    $routeProvider.when('/editTab', {
        templateUrl: 'templates/editTab.html',
        controller: 'editTabCtrl'
    });
    $routeProvider.when('/topicTab', {
        templateUrl: 'templates/topicTab.html',
        controller: 'topicTabCtrl'
    });
    $routeProvider.when('/author', {
        templateUrl: 'templates/author.html',
        controller: 'authorCtrl'
    });
    $routeProvider.when('/videoDetail/:id?', {
        templateUrl: 'templates/videoDetail.html',
        controller: 'VideoDetailCtrl'
    });
    $routeProvider.when('/listVideo', {
        templateUrl: 'templates/listVideo.html',
        controller: 'VideoListCtrl'
    });
    $routeProvider.when('/issueCalendar', {
        templateUrl: 'templates/issueCalendar.html',
        controller: 'IssueCalendarCtrl'
    });
    $routeProvider.when('/searchVideo', {
        templateUrl: 'templates/searchVideo.html',
        controller: 'searchVideoCtrl'
    });
    $routeProvider.when('/category', {
        templateUrl: 'templates/categoryList.html',
        controller: 'categoryListCtrl'
    });
    $routeProvider.when('/download', {
        templateUrl: 'templates/downloadTask.html',
        controller: 'downloadTaskCtrl'
    });
    $routeProvider.when('/push', {
        templateUrl: 'templates/push.html',
        controller: 'pushCtrl'
    });
    $routeProvider.when('/adDetail', {
        templateUrl: 'templates/adDetail.html',
        controller: 'adDetailCtrl'
    });
    $routeProvider.when('/adTrackTab', {
        templateUrl: 'templates/adTrackTab.html',
        controller: 'adTrackTabCtrl'
    });
    $routeProvider.when('/adControlInfo', {
        templateUrl: 'templates/adControlInfo.html',
        controller: 'adControlInfoCtrl'
    });
    $routeProvider.when('/shareConfig', {
        templateUrl: 'templates/shareConfig.html',
        controller: 'shareConfigCtrl'
    });
    $routeProvider.when('/webPageInfo', {
        templateUrl: 'templates/webPageInfo.html',
        controller: 'webPageInfoCtrl'
    });
    $routeProvider.when('/experiment/:id?', {
        templateUrl: 'templates/experiment.html',
        controller: 'experimentCtrl'
    });
    $routeProvider.when('/uploadImage', {
        templateUrl: 'templates/uploadImage.html',
        controller: 'uploadImageCtrl'
    });
    $routeProvider.when('/shortenUrl', {
        templateUrl: 'templates/shortenUrl.html',
        controller: 'shortenUrlCtrl'
    });
    $routeProvider.when('/card', {
        templateUrl: 'templates/card.html',
        controller: 'cardCtrl'
    });
    $routeProvider.when('/feedCard', {
        templateUrl: 'templates/feedCard.html',
        controller: 'feedCardCtrl'
    });
    $routeProvider.when('/cardGroup', {
        templateUrl: 'templates/cardGroup.html',
        controller: 'cardGroupCtrl'
    });
    $routeProvider.when('/generateWebActionUrl', {
        templateUrl: 'templates/generateWebActionUrl.html',
        controller: 'generateWebActionUrlCtrl'
    });
    $routeProvider.when('/videoTag', {
        templateUrl: 'templates/videoTag.html',
        controller: 'videoTagCtrl'
    });
    $routeProvider.when('/tagTab', {
        templateUrl: 'templates/tagTab.html',
        controller: 'tagTabCtrl'
    });
    $routeProvider.when('/commonConfig', {
        templateUrl: 'templates/commonConfig.html',
        controller: 'commonConfigCtrl'
    });
    $routeProvider.when('/configTab', {
        templateUrl: 'templates/configTab.html',
        controller: 'configTabCtrl'
    });
    $routeProvider.when('/candidateTab', {
        templateUrl: 'templates/candidate.html',
        controller: 'candidateCtrl'
    });
    $routeProvider.when('/forumTab', {
        templateUrl: 'templates/forum.html',
        controller: 'forumCtrl'
    });
    $routeProvider.when('/reply', {
        templateUrl: 'templates/reply.html',
        controller: 'replyCtrl'
    });
    $routeProvider.when('/adConfigTab', {
        templateUrl: 'templates/adConfigTab.html',
        controller: 'adConfigTabCtrl'
    });
    $routeProvider.otherwise({
        redirectTo: '/editTab'
    });
    $routeProvider.when('/register', {
        templateUrl: 'views/login.html',
        controller: 'registerCtrl'
    });
}]);
