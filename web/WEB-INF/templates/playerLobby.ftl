<!DOCTYPE html>
<html lang="en">
<head>
    <title>display hand</title>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet"  href="/GroupGames_Web_exploded/css/style.css"/>
    <script>
        var ws = new WebSocket("ws://localhost:8080/GroupGames_Web_exploded/playWS");
        var hostWS = new WebSocket("ws://localhost:8080/GroupGames_Web_exploded/hostWS")

        ws.onmessage = function (event) { //Receive from websocket
            console.log(event.data);
            if (JSON.parse(event.data).method == "refresh") {
                location.reload();
            }
        }
        ws.onopen = function(event){ws.send("{\"player_id\": \"${(uid)!""}\", \"gamecode\":\"${gamecode}\"}");};
    </script>
</head>

<body>
    <div class=" bg-secondary lobbyGrid">
        <div class="lbyuserContainer">
            <#list users as id, user>
                <div class="user">${user.username}</div>
            </#list>
            <ul id="userlist"></ul>
        </div> 
    </div>

    <script type="application/javascript" src="/GroupGames_Web_exploded/scripts/jquery-3.3.1.min.js"></script>
    <script src="/GroupGames_Web_exploded/scripts/playerLobby.js"></script>
</body>
</html>