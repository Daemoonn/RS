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
