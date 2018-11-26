<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet"  href="/GroupGames_Web_exploded/css/style.css"/>
</head>

<body>
    <div class="hostgrid bg-secondary">
        <div class="score-container">
                <#list users as id, user>
                    <div class="playerscore"> ${user.username}:{player.score}</div>
                </#list>
        </div>
        <div class="host-card">
            <div class="blackcard"> ${hostCard.text} </div>
        </div>
    </div>
    <div class="btn badge-pill badge-primary font-weight-bold border border-white timerbtn text-center">${timer}</div>

    <script src="/GroupGames_Web_exploded/scripts/host.js"></script>
    <script src="/GroupGames_Web_exploded/scripts/jquery-3.3.1.min.js"></script>
</body>
</html>