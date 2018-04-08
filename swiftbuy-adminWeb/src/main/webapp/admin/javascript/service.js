'use strict';

angular.module('swiftbuyServices', []).factory('DataSource', ["$rootScope", "$http", 'baseInfo', function ($rootScope, $http, baseInfo) {
    return {
        saveVideo: function (item) {
            var url;
            if (Number(item.id > 0)) {
                url = baseInfo.editVideoApi;
            } else {
                url = baseInfo.saveVideoApi;
            }

            return $http({
                method: 'post',
                url: url,
                //data: 'video='+encodeURIComponent(JSON.stringify(item))
                data: item
            });
        },
        getVideoList: function (date) {
            var params = {};
            if (date) {
                params = {
                    date: date
                }
            }

            return $http.get(baseInfo.videoListApi, {
                params: params
            });
        },
        getCandidateList: function () {
            return $http.get(baseInfo.candidateApi);
        },
        getForumList: function () {
            return $http.get(baseInfo.forumApi);
        },
        getVideoById: function (videoId) {
            return $http.get(baseInfo.getVideoApi, {
                params: {
                    id: videoId
                }
            });
        },
        saveIssue: function (date, releaseTime, issueType) {
            return $http.post(baseInfo.saveIssueApi, null, {
                params: {
                    date: date,
                    releaseTime: releaseTime,
                    issueType: issueType
                }
            });
        },
        saveOrder: function (idList, newIdxList) {
            return $http.post(baseInfo.saveOrderApi, null, {
                params: {
                    //match date
                    idList: idList,
                    orderList: newIdxList
                }
            });
        },
        getCategoryList: function () {
            return $http.get(baseInfo.getCategoryListApi);
        },
        getProviderList: function () {
            return $http.get(baseInfo.getProviderListApi);
        },
        getAuthorList: function () {
            return $http.get(baseInfo.getAuthorListApi);
        },
        changeVideoStatus: function (videoId, status) {
            return $http.post(baseInfo.changeVideoStatusApi, null, {
                params: {
                    id: videoId,
                    status: status
                }
            });
        },
        saveCategory: function (category) {
            return $http({
                method: 'post',
                url: baseInfo.saveCategoryApi,
                //data: 'video='+encodeURIComponent(JSON.stringify(item))
                data: category
            });
        },
        deleteCategory: function (categoryId) {
            return $http.post(baseInfo.deleteCategoryApi, null, {
                params: {
                    id: categoryId
                }
            });
        },
        saveDownloadTask: function (taskBean) {
            return $http.post(baseInfo.addDownloadTaskApi, null, {
                params: taskBean
            });
        },
        getDownloadTaskList: function (offset, limit) {
            return $http.get(baseInfo.getDownloadTaskListApi, {
                params: {
                    offset: offset,
                    limit: limit
                }
            })
        },
        getDownloadTask: function (url) {
            return $http.get(baseInfo.getDownloadTaskApi, {
                params: {
                    url: url
                }
            })
        },
        getConfigs: function (url) {
            return $http.get(baseInfo.getConfigsApi, {
                params: {
                    url: url
                }
            })
        },
        saveConfig: function (name, config) {
            return $http.post(baseInfo.saveConfigsApi, null, {
                params: {
                    name: name,
                    properties: JSON.stringify(config)
                }
            });
        },
        shortenUrl: function (rawUrl) {
            return $http.post(baseInfo.shortenUrlApi, null, {
                params: {
                    url: rawUrl
                }
            });
        },
        generateWebActionUrl: function (title, url) {
            return $http.post(baseInfo.generateWebActionUrlApi, null, {
                params: {
                    title: title,
                    url: url
                }
            });
        },
        saveAd: function (item) {
            var url = baseInfo.saveAdApi;

            return $http({
                method: 'post',
                url: url,
                data: item
            });
        },
        getIssueList: function (date) {
            var params = {
                date: date,
                num: 30
            };

            return $http.get(baseInfo.issueListApi, {
                params: params
            });
        },
        searchVideo: function (query, type, page, pageSize) {
            var params = {
                query: query,
                type: type,
                page: page,
                pageSize: pageSize
            };

            return $http.get(baseInfo.searchVideoApi, {
                params: params
            });
        },
        searchCandidate: function (query, type, page, pageSize) {
            var params = {
                query: query,
                type: type,
                page: page,
                pageSize: pageSize
            };

            return $http.get(baseInfo.searchCandidateApi, {
                params: params
            });
        },
        getShareConfigList: function () {
            return $http.get(baseInfo.getShareConfigListApi);
        },
        saveShareConfig: function (shareConfig) {
            return $http({
                method: 'post',
                url: baseInfo.saveShareConfigApi,
                data: shareConfig
            });
        },
        getWebPageInfoList: function () {
            return $http.get(baseInfo.getWebPageInfoListApi);
        },
        saveWebPageInfo: function (webPageInfo) {
            return $http({
                method: 'post',
                url: baseInfo.saveWebPageInfoApi,
                data: webPageInfo
            });
        },
        saveExperiment: function (experiment) {
            var url;
            if (Number(experiment.id > 0)) {
                url = baseInfo.updateExperimentApi;
            } else {
                url = baseInfo.addExperimentApi;
            }

            return $http({
                method: 'post',
                url: url,
                data: experiment
            });
        },
        adminRegister: function (experiment) {
            var url=baseInfo.registerApi;

            return $http({
                method: 'post',
                url: url,
                data: experiment
            });
        },
        getExperimentById: function (id) {
            return $http.get(baseInfo.getExperimentApi, {
                params: {
                    id: id
                }
            });
        },
        getStartPageVideoAdById: function (id) {
            return $http.get(baseInfo.getStartPageVideoAdApi, {
                params: {
                    id: id
                }
            });
        },
        saveStartPageVideoAd: function (item) {
            var url = baseInfo.saveStartPageVideoAdApi;

            return $http({
                method: 'post',
                url: url,
                data: item
            });
        },

        saveItem: function (type, item) {
            var url = baseInfo.baseUrl + '/' + type.name;
            var method = '';
            if (item.id > 0) {
                url = url + '/' + item.id;
                method = 'put'
            } else {
                method = 'post'
            }
            console.log(url);
            return $http({
                method: method,
                url: url,
                data: item
            });
        },
        changeStatus: function (type, item, key) {
            var url = baseInfo.baseUrl + '/' + type.name + '/' + item.id + '/' + key + '/' + item[key];
            return $http({
                method: 'put',
                url: url
            });
        },
        deleteItem: function (type, item) {
            var url = baseInfo.baseUrl + '/' + type.name + '/' + item.id;
            var method = 'delete';
            console.log(method);
            return $http({
                method: method,
                url: url
            });
        },
        deleteVideo: function (issueId, videoId) {
            var url = baseInfo.baseUrl + '/issues/' + issueId + '/videos/' + videoId;
            var method = 'delete';
            console.log(method);
            return $http({
                method: method,
                url: url
            });
        },
        getList: function (type, page, sort, sortBy) {
            var url = baseInfo.baseUrl + '/' + type.name;
            var params = {};
            params.pageSize = baseInfo.pageSize;
            params.page = page;
            params.sort = sort;
            params.sortBy = sortBy;
            if (type.library != null) {
                params.library = type.library;
            }
            if (type.infoStatus != null) {
                params.infoStatus = type.infoStatus;
            }
            if (type.status != null) {
                params.status = type.status;
            }
            if (type.belongToIssue != null) {
                params.belongToIssue = type.belongToIssue;
            }
            if (type.uploadStatus != null) {
                params.uploadStatus = type.uploadStatus;
            }
            if (type.translateStatus != null) {
                params.translateStatus = type.translateStatus;
            }
            return $http.get(url, {
                params: params
            })
        },
        httpGet: function (url, params) {
            return $http.get(url, {
                params: params
            })
        },
        updateVideoOrder: function (type, item, videoIdList) {
            console.log(videoIdList);
            var url = baseInfo.baseUrl + '/' + type.name + '/' + item.id + '/videos/order';
            return $http({
                method: 'put',
                url: url,
                data: videoIdList
            });
        },
        addVideos: function (type, item, videoIdList) {
            var url = baseInfo.baseUrl + '/' + type.name + '/' + item.id + '/videos';
            return $http({
                method: 'post',
                url: url,
                data: videoIdList
            });
        },
        removeVideos: function (type, item, videoIdList) {
            var url = baseInfo.baseUrl + '/' + type.name + '/' + item.id + '/videos/removeVideos';
            return $http({
                method: 'post',
                url: url,
                data: videoIdList
            });
        },
        getVideoTagList: function (videoId) {
            var url = baseInfo.getVideoTagListApi + '/' + videoId;
            return $http.get(url);
        },
        getTagVideoList: function (tagId) {
            var url = baseInfo.getTagVideoListApi + '/' + tagId;
            return $http.get(url);
        },
        getTopicVideoList: function (type, id) {
            var url = baseInfo.baseUrl + '/' + type.name + '/' + id + '/videos';
            return $http.get(url);
        },
        checkSourceUrlRepeating: function (id, sourceUrl) {
            var url = baseInfo.checkSourceUrlRepeatingApi;
            var params = {
                id: id,
                url: sourceUrl
            };
            return $http({
                method: 'get',
                url: url,
                params: params
            });
        },
        resetReplyReport: function (replyId) {
            var url = baseInfo.baseUrl + '/' + baseInfo.typeReply + '/' + replyId + '/reset';
            return $http({
                method: 'put',
                url: url
            });
        }
    }
}]).factory('ConstFromServer', function ($rootScope, $http, $q, DataSource, baseInfo) {
    var _cachedLists = {};

    function getConstList(constType, force) {
        var cachedList = _cachedLists[constType];
        if (cachedList instanceof Array && cachedList.length > 0 && force != true) {
            return $q.when(cachedList);
        }
        var url = baseInfo.baseUrl + '/' + constType;
        if (constType == baseInfo.typeAuthor) {
            url = url + '/all';
        }
        cachedList = $http.get(url, {headers: {'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8'}});
        return cachedList;
    }

    return {
        getConstList: getConstList
    };
});