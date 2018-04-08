var controllers = angular.module('swiftbuyControllers', ['ui.bootstrap', 'angularFileUpload', 'ngSanitize']);
controllers.controller('VideoDetailCtrl', ["$scope", "$routeParams", "$filter", '$upload', "$location", "$q", "baseInfo", "DataSource", "ConstFromServer", function ($scope, $routeParams, $filter, $upload, $location, $q, baseInfo, DataSource, ConstFromServer) {

    $scope.issueTypeList = baseInfo.issueTypeList;
    $scope.video = {};
    $scope.video.id = $routeParams.id || '0';
    $scope.videoId = Number($scope.video.id);
    var categoryPromise = getCategoryList(false);
    var providerPromise = getProviderList(false);
    var authorPromise = getAuthorList(false);

    if ($scope.videoId > 0) {
        DataSource.getVideoById($scope.video.id).then(function (response) {
            $scope.video = response.data;
            $scope.videoDate = (new Date($scope.video.date)).toString('yyyy-MM-dd');

            categoryPromise.then(function (response) {
                var found = $filter('filter')($scope.categoryList, {name: $scope.video.category}, true);
                $scope.videoCategory = found[0];
            });
            providerPromise.then(function (response) {
                var found = $filter('filter')($scope.providerInfoList, {id: $scope.video.providerId}, true);
                $scope.videoProvider = found[0];
            });
            authorPromise.then(function (response) {
                var found = $filter('filter')($scope.authorList, {id: $scope.video.authorId}, true);
                $scope.videoAuthor = found[0];
            });

            var videoTime = (new Date($scope.video.date)).toString('HH:mm');
            var found = $filter('filter')($scope.issueTypeList, {issueTime: videoTime}, true);
            $scope.issueType = found[0];

            $scope.getFromVideoId = true;
        })
    } else {
        var tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        $scope.videoDate = tomorrow.toString('yyyy-MM-dd');
        categoryPromise.then(function (response) {
            $scope.videoCategory = $scope.categoryList[0];
        });
        providerPromise.then(function (response) {
            $scope.videoProvider = $scope.providerInfoList[0];
        });
        authorPromise.then(function (response) {
            $scope.videoAuthor = $scope.authorList[0];
        });
    }

    $scope.processInfo = {}; // 上传视频
    $scope.uploadVideo = function ($files, $event) {
        if ($files.length > 0) {
            $scope.video.playUrl = '';
            $scope.upload = $upload.upload({
                url: baseInfo.uploadVideoApi,
                method: 'POST',
                file: $scope.videoFile // single file or a list of files. list is only for html5
            }).progress(function (evt) {
                if (evt.loaded === evt.total) {
                    $scope.processInfo[coverName] = '已上传100%, , 服务器正在处理...';
                } else {
                    $scope.processInfo.video = '已上传: ' + parseInt(100.0 * evt.loaded / evt.total, 10) + '%';
                }
            }).success(function (data, status, headers, config) {
                // 如果data 为 null或者undefined
                if (data == undefined) {
                    alert('上传失败, 请重新上传.');
                    return;
                }

                $scope.processInfo.video = '上传成功';
                $scope.video.playUrl = data.videoUrl;
                $scope.video.duration = data.duration;
            }).error(function () {
                alert('上传视频失败');
            });
        }
    };

    // 上传预览图
    $scope.uploadFeedCover = function ($files, coverName, $event) {
        if ($files.length > 0) {
            $scope.upload = $upload.upload({
                url: baseInfo.uploadAndBlurImageApi,
                method: 'POST',
                file: $scope[coverName]
            }).progress(function (evt) {
                if (evt.loaded === evt.total) {
                    $scope.processInfo[coverName] = '已上传100%, 服务器正在处理...';
                } else {
                    $scope.processInfo[coverName] = '已上传: ' + parseInt(100.0 * evt.loaded / evt.total, 10) + '%';
                }
            }).success(function (data, status, headers, config) {
                if (data && data[0] && data[1]) {
                    $scope.processInfo[coverName] = '上传成功';
                    $scope.video[coverName] = data[0].url;
                    $scope.video.coverBlurred = data[1].url;
                } else {
                    alert('上传图片失败');
                }

            }).error(function () {
                alert('上传图片失败');
            });
        }
    };

    $scope.submit = function () {
        if ($scope.video.videoProvider.name == "PGC") {
            if (!confirm("PGC视频信息请谨慎修改，确认提交？")) {
                return;
            }
        }
        var tmpDate = new Date($scope.videoDate + " " + $scope.issueType.issueTime + ":00");
        $scope.video.date = tmpDate.getTime();
        $scope.video.coverForDetail = $scope.video.coverForFeed;
        $scope.video.categoryId = $scope.videoCategory.id;
        $scope.video.providerId = $scope.videoProvider.id;
        $scope.video.authorId = $scope.videoAuthor.id;
        $scope.video.status = baseInfo.videoStatusList[1].status;

        DataSource.saveVideo($scope.video).success(function (data) {
            if (Number(data) > 0) {
                alert('保存成功');
                $location.search({
                    t: Date.now().getTime()
                });
            } else if (data.indexOf('true') > -1) {
                alert('修改成功');
            } else {
                alert('保存失败，该视频可能已经存在.');
            }
        }).error(function () {
            alert('保存失败');
        });
    };

    $scope.$watch('video.url', function (newValue, oldValue) {
        if (newValue !== oldValue) {
            if ($scope.getFromVideoId) {
                $scope.getFromVideoId = false;
                return;
            }
            getVideoDownloadInfo(newValue);
        }
    });

    $scope.checkLength = function (maxWidth) {
        var strInput = $scope.video.title;
        var strFinal = "";
        var width = 0;

        for (var i = 0; i < strInput.length; i++) {
            if (strInput.charCodeAt(i) >= 0 && strInput.charCodeAt(i) <= 255) {
                width += 1;
            } else {
                width += 2;
            }
            if (width > maxWidth) {
                $scope.video.title = strFinal;
                break;
            }
            strFinal += strInput[i];
        }
    };

    $scope.checkDescriptionLength = function (maxWidth) {
        var strInput = $scope.video.description;
        var strFinal = "";
        var width = 0;

        for (var i = 0; i < strInput.length; i++) {
            if (strInput.charCodeAt(i) >= 0 && strInput.charCodeAt(i) <= 255) {
                width += 1;
            } else {
                width += 2;
            }
            if (width > maxWidth) {
                $scope.video.description = strFinal;
                break;
            }
            strFinal += strInput[i];
        }
    };

    function getVideoDownloadInfo(url) {
        DataSource.getDownloadTask(url).success(function (data) {
            if (data && data.cdnUrl) {
                $scope.video.duration = data.duration;
                $scope.video.playUrl = data.cdnUrl;
            }
        })
    }

    function updateVideo(videoId) {
        return DataSource.getVideoById($scope.video.id).then(function (response) {
            $scope.video = response.data;
            $scope.videoDate = (new Date($scope.video.date)).toString('yyyy-MM-dd');
        });
    }

    function getCategoryList(force) {
        // Get category list from server
        return ConstFromServer.getCategoryList(force).then(function (response) {
            $scope.categoryList = response.data;
        });
    }

    function getProviderList(force) {
        // Get provider list from server
        return ConstFromServer.getProviderList(force).then(function (response) {
            $scope.providerInfoList = response.data;
        });
    }

    function getAuthorList(force) {
        return ConstFromServer.getAuthorList(force).then(function (response) {
            $scope.authorList = response.data;
        });
    }
}]).controller('VideoListCtrl', ["$scope", "$routeParams", "$sce", "$modal", "$location", "$filter", "baseInfo", "DataSource", function ($scope, $routeParams, $sce, $modal, $location, $filter, baseInfo, DataSource) {
    var tmpDateStr = $routeParams.dateStr || (new Date()).toString('yyyy-MM-dd');
    $scope.ismeridian = false;
    $scope.timePattern24Hour = new RegExp('^([01]\\d|2[0-3]):([0-5]\\d)$');
    $scope.selectedDate = new Date(tmpDateStr);
    $scope.issueTypeList = baseInfo.issueTypeList;
    $scope.publishStatusList = baseInfo.publishStatusList;
    $scope.videoStatusList = baseInfo.videoStatusList;

    $scope.dateOptions = {
        formatYear: 'yy',
        startingDay: 1
    };
    $scope.open = function ($event) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.opened = true;
    };

    $scope.format = 'yyyyMMdd';
    $scope.releaseTime = "00:00";
    $scope.issueTime = "00:00";

    $scope.trustSrc = function (src) {
        return $sce.trustAsResourceUrl(src);
    };

    $scope.apply = function () {
        getVideoList(getIssueTime());
    };
    $scope.lastIssue = function () {
        if ($scope.lastIssueDate != 0) {
            $scope.selectedDate = new Date($scope.lastIssueDate);
            getVideoList($scope.lastIssueDate);
        }
    };
    $scope.nextIssue = function () {
        if ($scope.nextIssueDate != 0) {
            $scope.selectedDate = new Date($scope.nextIssueDate);
            getVideoList($scope.nextIssueDate);
        }
    };

    getVideoList(getIssueTime());

    function getIssueTime() {
        return new Date($scope.selectedDate.toString("yyyy-MM-dd") + " " + $scope.issueTime + ":00").getTime()
    }

    function getIssueReleaseTime() {
        return new Date($scope.selectedDate.toString("yyyy-MM-dd") + " " + $scope.releaseTime + ":00").getTime()
    }

    function getVideoList(date) {
        DataSource.getVideoList(date).then(function (response) {
            $scope.videoList = response.data.videoList;
            if (response.data.releaseTime) {
                $scope.releaseTime = (new Date(response.data.releaseTime)).toString('HH:mm');
            }
            if (response.data.type == "normal") {
                $scope.issueTime = "00:00";
            } else if (response.data.type == "weekendExtra") {
                $scope.issueTime = "12:00";
            }

            $scope.lastIssueDate = response.data.lastIssueDate;
            $scope.nextIssueDate = response.data.nextIssueDate;
            $scope.issueType = $filter('filter')($scope.issueTypeList, {type: response.data.type}, true)[0];
            $scope.dailyPublishStatus = $filter('filter')($scope.publishStatusList, {status: response.data.status}, true)[0];
            var otherIssueLater = response.data.otherIssueLater;
            $scope.alertMessage = otherIssueLater ? '别忘了周末号外！！！' : '';
        });
    }

    $scope.videoPlay = function (e) {
        var videoElements = angular.element(e.srcElement);
        videoElements[0].pause();
    };
    $scope.modalVideoPlay = function (playUrl) {
        var modalInstance = $modal.open({
            templateUrl: "templates/videoPlayModal.html",
            controller: "videoPlayCtrl",
            resolve: {
                playUrl: function () {
                    return playUrl;
                }
            }
        });
    };
    $scope.changeIssueType = function (issueType) {
        if (issueType == "morning") {
            $scope.releaseTime = "09:00";
            $scope.issueTime = "09:00";
        } else if (issueType == "evening") {
            $scope.releaseTime = "21:00";
            $scope.issueTime = "21:00";
        }
    };
    $scope.saveIssue = function (e) {
        DataSource.saveIssue(getIssueTime(), getIssueReleaseTime(), $scope.issueType.type).then(function (response) {
            if (response.data) {
                alert('更新发布时间成功');
                $scope.dailyPublishStatus = $filter('filter')($scope.publishStatusList, {status: response.data.status}, true)[0];
            } else {
                alert('更新发布时间失败-response code');
            }
        })
    };
    $scope.saveOrder = function () {
        var idList = [];
        var idxList = [];
        for (var i = 0; i < $scope.videoList.length; i++) {
            if (i + 1 != $scope.videoList[i].idx) {
                idList.push($scope.videoList[i].id);
                idxList.push(i + 1);
            }
        }
        if (idList.length == 0) {
            alert('没有可以更新的内容');
            return;
        }
        DataSource.saveOrder(idList, idxList).then(function (response) {
            if (Number(response.data) >= 0) {
                alert('更新顺序成功');
                var searchParams = {
                    dateStr: getIssueTime(),
                    t: Date.now().getTime()
                };
                $location.search(searchParams);
            } else {
                alert('更新顺序失败');
            }
        });
    };
    $scope.changeVideoStatus = function (videoId, status) {
        DataSource.changeVideoStatus(videoId, status).success(function (data) {
            if ('true' === data) {
                var video = getVideoById(videoId);
                video.inChangingStatus = false;
            } else {
                alert(videoId + '更改失败, 重新刷新获取结果.');
            }
        }).error(function (data) {
            alert(videoId + '更改失败, 重新刷新获取结果.');
        });
    };
    function getVideoById(videoId) {
        return $filter('filter')($scope.videoList, {id: videoId}, true)[0];
    }

}]).controller('categoryListCtrl', ["$scope", "$location", "$filter", "$upload", "baseInfo", "DataSource", "ConstFromServer", function ($scope, $location, $filter, $upload, baseInfo, DataSource, ConstFromServer) {

    getCategoryList(false);

    $scope.editInfoList = ['编辑', '取消编辑'];

    // 上传预览图
    $scope.uploadCategoryCover = function ($files, category, $event) {
        if ($files.length > 0) {
            $scope.upload = $upload.upload({
                url: baseInfo.baseUrl + '/' + baseInfo.uploadImageApi,
                method: 'POST',
                file: category.coverFile
            }).progress(function (evt) {
                if (evt.loaded === evt.total) {
                    $scope.processInfo[coverName] = '已上传100%, 服务器正在处理...';
                } else {
                    $scope.processInfo[coverName] = '已上传: ' + parseInt(100.0 * evt.loaded / evt.total, 10) + '%';
                }
            }).success(function (data, status, headers, config) {
                category.processInfo = '上传成功';
                category.bgPicture = data.url;
            }).error(function () {
                alert('上传图片失败');
            });
        }
    };

    $scope.uploadCategoryHeaderImage = function ($files, category, $event) {
        if ($files.length > 0) {
            $scope.upload = $upload.upload({
                url: baseInfo.baseUrl + '/' + baseInfo.uploadImageApi,
                method: 'POST',
                file: category.headerImageFile
            }).progress(function (evt) {
                if (evt.loaded === evt.total) {
                    $scope.processInfo = '已上传100%, 服务器正在处理...';
                } else {
                    $scope.processInfo = '已上传: ' + parseInt(100.0 * evt.loaded / evt.total, 10) + '%';
                }
            }).success(function (data, status, headers, config) {
                category.processInfo = '上传成功';
                category.headerImage = data.url;
            }).error(function () {
                alert('上传图片失败');
            });
        }
    };

    $scope.editCategory = function (category) {
        category.isEditStatus = 1 ^ category.isEditStatus;
    };

    $scope.addCategory = function () {
        var tmp = {
            isEditStatus: 1,
            id: 0
        };
        $scope.categoryList.push(tmp);
    };

    $scope.saveCategory = function (category) {
        DataSource.saveCategory(category).success(categoryEditCb).error(function (data) {
            alert('保存失败');
        })
    };

    $scope.removeCategory = function (categoryId) {
        DataSource.deleteCategory(categoryId).success(categoryEditCb);
    };

    function categoryEditCb(data) {
        if (data.indexOf('true') >= 0) {
            alert('成功');
            getCategoryList(true);
        } else {
            alert('失败');
        }
    }

    function getCategoryList(force) {
        DataSource.getCategoryList(force).then(function (response) {
            $scope.categoryList = JSON.parse(JSON.stringify(response.data));
            for (var i = 0; i < $scope.categoryList.length; i++) {
                $scope.categoryList[i].isEditStatus = 0;
            }
        })
    }
}]).controller('videoPlayCtrl', ["$scope", "$sce", "$modalInstance", "playUrl", function ($scope, $sce, $modalInstance, playUrl) {
    $scope.playUrl = playUrl;

    $scope.trustSrc = function (src) {
        return $sce.trustAsResourceUrl(src);
    };
    $scope.videoPlay = function (e) {
        var videoElements = angular.element(e.srcElement);
        videoElements[0].pause();
    };
}]).controller('downloadTaskCtrl', ["$scope", "DataSource", function ($scope, DataSource) {
    $scope.addDownloadTask = function () {
        var tmp = {
            isEditStatus: 1,
            id: 0
        };
        $scope.downloadTaskList.push(tmp);
    };

    getDownloadTaskList();

    function getDownloadTaskList() {
        DataSource.getDownloadTaskList(0, 30).success(function (data) {
            $scope.downloadTaskList = data;
        });
    }

    $scope.saveDownloadTask = function (task) {
        var data = {};
        angular.forEach(task, function (value, key) {
            if (key === 'url' || key === 'downloadUrl') {
                data[key] = value;
            }
        });
        DataSource.saveDownloadTask(data).success(function (data) {
            if (Number(data) > 0) {
                alert('保存成功');
                getDownloadTaskList();
            }
        }).error(function (data) {
            alert('保存失败');
        })
    };
}]).controller('pushCtrl', ["$scope", "DataSource", function ($scope, DataSource) {

    getPushConfig();

    function getPushConfig() {
        DataSource.getConfigs().success(function (data) {
            var configs = data;
            $scope.push = configs['push'];
        });
    }

    $scope.submit = function () {
        DataSource.saveConfig("push", $scope.push).success(function (data) {
            if (data.indexOf('success') > -1) {
                alert('保存成功');
            } else {
                alert('保存失败');
            }
        }).error(function () {
            alert('保存失败');
        });
    }
}]).controller('configTabCtrl', function ($scope, commonMethod, baseInfo) {
}).controller('splashCtrl', ["$scope", "$upload", "baseInfo", "DataSource", function ($scope, $upload, baseInfo, DataSource) {

    loadSplash();

    function loadSplash() {
        DataSource.getConfigs().success(function (data) {
            var configs = data;
            $scope.splash = {image: configs['startPage']['imageUrl']};
        });
    }

    $scope.uploadSplash = function ($files, splash, $event) {
        if ($files.length > 0) {
            $scope.upload = $upload.upload({
                url: baseInfo.baseUrl + '/' + baseInfo.uploadImageApi,
                method: 'POST',
                file: splash.file
            }).progress(function (evt) {
            }).success(function (data, status, headers, config) {
                splash.processInfo = '上传成功';
                splash.image = data.url;
            }).error(function () {
                alert('上传图片失败');
            });
        }
    };

    $scope.submit = function () {
        var startPage = {imageUrl: $scope.splash.image, actionUrl: ""};
        DataSource.saveConfig("startPage", startPage).success(function (data) {
            if (data.indexOf('success') > -1) {
                alert('保存成功');
            } else {
                alert('保存失败');
            }
        }).error(function () {
            alert('保存失败');
        });
    }
}]).controller('homepageCoverCtrl', ["$scope", "$upload", "baseInfo", "DataSource", function ($scope, $upload, baseInfo, DataSource) {

    loadCover();

    function loadCover() {
        DataSource.getConfigs().success(function (data) {
            var configs = data;
            $scope.cover = {image: configs['homepage']['cover']};
        });
    }

    $scope.uploadCover = function ($files, cover, $event) {
        if ($files.length > 0) {
            $scope.upload = $upload.upload({
                url: baseInfo.baseUrl + '/' + baseInfo.uploadImageApi,
                method: 'POST',
                file: cover.file
            }).progress(function (evt) {
            }).success(function (data, status, headers, config) {
                cover.processInfo = '上传成功';
                cover.image = data.url;
            }).error(function () {
                alert('上传图片失败');
            });
        }
    };

    $scope.submit = function () {
        var homepage = {cover: $scope.cover.image};
        DataSource.saveConfig("homepage", homepage).success(function (data) {
            if (data.indexOf('success') > -1) {
                alert('保存成功');
            } else {
                alert('保存失败');
            }
        }).error(function () {
            alert('保存失败');
        });
    }

    $scope.delete = function() {
        var homepage = {cover: null};
        DataSource.saveConfig("homepage", homepage).success(function (data) {
            if (data.indexOf('success') > -1) {
                alert('保存成功');
            } else {
                alert('保存失败');
            }
        }).error(function () {
            alert('保存失败');
        });
        loadCover();
    }
}]).controller('dialogCtrl', function ($scope, commonMethod, baseInfo, DataSource) {
    // init
    commonMethod.init($scope, DataSource);
    $scope.editRadio = {
        model: 'modify',
        enabledModes: ['delete', 'modify']
    };
    $scope.type.name = baseInfo.typeDialog;
    $scope.defaultNewItem = {
        id: 0,
        title: '',
        status: 'ready'
    };
    $scope.type.itemInfos = [
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'title', name: '标题', editType: 'not-required'},
        {key: 'image', name: '弹窗图片', showType: 'image', editType: 'not-required'},
        {key: 'content', name: '文字内容', showType: "hide", editType: 'long-text'},
        {key: 'buttonListString', name: '点击按钮配置', showType: "hide", editType: 'long-text'},
        {key: 'showTimes', name: '显示次数'},
        {key: 'animateImage', name: '动画图片', showType: 'image', editType: 'not-required'},
        {key: 'actionUrl', name: '图片跳转地址', showType: 'hide', editType: 'not-required'},
        {key: 'animateType', name: '动画类型', status: baseInfo.animateTypeList, showType: 'hide', editType: 'select-not-required'},
        {key: 'animateTimeLength', name: '动画时长', showType: 'hide', editType: 'not-required'},
        {key: 'ruleId', name: '小流量 id', showType: "hide", editType: 'not-required'},
        {key: 'startTime', name: '上线时间', showType: 'date', editType: 'date'},
        {key: 'endTime', name: '下线时间', showType: 'date', editType: 'date'},
        {key: 'status', name: '上线状态', status: baseInfo.statusList, editType: "select"}
    ];
    $scope.load();
}).controller('uploadImageCtrl', ["$scope", "$upload", "baseInfo", "DataSource", function ($scope, $upload, baseInfo, DataSource) {

    $scope.uploadImage = function ($files, image, $event) {
        if ($files.length > 0) {
            $scope.upload = $upload.upload({
                url: baseInfo.baseUrl + '/' + baseInfo.uploadImageApi,
                method: 'POST',
                file: image.file
            }).progress(function (evt) {
            }).success(function (data, status, headers, config) {
                image.processInfo = '上传成功';
                image.url = data.url;
            }).error(function () {
                alert('上传图片失败');
            });
        }
    };
}]).controller('shortenUrlCtrl', ["$scope", "$upload", "baseInfo", "DataSource", function ($scope, $upload, baseInfo, DataSource) {

    $scope.submit = function () {
        DataSource.shortenUrl($scope.rawUrl).success(function (data) {
            $scope.shortUrl = data.replace(/"/g, "");
        }).error(function () {
            alert('失败');
        });
    };

    $scope.clear = function () {
        $scope.rawUrl = '';
        $scope.shortUrl = '';
    }
}]).controller('adDetailCtrl', ["$scope", "$upload", "baseInfo", "DataSource", function ($scope, $upload, baseInfo, DataSource) {

    $scope.ad = {};
    $scope.ad.actionUrlAndroid = "";
    $scope.ad.actionUrlIos = "";
    $scope.boolList = baseInfo.boolList;

    $scope.submit = function () {
        $scope.ad.startTime = new Date($scope.startTimeStr).getTime();
        $scope.ad.endTime = new Date($scope.endTimeStr).getTime();
        $scope.ad.countdown = $scope.countdown.value;
        $scope.ad.canSkip = $scope.canSkip.value;
        DataSource.saveAd($scope.ad).success(function (data) {
            if (Boolean(data) == true) {
                alert('保存成功');
            } else {
                alert('保存失败');
            }
        }).error(function () {
            alert('保存失败');
        });
    };

    $scope.processInfo = "";
    $scope.uploadCover = function ($files, cover, $event) {
        if ($files.length > 0) {
            $scope.upload = $upload.upload({
                url: baseInfo.baseUrl + '/' + baseInfo.uploadAndBlurImageApi,
                method: 'POST',
                file: cover
            }).progress(function (evt) {
                if (evt.loaded === evt.total) {
                    $scope.processInfo = '已上传100%, 正在生成高斯模糊图...';
                } else {
                    $scope.processInfo = '已上传: ' + parseInt(100.0 * evt.loaded / evt.total, 10) + '%';
                }
            }).success(function (data, status, headers, config) {
                if (data && data[0] && data[1]) {
                    $scope.processInfo = '上传成功';
                    $scope.ad.imageUrl = data[0].url;
                    $scope.ad.blurredImageUrl = data[1].url;
                } else {
                    alert('上传图片失败');
                }
            }).error(function () {
                alert('上传图片失败');
            });
        }
    }
}]).controller('adConfigTabCtrl', function ($scope, commonMethod, baseInfo) {
}).controller('startPageVideoAdCtrl', function ($scope, commonMethod, baseInfo, DataSource) {

    commonMethod.init($scope, DataSource);
    $scope.editRadio = {
        model: 'modify',
        enabledModes: ['modify', 'delete']
    };
    $scope.type.name = baseInfo.typeStartPageVideoAd;
    $scope.defaultNewItem = {
        id: 0,
        status: 'READY'
    };

    $scope.type.itemInfos = [
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'title', name: '主标题'},
        {key: 'description', name: '副标题'},
        {key: 'icon', name: '品牌 icon'},
        {key: 'url', name: '视频地址'},
        {key: 'actionUrl', name: '跳转地址', editType: 'not-required'},
        {key: 'imageUrl', name: '图片地址', editType: 'not-required', showType: 'hide'},
        {key: 'videoAdType', name: '广告位置类型', status: baseInfo.videoAdTypeList, editType: 'select'},
        {key: 'position', name: '位置序号', editType: 'not-required'},
        {key: 'displayTimeDuration', name: '展现时长', editType: 'not-required'},
        {key: 'countdown', name: '是否有倒计时', status: baseInfo.filterBoolList, showType: 'hide', editType: 'select'},
        {key: 'canSkip', name: '是否可以跳过', status: baseInfo.filterBoolList, showType: 'hide', editType: 'select'},
        {key: 'timeBeforeSkip', name: '跳过前所需时间', editType: 'not-required'},
        {key: 'showActionButton', name: '是否显示跳转 button', status: baseInfo.filterBoolList, showType: 'hide', editType: 'select'},
        {key: 'videoType', name: '视频类型', status: baseInfo.videoTypeList, showType: 'hide', editType: 'select'},
        {key: 'startTime', name: '上线时间', showType: 'date', editType: 'date'},
        {key: 'endTime', name: '下线时间', showType: 'date', editType: 'date'},
        {key: 'status', name: '状态', status: baseInfo.statusListUpperCase, editType: "select"}
    ];

    $scope.load();

}).controller('profilePageAdCtrl', function ($scope, commonMethod, baseInfo, DataSource) {
    commonMethod.init($scope, DataSource);
    $scope.editRadio = {
        model: 'modify',
        enabledModes: ['delete', 'modify']
    };
    $scope.type.name = baseInfo.typeProfilePageAd;
    $scope.defaultNewItem = {
        id: 0,
        status: 'ready'
    };
    $scope.type.itemInfos = [
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'imageUrl', name: '图片', showType: 'image'},
        {key: 'actionUrl', name: '跳转地址'},
        {key: 'startTime', name: '上线时间', showType: 'date', editType: 'date'},
        {key: 'endTime', name: '下线时间', showType: 'date', editType: 'date'},
        {key: 'status', name: '状态', status: baseInfo.statusList, editType: "select"}
    ];
    $scope.load();
}).controller('searchVideoCtrl', function ($scope, $modal, commonMethod, baseInfo, DataSource) {
    // init
    commonMethod.init($scope);
    $scope.editRadio = {
        model: 'modify',
        enabledModes: ['modify', 'delete']
    };
    $scope.type.name = baseInfo.typeVideo;
    $scope.type.itemInfos = [
        {key: 'library', name: '推荐性', status: baseInfo.libraryStatusList, showType: 'library', editType: 'select-disabled'},
        //{key: 'issueDate', name: '日报', showType: 'date'},
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'sourceUrl', name: '源地址', isRepeating: false, showType: "hide", editType: 'source-url'},
        {key: 'title', name: '标题'},
        {key: 'premiereDate', name: '预设上线时间', showType: "hide", editType: 'date'},
        {key: 'cover', name: 'Cover图', showType: 'cover', editType: 'cover'},
        {key: 'scoreFull', name: '全局得分',  editType: 'disabled'},
        {key: 'scoreRecent', name: '近期得分',  editType: 'disabled'},
        {key: '', name: '视频信息', showType: "hide", editType: 'video'},
        {key: 'type', name: '类型', status: baseInfo.videoTypeList, showType: "hide", editType: "select"},
        {key: 'categoryId', name: '分类', status: baseInfo.constTypeCategory, editType: "select"},
        {key: 'providerId', name: '视频来源', status: baseInfo.constTypeProvider, showType: "hide", editType: "select"},
        {key: 'authorId', name: '作者', status: baseInfo.constTypeAuthor, showType: "hide", editType: "select-not-required"},
        {key: 'tailTimePoint', name: '片尾时间', showType: "hide", editType: "not-required"},
        {key: 'description', name: '详情描述', showType: "hide", editType: 'description'},
        {key: 'dimension', name: '清晰度', editType: "hide"},
        {key: '', name: '播放', showType: "videoPlay", editType: "hide"},
        {key: 'ad', name: '广告', status: baseInfo.filterBoolList, showType: "hide", editType: "select"},
        {key: 'infoStatus', name: 'Check', status: baseInfo.infoStatusList, showType: 'select', editType: "select-disabled"},
        {key: 'status', name: 'status', status: baseInfo.videoStatusList, showType: 'select', editType: "select-disabled"},
        {key: '', name: 'tag', showType: "tag", editType: "hide"}
    ];

    $scope.search = function search(query, type) {
        $scope.loading = true;
        $scope.searchQuery = query;
        $scope.searchType = type;
        DataSource.searchVideo(query, type, $scope.currentPage, baseInfo.pageSize).then(function (response) {
            $scope.commonList = response.data.itemList;
            $scope.totalItems = response.data.totalItems;
            if ($scope.commonList.length != 0) {
                $scope.emptyText = "";
            } else {
                $scope.emptyText = "没有找到任何相关视频 ";
            }
        }, function () {
            $scope.emptyText = "网络错误";
        }).finally(function () {
            $scope.loading = false;
        });
    };

    $scope.load = function () {
        $scope.search($scope.searchQuery, $scope.searchType);
    };
}).controller('candidateCtrl', function ($scope, $modal, commonMethod, baseInfo, DataSource) {

    // init
    commonMethod.init($scope, DataSource);

    $scope.buttons = [
        {key: 'detail', name:'修改',},
        {key: 'pass', name:'Pass'},
        {key: 'forum', name:'讨论'},
    ];

    $scope.filterCandidateStatusList = baseInfo.filterCandidateStatusList;


    $scope.type.name = baseInfo.typeCandidate;
    $scope.defaultNewItem = {
        id: 0
    };
    $scope.type.itemInfos = [
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'title', name: '标题',showType: 'link'},
        {key: 'srcUrl', name: '地址'},
        {key: 'categoryId', name: '分类', status: baseInfo.constTypeCategory, editType: "select"},
        {key: 'duration', name: '时长',showType:'duration', editType: 'not-required'},
        {key: 'desc', name: '备注', editType: 'not-required'},
        // {key: 'category', name: '类别', editType: 'not-required'},
        {key: 'srcType', name: '来源', editType: 'not-required'},
        {key: 'status', name: '状态',status: baseInfo.candidateStatusList, showType: 'select', editType: 'select-not-required'},
        {key: 'createTime', name: '入库时间', showType:'date', editType: 'not-required'},
        {key: 'coverUrl', name: 'Cover 图', showType: 'image', editType: 'not-required'}
    ];

    $scope.filter = function () {
        if ($scope.filter.status != null) {
            $scope.type.status = $scope.filter.status.key;
        }
        $scope.load();
    };

    $scope.searchCandidates = function search(query, type) {
        $scope.loading = true;
        $scope.searchQuery = query;
        $scope.searchType = type;
        DataSource.searchCandidate(query, type, $scope.currentPage, baseInfo.pageSize).then(function (response) {
            $scope.commonList = response.data.itemList;
            $scope.totalItems = response.data.totalItems;
            if ($scope.commonList.length != 0) {
                $scope.emptyText = "";
            } else {
                $scope.emptyText = "没有找到任何相关视频 ";
            }
        }, function () {
            $scope.emptyText = "网络错误";
        }).finally(function () {
            $scope.loading = false;
        });
    };

    $scope.load();


}).controller('forumCtrl', function ($scope, $modal, commonMethod, baseInfo, DataSource) {

    // init
    commonMethod.init($scope, DataSource);

    $scope.buttons = [
        {key: 'detail', name:'详情',},
        {key: 'pass', name:'Pass'},
        {key: 'pick', name:'Pick'},
    ];
    $scope.filterForumStatusList = baseInfo.filterForumStatusList;
    $scope.filterTranslateStatusList = baseInfo.filterTranslateStatusList;
    $scope.filterUploadStatusList = baseInfo.filterUploadStatusList;
    $scope.type.name = baseInfo.typeForums;
    $scope.defaultNewItem = {
        id: 0
    };
    $scope.type.itemInfos = baseInfo.forumItemInfos;
    $scope.filter = function () {
        if ($scope.filter.status != null) {
            $scope.type.status = $scope.filter.status.key;
        }
        if ($scope.filter.translateStatus != null) {
            $scope.type.translateStatus = $scope.filter.translateStatus.key;
        }
        if ($scope.filter.uploadStatus != null) {
            $scope.type.uploadStatus = $scope.filter.uploadStatus.key;
        }
        $scope.load();
    };
    $scope.load();

}).controller('shareConfigCtrl', ["$scope", "$location", "$filter", "$upload", "baseInfo", "DataSource", "ConstFromServer", function ($scope, $location, $filter, $upload, baseInfo, DataSource, ConstFromServer) {

    getShareConfigList(false);
    $scope.pageTypeList = baseInfo.pageTypeList;
    $scope.sharePlatformList = baseInfo.sharePlatformList;

    $scope.editInfoList = ['编辑', '取消编辑'];

    $scope.editShareConfig = function (shareConfig) {
        shareConfig.isEditStatus = 1 ^ shareConfig.isEditStatus;
    };

    $scope.saveShareConfig = function (shareConfig) {
        shareConfig.pageType = shareConfig.pageTypeOption.type;
        shareConfig.sharePlatform = shareConfig.sharePlatformOption.platform;
        DataSource.saveShareConfig(shareConfig).success(shareConfigEditCb).error(function (data) {
            alert('保存失败');
        })
    };

    $scope.addShareConfig = function () {
        var tmp = {
            isEditStatus: 1
        };
        $scope.shareConfigList.push(tmp);
    };

    function shareConfigEditCb(data) {
        if (data.indexOf('true') >= 0) {
            alert('成功');
            getShareConfigList(true);
        } else {
            alert('失败');
        }
    }

    function getShareConfigList() {
        DataSource.getShareConfigList().then(function (response) {
            $scope.shareConfigList = response.data;
            for (var i = 0; i < $scope.shareConfigList.length; i++) {
                $scope.shareConfigList[i].isEditStatus = 0;
                var found = $filter('filter')($scope.pageTypeList, {type: $scope.shareConfigList[i].pageType}, true);
                $scope.shareConfigList[i].pageTypeOption = found[0];
                found = $filter('filter')($scope.sharePlatformList, {platform: $scope.shareConfigList[i].sharePlatform}, true);
                $scope.shareConfigList[i].sharePlatformOption = found[0];
            }
        })
    }
}]).controller('webPageInfoCtrl', ["$scope", "$location", "$filter", "$upload", "baseInfo", "DataSource", "ConstFromServer", function ($scope, $location, $filter, $upload, baseInfo, DataSource, ConstFromServer) {

    getWebPageInfoList(false);
    $scope.pageTypeList = baseInfo.pageTypeList;
    $scope.editInfoList = ['编辑', '取消编辑'];

    $scope.editWebPageInfo = function (webPageInfo) {
        webPageInfo.isEditStatus = 1 ^ webPageInfo.isEditStatus;
    };

    $scope.saveWebPageInfo = function (webPageInfo) {
        webPageInfo.pageType = webPageInfo.pageTypeOption.type;
        DataSource.saveWebPageInfo(webPageInfo).success(webPageInfoEditCb).error(function (data) {
            alert('保存失败');
        })
    };
    $scope.addWebPageInfo = function () {
        var tmp = {
            isEditStatus: 1
        };
        $scope.webPageInfoList.push(tmp);
    };

    function webPageInfoEditCb(data) {
        if (data.indexOf('true') >= 0) {
            alert('成功');
            getWebPageInfoList(true);
        } else {
            alert('失败');
        }
    }

    function getWebPageInfoList() {
        DataSource.getWebPageInfoList().then(function (response) {
            $scope.webPageInfoList = response.data;
            for (var i = 0; i < $scope.webPageInfoList.length; i++) {
                $scope.webPageInfoList[i].isEditStatus = 0;
                var found = $filter('filter')($scope.pageTypeList, {type: $scope.webPageInfoList[i].pageType}, true);
                $scope.webPageInfoList[i].pageTypeOption = found[0];
            }
        })
    }
}]).controller('experimentCtrl', ["$scope", "$routeParams", "$filter", "baseInfo", "DataSource", function ($scope, $routeParams, $filter, baseInfo, DataSource) {

    $scope.experiment = {};
    $scope.experiment.id = $routeParams.id || '0';
    $scope.experimentId = Number($scope.experiment.id);

    $scope.dateFomat = 'yyyy-MM-dd HH:mm:ss';
    $scope.experimentStatusList = baseInfo.experimentStatusList;

    if ($scope.experimentId > 0) {
        DataSource.getExperimentById($scope.experiment.id).then(function (response) {
            $scope.experiment = response.data;
            $scope.startTimeStr = (new Date($scope.experiment.startTime)).toString($scope.dateFomat);
            $scope.endTimeStr = (new Date($scope.experiment.endTime)).toString($scope.dateFomat);
            $scope.whiteListStr = listToStr($scope.experiment.whiteList);
            $scope.udidLastNValuesStr = listToStr($scope.experiment.udidLastNValues);

            var found = $filter('filter')($scope.experimentStatusList, {status: $scope.experiment.status}, true);
            $scope.status = found[0];
        })
    }

    $scope.submit = function () {
        $scope.experiment.startTime = new Date($scope.startTimeStr).getTime();
        $scope.experiment.endTime = new Date($scope.endTimeStr).getTime();
        $scope.experiment.whiteList = parseList($scope.whiteListStr);
        $scope.experiment.udidLastNValues = parseList($scope.udidLastNValuesStr);
        if ($scope.experimentId > 0) {
            $scope.experiment.status = $scope.status.status;
        }
        DataSource.saveExperiment($scope.experiment).success(function (data) {
            if (Boolean(data) == true) {
                alert('保存成功');
            } else {
                alert('保存失败');
            }
        }).error(function () {
            alert('保存失败');
        });
    };

    function parseList(string) {
        if (string) {
            return string.split(",");
        } else {
            return [];
        }
    }

    function listToStr(list) {
        return list.join(",");
    }

}]).controller('contentTabCtrl', function ($scope, commonMethod, baseInfo) {
    commonMethod.init($scope);
    //$scope.setPage = function (pageNo) {
    //    $scope.currentPage = pageNo;
    //};
    //
    //$scope.search = function search(query, type) {
    //    DataSource.searchVideo(query, type).then(function (response) {
    //        $scope.commonList = response.data;
    //        $scope.emptySearchResult = $scope.commonList.length == 0 ? "没有找到任何内容" : "";
    //    });
    //};
    //
    //$scope.pageChanged = function (current) {
    //    $log.log('Page changed to: ' + current);
    //    $scope.currentPage = current;
    //    $scope.search($scope.currentPage, 'SearchTitle');
    //};
    //
    //// do load
    //$scope.search($scope.currentPage, 'SearchTitle');
    $scope.type.name = baseInfo.typeVideo;

}).controller('editLibraryCtrl', function ($scope, commonMethod, baseInfo) {
    // init
    commonMethod.init($scope);
    $scope.editRadio = {
        model: 'modify',
        enabledModes: ['modify', 'delete']
    };
    $scope.type.name = baseInfo.typeVideo;
    $scope.filterLibraryStatusList = baseInfo.filterLibraryStatusList;
    $scope.filterInfoStatusList = baseInfo.filterInfoStatusList;
    $scope.filterVideoStatusList = baseInfo.filterVideoStatusList;
    $scope.filterBoolList = baseInfo.filterBoolList;
    $scope.defaultNewItem = {
        id: 0,
        library: 'NONE',
        type: 'NORMAL',
        infoStatus: 'NOT_CONFIRMED',
        status: 'DEFAULT',
        ad: false
    };

    $scope.type.itemInfos = [
        {key: 'library', name: '推荐性', status: baseInfo.libraryStatusList, showType: 'library', editType: 'select-disabled'},
        //{key: 'issueDate', name: '日报', showType: 'date'},
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'sourceUrl', name: '源地址', isRepeating: false, showType: "hide", editType: 'source-url'},
        {key: 'title', name: '标题'},
        {key: 'titlePgc', name: 'PGC标题', editType: 'titlePgc'},
        {key: 'premiereDate', name: '预设上线时间', showType: "hide", editType: 'date'},
        {key: 'cover', name: 'Cover图', showType: 'cover', editType: 'cover'},
        {key: 'scoreFull', name: '全局得分',  editType: 'disabled'},
        {key: 'scoreRecent', name: '近期得分',  editType: 'disabled'},
        {key: '', name: '视频信息', showType: "hide", editType: 'video'},
        {key: 'type', name: '类型', status: baseInfo.videoTypeList, showType: "hide", editType: "select"},
        {key: 'categoryId', name: '分类', status: baseInfo.constTypeCategory, editType: "select"},
        {key: 'providerId', name: '视频来源', status: baseInfo.constTypeProvider, editType: "select"},
        {key: 'authorId', name: '作者', status: baseInfo.constTypeAuthor, showType: "hide", editType: "select-not-required"},
        {key: 'tailTimePoint', name: '片尾时间', showType: "hide", editType: "not-required"},
        {key: 'description', name: '详情描述', showType: "hide", editType: 'description'},
        {key: 'descriptionPgc', name: 'PGC描述', showType: "hide", editType: 'descriptionPgc'},
        {key: 'dimension', name: '清晰度', editType: "hide"},
        {key: '', name: '播放', showType: "videoPlay", editType: "hide"},
        {key: 'ad', name: '广告', status: baseInfo.filterBoolList, showType: "hide", editType: "select"},
        {key: 'infoStatus', name: 'Check', status: baseInfo.infoStatusList, showType: 'select', editType: "select-disabled"},
        {key: 'status', name: 'status', status: baseInfo.videoStatusList, editType: "select-disabled"},
        {key: '', name: 'tag', showType: "tag", editType: "hide"}
    ];

    $scope.filter = function () {
        if ($scope.filter.library != null) {
            $scope.type.library = $scope.filter.library.key;
        }
        if ($scope.filter.infoStatus != null) {
            $scope.type.infoStatus = $scope.filter.infoStatus.key;
        }
        if ($scope.filter.status != null) {
            $scope.type.status = $scope.filter.status.key;
        }
        if ($scope.filter.belongToIssue != null) {
            $scope.type.belongToIssue = $scope.filter.belongToIssue.key;
        }
        $scope.load();
    };

    $scope.load();
}).controller('editTabCtrl', function ($scope, commonMethod, baseInfo) {
}).controller('dailyLibraryCtrl', function ($scope, $routeParams, $sce, $modal, $location, $filter, baseInfo, DataSource) {
    var tmpDateStr = $routeParams.dateStr || (new Date()).toString('yyyy-MM-dd');
    $scope.ismeridian = false;
    $scope.timePattern24Hour = new RegExp('^([01]\\d|2[0-3]):([0-5]\\d)$');
    $scope.selectedDate = new Date(tmpDateStr);
    $scope.issueTypeList = baseInfo.issueTypeList;

    $scope.dateOptions = {
        formatYear: 'yy',
        startingDay: 1
    };
    $scope.open = function ($event) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.opened = true;
    };

    $scope.format = 'yyyyMMdd';
    $scope.releaseTime = "00:00";
    $scope.issueTime = "00:00";

    $scope.trustSrc = function (src) {
        return $sce.trustAsResourceUrl(src);
    };

    $scope.lastIssue = function () {
        var lastDate = new Date($scope.selectedDate.getTime() - 24 * 60 * 60 * 1000);
        $scope.selectedDate = lastDate;
        $scope.refresh();
    };
    $scope.nextIssue = function () {
        var nextDate = new Date($scope.selectedDate.getTime() + 24 * 60 * 60 * 1000);
        $scope.selectedDate = nextDate;
        $scope.refresh();
    };

    $scope.refresh = function () {
        $scope.loading = true;
        var type = {};
        type.name = baseInfo.typeIssue + '/' + $scope.selectedDate.toString($scope.format);
        DataSource.getList(type).then(function (response) {
            $scope.issues = response.data;
        }, function () {
            alert("加载失败");
        }).finally(function () {
            $scope.loading = false;
        });
    };
    $scope.refresh();
}).controller('IssueCalendarCtrl', function ($scope, $routeParams, $sce, $modal, $location, $filter, baseInfo, DataSource) {
    var tmpDateStr = $routeParams.dateStr || (new Date()).toString('yyyy-MM-dd');
    var weekday = new Array(
        '周日',
        '周一',
        '周二',
        '周三',
        '周四',
        '周五',
        '周六'
    );

    $scope.ismeridian = false;
    $scope.timePattern24Hour = new RegExp('^([01]\\d|2[0-3]):([0-5]\\d)$');
    $scope.selectedDate = new Date(tmpDateStr);

    $scope.dateOptions = {
        formatYear: 'yy',
        startingDay: 1
    };
    $scope.open = function ($event) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.opened = true;
    };

    $scope.format = 'yyyyMMdd';

    $scope.trustSrc = function (src) {
        return $sce.trustAsResourceUrl(src);
    };

    $scope.refresh = function () {
        getIssueList($scope.selectedDate.toString("yyyyMMdd"));
    };

    function getIssueList(date) {
        $scope.loading = true;
        DataSource.getIssueList(date).then(function (response) {
            $scope.issueList = response.data;
            $scope.issueList.reverse();
            for (var i in $scope.issueList) {
                var issue = $scope.issueList[i];
                if (issue.status == 'OFFLINE') {
                    $scope.issueList.splice(i, 1);
                }
            }
            for (var i in $scope.issueList) {
                var issue = $scope.issueList[i];
                var date = new Date(issue.releaseTime);
                issue.dateStr = date.toString('yyyy-MM-dd');
                issue.typeStr = issue.type == 'weekendExtra' ? "号外版" : "";
                issue.weekday = weekday[date.getDay()];
            }
        }).finally(function () {
            $scope.loading = false;
        });
    }
}).controller('authorCtrl', function ($scope, commonMethod, baseInfo, DataSource) {
    // init
    commonMethod.init($scope, DataSource);
    $scope.editRadio = {
        model: 'modify',
        enabledModes: ['delete', 'modify']
    };
    $scope.type.name = baseInfo.typeAuthor;
    $scope.defaultNewItem = {
        id: 0
    };
    $scope.type.itemInfos = [
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'name', name: '名称'},
        {key: 'icon', name: 'icon', showType: 'icon'},
        {key: 'description', name: '描述'},
        {key: 'cover', name: 'Cover 图', showType: 'image', editType: 'not-required'},
        {key: 'latestReleaseTime', name: '最新更新时间', showType: 'date', editType: "hide"}
    ];

    $scope.load();
}).controller('cardCtrl', function ($scope, commonMethod, baseInfo, DataSource) {
    // init
    commonMethod.init($scope, DataSource);
    $scope.editRadio = {
        model: 'modify',
        enabledModes: ['delete', 'modify']
    };
    $scope.type.name = baseInfo.typeCard;
    $scope.defaultNewItem = {
        id: 0,
        title: '',
        description: ''
    };
    $scope.type.itemInfos = [
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'title', name: '标题', editType: 'not-required'},
        {key: 'description', name: '描述', editType: 'not-required'},
        {key: 'imageIos', name: 'iOS Cover图', showType: 'image'},
        {key: 'imageAndroid', name: 'android Cover图', showType: 'image'},
        {key: 'actionUrlIos', name: 'iOS action url'},
        {key: 'actionUrlAndroid', name: 'android action url'},
        {key: 'label', name: '标签文字', showType: 'hide', editType: 'not-required'},
        {key: 'type', name: 'type', status: baseInfo.cardTypeList, editType: "select"}
    ];

    $scope.load();
}).controller('cardGroupCtrl', function ($scope, commonMethod, baseInfo, DataSource) {
    // init
    commonMethod.init($scope, DataSource);
    $scope.editRadio = {
        model: 'modify',
        enabledModes: ['delete', 'modify']
    };
    $scope.type.name = baseInfo.typeCardGroupConfig;
    $scope.defaultNewItem = {
        id: 0,
        updateTime: 0,
        status: 'ready'
    };
    $scope.type.itemInfos = [
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'location', name: '位置', status: baseInfo.cardGroupLocationList, editType: "select"},
        {key: 'updateTime', name: '更新时间', showType: 'date', editType: 'date'},
        {key: 'cardIds', name: '卡片列表', editType: 'int-list'},
        {key: 'status', name: '上线状态', status: baseInfo.statusList, editType: "select-disabled"}
    ];

    $scope.load();
}).controller('feedCardCtrl', function ($scope, commonMethod, baseInfo, DataSource) {
    // init
    commonMethod.init($scope, DataSource);
    $scope.editRadio = {
        model: 'modify',
        enabledModes: ['delete', 'modify']
    };
    $scope.type.name = baseInfo.typeFeedCard;
    $scope.defaultNewItem = {
        id: 0,
        data: '',
        status: 'ready'
    };
    $scope.type.itemInfos = [
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'cardId', name: '卡片ID'},
        {key: 'type', name: 'type', status: baseInfo.feedCardTypeList, editType: "select"},
        {key: 'data', name: 'data', editType: 'not-required'},
        {key: 'startTime', name: '上线时间', showType: 'date', editType: 'date'},
        {key: 'endTime', name: '下线时间', showType: 'date', editType: 'date'},
        {key: 'status', name: '上线状态', status: baseInfo.statusList, editType: "select-disabled"}
    ];

    $scope.load();
}).controller('generateWebActionUrlCtrl', ["$scope", "$upload", "baseInfo", "DataSource", function ($scope, $upload, baseInfo, DataSource) {

    $scope.submit = function () {
        DataSource.generateWebActionUrl($scope.title, $scope.url).success(function (data) {
            $scope.actionUrl = data.replace(/"/g, "");
        }).error(function () {
            alert('失败');
        });
    };
}]).controller('tagTabCtrl', function ($scope, commonMethod, baseInfo) {
}).controller('videoTagCtrl', function ($scope, commonMethod, baseInfo, DataSource) {
    // init
    commonMethod.init($scope, DataSource);
    $scope.editRadio = {
        model: 'delete',
        enabledModes: ['delete']
    };
    $scope.type.name = baseInfo.typeVideoTag;
    $scope.itemsPerPage = 20;
    $scope.defaultNewItem = {
        id: 0,
        videoId: $scope.videoId
    };
    $scope.type.itemInfos = [
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'videoId', name: '视频 ID'},
        {key: 'tagId', name: 'Tag ID', status: baseInfo.constTypeTag}
    ];

    $scope.getTags = function getTags(videoId) {
        $scope.loading = true;
        $scope.videoId = videoId;
        DataSource.getVideoTagList(videoId).then(function (response) {
            $scope.commonList = response.data.itemList;
            $scope.totalItems = response.data.totalItems;
            if ($scope.commonList.length != 0) {
                $scope.emptyText = "";
            } else {
                $scope.emptyText = "该视频还没有 Tag";
            }
        }, function () {
            $scope.emptyText = "网络错误";
        }).finally(function () {
            $scope.loading = false;
        });
    };

    $scope.load = function () {
        $scope.getTags($scope.videoId);
    };
}).controller('tagCtrl', function ($scope, commonMethod, baseInfo, DataSource) {
    // init
    commonMethod.init($scope, DataSource);
    $scope.editRadio = {
        model: 'modify',
        enabledModes: ['delete', 'modify']
    };
    $scope.type.name = baseInfo.typeTag;
    $scope.itemsPerPage = 1000;
    $scope.defaultNewItem = {
        id: 0
    };
    $scope.type.itemInfos = [
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'name', name: 'tag'},
        {key: 'bgPicture', name: 'tag 图片', showType: 'image'},
        {key: 'tagStatus', name: 'status', status: baseInfo.tagStatusList, editType: "select"}
    ];

    $scope.load();
}).controller('tagVideoCtrl', function ($scope, commonMethod, baseInfo, DataSource) {
    // init
    commonMethod.init($scope, DataSource);
    $scope.type.name = baseInfo.typeVideoTag;
    $scope.itemsPerPage = 1000;
    $scope.type.itemInfos = [
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'videoId', name: '视频 ID'},
        {key: 'tagId', name: 'Tag ID', status: baseInfo.constTypeTag}
    ];

    $scope.getVideos = function getVideos(tagId) {
        $scope.loading = true;
        $scope.tagId = tagId;
        DataSource.getTagVideoList(tagId).then(function (response) {
            $scope.commonList = response.data.itemList;
            $scope.totalItems = response.data.totalItems;
            if ($scope.commonList.length != 0) {
                $scope.emptyText = "";
            } else {
                $scope.emptyText = "该 Tag 下没有视频";
            }
        }, function () {
            $scope.emptyText = "网络错误";
        }).finally(function () {
            $scope.loading = false;
        });
    };

    $scope.load = function () {
        $scope.getVideos($scope.tagId);
    };
}).controller('adControlInfoCtrl', function ($scope, commonMethod, baseInfo, DataSource) {
    // init
    commonMethod.init($scope, DataSource);
    $scope.editRadio = {
        model: 'modify',
        enabledModes: ['delete', 'modify']
    };
    $scope.type.name = baseInfo.typeAdControl;
    $scope.defaultNewItem = {
        id: 0,
        date: new Date().getTime(),
        startTime: 0,
        endTime: 0,
        status: 'ready'
    };
    $scope.type.itemInfos = [
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'videoId', name: 'Video ID'},
        {key: 'adVideoLocation', name: '所在页面', status: baseInfo.adVideoLocationList, editType: "select"},
        {key: 'subLocation', name: '页面编号'},
        {key: 'index', name: '位置'},
        {key: 'headerDescription', name: '副标题'},
        {key: 'headerIcon', name: '品牌 logo'},
        {key: 'showLabel', name: '广告标签'},
        {key: 'startTime', name: '上线时间', showType: 'date', editType: 'date'},
        {key: 'endTime', name: '下线时间', showType: 'date', editType: 'date'},
        {key: 'status', name: '上线状态', status: baseInfo.statusList, editType: "select-disabled"}
    ];

    $scope.load();
}).controller('topicTabCtrl', function ($scope, commonMethod, baseInfo) {
}).controller('specialTopicCtrl', function ($scope, commonMethod, baseInfo) {
    // init
    commonMethod.init($scope);
    $scope.editRadio = {
        model: 'modify',
        enabledModes: ['modify', 'delete']
    };
    $scope.type.name = baseInfo.typeTopic;
    $scope.defaultNewItem = {
        id: 0,
        title: '',
        status: 'ready'
    };

    $scope.type.itemInfos = [
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'title', name: '标题', editType: 'not-required'},
        {key: 'imageIos', name: 'IosCover图', showType: 'image'},
        {key: 'imageAndroid', name: 'AndCover图', showType: 'image'},
        {key: 'actionUrlIos', name: 'iOS action url'},
        {key: 'actionUrlAndroid', name: 'android action url'},
        {key: 'releaseTime', name: '发布时间', showType: 'date', editType: 'date'},
        {key: 'feedTopDuration', name: '在 feed 页置顶的时间'},
        {key: 'label', name: '标签文字', showType: 'hide', editType: 'not-required'},
        {key: 'showInListPage', name: '是否出现在列表中'},
        {key: 'status', name: '上线状态', status: baseInfo.statusList, editType: "select-disabled"}
    ];
    $scope.load();
}).controller('lightTopicCtrl', function ($scope, commonMethod, baseInfo, DataSource) {
    // init
    commonMethod.init($scope, DataSource);
    $scope.editRadio = {
        model: 'modify',
        enabledModes: ['delete', 'modify']
    };
    $scope.type.name = baseInfo.typeLightTopic;
    $scope.defaultNewItem = {
        id: 0,
        title: '',
        status: 'ready'
    };
    $scope.type.itemInfos = [
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'title', name: '标题', editType: 'not-required'},
        {key: 'image', name: '列表卡片封面图', showType: 'image'},
        {key: 'headerImage', name: '专题页面内顶图', showType: 'image', editType: 'not-required'},
        {key: 'brief', name: '专题引语', showType: "hide", editType: 'long-text'},
        {key: 'text', name: '专题介绍', showType: "hide", editType: 'long-text'},
        {key: 'actionUrl', name: '跳转地址', editType: 'not-required'},
        {key: 'label', name: '标签文字', showType: 'hide', editType: 'not-required'},
        {key: 'showInListPage', name: '是否出现在列表中'},
        {key: 'releaseTime', name: '上线时间', showType: 'date', editType: 'date'},
        {key: 'endTime', name: '下线时间', showType: 'date', editType: 'date'},
        {key: 'status', name: '上线状态', status: baseInfo.statusList, editType: "select"}
    ];
    $scope.load();
}).controller('lightTopicVideosCtrl', function ($scope, commonMethod, baseInfo, DataSource) {
    // init
    commonMethod.init($scope, DataSource);
    $scope.editRadio = {
        model: 'delete',
        enabledModes: ['delete']
    };
    $scope.type.name = baseInfo.typeLightTopic;
    $scope.itemsPerPage = 20;

    $scope.getVideos = function getVideos(lightTopicId) {
        $scope.loading = true;
        var type = $scope.type;
        $scope.lightTopicId = lightTopicId;
        $scope.topicId = lightTopicId;
        DataSource.getTopicVideoList(type, lightTopicId).then(function (response) {
            $scope.commonList = response.data.itemList;
            $scope.totalItems = response.data.totalItems;
            if ($scope.commonList.length != 0) {
                $scope.emptyText = "";
            } else {
                $scope.emptyText = "该专题下没有视频";
            }
        }, function () {
            $scope.emptyText = "网络错误";
        }).finally(function () {
            $scope.loading = false;
        });
    };

    $scope.addVideo = function addVideo(videoId) {
        var type = {};
        type.name = $scope.type.name;
        var item = {};
        item.id = $scope.lightTopicId;
        var videoIdList = [];
        videoIdList.push(videoId);
        DataSource.addVideos(type, item, videoIdList).then(function () {
            alert('success');
            $scope.load();
        }, function () {
            alert('error');
            $scope.load();
        });
    };

    $scope.saveOrder = function () {
        var type = {};
        type.name = $scope.type.name;
        var commonList = $scope.commonList;
        var item = {};
        item.id = $scope.lightTopicId;
        var videoIdList = [];
        for (var i = 0; i < commonList.length; i++) {
            videoIdList.push(commonList[i].id);
        }
        DataSource.updateVideoOrder(type, item, videoIdList).then(function () {
            alert('success');
            $scope.load();
        }, function () {
            alert('error');
            $scope.load();
        });
    };

    $scope.load = function () {
        $scope.getVideos($scope.lightTopicId);
    };
}).controller('commonConfigCtrl', function ($scope, commonMethod, baseInfo, DataSource) {
    // init
    commonMethod.init($scope, DataSource);
    $scope.editRadio = {
        model: 'modify',
        enabledModes: ['delete', 'modify']
    };
    $scope.type.name = baseInfo.typeCommonConfig;
    $scope.defaultNewItem = {
        id: 0
    };
    $scope.itemsPerPage = 1000;
    $scope.type.itemInfos = [
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'name', name: 'name'},
        {key: 'value', name: 'value'}
    ];

    $scope.load();
}).controller('adTrackTabCtrl', function ($scope, commonMethod, baseInfo) {
}).controller('adTrackInfoCtrl', function ($scope, commonMethod, baseInfo, DataSource) {
    // init
    commonMethod.init($scope, DataSource);
    $scope.editRadio = {
        model: 'modify',
        enabledModes: ['delete', 'modify']
    };
    $scope.type.name = baseInfo.typeAdTrack;
    $scope.defaultNewItem = {
        id: 0,
        status: 'SHOW'
    };
    $scope.type.itemInfos = [
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'adLocation', name: '广告位', status: baseInfo.adLocationList, editType: 'select'},
        {key: 'identity', name: 'identity'},
        {key: 'organization', name: '检测平台', status: baseInfo.adTrackOrganizationList, editType: 'select'},
        {key: 'viewUrl', name: 'viewUrl'},
        {key: 'clickUrl', name: 'clickUrl'},
        {key: 'playUrl', name: 'playUrl'},
        {
            key: 'viewUrlStatus',
            name: 'viewUrl状态',
            status: baseInfo.baseStatus,
            editType: 'select'
        },
        {
            key: 'clickUrlStatus',
            name: 'clickUrl状态',
            status: baseInfo.baseStatus,
            editType: 'select'
        },
        {
            key: 'playUrlStatus',
            name: 'playUrl状态',
            status: baseInfo.baseStatus,
            editType: 'select'
        },
        {
            key: 'status',
            name: 'status',
            status: baseInfo.baseStatus,
            showType: 'select',
            editType: 'select',
            quickEdit: true
        }
    ];

    $scope.load();
}).controller('adTrackAdvancingInfoCtrl', function ($scope, commonMethod, baseInfo, DataSource) {
    // init
    commonMethod.init($scope, DataSource);
    $scope.editRadio = {
        model: 'modify',
        enabledModes: ['delete', 'modify']
    };
    $scope.type.name = baseInfo.typeAdTrackAdvancing;
    $scope.defaultNewItem = {
        id: 0,
        status: 'ready'
    };
    $scope.type.itemInfos = [
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'adTrackInfoId', name: '广告监测 id'},
        {key: 'proportion', name: '抽取用户比例', editType: 'not-required'},
        {key: 'regressionRate', name: '衰减速度', editType: 'not-required'},
        {key: 'startTime', name: '上线时间', showType: 'date', editType: 'date'},
        {key: 'endTime', name: '下线时间', showType: 'date', editType: 'date'},
        {key: 'status', name: '上线状态', status: baseInfo.statusList, editType: "select-disabled"}
    ];

    $scope.load();
}).controller('replyCtrl', function ($scope, commonMethod, baseInfo, DataSource) {
    // init
    commonMethod.init($scope, DataSource);
    $scope.editRadio = {
        model: 'delete',
        enabledModes: ['delete', 'modify']
    };
    $scope.type.name = baseInfo.typeReply;
    $scope.defaultNewItem = {
        id: 0
    };
    $scope.type.itemInfos = [
        {key: 'id', name: 'id', editType: 'disabled'},
        {key: 'videoId', name: '视频 id', editType: 'disabled'},
        {key: 'videoTitle', name: '视频标题', editType: 'disabled'},
        {key: 'message', name: '内容', editType: 'disabled'},
        {key: 'createTime', name: '创建时间', showType: 'date', editType: 'disabled'},
        {key: 'likeCount', name: '赞数', editType: 'disabled'},
        {key: 'reportCount', name: '举报数', editType: 'disabled'},
        {key: '', name: '清零', showType: 'resetReplyReport', editType: 'hide'}
    ];

    $scope.load();
}).controller('registerCtrl', ["$scope", "$routeParams", "$filter", "baseInfo", "DataSource", function ($scope, $routeParams, $filter, baseInfo, DataSource) {

    $scope.experiment = {};

    $scope.submit = function () {
        $scope.experiment.username = $scope.username;
        $scope.experiment.phone = $scope.phone;
        $scope.experiment.password = $scope.password;
        DataSource.adminRegister($scope.experiment).success(function (data) {
            if (Boolean(data) == true) {
                alert('注册成功');
            } else {
                alert('注册失败');
            }
        }).error(function () {
            alert('注册失败');
        });
    };

    function parseList(string) {
        if (string) {
            return string.split(",");
        } else {
            return [];
        }
    }

    function listToStr(list) {
        return list.join(",");
    }

}]);
