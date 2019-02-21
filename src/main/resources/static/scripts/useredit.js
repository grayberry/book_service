let $div;
let $altername;
let $pass;
let $confirmPass;
let $save;
let $cancel;

$("#editbtn").on("click", function () {
    editWindow();

});

$("#addphoto").on("click", function () {
    addPhotoWindow();
})
function editWindow() {
    $div = $("<div/>").addClass("edit_div");
    $altername = $("<input/>").attr({
        id:  "altername",
        name: "altername",
        type: "text",
        placeholder: "nickname",
        autocomplete: "off"});
    $pass = $("<input/>").attr({
        id: "password",
        name: "password",
        placeholder: "new password",
        type: "password"});
    $confirmPass = $("<input/>").attr({
        id: "confirmPassword",
        name: "confirmPassword",
        placeholder: "confirm password",
        type: "password"});

    $save = $("<input/>").attr({
        id: "save",
        type: "button",
        value: "save",
        disabled: true});
    $cancel = $("<span/>").addClass("cancel").append("X");
    $div.append($altername, $pass, $confirmPass, $save, $cancel);
    $("body").append($div);
    enab()
}

function addPhotoWindow() {
    let $div = $("<div/>").addClass("edit_div");
    let $form = $("<form/>").attr({
        method:"post",
        action: "/user/mypage/img"
    });
    let $img = $("<input/>").attr({
        type: "file",
        name: "photo"
    });
    let $upload = $("<input/>").attr({
        type: "submit",
        value: "upload"
    });

    $form.append($img, $upload);
    $div.append($form);
    $("body").append($div);
}

function isAlternameValid(){
    return $altername.val().length >3 && $altername.val().length <20 || $altername.val().length===0;
}

function isPasswordValid(){
    return $pass.val().length > 6 || $pass.val().length===0;
}

function arePasswordsMatching() {
    return $pass.val() === $confirmPass.val();
}

function canSubmit() {
    return isAlternameValid() && arePasswordsMatching() && isPasswordValid() && ( $altername.val().length!=0 || $pass.val().length!=0);
}

function alternameEvent(){
    if(isAlternameValid()) {
        $altername.css("background", "#9bff9b");
    } else {
        $altername.css("background", "#ff8282")
    }
}

function passEvent(){
    if(isPasswordValid()) {
        $pass.css("background", "#9bff9b");
    } else {
        $pass.css("background", "#ff8282")
    }
}

function confirmPassEvent(){
    if(arePasswordsMatching()) {
        $confirmPass.css("background", "#9bff9b");
    } else {
        $confirmPass.css("background", "#ff8282")
    }
}

function enableSubmitEvent() {
    $save.prop("disabled", !canSubmit());
    if(canSubmit()){
        $save.css("background", "#9bff9b");
    } else {
        $save.css("background", "snow");
    }
}

function enab(){
    $altername.focus(alternameEvent).keyup(alternameEvent).keyup(enableSubmitEvent);
    $pass.focus(passEvent).keyup(passEvent).keyup(confirmPassEvent).keyup(enableSubmitEvent);
    $confirmPass.focus(confirmPassEvent).keyup(confirmPassEvent).keyup(enableSubmitEvent);
    enableSubmitEvent();
    $save.on("click", function () {
        save()
    })

    $cancel.on("click", function () {
        $div.detach();
    })
}

function save() {
    let request = {};
    request.altername = $altername.val().replace(/\s+/g, ' ').trim();
    request.password = $pass.val();

    $.ajax("/user/mypage/edit", {
        type: "post",
        contentType: "text/plain; charset=UTF-8",
        data: JSON.stringify(request),
        success: function (response) {
            window.location.reload()
        },
        error:function (response) {
          console.log(response)
          if(response.status ==301){
              window.location.href = window.location.origin + "/login";
          }
        }
    })
}
