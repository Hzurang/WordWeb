$('#down').click(function () {
    var wordId = $('#hidden_input1').val();
    var value = parseInt(wordId);
    $.ajax({
        //跳转 url
        url: "/KnowToUnKnow/"+value,
        type: "Post",
        data: value,
        datatype: "HTML",
        success: function (data) {
            var obj = JSON.parse(data);
            if (obj.msg === "success") {
                location.href = "/testByEnglish";
            }
        }
    })
})

$('#show').click(function () {
    var wordId = $('#hidden_input1').val();
    var value = parseInt(wordId);
    $.ajax({
        //跳转 url
        url: "/collectionWordByTest/"+value,
        type: "Post",
        data: value,
        datatype: "HTML",
        success: function (data) {
            var obj = JSON.parse(data);
            if (obj.msg === "success") {
                alert("收藏成功，可前往单词本查看");
            } else if (obj.msg === "该单词已收藏") {
                alert("该单词已收藏");
            }
        }
    })
})

$('#next').click(function () {
    var value = $('#hidden_input1').val();
    var wordId = parseInt(value);
    var inWord = $('#inputWord').val();
    var pram = {
        wordId: wordId,
        inWord: inWord,
    }
    $.ajax({
        //跳转 url
        url: "/WordTestByChi/"+value,
        type: "Post",
        data: pram,
        datatype: "HTML",
        success: function (data) {
            var obj = JSON.parse(data);
            if (obj.msg === "right") {
                location.href = "/testByEnglish";
            } else if (obj.msg === "error") {
                location.href = "/testByEnglish";
            }
        }
    })
})

$('#out').click(function () {
    window.location.href = "/selectWord";
})