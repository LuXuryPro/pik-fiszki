function nameEditButtonClick(e) {
    e.preventDefault(); // prevent the default click action
    var r = $("#nameRow").children()[1];
    var name = r.innerHTML;
    r.innerHTML = "";
    var inputFiled = $("<input>").attr("id", "nameInput");
    inputFiled.val(name);
    inputFiled.appendTo(r);
    var saveFiled = $("<input>").attr("id", "nameInputSave").attr("type", "hidden");
    saveFiled.val(name);
    saveFiled.appendTo(r);
    //<button type="button" class="btn btn-lg btn-info">Edytuj</button>
    var b = $("#nameRow").children()[2];
    b.innerHTML = "";
    var bOk = $('<button>OK</button>').attr("type", "button").addClass("btn btn-lg btn-success").attr("id", "nameOkButtion");
    bOk.appendTo(b);
    bOk.click(
        function () {
            var input = $("#nameInput").val();
            var r = $("#nameRow").children()[1];
            r.innerHTML = input;
            var b = $("#nameRow").children()[2];
            b.innerHTML = "";
            var bname = $('<button>Edytuj</button>').attr("type", "button").addClass("btn btn-lg btn-info").attr("id", "nameButton");
            bname.click(nameEditButtonClick);
            bname.appendTo(b);
        }
    )

    var bCancel = $('<button>Anuluj</button>').attr("type", "button").addClass("btn btn-lg btn-danger").attr("id", "nameOkButtion");
    bCancel.appendTo(b);
    bCancel.click(
        function () {
            var input = $("#nameInput").val();
            var saved = $("#nameInputSave").val();
            r.innerHTML = saved
            var b = $("#nameRow").children()[2];
            b.innerHTML = "";
            var bname = $('<button>Edytuj</button>').attr("type", "button").addClass("btn btn-lg btn-info").attr("id", "nameButton");
            bname.click(nameEditButtonClick);
            bname.appendTo(b);
        }
    )
}


function saveUser() {
    var name = $("#nameRow").children()[1].innerHTML;
    var secondName = $("#secondNameRow").children()[1].innerHTML;
    var email = $("#emainRow").children()[1].innerHTML
    var id = $("#userID")[0].innerHTML;
    var data = {
        "firstName": name,
        "secondName": secondName,
        "email": email,
        "id": id
    };
    $.ajax({
        url: "/saveUserForm",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data), //Stringified Json Object
        async: true,    //Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation
        cache: false,    //This will force requested pages not to be cached by the browser  
        processData: false, //To avoid making query String instead of JSON
        success: function (resposeJsonObject) {
            $("#success")[0].innerHTML = "";
            var inv = $('<h1><span class="label label-success">Success</span></h1>');
            inv.appendTo($("#success"));
            inv.fadeOut(3000);
        }
    });
}
$(function () {
    $('#nameButton').on('click', nameEditButtonClick);
    $('#saveUser').on('click', saveUser);
})
