//导航菜单
function navList(id) {
    var $obj = $("#nav_dot"), $item = $("#J_nav_" + id);
    $item.addClass("on").parent().removeClass("none").parent().addClass("selected");
    $obj.find("h4").hover(function () {
        $(this).addClass("hover");
    }, function () {
        $(this).removeClass("hover");
    });
    $obj.find("p").hover(function () {
        if ($(this).hasClass("on")) { return; }
        $(this).addClass("hover");
    }, function () {
        if ($(this).hasClass("on")) { return; }
        $(this).removeClass("hover");
    });
    $obj.find("h4").click(function () {
        var $div = $(this).siblings(".list-item");
        if ($(this).parent().hasClass("selected")) {
            $div.slideUp(600);
            $(this).parent().removeClass("selected");
        }
        if ($div.is(":hidden")) {
            $("#nav_dot li").find(".list-item").slideUp(600);
            $("#nav_dot li").removeClass("selected");
            $(this).parent().addClass("selected");
            $div.slideDown(600);

        } else {
            $div.slideUp(600);
        }
    });
}

/****表格隔行高亮显示*****/
window.onload=function(){
    var changeFormId = 0;

    oTable=document.getElementById("tab");//找表格
    aTr=document.getElementsByTagName("tr");//找所有的行
    for(i=0;i<aTr.length;i++){
        if(i%2==0){
            aTr[i].style.background="#fff";
        }else{
            aTr[i].style.background="#ccc";
        };
    };

    // 切换form框
    $('.j-add-new-data').on('click', function (e) {
        var $curClickDom = $(e.currentTarget);
        changeFormId = $curClickDom.data('id');

        $('.j-add-manage-form' + changeFormId).show().siblings('form').hide();
    });

    // 提交按钮
    $('.j-manage-form-btn').on('click', function (e) {
        e.preventDefault();
        // user manage /admin/user/insert
        // business manage  /admin/business/testRegister
        // shop manage /admin/goods/testInsert
        var ajaxUrl = [
            'http://localhost:8081/admin/user/insert',
            'http://localhost:8081/admin/business/testRegister',
            'http://localhost:8081/admin/goods/testInsert'
        ];

        $.ajax({
            type: "get",
            dataType: "json",
            url: ajaxUrl[changeFormId],
            data: $('.j-add-manage-form' + changeFormId).serialize(),
            success: function (result) {
                if (result && result.resultCode === 200) {
                    alert('添加成功！');
                } else {
                    alert('添加信息时', result.resultMessage);
                }
            },
            error: function () {
                alert("添加信息失败！");
            }
        });
    })
};
