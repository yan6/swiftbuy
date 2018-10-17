/**
 * 商品详情页
 * @type {{init: detail.init, getShopData: detail.getShopData}}
 */

var detail = {
    curUserName: '',
    curShopId: '',
    init: function () {
        var curShopId = location.search && location.search.split('=')[1];

        this.curUserName = this.getCookie('userName');

        this.getShopData(curShopId);

        this.sureBuyBtnEvent();

        $('.j-back-index').attr('href', '/swiftbuy/views/index.html?username=' + this.curUserName);
    },
    getShopData: function (shopId) {
        var that = this;
        var shopDataArr = [];

        $.ajax({
            type: "get",
            dataType: "json",
            url: "http://47.93.189.233:8080/swiftbuy-webapp/api/goods/detail",
            data: {
                id: shopId
            },
            success: function (result) {
                shopDataArr[0] = result.goods;
                shopDataArr[1] = result.business;
                shopDataArr[2] = result.replyBeanList;

                that.curShopId = result.goods.id;

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
                '<p class="shop-buy clearfix">' +
                '<a class="shop-btn j-shop-btn">加入购物车</a>' +
                '<a class="buy-btn j-shop-buy">购 买</a>' +
                '</p>' +
                '</div>';
            $(renderParDom).html(detailShop);

            // 调用添加购物车事件
            that.addShopEvent();
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
        var replyDate = !!date && new Date(date) || new Date();

        var year = replyDate.getFullYear(),
            month = replyDate.getMonth() + 1,
            day = replyDate.getDate(),
            hours = replyDate.getHours(),
            minutes = replyDate.getMinutes(),
            seconds = replyDate.getSeconds();

        return year + '年' + month + '月' + day + '日' + ' ' + hours + '时' + minutes + '分' + seconds + '秒';
    },
    addShopEvent: function() {
        var that = this;
        // 加入购物车
        $('body').on('click', '.j-shop-btn', function (e) {
            e.preventDefault();
            var ajaxData = {
                goodsId: that.curShopId,
                username: that.curUserName
            };
            $.ajax({
                type: "get",
                dataType: "json",
                url: "http://47.93.189.233:8080/swiftbuy-webapp/api/shoppingCart/add",
                data: ajaxData, // JSON.stringify(ajaxData),
                success: function (result) {
                    if (result && result.resultCode === 200) {
                        alert('添加购物车成功！');
                    } else {
                        alert('添加购物车失败！');
                    }
                },
                error: function (err) {
                    alert("购买失败！" + err);
                }
            });
        });

        // 购买
        $('body').on('click', '.j-shop-buy', function (e) {
            e.preventDefault();
            $('input[name=goodsId]').val(that.curShopId);
            $('input[name=username]').val(that.curUserName);
            $('.j-shop-buy-dialog').show();
        });
    },
    sureBuyBtnEvent: function () {
        $('.j-sure-buy-btn').on('click', function (e) {
            e.preventDefault();
            $.ajax({
                type: "get",
                dataType: "json",
                url: "http://47.93.189.233:8080/swiftbuy-webapp/api/buy/buyShop",
                data: $('.shop-buy-dialog-form').serialize(),
                success: function (result) {
                    if (result && result.resultCode === 200) {
                        alert('购买商品成功!');
                        $('.j-shop-buy-dialog').hide();
                    } else {
                        alert('购买失败!');
                    }
                },
                error: function (err) {
                    alert("购买失败！" + err);
                }
            });
        });

        $('.j-close-dialog').on('click', function (e) {
            e.preventDefault();
            $('.j-shop-buy-dialog').hide();
        })
    },
    getCookie: function(c_name) {
        if (document.cookie.length>0)
        {
            c_start=document.cookie.indexOf(c_name + "=")
            if (c_start!=-1)
            {
                c_start=c_start + c_name.length+1
                c_end=document.cookie.indexOf(";",c_start)
                if (c_end==-1) c_end=document.cookie.length
                return unescape(document.cookie.substring(c_start,c_end))
            }
        }
        return ""
    }
};

detail.init();
