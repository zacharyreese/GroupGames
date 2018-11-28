<!DOCTYPE html>
<html lang="en">
<head>
    <title>display hand</title>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet"  href="/css/handview.css"/>
    <script>
        var ws = new WebSocket("ws://localhost:8080/hostWS");
        var time = 15;
        ws.onmessage = function (event) { //Receive from websocket
            console.log(event.data);
            time = JSON.parse(event.data).timer;
            console.log(time);
            document.getElementById("timer").innerHTML = time;
        }
        ws.onopen = function(event){ws.send("{\"player_id\": \"${(uid)!""}\", \"gamecode\":\"${gamecode}\"}");};
    </script>
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
    <button class="btn btn-primary font-weight-bold" id="timer"></button>

    <script type="application/javascript" src="/scripts/jquery-3.3.1.min.js"></script>
    <script src="/scripts/handview.js"></script>
</body>
</html>