$(function () {
    var gameWord;
    window.onload = start;

    /*var ws = new WebSocket("ws://localhost:8080/GroupGames_Web_exploded/hostWS");

     ws.onmessage = function (event) { //Receive from websocket
         console.log(event.data);
         console.log(event.data);
         if (event.data.method == "refresh") {
             location.reload();
         }
     }
     ws.onopen = function(event){ws.send("{\"player_id\": \"${(uid)!""}\", \"gamecode\":\"${gamecode}\"}");};*/

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

    function kick() {
        $(this).attr("data-playerId"); //this gets the id of the player to be kicked rest of function needs writing
    }
});