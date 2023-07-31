$('#cet4D1').click(function () {
    $('#cet4BT').click(function () {
        var num = $('#cet4IN').val()
        var para = {
            num: num,
        }
        $.ajax({
            //跳转 url
            url: "/studyWord/CET4",
            type: "Post",
            data: para,
            datatype: "HTML",
            success: function (data) {
                if (data === '请重置学习进度') {
                    alert("请返回点击重置按钮");
                }
                var num = data;
                console.log(num);
                window.location.href = "/studyWord/"+num;
            }
        })
    })
});

$('#cet4C1').click(function () {
    $('#cet4BTReset').click(function () {
        $.ajax({
            //跳转 url
            url: "/resetWord/CET4",
            type: "Post",
            datatype: "HTML",
            success: function (data) {
                if (data === '重置成功') {
                    alert("重置成功");
                    window.location.href = "/selectWord";
                }
            }
        })
    })
})