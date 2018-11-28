var gameWord;
window.onload = start;

function start() {
    $(".startbtn").click(startgame);
    $(".lbyuserxbtn").click(kick);
    $(".gameType").click(changeGameText);
}


function startgame () {
    var gameSelection = gameWord || $(".gameType")[0].attr("data-gameId");

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

function changeGameText () {
    document.getElementById("lbygametext").innerHTML =  this.innerHTML;
    gameWord = $(this).attr("data-gameId");
}

function kick () {
    var playerUid = $(this).attr("data-playerId"); //this gets the id of the player to be kicked rest of function needs writing

    //your ajax request will go here.
    $.ajax({
        type: "POST",
        url: window.location.pathname,
        data: JSON.stringify({
            "type": "kick",
            "uid": playerUid,
        }),
        success: function () {
        },
        dataType: "json",
        contentType: "application/json; charset=utf-8",
    });
}

function updateUsers(users, isHost){
    var userContainer = $(".lbyuserContainer").first();
    userContainer.empty();

    users.forEach(function (elem) {
        var userDom = $("<div class=\"user\"></div>");
        userDom.text(elem.username);

        if (isHost){
            var buttonDom = $("<button class=\"border border-white btn btn-danger float-right font-weight-bold lbyuserxbtn\">X</button>");
            buttonDom.attr("data-playerId", elem.userID)
            userDom.append(buttonDom);
        }

        userContainer.append(userDom);
        $(".lbyuserxbtn").click(kick);
    });
}