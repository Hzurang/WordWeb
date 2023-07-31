$('#next').click(function () {
    var str1 = $('#in').val();
    var str2 = $('#word_name').text();
    console.log("2222" + str2);
    if (str1 !== str2) {
        alert("拼写不正确，请重新拼写");
    } else {
        var wordId = $('#hidden_input2').val();
        var value = parseInt(wordId);
        $.ajax({
            //跳转 url
            url: "/KnowWord/"+value,
            type: "Post",
            data: value,
            datatype: "HTML",
            success: function (data) {
                var obj = JSON.parse(data);
                if (obj.msg === "success") {
                    location.href = "/studyWord/"+obj.num;
                }
            }
        })
    }
})

$('#remember').click(function () {
    var wordId = $('#hidden_input2').val();
    var value = parseInt(wordId);
    $.ajax({
        //跳转 url
        url: "/KnowWord/"+value,
        type: "Post",
        data: value,
        datatype: "HTML",
        success: function (data) {
            var obj = JSON.parse(data);
            if (obj.msg === "success") {
                location.href = "/studyWord/"+obj.num;
            }
        }
    })
})

$('#gift').click(function () {
    var wordId = $('#hidden_input2').val();
    var value = parseInt(wordId);
    $.ajax({
        //跳转 url
        url: "/collectionWord/"+value,
        type: "Post",
        data: value,
        datatype: "HTML",
        success: function (data) {
            var obj = JSON.parse(data);
            if (obj.msg === "200") {
                $('#gift').attr('src', obj.src);
            } else if (obj.msg === "该单词已收藏") {
                alert(obj.msg);
            }
        }
    })
})

$('#forget').click(function () {
    var wordId = $('#hidden_input2').val();
    var value = parseInt(wordId);
    $.ajax({
        //跳转 url
        url: "/unKnowWord/"+value,
        type: "Post",
        data: value,
        datatype: "HTML",
        success: function (data) {
            var obj = JSON.parse(data);
            var num = obj.wordId;
            var vv = parseInt(num);
            location.href = "/studyWord/"+vv;
        }
    })
})