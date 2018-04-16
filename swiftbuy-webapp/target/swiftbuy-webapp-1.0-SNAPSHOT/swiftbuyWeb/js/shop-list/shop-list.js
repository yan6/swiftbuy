var shopList = {
    init: function () {
        var curSearchQuery = location.search.split('=')[1];
        $('input[name=list-search]').val(decodeURIComponent(curSearchQuery));
        this.getSearchData(curSearchQuery);

        this.searchBtnEvent();
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
            url: "http://localhost:8080/api/search",
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
    }
};

shopList.init();
