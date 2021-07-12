$( document ).ready(function() {
    if($("body").hasClass("light")){
        document.getElementById('switch').checked = false;
        //document.getElementById('label').innerText = "Light";
        document.getElementById("symbol").innerHTML = "&#xf185;";
    }else{
        document.getElementById('switch').checked = true;
        // document.getElementById('label').innerText = "Dark";
        document.getElementById("symbol").innerHTML = "&#xf186;";
    }

});

function setClassToBody() {
    if(document.getElementById('switch').checked === false){
        document.querySelector('body').classList.add('light');
        //document.getElementById('label').innerText = "Light";
        document.getElementById("symbol").innerHTML = "&#xf185;";
        $.post("/setTheme", {"theme":"LIGHT"}, function(result){});
    }else {
        document.querySelector('body').classList.remove('light');
        //document.getElementById('label').innerText = "Dark";
        document.getElementById("symbol").innerHTML = "&#xf186;";
        $.post("/setTheme", {"theme":"DARK"}, function(result){});
    }
}


