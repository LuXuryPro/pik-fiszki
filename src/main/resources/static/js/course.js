function getNextFishe() {
    var userId = $("#userId")[0].innerHTML;
    var courseId = $("#courseId")[0].innerHTML;
    var data = {'userId': userId, "courseId": courseId}
    $.ajax({
        url: "/getfishe",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data), //Stringified Json Object
        async: true,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
        cache: false,    //This will force requested pages not to be cached by the browser  
        processData: false, //To avoid making query String instead of JSON
        success: function (resposeJsonObject) {
            var data = resposeJsonObject;
            var question = data["face"];
            var answer = data["back"];
            var courseId = data["courseId"];
            var questionId = data["questionId"];
            var f = $("#fishe")[0];
            f.innerHTML = question;
            var f = $("#answer")[0];
            f.innerHTML = answer;
            var f = $("#cid")[0];
            f.innerHTML = answer;
            var f = $("#qid")[0];
            f.innerHTML = answer;
            var b = $("#wid");
            var bOk = $('<button>Pokaż odpowiedź</button>').attr("type", "button").addClass("btn btn-lg btn-success");
            bOk.click(function () {
                var f = $("#fishe")[0];
                f.innerHTML = answer;
            })
            bOk.appendTo(b);
        }
    });
}
$(function () {
    $('#next-fishe').on('click', getNextFishe);
})
