var $book = $("#bookid");
var $sendbook = $("#sendbook");

$sendbook.on("click", function () {
    let req = {};
    req.userTo =  $book.data("userTo");
    req.bookId = $book.data("bookId");
    send(req);
});

function send(request) {
    $.ajax('/transfers/send', {
        type: 'POST',
        data:JSON.stringify(request),
        contentType: "text/plain; charset=utf-8",
        xhrFields: {
            withCredentials: true
        },
        success: function (response) {
            console.log(request)
        },
        error: function (response) {
            if (response.status == 301) {
                window.location.href = window.location.origin + "/login";
            }
            if (response.status == 400){
                alert("you have already sent a request");
            } if(response.status == 404){
                alert("you must have at least one book");
            }
        },
    });
}