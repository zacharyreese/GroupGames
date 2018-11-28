<!DOCTYPE html>
<html lang="en">
<head>
    <title>display hand</title>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet"  href="/css/style.css"/>
</head>

<body>
    <div class=" bg-secondary lobbyGrid">
        <div class="lbyuserContainer">
            <#list users as id, user>
                <div class="user">${user.username}</div>
            </#list>
        </div> 
    </div>

    <script type="application/javascript" src="/scripts/jquery-3.3.1.min.js"></script>
    <script src="/scripts/lobby.js"></script>
    <script>
        var gameCode = "${gamecode}";
        <#if uid??>
        var userId = "${uid}";
        </#if>

        var wsHost = window.location.hostname + (window.location.port != "" ? ":" + window.location.port : "");
        var ws = new WebSocket("ws://" + wsHost + "/playWS");

        ws.onopen = function(event){
            ws.send(JSON.stringify({
                "player_id": userId,
                "gamecode" : gameCode
            }));
        };

        ws.onmessage = function (event) { //Receive from websocket
            console.log(event.data);

            var updateEvent = JSON.parse(event.data);

            if (updateEvent.method == "refresh") {
                location.reload();
            } else {
                if(updateEvent.users)
                    updateUsers(updateEvent.users, false);
                if(updateEvent.kick) {
                    if (updateEvent.kick == userId){
                        alert("You have been kicked from the game");
                        window.location.href = "/"
                    }
                }
            }
        };
    </script>
</body>
</html>