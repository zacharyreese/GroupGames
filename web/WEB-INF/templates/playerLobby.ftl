<!DOCTYPE html>
<html lang="en">
<head>
    <title>display hand</title>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet"  href="/GroupGames_Web_exploded/css/style.css"/>
</head>

<body>
    <div class=" bg-secondary lobbyGrid">
        <div class="lbyuserContainer">
            <#list users as id, user>
                <div class="user">${user.username}</div>
            </#list>
        </div> 
    </div>

    <script type="application/javascript" src="/GroupGames_Web_exploded/scripts/jquery-3.3.1.min.js"></script>
    <script src="/GroupGames_Web_exploded/scripts/lobby.js"></script>
</body>