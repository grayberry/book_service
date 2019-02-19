var $password = $("#password");
var $confirmPassword = $("#confirm_password");
var $username = $("#username");
var $email = $("#email")

function isEmailValid() {

    return /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/.test($email.val())
}

function isUsernameValid() {
    return $username.val().length > 5 && $username.val().length < 20 && /^(?![_.0-9])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$/.test($username.val());
}

function isPasswordValid() {
    return $password.val().length > 6;
}

function arePasswordsMatching() {
    return $password.val() === $confirmPassword.val();
}

function canSubmit() {
    return isPasswordValid() && arePasswordsMatching() && isUsernameValid() && isEmailValid();
}

function emailEvent(){
    //Find out if password is valid
    if(isEmailValid()) {
        //Hide hint if valid
        $email.css("background", "#9bff9b");
    } else {
        //else show hint
        $email.css("background", "#ff8282")
    }
}

function usernameEvent(){
    //Find out if password is valid
    if(isUsernameValid()) {
        //Hide hint if valid
        $username.css("background", "#9bff9b");
    } else {
        //else show hint
        $username.css("background", "#ff8282")
    }
}
function passwordEvent(){
    //Find out if password is valid
    if(isPasswordValid()) {
        //Hide hint if valid
        $password.css("background", "#9bff9b");
    } else {
        //else show hint
        $password.css("background", "#ff8282")
    }
}

function confirmPasswordEvent() {
    //Find out if password and confirmation match
    if(arePasswordsMatching()) {
        //Hide hint if match
        $confirmPassword.css("background", "#9bff9b");
    } else {
        //else show hint
        $confirmPassword.css("background", "#ff8282");
    }
}

function enableSubmitEvent() {
    $("#submit").prop("disabled", !canSubmit());
    if(canSubmit()){
        $("#submit").css("background", "#9bff9b");
    }else{
        $("#submit").css("background", "f2f2f2");
    }
}

$email.focus(emailEvent).keyup(emailEvent).keyup(enableSubmitEvent);

$username.focus(usernameEvent).keyup(usernameEvent).keyup(enableSubmitEvent);
//When event happens on password input

$password.focus(passwordEvent).keyup(passwordEvent).keyup(confirmPasswordEvent).keyup(enableSubmitEvent);

//When event happens on confirmation input
$confirmPassword.focus(confirmPasswordEvent).keyup(confirmPasswordEvent).keyup(enableSubmitEvent);

enableSubmitEvent();