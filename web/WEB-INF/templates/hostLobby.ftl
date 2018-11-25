<!DOCTYPE html>
<html lang="en">
<head>
    <title>display hand</title>
    <meta charset="utf-8" />
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet"  href="/GroupGames_Web_exploded/css/style.css"/>

</head>

<body>
    <button class="btn badge-primary font-weight-bold border border-white gtbtn lbybtn">GT</button>
    <button class="btn badge-primary font-weight-bold border border-white gtbtn2 lbybtn">#P</button>
    <div class=" bg-secondary lobbyGrid">
    <div class="lbyCode">${gamecode}</div>
        <div class="lbyuserContainer">
            <#list users as id, user>
                <div class="user">${user.username}<button data-uid="${user.userID}" class="border border-white btn btn-danger float-right font-weight-bold lbyuserxbtn">X</button></div>
            </#list>
        </div> 
        
        <button type="submit" class="btn badge-pill badge-primary font-weight-bold lbyGridBtn border border-white lbybtn">Start Game</button>
    </div>

    <script type="application/javascript" src="/GroupGames_Web_exploded/scripts/jquery-3.3.1.min.js"></script>
    <script src="/GroupGames_Web_exploded/scripts/index.js"></script>
</body>