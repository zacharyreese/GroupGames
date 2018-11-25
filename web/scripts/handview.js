window.onload = startup;

function startup() {
    var i;
    var cards = document.getElementsByClassName("card--content");
    for (i = 0; i < cards.length; i++) {
        cards[i].addEventListener("click", selectMe);
    }
    document.getElementById("timer").addEventListener("click", submit);
    document.getElementsByClassName("waitText")[0].style.display = "none";
}

function selectMe() {

    var list = $(".selectedcard").removeClass("selectedcard");
    $(this).addClass("selectedcard");
}

function submitted () {
     document.getElementsByClassName("waitText")[0].style.display = "block";
     document.getElementsByClassName("cardy")[0].style.display = "none";
}


function submit() {
    var goldcard = $(".selectedcard").first();
    var submitAction = {
        "type":"submit", 
        "cardID":goldcard.attr("data-cardId")
    }
    $.ajax({
      type: "POST",
      url: $(".cardy").attr("data-votePlay"),
      data: JSON.stringify(submitAction),
      success: submitted,
      dataType: "json",
      contentType: "application/json; charset=utf-8",
    });
    
}

