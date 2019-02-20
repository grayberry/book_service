let socket = new SockJS('/ws');
let stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    stompClient.subscribe("/user/message", function (data) {
        let message = JSON.parse(data.body);
        if(window.location.pathname === "/dialogs"){
            let $chat = $('ul[name='+ message.user +']');
            let $li = $('<li/>');
            let $div = $('<div/>');
            $div.text(message.content);
            let d = new Date();
            let $time = $("<span/>").append(d.getHours() + ":" + d.getMinutes());
            $chat.append($li.append($div, $time));
            $("#un-chat").scrollTop(999999999)
        } else{
            notifymessage(message)
        }
    });
    stompClient.subscribe('/user/transfer', function (data) {
        let message = JSON.parse(data.body);
        if(window.location.pathname === "/transfers"){
            let $chat = $('ul[name='+ message.user +']');
            let $li = $('<li/>');
            let $div = $('<div/>');
            let $im = $("<img/>").attr("src", message.content.book.imageRef);
            $div.append($im);
            $chat.append($li.append($div));
            $chat.scrollTop($chat.scrollHeight)
            $("#un-chat").scrollTop(999999999)

        } else{
            notifytransfer(message)
        }
    });
});

function notifymessage(data){
    let $div = $("<div/>").addClass("notify").css("bottom", "100px");
    let $text = $("<p/>")
    let $span = $("<span/>").addClass("cancel").append("X")
    $text.append('<a href="'+ window.location.origin + "/dialogs?u=" + data.user + '">' + data.user + '<a/>');
    let $img = $("<img/>").attr("src",  "http://stellae.usc.es/red/file/download/152679")
    $("body").append($div.append($span, $text, $img));
    $div.hide(15000);
    $("#dlnotify").css("background", "green")
    $(".notify span").on("click", function () {
        let $t = $(this);
        $t.parent().detach();
    });


}

function notifytransfer(data) {
    let $div = $("<div/>").addClass("notify");
    let $text = $("<p/>")
    let $span = $("<span/>").addClass("cancel").append("X");
    $text.append('<a href="'+ window.location.origin + "/transfers?u=" + data.user + '">' + data.user + '<a/>');
    let $img = $("<img/>").attr("src", data.content.book.imageRef);
    $("body").append($div.append($span, $text, $img));
    $div.hide(20000);
    $("#trnotify").css("background", "#8080f1");
    $(".notify span").on("click", function () {
        let $t = $(this);
        $t.parent().detach();
    });

}