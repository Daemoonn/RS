document.addEventListener("error", function (e) {
    var elem = e.target;
    if (elem.tagName.toLowerCase() == 'img') {
        var url = "/img/movies_pic/"+ elem.id +".jpg";
        var isJpg = false;
        $.get(url, function (data, status) {
            if (status == "success") {
                elem.src = url;
                isJpg = true;
            }
        });
        if (!isJpg) {
            elem.src = "/img/movies_pic/default.png";
        }
    }
}, true);

// function checkImg() {
//     $("img").on("error", function () {
//         $(this).attr("src", "/img/movies_pic/default.png");
//     });
// }
//
// function changeImg(t) {
//     $(t).attr("src", "/img/movies_pic/default.png");
// }

$(function () {
    refreshIndexPage("init");
})

//option: init, append
var refreshIndexPage = function (option) {
    var tc = typeChooser;
    if (tc == "Others") {
        tc = "no genres listed";
    }
    $.post("/rank", {pageNum: pageNum, pageSize: pageSize, typeChooser: tc, radioChooser: radioChooser}, function (res) {
        if (res.code == 200) {
            var list = res.data;
            if (option == "init") {
                $("#pages").html("");
            }
            for (var r = 1; r <= 2; r++) {
                rowCou = rowCou + 1;
                var rowId = "row" + rowCou;
                $("#pages").append("<div id=\""+ rowId +"\" class=\"row\">\n");
                for (var i = (r - 1) * (list.length / 2); i < r * (list.length / 2); i++) {
                    var pageMovie = list[i];
                    $("#" + rowId).append(
                        "<div class=\"col-md-2\">\n" +
                        "    <a href=\"#\">\n" +
                        "        <img id='" + pageMovie.movieId + "' src=\"/img/movies_pic/"+ pageMovie.movieId + ".webp\" class=\"img-thumbnail\"/>\n" +
                        "        <p>" + pageMovie.en_title + " <strong style=\"color: #e09015\">" + pageMovie.avg2.toFixed(1) + "</strong></p>\n" +
                        "    </a>\n" +
                        "</div>\n"
                    );
                }
                $("#pages").append("</div>\n");
            }
        } else {
            alert("error!")
        }
    });
}

$(function () {
    //input:radio[id=pure_rank]
   $(".rank").click(function () {
       var id = $(this).attr("id");
       var clickType = $(this).attr("type");
       if (clickType == "button") {
           $("#types .btn-primary").attr("class", "btn btn-link rank");
           $("#" + id).attr("class", "btn btn-primary rank");
           typeChooser = id;
           // alert("typeChooser:" + typeChooser);
       } else {
           radioChooser = id;
           // alert("radioChooser:" + radioChooser);
       }
       pageNum = 1;
       rowCou = 0;
       refreshIndexPage("init");
   });
});

$(function () {
    $("#more").click(function () {
        pageNum = pageNum + 1;
        refreshIndexPage("append");
    });
});