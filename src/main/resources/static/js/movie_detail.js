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