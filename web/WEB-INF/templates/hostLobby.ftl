<!DOCTYPE html>
<html lang="en">
<head>
    <title>Totally Not Kahoot</title>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet"  href="/css/style.css"/>
    <link rel="stylesheet"  href="/css/sidebar.css"/>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
</head>

<body>
    <div id="wrapper">
        <div class="overlay"></div>
        <nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation">
            <ul class="nav sidebar-nav">
                <li class="sidebar-brand">
                    <div>Game Type</div>
                </li>
                <li>
                    <div class="gameType"  data-gameId = "KaH">Kahoot Against Humanity</div>
                </li>
                <li>
                    <div class="gameType" data-gameId = "TTT">Tic-Tac-Toe</div>
                </li>
                <li>
                    <div class="gameType" data-gameId = "Sky">Skyrim</div>
                </li>
                <li>
                    <div class="gameType" data-gameId = "CSS">CS:GO</div>
                </li>
                <li>
                    <div class="gameType" data-gameId = "MON">Monopoly</div>
                </li>
            </ul>
        </nav>
        <div id="page-content-wrapper">
            <button type="button" class="hamburger is-closed" data-toggle="offcanvas">
                <span class="hamb-top"></span>
                <span class="hamb-middle"></span>
                <span class="hamb-bottom"></span>
            </button>
            <div class="container">
                <div class=" bg-secondary lobbyGrid">
                    <div class="lbyCode">${gamecode}</div>
                    <div class="lbygametext" id="lbygametext">Please select a game type</div>
                    <div class="lbyuserContainer">
                            <#list users as id, user>
                                <div class="user">${user.username}<button data-playerId = "${user.userID}" class="btn-danger float-right font-weight-bold lbyuserxbtn">X</button></div>
                            </#list>
                    </div>
                    <button type="submit" class="badge-pill badge-primary font-weight-bold lbyGridBtn startbtn">Start Game</button>
                </div>
            </div>
        </div>
    </div>

    <script type="application/javascript" src="/scripts/jquery-3.3.1.min.js"></script>
    <script src="/scripts/lobby.js"></script>
    <script src="/scripts/sidebar.js"></script>
    <script>
        var wsHost = window.location.hostname + (window.location.port != "" ? ":" + window.location.port : "");
        var ws = new WebSocket("ws://" + wsHost + "/hostWS");

        ws.onopen = function(event){
            ws.send(JSON.stringify({
                "gamecode" : "${gamecode}"
            }));
        };

        ws.onmessage = function (event) { //Receive from websocket
            console.log(event.data);
            var updateEvent = JSON.parse(event.data);

            if (updateEvent.method == "refresh") {
                location.reload();
            } else {
                if(updateEvent.users)
                    updateUsers(updateEvent.users, true);
            }
        };
    </script>
</body>
</html>