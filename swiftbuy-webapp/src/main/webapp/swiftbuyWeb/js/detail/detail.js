/**
 * 商品详情页
 * @type {{init: detail.init, getShopData: detail.getShopData}}
 */

var detail = {
    init: function () {
        var curShopId = location.search && location.search.split('=')[1];

        this.getShopData(curShopId);
    },
    getShopData: function (shopId) {
        var that = this;
        var shopDataArr = [];

        $.ajax({
            type: "get",
            dataType: "json",
            url: "http://localhost:8080/api/goods/detail",
            data: {
                id: shopId
            },
            success: function (result) {
                shopDataArr[0] = result.goods;
                shopDataArr[1] = result.business;
                shopDataArr[2] = result.replyBeanList;

                that.renderShopDetailDom(shopDataArr);
            },
            error: function () {
                alert("获取商品详情失败！");
            }
        });
    },
    renderShopDetailDom: function (shopData) {
        var that = this;
        var detailShop = '';
        var detailBusiness = '';
        var detailReply = '';

        for(var i = 0, len = shopData.length; i < len; i++) {
            if (i === 0) {
                renderShopHtml(shopData[i], '.j-detail-info' + i);
            } else if (i === 1) {
                renderBusinessHtml(shopData[i], '.j-detail-info' + i);
            } else if (i === 2) {
                renderReplyHtml(shopData[i], '.j-detail-info' + i);
            }
        }

        function renderShopHtml(dataList, renderParDom) {
            detailShop += '<img class="info-img" src="'+ dataList.image +'">' +
                '<div class="info-main">' +
                '<p class="name">'+ dataList.name +'</p>' +
                '<p class="description">'+ dataList.description +'</p>' +
                '<p class="price-num clearfix">' +
                '<span class="price">'+ dataList.priceBase +'元</span>' +
                '<span class="num">'+ dataList.remainCount +'件</span>' +
                '</p>' +
                '</div>';
            $(renderParDom).html(detailShop);
        }
        function renderBusinessHtml(dataList, renderParDom) {
            detailBusiness += '<img class="business-img" src="'+ dataList.icon +'">' +
                '<div class="business-detail">' +
                '<p class="name">'+ dataList.name +'</p>' +
                '<p class="description">'+ dataList.description +'</p>' +
                '<p class="address"><span>'+ dataList.city +'</span> > <span>'+ dataList.county +'</span></p>' +
                '</div>';
            $(renderParDom).html(detailBusiness);
        }
        function renderReplyHtml(dataList, renderParDom) {
            for(var j = 0, leng = dataList.length; j < leng; j++) {
                detailReply += '<li class="reply-list">' +
                    '<p class="reply-list-info">'+ dataList[j].content +'</p>' +
                    '<p class="reply-list-date">'+ that.formatDate(dataList[j].createTime) +'</p>' +
                    '</li>';
            }
            $(renderParDom).html(detailReply);
        }
    },
    formatDate: function (date) {
        var replyDate = !!date && date || new Date().getTime();

        var year = replyDate.getFullYear(),
            month = replyDate.getMonth() + 1,
            day = replyDate.getDate(),
            hours = replyDate.getHours(),
            minutes = replyDate.getMinutes(),
            seconds = replyDate.getSeconds();

        return year + '年' + month + '月' + day + '日' + ' ' + hours + '时' + minutes + '分' + seconds + '秒';
    }
};

detail.init();
