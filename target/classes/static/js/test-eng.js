$("#bt1").click(function () {
    var str = $("#bt1").text()
    var inWordChi = str.slice(2)
    var value = $('#hidden_input1').val()
    var wordId = parseInt(value)
    var pram = {
        wordId: wordId,
        inWordChi: inWordChi,
    }
    $.ajax({
        //跳转 url
        url: "/WordTestByEng/"+value,
        type: "Post",
        data: pram,
        datatype: "HTML",
        success: function (data) {
            var obj = JSON.parse(data);
            if (obj.msg === "right") {
                location.href = "/testByChinese";
            } else if (obj.msg === "error") {
                location.href = "/testByChinese";
            }
        }
    })
})

$("#bt2").click(function () {
    var str = $("#bt2").text()
    var inWordChi = str.slice(2)
    var value = $('#hidden_input1').val()
    var wordId = parseInt(value)
    var pram = {
        wordId: wordId,
        inWordChi: inWordChi,
    }
    $.ajax({
        //跳转 url
        url: "/WordTestByEng/"+value,
        type: "Post",
        data: pram,
        datatype: "HTML",
        success: function (data) {
            var obj = JSON.parse(data);
            if (obj.msg === "right") {
                location.href = "/testByChinese";
            } else if (obj.msg === "error") {
                location.href = "/testByChinese";
            }
        }
    })
})

$("#bt3").click(function () {
    var str = $("#bt3").text()
    var inWordChi = str.slice(2)
    var value = $('#hidden_input1').val()
    var wordId = parseInt(value)
    var pram = {
        wordId: wordId,
        inWordChi: inWordChi,
    }
    $.ajax({
        //跳转 url
        url: "/WordTestByEng/"+value,
        type: "Post",
        data: pram,
        datatype: "HTML",
        success: function (data) {
            var obj = JSON.parse(data);
            if (obj.msg === "right") {
                location.href = "/testByChinese";
            } else if (obj.msg === "error") {
                location.href = "/testByChinese";
            }
        }
    })
})

$("#bt4").click(function () {
    var str = $("#bt4").text()
    var inWordChi = str.slice(2)
    var value = $('#hidden_input1').val()
    var wordId = parseInt(value)
    var pram = {
        wordId: wordId,
        inWordChi: inWordChi,
    }
    $.ajax({
        //跳转 url
        url: "/WordTestByEng/"+value,
        type: "Post",
        data: pram,
        datatype: "HTML",
        success: function (data) {
            var obj = JSON.parse(data);
            if (obj.msg === "right") {
                location.href = "/testByChinese";
            } else if (obj.msg === "error") {
                location.href = "/testByChinese";
            }
        }
    })
})

$('#out').click(function () {
    window.location.href = "/selectWord";
})