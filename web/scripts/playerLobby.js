
var gameWord;
window.onload = start;

function start() {
    //$(".selbox")[0].style.display = "none";
    $(".gtbtn").click(swap);
    $(".gtbtnx").click(swapback);
    $(".startbtn").click(startgame);
    $(".lbyuserxbtn").click(kick);
}

function swap() {
    $(".selbox")[0].style.display = "block";
}

function swapback() {
    $(".selbox")[0].style.display = "none";
    gameWord = $(".gtSelector")[0].value;
}

function startgame() {
    var gameSelection = gameWord || $(".gtSelector")[0].value;

    //your ajax request will go here.
    $.ajax({
        type: "POST",
        url: window.location.pathname,
        data: JSON.stringify({
            "type": "start",
            "selection": gameSelection,
        }),
        success: function () {
            location.reload();
        },
        dataType: "json",
        contentType: "application/json; charset=utf-8",
    });
}
