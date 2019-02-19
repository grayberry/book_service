var $userTo = $("#userTo");
let $txtMessage = $('#txtMessage');
var transfer = {};
var $book;

/*----------TRANSFER METHODS----------*/

/*-------------check book------------*/
$(".submit-class").on("click", function() {
    let $b = $(this);
    $book = $b.parent().parent();
    let user = $userTo.text();
    let bookId = $b.parent().data("bookId");
    transfer.mybook = bookId;
    checkBook(user, bookId);
});

function checkBook(user, bookId) {
    $.ajax("/transfers/checkbook", {
        type: "GET",
        data:{
            u: user,
            bookId: bookId
        },
        contentType: "text/plain; charset=utf-8",
        xhrFields: {
            withCredentials: true
        },
        success: function (response) {
            console.log("a")
            getbooks()
        },
        error: function (response) {

        }
    })
}
/*---------- get books -------------*/
function getbooks() {
    let username = $userTo.text();
    $.ajax("/transfers/getbooks", {
        type: 'GET',
        data: {username: username},
        contentType: "text/plain; charset=utf-8",
        xhrFields: {
            withCredentials: true
        },
        success: function (response) {
            console.log("response" + JSON.stringify(response));
            let $div = $("<div/>").attr("class", "book_div");
            $div.append(bookAppend(response));
            let $span = $("<span/>").addClass("cancel").append("X");
            $div.append($span);
            $("body").append($div);
            $(".book_div span").on("click", function () {
                $(".book_div").detach();
            })
            $(".book_div li").on("click", function () {
                let $b = $(this);
                transfer.user = username;
                transfer.userBook = $b.data("bookId");
                $(".book_div").detach();
                booksTransfer(transfer);
            })
        },
        error:function (response) {
            console.log(response)
        }
    })
}

function bookAppend(data){
    let $ul = $("<ul/>").attr("class", "book_ul");
    $ul.append($("<h2/>").append("CHOOSE USER BOOK").attr("style", "text-align:center"))
    for(var i = 0; i<data.length; i++){
        let $img = $("<img/>");
        let $p =  $("<p/>").addClass("book_p").append(data[i].book.title);
        $img.attr("src", data[i].book.imageRef).addClass("book_img")

        $ul.append($("<li/>").attr("data-book-id", data[i].id).append($img).append($p));
    }
    return $ul;
}

function booksTransfer(request){
    $book.empty();
    $.ajax("/transfers/change",{
        type: 'POST',
        data: JSON.stringify(request),
        contentType: "text/plain; charset=utf-8",
        xhrFields: {
            withCredentials: true
        },
        success: function (response) {
           console.log(response);
           $book.append("SUCCESS")
        },
        error: function (response) {
            $book.append("ERROR");
        }

})
}

/*--------- transfer cancel ----------*/
$(".cancel-class").on("click", function () {
    let $b = $(this)
    let req= {};
    req.user = $userTo.text();
    req.bookId = $b.parent().data("bookId");
    $b.parent().parent().empty();
    cancelTransfer(req);
})

function cancelTransfer(request) {
    $.ajax("/transfers/delete", {
        type: 'POST',
        data: JSON.stringify(request),
        contentType: "text/plain; charset=utf-8",
        xhrFields: {
            withCredentials: true
        },
        success: function (response) {
            console.log("msg" + request);
            console.log("response" + response);

        },
        error: function(response){
            if(response.status == 301){
                window.location.href = window.location.origin + "/login";
            }
            console.log("mtav error");
        },
    })
}
/*-------------------------*/

/*----------MESSAGE METHODS-----------*/
$("#send").on("click", function () {
    sendMessage()
});

$txtMessage.on('keypress', function (e) {
    if (e.which === 13) {
        sendMessage();
    }
});

function sendMessage() {
    let message = $txtMessage.val();
    $("#un-chat").scrollTop(9999)
    send(message);
}

function send(message) {
    let username = $userTo.text();
    let request = {};
    request.user = username;
    request.content = message;
    request.placeholder = "message";
    $.ajax('/dialogs/send', {
        type: 'POST',
        data: JSON.stringify(request),
        contentType: "text/plain; charset=utf-8",
        xhrFields: {
            withCredentials: true
        },
        success: function (response) {
            console.log("msg" + request);
            console.log("response" + response);
            let $li = $('<li/>').addClass('me');
            let $div = $('<div/>').text(message);
            let $chat = $("#chat");

            $chat.append($li.append($div));
            $("#un-chat").scrollTop(999999999)
            $txtMessage.val('');
        },
        error: function(response){
            if(response.status == 301){
                window.location.href = window.location.origin + "/login";
            }
            console.log("mtav error");
        },
    });

}