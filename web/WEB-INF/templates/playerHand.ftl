<!DOCTYPE html>
<html lang="en">
<head>
    <title>display hand</title>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet"  href="/css/handview.css"/>
</head>

<body>
    <section class="cardy">
        <!-- gameState.submitUrl is a url string for the ajax request. It should tell the page when it is on the vote screen or on the play screen -->
        <#if cards??>
        <#list cards as cardID, cardText>
            <div class="card--content" data-cardId = "${cardID}"> ${cardText}</div>
        </#list>
        </#if>
    </section>
    <button class="btn btn-danger font-weight-bold" id="x-btn">X</button>
    <button class="btn btn-primary font-weight-bold timerbtn" id="timer">${timer}</button>

    <script type="application/javascript" src="/scripts/jquery-3.3.1.min.js"></script>
    <script src="/scripts/handview.js"></script>
    <script src="/scripts/timer.js"></script>
    <script>
        var wsHost = window.location.hostname + (window.location.port != "" ? ":" + window.location.port : "");
        var ws = new WebSocket("ws://" + wsHost + "/playWS");

        ws.onopen = function(event){
            ws.send(JSON.stringify({
                "player_id": "${uid}",
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
</html>