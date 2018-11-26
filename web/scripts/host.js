var timer;
window.onload = start;

function start() {
    timer = $(".btn")[0];
}

function setTimer (time){
    timer.innerHTML = time;
}