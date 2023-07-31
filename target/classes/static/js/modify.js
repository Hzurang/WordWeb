$("#modify").click(function () {
    var name = $('#text-input1').val()
    var para = {
        userName: name,
    };
    $.ajax({
        //跳转 url
        url: "/user/modify",
        type: "Post",
        data: para,
        datatype: "HTML",
        success: function (data) {
            if (data === "更新成功") {
                alert("更新成功")
                window.location.href = "main";
            }
        }
    })
});