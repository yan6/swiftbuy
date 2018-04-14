/**
 * 首页数据
 * @type {{init: shopIndex.init, indexSlider: shopIndex.indexSlider, addressLink: shopIndex.addressLink, indexAddDefaultData: shopIndex.indexAddDefaultData, renderShopList: shopIndex.renderShopList}}
 */

var shopIndex = {
    init: function () {
        // 首页 slider
        this.indexSlider();
        // 地址联动
        this.addressLink();
        // default data
        this.indexAddDefaultData();
    },
    indexSlider: function () {
        var options = {
            $AutoPlay: 1,
            $Idle: 1500
        };
        // slider
        new $JssorSlider$("index-slider", options);
    },
    addressLink: function () {
        // 三级联动
        $("#city-picker1").citypicker();
    },
    indexAddDefaultData: function () {
        var that = this;
        var renderAllData = [];
        var selectedDefaultData = '?categoryName=电子产品&province=北京市&city=北京市&county=昌平区';
        var selectedApi = 'http://localhost:8080/api/selected/recentGoodsList' + selectedDefaultData;
        var recommendApi = 'http://localhost:8080/api/selected/recommendGoodsList';
        var hotApi = 'http://localhost:8080/api/selected/hotGoodsList';

        $.when($.ajax(selectedApi), $.ajax(recommendApi), $.ajax(hotApi)).then(function (resSelectedData, resRecommendData, resHotData) {
            renderAllData[0] = resSelectedData && resSelectedData[0].goodsBeans;
            renderAllData[1] = resRecommendData && resRecommendData[0];
            renderAllData[2] = resHotData && resHotData[0];

            that.renderShopList(renderAllData);
        });
    },
    renderShopList: function (renderData) {
        var renderListStr = '';

        for(var i = 0, leni = renderData.length; i < leni; i++) {
            for(var j = 0, lenj = renderData[i].length; j < lenj; j++) {
                renderListStr += '<li class="shop-content-item clearfix">' +
                    '<img class="item-img" src="'+ renderData[i][j].image +'" alt="">' +
                    '<div class="item-detail">' +
                    '<p class="name">' +
                    '<a href="/swiftbuy/views/detail.html?id='+ renderData[i][j].id +'">'+ renderData[i][j].name +'</a>' +
                    '</p>' +
                    '<p class="description">'+ renderData[i][j].description +'</p>' +
                    '<p class="price-num clearfix">' +
                    '<span class="price">'+ renderData[i][j].priceBase +'元</span>' +
                    '<span class="num">'+ renderData[i][j].remainCount +'件</span></p>' +
                    '</div>' +
                    '</li>';
            }

            var $renderListDom = $('.j-shop-content-list' + i);
            $renderListDom.html(renderListStr);
        }
    }
};

shopIndex.init();