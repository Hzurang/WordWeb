$('#move').click(function () {
    var wordId = $('#hidden_input1').val();
    var value = parseInt(wordId);
    $.ajax({
        //跳转 url
        url: "/DeleteCollection/"+value,
        type: "Post",
        data: value,
        datatype: "HTML",
        success: function (data) {
            if (data === "取消成功") {
                console.log(111111111)
                location.href = "/toCollection";
            }
        }
    })
})