<!DOCTYPE html>
<html lang="en">
<head>
    <title>display hand</title>
    <meta charset="utf-8" />
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet"  href="/GroupGames_Web_exploded/css/style.css"/>
    <script src="scripts/index.js"></script>
   
</head>
<body>
    <button class="btn badge-primary font-weight-bold border border-white gtbtn lbybtn">GT</button>
    <button class="btn badge-primary font-weight-bold border border-white gtbtn2 lbybtn">GT</button>
    <div class=" bg-secondary lobbyGrid">
        <p>${gamecode}</p>
        <div class="lbyuserContainer">
            <#list users as id, player>
                <div class="user ">${player.name} <button value="${id}" class="border border-white btn btn-danger float-right font-weight-bold lbyuserxbtn">X</button></div>
            </#list>
        </div> 
        
        <button type="submit" class="btn badge-pill badge-primary font-weight-bold lbyGridBtn border border-white lbybtn">Start Game</button>
        <form class="main item-b" action="game/host/new" method="post">
            <button type="submit" class="main-child badge badge-pill badge-primary">Host Lobby</button>
        </form>
    </div>


</body>