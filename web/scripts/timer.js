
function setTimer (time){
    var timer = $(".timerbtn")[0];
    timer.innerHTML = time;
}

$("#x-btn").click(function () {
    $.ajax({
        type: "POST",
        url: window.location.pathname,
        data: JSON.stringify({
            "type": "navigation",
            "selection": "lobby",
        }),
        success: function () {
        },
        dataType: "json",
        contentType: "application/json; charset=utf-8",
    });
});