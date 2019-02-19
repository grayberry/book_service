document.getElementById("chosenBooks").innerHTML;
var addBooks = [];
var nullImage = '/images/no_cover.jpg';
var button;

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

$(function(){
    console.log("mtav");
    return butt();
})

function butt(){
    let HtmlForm = '<input id="search" type="text" name="q" placeholder="Live search" autocomplete="off"/><div><ul id="search_result"></ul></div>'
    button = $('#addBook')
    button.on("click", function(){
        $('#addBook').detach();
        $('#bookForm').html(HtmlForm);
        search();
    })
}

function search(){
    $('#search').on("keyup paste", delay(function() {
        let img;
        if(this.value.length>=0){
            document.getElementById('search_result').innerHTML = "";
        }

        if(this.value.length>=2){
            let books =[];
            let bookId = [];
            document.getElementById('search_result').innerHTML = "";
            $.ajax({
                type: "get",
                datatype: "json",
                url: "https://www.googleapis.com/books/v1/volumes", //Путь к обработчику
                data: {'q' : this.value},
                success: function(data){
                    if(data.items==undefined){
                        search_result.innerHTML="<span style='color:gray'>no search results</span>";
                        return;
                    }
                    for(var i=0; i<data.items.length && books.length<5; i++){
                        if (data.items[i].volumeInfo.title==undefined ||
                            data.items[i].volumeInfo.authors==undefined ||
                            data.items[i].volumeInfo.categories==undefined ||
                            data.items[i].volumeInfo.language == undefined ||
                            data.items[i].volumeInfo.pageCount == undefined) {
                            console.log("continue");
                            continue;
                        }
                        books.push(data.items[i].volumeInfo);
                        bookId.push(data.items[i].id);
                        if(data.items[i].volumeInfo.imageLinks!=undefined){
                            img=data.items[i].volumeInfo.imageLinks.smallThumbnail;
                        }else{
                            img = nullImage;
                        }
                        search_result.innerHTML+="<li ><img src='" + img + "'/>" +
                            data.items[i].volumeInfo.title + " : " + data.items[i].volumeInfo.authors + "</li>";
                        if(search_result.length == 0){
                            search_result.innerHTML = "<span style='color:gray'>no search results</span>";
                            console.log("empty");
                        }
                    }
                    $("li").on("click", function(){
                        let s = $(this).index();
                        bookPushArray(books[s], addBooks, bookId[s]);
                        console.log(addBooks);
                        $('#bookForm').empty();
                        $('#bookForm').append(button);
                        if(addBooks.length==1){
                            subm(1);
                        }

                        chosenBooks.innerHTML+="<tr><td>" + "<img src='" + addBooks[addBooks.length-1].image + "'/>" +
                            addBooks[addBooks.length-1].title + " : " + addBooks[addBooks.length-1].authors +"</td><td class='cancel'><b>X</b></td></tr>";
                        del();
                        return butt();
                    });
                }
            })
        }
    },350))
}
function bookPushArray(from, to, id){
    let map = new Object();
    map.title = from.title;
    map.authors = from.authors;
    map.categories = from.categories;
    map.language = from.language;
    map.pageCount = from.pageCount;
    map.description = from.description;

    if(from.imageLinks!=undefined){
        let bigImg= from.imageLinks.thumbnail //.replace("zoom=1", "zoom=2");
        map.image = bigImg;
    }else{
        map.image = nullImage;
    }
    if(from.industryIdentifiers!=undefined){
        if(from.industryIdentifiers[0]!=undefined)
        map.isbn10 = from.industryIdentifiers[0].identifier;
        if(from.industryIdentifiers[1]!=undefined)
        map.isbn13 = from.industryIdentifiers[1].identifier;
    }else{
        map.isbn10 = null;
        map.isbn13 = null;
    }
    to.push(map);
}

function del(){
    $("#chosenBooks tr td.cancel").on("click",function(){
        let b = $(this).parent('tr');
        console.log(b.index())
        addBooks.splice(b.index(),1);
        b.detach();
        if(addBooks.length==0){
            subm(0);
        }
    });
}

function subm(i){
    console.log("stee mtav")
    if(i==1) {
        let submitButton = "<input id='submitButton' type='submit' value='submit'/>";
        $("#submitForm").append(submitButton);
        console.log("sarqec")
        $("#submitButton").on("click", function () {
            $("#submitForm").empty()
            $.ajax("/books/add",{
                type: "POST",
                headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
                data: JSON.stringify(addBooks),
                contentType: "text/plain; charset=utf-8",
                xhrFields: {
                    withCredentials: true
                },
                success: function(response, textStatus, jQxhr ){
                    console.log(response)
                    console.log(textStatus)
                    console.log(jQxhr)

                    $("#bookForm").empty()
                    $('#response pre').html( "SUCCESS" )
                    setTimeout(function () {
                        location.reload()
                    }, 20)
                },

                error: function(response , jqXhr, textStatus, errorThrown ){
                    if(response.status == 301){
                        window.location.href = window.location.origin + "/login";
                    }
                    console.log("mtav error");
                },
            });
            console.log("gamardjopa");
            console.log(addBooks)
        })
    }
    if(i==0){
        $("#submitForm").empty();
        console.log("jnjec");
        return;
    }
}

