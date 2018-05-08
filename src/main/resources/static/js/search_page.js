//option: init, append
var refreshSearchPage = function (option) {
    var keyWord = $("#key_word").val();
    $.post("/search2", {pageNum: spageNum, pageSize: spageSize, keyWord: keyWord}, function (res) {
        if (res.code == 200) {
            $("#sh2").text('Search ' + keyWord);
            var list = res.data;
            if (option == "init") {
                $("#pages").html("");
            }
            var rlimt = Math.ceil(list.length / 6.0);
            for (var r = 1; r <= rlimt; r++) {
                srowCou = srowCou + 1;
                var rowId = "row" + srowCou;
                $("#pages").append("<div id=\""+ rowId +"\" class=\"row\">\n");
                var thres;
                if (r != rlimt) {
                    thres = r * 6;
                } else {
                    thres = list.length;
                }
                for (var i = (r - 1) * 6; i < thres; i++) {
                    var pageMovie = list[i];
                    var mtitle;
                    if (pageMovie.lang == 'cn') {
                        mtitle = pageMovie.cn_title;
                    } else {
                        mtitle = pageMovie.en_title;
                    }
                    $("#" + rowId).append(
                        "<div class=\"col-md-2\">\n" +
                        "    <a href=\"searchMovieDetail?movieId=" + pageMovie.movieId + "&avg2=" + pageMovie.avg2.toFixed(1) + "\" target='_blank'>\n" +
                        "        <img id='" + pageMovie.movieId + "' src=\"/img/movies_pic/"+ pageMovie.movieId + ".webp\" class=\"img-thumbnail\"/>\n" +
                        "        <p>" + mtitle + " <strong style=\"color: #e09015\">" + pageMovie.avg2.toFixed(1) + "</strong></p>\n" +
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
    $("#more").click(function () {
        spageNum = spageNum + 1;
        refreshSearchPage("append");
    });
    $("#key_word").bind('keypress', function (event) {
        if (event.keyCode == "13") {
            document.getElementById("search_btn").click();
        }
    });
    $("#search_btn").click(function () {
        spageNum = 1;
        srowCou = 0;
        refreshSearchPage("init");
    });
});

$(function () {
    document.getElementById("search_btn").click();
});