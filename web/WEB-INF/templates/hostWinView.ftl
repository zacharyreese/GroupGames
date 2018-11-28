<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet"  href="/css/style.css"/>
</head>

<body>
    <div class="hostgrid bg-secondary">
        <!--<div class="score-container">
                <#--<#list users as user>
                    <div class="playerscore"> ${user.username}:{player.score??}</div>
                </#list>-->
        </div>-->
        <div class="host-card">
            <div class="whitecard"> ${winnerCardText} </div>
        </div>
    </div>
    <div class="btn badge-pill badge-primary font-weight-bold border border-white timerbtn text-center">${timer}</div>

    <script src="/scripts/jquery-3.3.1.min.js"></script>
    <script src="/scripts/timer.js"></script>
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
                if(updateEvent.timer)
                    setTimer(updateEvent.timer);
            }
        };
    </script>
</body>