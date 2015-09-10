function poll() {
    var counter = 0;

    var timer = setInterval(function () {
        var ajaxRequest = $.ajax({
            type: "GET",
            url: "/canplay",
            //headers: {'Cookie': document.cookie},
            cache: false,
            dataType: "html",
            success: function (data) {
                if (!data.contains('Waiting')) {
                    window.clearInterval(timer);
                    ajaxRequest.abort();
                    $('body').replaceWith(data);
                    return;
                }
                counter += 3;

                if (counter >= 10) {
                    window.clearInterval(timer);
                    //ajaxRequest.abort();

                    var r = confirm("Do you want to wait?");
                    if (r == true) {
                        poll();
                    } else {
                        $.ajax({
                            type: "GET",
                            url: "/",
                            cache: false,
                            dataType: "html",
                            success: function (data) {
                                $('body').replaceWith(data);
                            }
                        });
                    }
                }
            }
        });
    }, 3000);
}

function getState() {
    $.ajax({
            type: "GET",
            url: "/state",
           // headers: {'Cookie': document.cookie},
            cache: false,
            dataType: "html",
            success: function (data) {
                $('body').html(data);
            }
        });
}

//function getState(yourTurn) {
//    var timer = setInterval(function() {
//        $.ajax({
//            type: "GET",
//            url: "/state",
//           // headers: {'Cookie': document.cookie},
//            cache: false,
//            dataType: "html",
//            success: function (data) {
//                $('body').html(data);
//                if (yourTurn) {
//                    window.clearInterval(timer);
//                }
//            }
//        });
//    }, 2000);
//
//}