function delay(callback, ms) {
    let timer = 0;
    return function() {
        let context = this, args = arguments;
        clearTimeout(timer);
        timer = setTimeout(function () {
            callback.apply(context, args);
        }, ms || 0);
    };
}


$('#bookSearch').on("keyup paste", delay(function() {
    let $result = $("#result").empty();
        if(this.value.length>=2){

            $.ajax({
                type: "get",
                contentType: "text/plain; charset=UTF-8",
                url: window.location.origin + "/books/search", //Путь к обработчику
                data: {term : this.value},
                success: function(data){
                    for(let i=0; i<data.length && i < 5; i++){
                        let json = JSON.parse(data[i])
                        console.log(json.id)
                        console.log(json.book.title);
                        $result.append($("<li/>").append($("<img/>").attr("src", json.book.imageRef)).append(json.book.title))
                    }
                    cl(data);
                },
                error: function (data) {
                    console.log(data);
                }
            })
        }
    },350));


function cl(data) {
    $("#result li").on("click", function () {
        let $i = $(this).index();
        let $id = JSON.parse(data[$i]).id;
        window.location = window.location.origin + "/books?id=" + $id;
    })
}