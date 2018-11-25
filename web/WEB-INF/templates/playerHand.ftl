<!DOCTYPE html>
<html lang="en">
<head>
    <title>display hand</title>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet"  href="/GroupGames_Web_exploded/css/handview.css"/>

</head>

<body>
    <section class="cardy" data-votePlay = "${gameState.submitUrl}">
        <! gameState.submitUrl is a url string for the ajax request. It should tell the page when it is on the vote screen or on the play screen>
        <#list gameState.cards as card>
            <div class="card--content" data-cardId = "${card.id}"> ${card.text}</div>
        </#list>
    </section>
    <button class="btn btn-danger font-weight-bold" id="x-btn">X</button>
    <button class="btn btn-primary font-weight-bold" id="timer">${gameState.time}</button>

    <script type="application/javascript" src="/GroupGames_Web_exploded/scripts/jquery-3.3.1.min.js"></script>
    <script src="/GroupGames_Web_exploded/scripts/handview.js"></script>
</body>