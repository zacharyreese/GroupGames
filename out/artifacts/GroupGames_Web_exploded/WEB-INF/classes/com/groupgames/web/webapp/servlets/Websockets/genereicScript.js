var ws = new WebSocket("ws://localhost:8080/GroupGames_Web_exploded/ws");
ws.onmessage = function (event) {
    console.log(event.data);
    document.getElementById("log").value += "[" + timestamp() + "] " + event.data + "\n";
}

document.getElementById("InsertButtonToSendCommandHere").addEventListener("keyup", function (event) {
        ws.send(event.target.value); //Send servlet command to websocket
        event.target.value = "";
});

function timestamp() {
    var d = new Date(), minutes = d.getMinutes();
    if (minutes < 10) minutes = '0' + minutes;
    return d.getHours() + ':' + minutes;
}