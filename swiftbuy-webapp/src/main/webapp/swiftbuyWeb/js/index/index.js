/**
 * 首页数据
 * @type {{init: shopIndex.init, indexSlider: shopIndex.indexSlider, addressLink: shopIndex.addressLink, indexAddDefaultData: shopIndex.indexAddDefaultData, renderShopList: shopIndex.renderShopList}}
 */

var shopIndex = {
    shopListSearchType: '电子产品',
    init: function () {
        var nameSearchData = location.search.split('=') && location.search.split('=')[1];
        $('.j-detail-link').html(decodeURIComponent(nameSearchData));

        this.setCookie('userName', decodeURIComponent(nameSearchData));

        // 首页 slider
        this.indexSlider();
        // 地址联动
        this.addressLink();
        // default data
        this.indexAddDefaultData();

        this.searchAll();
        this.selectedSearchType();
        this.searchShopList();
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
        $("#city-picker1").citypicker({
            province: "北京市",
            city: "北京市",
            district: "昌平区"
        });
    },
    indexAddDefaultData: function () {
        var that = this;
        var renderAllData = [];
        var selectedDefaultData = '?categoryName=电子产品&province=北京市&city=北京市&county=昌平区';
        var selectedApi = 'http://47.93.189.233:8080/api/selected/recentGoodsList' + selectedDefaultData;
        var recommendApi = 'http://47.93.189.233:8080/api/selected/recommendGoodsList';
        var hotApi = 'http://47.93.189.233:8080/api/selected/hotGoodsList';

        $.when($.ajax(selectedApi), $.ajax(recommendApi), $.ajax(hotApi)).then(function (resSelectedData, resRecommendData, resHotData) {
            renderAllData[0] = resSelectedData && resSelectedData[0].goodsBeans;
            renderAllData[1] = resRecommendData && resRecommendData[0];
            renderAllData[2] = resHotData && resHotData[0];

            that.renderShopList(renderAllData);
        });
    },
    renderShopList: function (renderData) {
        for(var i = 0, leni = renderData.length; i < leni; i++) {
            var renderListStr = '';
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
    },
    searchAll: function () {
        $('.j-search-detail-btn').on('click', function (e) {
            e.preventDefault();
            var curSearchQuery = $('input[name=search]').val();
            window.location.href = '/swiftbuy/views/shop-list.html?search=' + curSearchQuery + '&goodsList=';
        })
    },
    selectedSearchType: function () {
        var that = this;
        $('.style-detail-list').on('click', function (e) {
            e.preventDefault();
            e.stopPropagation();
            var curClickDom = $(e.currentTarget),
                curTypeInfo = curClickDom.find('input[name=search-type]').val();

            curClickDom.addClass('selected-list').siblings().removeClass('selected-list');
            that.shopListSearchType = curTypeInfo;
        });
    },
    searchShopList: function () {
        var that = this;
        var curSearchAddress = '',
            addressArr = '';

        $('.j-address-search-btn').on('click', function (e) {
            e.preventDefault();
            curSearchAddress = $('input[name=address]').val();
            addressArr = curSearchAddress && curSearchAddress.split('/');

            $.ajax({
                type: "get",
                dataType: "json",
                url: "http://47.93.189.233:8080/api/selected/recentGoodsList",
                data: {
                    categoryName: that.shopListSearchType,
                    province: addressArr[0] || '北京市',
                    city: addressArr[1] || '北京市',
                    county: addressArr[2] || '昌平区'
                },
                success: function (result) {
                    var renderListStr = '';
                    if (!!result) {
                        for(var i = 0, len = result.goodsBeans.length; i < len; i++) {
                            renderListStr += '<li class="shop-content-item clearfix">' +
                                '<img class="item-img" src="'+ result.goodsBeans[i].image +'" alt="">' +
                                '<div class="item-detail">' +
                                '<p class="name">' +
                                '<a href="/swiftbuy/views/detail.html?id='+ result.goodsBeans[i].id +'">'+ result.goodsBeans[i].name +'</a>' +
                                '</p>' +
                                '<p class="description">'+ result.goodsBeans[i].description +'</p>' +
                                '<p class="price-num clearfix">' +
                                '<span class="price">'+ result.goodsBeans[i].priceBase +'元</span>' +
                                '<span class="num">'+ result.goodsBeans[i].remainCount +'件</span></p>' +
                                '</div>' +
                                '</li>';
                        }
                    } else {
                        renderListStr += '<li class="shop-content-item-no">暂无搜索数据……</li>';
                    }

                    $('.j-shop-content-list0').html(renderListStr);
                },
                error: function () {
                    alert("搜索精选商品失败！");
                }
            });
        });
    },
    setCookie: function(c_name,value,expiredays) {
        var exdate = new Date();
        exdate.setDate(exdate.getDate()+expiredays);
        document.cookie = c_name + "=" + escape(value) + ((expiredays==null) ? "" : ";expires=" + exdate.toGMTString())
    }
};

shopIndex.init();