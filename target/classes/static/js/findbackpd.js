$("#findBack").click(function () {
    var para = getUserFindBackInfo()
    var reg2 = /^[a-zA-Z0-9]{6,18}$/;
    var reg3 = /^((13\d)|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[0,1,3,5,7,8])|(18[0-9])|(19[8,9]))\d{8}/;
    var reg4 = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
    if (para.findBackWay === 0) {
        if (!reg3.test(para.userNum) || para.userNum.length !== 11 || para.userNum === "") {
            alert("手机号码格式错误");
            return;
        }
    }
    if (para.findBackWay === 1) {
        if (!reg4.test(para.userNum) || para.userNum === "") {
            alert("邮箱号码格式错误");
            return;
        }
    }
    if (para.pwd !== para.pwd_second) {
        alert("两次密码输入不一致，请重新输入");
        return;
    }
    if (para.pwd === null || para.pwd === "") {
        alert("密码不能为空");
        return;
    }
    if (!reg2.test(para.pwd)) {
        alert("密码格式错误。长度需符合8-16位，需包含数字和英文字母。");
        return;
    }
    $.ajax({
        //跳转 url
        url: "/user/findBackPassword",
        type: "Post",
        data: para,
        datatype: "HTML",
        success: function (data) {
            if (data === "找回成功") {
                alert(data);
                window.location.href = "/";
            }
        }
    })
});

function getCurrentTabIndex() {
    var $tabs = $('#findBack_tabs').children('li').children('a');
    var i = 0;

    $tabs.each(function () {
        var $tab = $(this);
        if ($tab.hasClass('active')) {
            return false;
        } else {
            i++;
        }
    });
    return i;
}

function getUserFindBackInfo() {
    var way = getCurrentTabIndex()
    var accounting
    var password
    var password_second
    var code = $("#vc").val()
    switch (way) {
        case 0:
            accounting = $("#user_tel").val()
            password = $('#tel_pw').val()
            password_second = $('#tel_pw1').val()
            break;
        case 1:
            accounting = $('#user_mail').val()
            password = $('#mail_pw').val()
            password_second = $('#mail_pw1').val()
            break;
        case 2:
            break;
    }
    var para = {
        //登录方式
        findBackWay: way,
        //用户登录账号
        userNum: accounting,
        pwd: password,
        pwd_second: password_second,
        code: code
    };
    return para;
}

var InterValObj; //timer变量，控制时间
var count = 60; //间隔函数，1秒执行
var curCount;//当前剩余秒数
$('#getVc').on('click', function () {
    var reg1 = /^((13\d)|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[0,1,3,5,7,8])|(18[0-9])|(19[8,9]))\d{8}/;
    var reg2 = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
    var para = getUserFindBackInfo();
    var data = {
        findBackWay: para.findBackWay,
        userNum: para.userNum,
    }
    if (data.userNum == null || data.userNum === "") {
        alert("请先输入手机号或邮箱号噢");
        return;
    }
    if (data.findBackWay === 0) {
        if (!reg1.test(data.userNum) || data.userNum.length !== 11) {
            alert("手机号码格式错误");
            return;
        }
    }
    if (data.findBackWay === 1) {
        if (!reg2.test(data.userNum)) {
            alert("邮箱号码格式错误");
            return;
        }
    }
    $.ajax({
        //跳转 url
        url: "/user/findBackPassword/code",
        type: "Post",
        data: data,
        datatype: "HTML",
        success: function (data) {
            eval(data);
        }
    })
    curCount = count;
    //设置button效果，开始计时
    $("#getVc").attr("disabled", "disabled");
    $("#getVc").addClass('disable');
    InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
});

function SetRemainTime() {
    if (curCount == 0) {
        window.clearInterval(InterValObj);//停止计时器
        $("#getVc").removeAttr("disabled");//启用按钮
        $("#getVc").removeClass('disable');
        $("#getVc").text("重新获取");
    } else {
        curCount--;
        $("#getVc").text("" + curCount + "s后获取");
    }
}