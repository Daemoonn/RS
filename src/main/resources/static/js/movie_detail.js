$(window).on('beforeunload', function(){
    var rating = $("#rating-star").val();
    var movieId = $("#movieId").val();
    var nowStar = $("#rating-star").val();
    // "4.0" != "4"
    if (parseFloat(nowStar) != parseFloat(initStar)) {
        $.post("/crudRating", { movieId: movieId, rating: rating });
    }
    return 'Are you sure you want to leave?';
});

$(function () {
    $("#input-7-xs").rating({displayOnly: true, step: 0.5});
});

$(function () {
    refreshRecommend();
});

var appendPageMovie = function(recommendList, option) {
    var rowId;
    if (option == 'ucf') {
        rowId = 'ucfr';
    } else if (option == 'icf') {
        rowId = 'icfr';
    } else {
        rowId = 'mostSimir';
    }
    for (var i = 0; i < recommendList.length; i++) {
        var pageMovie = recommendList[i];
        $("#" + rowId).append(
            "<div class=\"col-md-2\">\n" +
            "    <a href=\"searchMovieDetail?movieId=" + pageMovie.movieId + "&avg2=" + pageMovie.avg2.toFixed(1) + "\" target='_blank'>\n" +
            "        <img id='" + pageMovie.movieId + "' src=\"/img/movies_pic/"+ pageMovie.movieId + ".webp\" class=\"img-thumbnail\"/>\n" +
            "        <p>" + pageMovie.en_title + " <strong style=\"color: #e09015\">" + pageMovie.avg2.toFixed(1) + "</strong></p>\n" +
            "    </a>\n" +
            "</div>\n"
        );
    }
}

var refreshRecommend = function () {
    var userId = $("#userId").text(), genres = $("#genres").val(), movieId = $("#movieId").val();
    var size = 6;
    $.post("/recommend", {userId: userId, movieId: movieId, genres: genres, size: size}, function (res) {
        if (res.code == 200) {
            var icfl = res.data.icf;
            if (icfl.length > 0) {
                $("#icfr").html("");
                $("#icfr").append(
                    "<div class=\"col-md-4\" style='padding-bottom: 15px'>\n" +
                    "    Item-based Recommend:\n" +
                    "</div>\n" +
                    "<div class=\"col-md-8\" style='padding-bottom: 15px' >\n" +
                    "&nbsp;" +
                    "</div>"
                );
                appendPageMovie(icfl, 'icf');
            }
        } else {
            alert("error!")
        }
    });
}