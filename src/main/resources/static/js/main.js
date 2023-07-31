// setInterval()
// localStorage.setItem()
// localStorage.
// localStorage.removeItem()

setInterval(function () {
    var number = parseInt(Math.random() * (23252 - 1 + 1) + 1);
    var para = {
        num: number,
    }
    $.ajax({
        url: "/main/data",
        type: "Post",
        data: para,
        datatype: "HTML",
        success: function (data) {
            var obj = JSON.parse(data);
            $('#eng').html(obj.eng);
            $('#chi').html(obj.chi);
            $('#photo').attr('src', obj.photo);
        }
    })
}, 10000);