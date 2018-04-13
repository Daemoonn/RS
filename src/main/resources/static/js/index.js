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
   $("input:radio[id=pure_rank]").click(function () {
       alert("pure_rank clicked!");
       pageNum = 1;
       rowCou = 0;
       $.post("/pure_rank", {pageNum: pageNum, pageSize: pageSize}, function (res) {
           if (res.code == 200) {
               var list = res.data;

               $("#pages").html("");
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
                           "        <p>" + pageMovie.en_title + " <strong style=\"color: #e09015\">9.6</strong></p>\n" +
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
   });
});