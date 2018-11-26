<!DOCTYPE html>
<html lang="en">
<head>
    <title>display hand</title>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet"  href="/GroupGames_Web_exploded/css/style.css"/>
</head>

<body>
    <button class="btn badge-primary font-weight-bold border border-white gtbtn lbybtn">GT</button>
    <div class="border border-white btn bg-dark font-weight-bold selbox center">
        <select class="gtSelector border border-white lbybtn bg-primary text-white font-weight-bold">
            <option value="KaH">Kahoot Against Humanity</option>
            <option value="TTT">Tic-Tac-Toe</option>
            <option value="CT4">Connect 4</option>
            <option value="Sky">Skyrim</option>
        </select>
        <br/>
        <button class= "badge-pill badge-primary font-weight-bold border border-white gtbtnx lbybtn">Confirm</button>
    </div>
    <div class=" bg-secondary lobbyGrid">
        <div class="lbyCode">${gamecode}</div>
        <div class="lbyuserContainer">
                <#list users as id, user>
                    <div class="user">${user.username}<button data-playerId = "${user.userID}" class="border border-white btn btn-danger float-right font-weight-bold lbyuserxbtn">X</button></div>
                </#list>
        </div>

        <button class="btn badge-pill badge-primary font-weight-bold lbyGridBtn border border-white lbybtn startbtn" id="startGame">Start Game</button>
    </div>

    <script type="application/javascript" src="/GroupGames_Web_exploded/scripts/jquery-3.3.1.min.js"></script>
    <script src="/GroupGames_Web_exploded/scripts/lobby.js"></script>
    <script>
        var ws = new WebSocket("ws://localhost:8080/GroupGames_Web_exploded/hostWS");

        ws.onmessage = function (event) { //Receive from websocket
            var updateEvent = JSON.parse(event.data);

            if (updateEvent.method == "refresh") {
                location.reload();
            } else {
                updateUsers(updateEvent.users, true);
            }
        };

        ws.onopen = function(event){
            ws.send(JSON.stringify({
                "gamecode" : "${gamecode}"
            }));
        };
    </script>
</body>
</html>