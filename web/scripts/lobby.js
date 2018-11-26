var gameWord;
$(".selbox")[0].style.display = "none";
window.onload = start;

function start() {
    $(".selbox")[0].style.display = "none";
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
    setTimeout(function () {
        location.reload();
    }, 500);
}

function kick() {
    $(this).attr("data-playerId"); //this gets the id of the player to be kicked rest of function needs writing
    location.reload();
}

function updateUsers(users, isHost){
    var userContainer = $(".lbyuserContainer").first();
    userContainer.empty();

    users.forEach(function (elem) {
        var userDom = $("<div class=\"user\"></div>");
        if (isHost){
            var buttonDom = $("<button data-playerId = \"${user.userID}\" class=\"border border-white btn btn-danger float-right font-weight-bold lbyuserxbtn\">X</button>");
            userDom.append(buttonDom);
        }
        userDom.text(elem.username);
        userContainer.append(userDom);
    });
}