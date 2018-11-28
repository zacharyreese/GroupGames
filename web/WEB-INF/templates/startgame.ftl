<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script>
        var ws = new WebSocket("ws://ansel.jacobposton.com/hostWS");
        ws.onmessage = function (event) { //Receive from websocket
            console.log(event.data);
        }
    </script>
    <style>
        body, html {
            height: 100%;
            margin: 0;
        }

        .bg {
            /* The image used */
            background-image: url("https://i.imgur.com/WCW0ky3.png");

            /* Full height */
            height: 100%;

            /* Center and scale the image nicely */
            background-position: center;
            background-repeat: no-repeat;
            background-size: cover;
        }
        </style>
    <script type="application/javascript" src="/scripts/jquery-3.3.1.min.js"></script>
    <script src="/scripts/startgame.js"></script>
</head>

<body>
<div class="bg"></div>
</body>
</html>