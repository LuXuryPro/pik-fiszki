function closeAllEdits() {
    closeNameEdit();
    closeLastNameEdit();
    closeEmailEdit();
}

function closeNameEdit() {
    var input = $("#nameInput").val();
    if (input == undefined) {
        return;
    }
    var r = $("#nameRow").children()[1];
    r.innerHTML = input;
    var b = $("#nameRow").children()[2];
    b.innerHTML = "";
    var bname = $('<button>Edytuj</button>').attr("type", "button").addClass("btn btn-lg btn-info").attr("id", "nameButton");
    bname.click(nameEditButtonClick);
    bname.appendTo(b);
}

function closeLastNameEdit() {
    var input = $("#lastNameInput").val();
    if (input == undefined) {
        return;
    }
    var r = $("#lastNameRow").children()[1];
    r.innerHTML = input;
    var b = $("#lastNameRow").children()[2];
    b.innerHTML = "";
    var bname = $('<button>Edytuj</button>').attr("type", "button").addClass("btn btn-lg btn-info").attr("id", "lastNameButton");
    bname.click(lastNameEditButtonClick);
    bname.appendTo(b);
}

function closeEmailEdit() {
    var input = $("#emailInput").val();
    if (input == undefined) {
        return;
    }
    var r = $("#emailRow").children()[1];
    r.innerHTML = input;
    var b = $("#emailRow").children()[2];
    b.innerHTML = "";
    var bname = $('<button>Edytuj</button>').attr("type", "button").addClass("btn btn-lg btn-info").attr("id", "lastNameButton");
    bname.click(emailEditButtonClick);
    bname.appendTo(b);

}

function cancelAllEdits() {
    cancelNameEdit();
    cancelLastNameEdit();
    cancelEmailEdit();
}

function cancelNameEdit() {
    var r = $("#nameRow").children()[1];
    var saved = $("#userBackup").find("div")[1];
    r.innerHTML = saved.innerHTML;
    var b = $("#nameRow").children()[2];
    b.innerHTML = "";
    var bname = $('<button>Edytuj</button>').attr("type", "button").addClass("btn btn-lg btn-info").attr("id", "nameButton");
    bname.click(nameEditButtonClick);
    bname.appendTo(b);
}

function cancelLastNameEdit() {
    var r = $("#lastNameRow").children()[1];
    var saved = $("#userBackup").find("div")[2];
    r.innerHTML = saved.innerHTML;
    var b = $("#lastNameRow").children()[2];
    b.innerHTML = "";
    var bname = $('<button>Edytuj</button>').attr("type", "button").addClass("btn btn-lg btn-info").attr("id", "lastNameButton");
    bname.click(lastNameEditButtonClick);
    bname.appendTo(b);
}

function cancelEmailEdit() {
    var r = $("#emailRow").children()[1];
    var saved = $("#userBackup").find("div")[3];
    r.innerHTML = saved.innerHTML;
    var b = $("#emailRow").children()[2];
    b.innerHTML = "";
    var bname = $('<button>Edytuj</button>').attr("type", "button").addClass("btn btn-lg btn-info").attr("id", "emailButton");
    bname.click(emailEditButtonClick);
    bname.appendTo(b);
}

function nameEditButtonClick(e) {
    e.preventDefault(); // prevent the default click action
    var r = $("#nameRow").children()[1];
    var name = r.innerHTML;
    r.innerHTML = "";
    var inputFiled = $("<input>").attr("id", "nameInput");
    inputFiled.val(name);
    inputFiled.appendTo(r);
    var b = $("#nameRow").children()[2];
    b.innerHTML = "";
    var bOk = $('<button>OK</button>').attr("type", "button").addClass("btn btn-lg btn-success").attr("id", "nameOkButtion");
    bOk.appendTo(b);
    bOk.click(closeNameEdit);
    var bCancel = $('<button>Anuluj</button>').attr("type", "button").addClass("btn btn-lg btn-danger").attr("id", "nameOkButtion");
    bCancel.appendTo(b);
    bCancel.click(cancelNameEdit);
}

function lastNameEditButtonClick(e) {
    e.preventDefault(); // prevent the default click action
    var r = $("#lastNameRow").children()[1];
    var name = r.innerHTML;
    r.innerHTML = "";
    var inputFiled = $("<input>").attr("id", "lastNameInput");
    inputFiled.val(name);
    inputFiled.appendTo(r);
    var b = $("#lastNameRow").children()[2];
    b.innerHTML = "";
    var bOk = $('<button>OK</button>').attr("type", "button").addClass("btn btn-lg btn-success").attr("id", "nameOkButtion");
    bOk.appendTo(b);
    bOk.click(closeLastNameEdit);
    var bCancel = $('<button>Anuluj</button>').attr("type", "button").addClass("btn btn-lg btn-danger").attr("id", "nameOkButtion");
    bCancel.appendTo(b);
    bCancel.click(cancelLastNameEdit);
}

function emailEditButtonClick(e) {
    e.preventDefault(); // prevent the default click action
    var r = $("#emailRow").children()[1];
    var name = r.innerHTML;
    r.innerHTML = "";
    var inputFiled = $("<input>").attr("id", "emailInput");
    inputFiled.val(name);
    inputFiled.appendTo(r);
    var b = $("#emailRow").children()[2];
    b.innerHTML = "";
    var bOk = $('<button>OK</button>').attr("type", "button").addClass("btn btn-lg btn-success").attr("id", "nameOkButtion");
    bOk.appendTo(b);
    bOk.click(closeEmailEdit);
    var bCancel = $('<button>Anuluj</button>').attr("type", "button").addClass("btn btn-lg btn-danger").attr("id", "nameOkButtion");
    bCancel.appendTo(b);
    bCancel.click(cancelEmailEdit);
}



function saveUser() {
    closeAllEdits();
    var name = $("#nameRow").children()[1].innerHTML;
    var secondName = $("#lastNameRow").children()[1].innerHTML;
    var email = $("#emailRow").children()[1].innerHTML
    var id = $("#userBackup").find("div")[0].innerHTML;
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
function cancelEdit() {
    closeAllEdits();
    cancelAllEdits();
}

$(function () {
    $('#nameButton').on('click', nameEditButtonClick);
    $('#lastNameButton').on('click', lastNameEditButtonClick);
    $('#emailButton').on('click', emailEditButtonClick);
    $('#saveUser').on('click', saveUser);
    $('#abort').on('click', cancelEdit);
})
