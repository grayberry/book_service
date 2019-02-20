let $dlnotify = $("#dlnotify");
let $trnotify = $("#trnotify");

$(function () {
    $.ajax("/dialogs/c",{
        type:"get",
        contentType: "text/plain; charset=utf-8",
        xhrFields: {
            withCredentials: true
        },
        success: function (response) {
            if(response.length !=0){
                $dlnotify.css("background", "green");
                if(window.location.pathname == "/dialogs") {
                    dialogNotify(response);
                }
            }
        },
        error: function (response) {
            console.log(response)
        }
    });
});

$(function () {
    $.ajax("/transfers/c",{
        type: "get",
        contentType: "text/plain; charset=utf-8",
        xhrFields: {
            withCredentials: true
        },
        success: function (response) {
            if(response.length !=0){
                $trnotify.css("background", "#8080f1");
                if(window.location.pathname == "/transfers") {
                    dialogNotify(response)
                }
            }
        },
        error: function (response) {
            console.log(response)
        }
    })
});

function dialogNotify(data) {
    let $dialogs = $(".dialog_div");
    for(let i=0; i<data.length; i++){
        $dialogs.filter(function () {
            return $(this).text() === data[i];
        }).css("background", "#7aff7a")
    }
}