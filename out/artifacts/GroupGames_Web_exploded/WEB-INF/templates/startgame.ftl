<head>
    <title>Totally Not Kahoot</title>
    <meta charset="utf-8" />
    <link rel="stylesheet"  href="/GroupGames_Web_exploded/css/style.css" />
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>
<body>
<div class="bg-secondary myGrid">
    <div class="header font-weight-bold text-center text-white item-a">
        <p class="header-child">SEGA</p>
    </div>
    <form class="main item-b" action="game/play/join" method="post">
        <input type="text" class="text-center main-child rounded bg-primary text-white font-weight-bold" name="userName" placeholder="User Name:">
        <input type="text" class="text-center main-child rounded bg-primary text-white font-weight-bold" name="lobbyName" placeholder="Lobby Name:">
        <button type="submit" class="main-child badge badge-pill badge-primary">Join Lobby</button>
    </form>
    <form class="main item-b" action="game/host/new" method="post">
        <button type="submit" class="main-child badge badge-pill badge-primary">Host Lobby</button>
    </form>
</div>
<form class="main item-b" action="game/host/new" method="post">
    <button type="submit" class="main-child badge badge-pill badge-primary">Host Lobby</button>
</form>
</body>