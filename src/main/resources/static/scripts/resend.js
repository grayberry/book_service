let $err = $("#err")
let $formDiv = $(".form");

$(function () {
    if($err.text()=="User is disabled"){
        activebutton();
    }

})

function activebutton(){
    let $btn = $("<input/>").attr({
        type: "button",
        value: "Activate",
    }).css("background", "#43A047")
        .css("cursor", "pointer")
        .css("color", "white");
    let $message = $("<p/>").append("Your account is not activated");
    $formDiv.empty();
    $formDiv.append($message, $btn);
    $btn.on("click", function () {
        createForm();
    })
}

function createForm() {
    let $div = $("<div/>");
    let $form = $("<form/>").attr({
        method: "post",
        action: "/resend"
    })
    let $input = $("<input/>").attr({
        name: "email",
        type: "text",
        placeholder: "enter your email",
        class: "login-form"
    })
    $div.append($form.append($input));
    $formDiv.append($div)

    $(function () {
        $.ajax('/resend', {
            type: 'POST',
            data: {email: $input.val()},
            contentType: "text/plain; charset=utf-8",
            xhrFields: {
                withCredentials: true
            },
            success: function (response) {
                console.log(response)
            },
            error: function (response) {
                console.log(response)
            },
        });
    })

}

