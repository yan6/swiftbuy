var shopList = {
    curUserName: '',
    init: function () {
        var curSearQueryArr = location.search.split('&');
        var curSearchQuery = curSearQueryArr[0].split('=')[1];
        $('input[name=list-search]').val(decodeURIComponent(curSearchQuery));

        this.curUserName = this.getCookie('userName');

        if (!!curSearQueryArr[1].split('=')[1]) {
            // 查看购物车列表
            this.getAllGoodsDataEvent();
        } else {
            // 搜索数据列表
            this.getSearchData(curSearchQuery);
        }

        this.searchBtnEvent();

        $('.j-back-index').attr('href', '/swiftbuy-webapp/swiftbuy/views/index.html?username=' + this.curUserName);
    },
    searchBtnEvent: function () {
        var that = this;

        $('.j-list-search-btn').on('click', function (e) {
            e.preventDefault();
            var curSearchData = $('input[name=list-search]').val();

            that.getSearchData(curSearchData);
        });
    },
    getSearchData: function (queryData) {
        var that = this;

        $.ajax({
            type: "get",
            dataType: "json",
            url: "http://47.93.189.233:8080/swiftbuy-webapp/api/search",
            data: {
                query: decodeURIComponent(queryData),
                start: 0,
                num: 10
            },
            success: function (result) {
                that.renderShopListData(result);
            },
            error: function () {
                alert("获取商品详情失败！");
            }
        });
    },
    renderShopListData: function (shopListData) {
        var shopListHtml = '';
        if (!!shopListData) {
            for (var i = 0, len = shopListData.length; i < len; i++) {
                shopListHtml += '<li class="shop-list-item clearfix">' +
                    '<img class="shop-list-item-img" src="'+ shopListData[i].image +'" alt="">' +
                    '<div class="shop-list-item-info">' +
                    '<p class="name">'+ shopListData[i].name +'</p>' +
                    '<p class="description">'+ shopListData[i].description +'</p>' +
                    '<p class="price-num clearfix">' +
                    '<span class="price">'+ shopListData[i].priceBase +'元</span>' +
                    '<span class="num">'+ shopListData[i].remainCount +'件</span>' +
                    '</p>' +
                    '</div>' +
                    '</li>';
            }
        } else {
            shopListHtml += '<li class="shop-list-item-no">搜索数据为空……</li>';
        }

        $('.j-list-content-detail').html(shopListHtml);
    },
    getAllGoodsDataEvent: function () {
        var that = this;

        $.ajax({
            type: "get",
            dataType: "json",
            url: "http://47.93.189.233:8080/swiftbuy-webapp/api/shoppingCart/list",
            data: {
                username: that.curUserName
            },
            success: function (result) {
                var goodsList = [];
                for (var i = 0, len = result.length; i < len; i++) {
                    goodsList.push(result[i].goods);
                }
                that.renderShopListData(goodsList);
            },
            error: function () {
                alert("获取商品失败！");
            }
        });
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

shopList.init();
