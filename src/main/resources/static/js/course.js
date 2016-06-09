function saveMark(e) {
}
function addMarks() {
    $("#marks")[0].innerHTML = "";
    var m = $("#marks");
    var info = $('<div>Proszę ocenić łatwość pytania:</div>');
    info.appendTo(m);
    var zero = $('<button>0</button>').attr("type", "button").addClass("btn btn-lg btn-success");
    zero.appendTo(m);
    zero.click(function () {
        $("#savedmark")[0].innerHTML = "0";
    })
    var one = $('<button>1</button>').attr("type", "button").addClass("btn btn-lg btn-success");
    one.appendTo(m);
    one.click(function () {
        $("#savedmark")[0].innerHTML = "1";
    })
    var two = $('<button>2</button>').attr("type", "button").addClass("btn btn-lg btn-success");
    two.appendTo(m);
    two.click(function () {
        $("#savedmark")[0].innerHTML = "2";
    })
    var three = $('<button>3</button>').attr("type", "button").addClass("btn btn-lg btn-success");
    three.appendTo(m);
    three.click(function () {
        $("#savedmark")[0].innerHTML = "3";
    })
    var four = $('<button>4</button>').attr("type", "button").addClass("btn btn-lg btn-success");
    four.appendTo(m);
    four.click(function () {
        $("#savedmark")[0].innerHTML = "4";
    })
    var five = $('<button>5</button>').attr("type", "button").addClass("btn btn-lg btn-success");
    five.appendTo(m);
    five.click(function () {
        $("#savedmark")[0].innerHTML = "5";
    })
    $("#confirm-fishe")[0].innerHTML = "";
    var m = $("#confirm-fishe");
    var confirm = $('<button>Potwierdź ocenę</button>').attr("type", "button").addClass("btn btn-lg btn-primary");
    confirm.click(function () {
        var mark = $("#savedmark")[0].innerHTML;
        var courseId = $("#cid")[0].innerHTML;
        var questionId = $("#qid")[0].innerHTML;
        var data = {
            "mark": mark,
            "courseId": courseId,
            "questionId": questionId
        };
        $.ajax({
            url: "/answer",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data), //Stringified Json Object
            async: true,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
            cache: false,    //This will force requested pages not to be cached by the browser  
            processData: false, //To avoid making query String instead of JSON
            success: function (resposeJsonObject) {
                getNextFishe();
            }
        });
    })
    confirm.appendTo(m);
    $("#savedmark")[0].innerHTML = "0";
}

function getNextFishe() {
    $("#answer-container").hide();
    $("#confirm-fishe")[0].innerHTML = "";
    $("#savedmark")[0].innerHTML = "";
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
            if (question == "Koniec Pytan") {
                var f = $("#fishe")[0];
                f.innerHTML = "W tym momencie nie ma więcej fiszek które trzeba powtórzyć";
                $("#answer-container").hide();
                $('#next-fishe').hide()
                $("#question-container").show();
                $("#show-answer-button").hide();
                return;
            }
            var answer = data["back"];
            var courseId = data["courseId"];
            var questionId = data["questionId"];
            var f = $("#fishe")[0];
            f.innerHTML = question;
            var f = $("#answer")[0];
            f.innerHTML = answer;
            var f = $("#cid")[0];
            f.innerHTML = courseId;
            var f = $("#qid")[0];
            f.innerHTML = questionId;
            $("#question-container").show();
        }
    });
}
$(function () {
    $('#next-fishe').on('click', getNextFishe);
    $("#answer-container").hide();
    $("#question-container").hide();
    $("#show-answer-button").on('click', function () {
        $("#answer-field")[0].innerHTML = $("#answer")[0].innerHTML;
        addMarks();
        $("#answer-container").show();
    });
})
