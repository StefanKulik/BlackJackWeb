function menuOpen() {
    document.getElementById("menu-bar").classList.toggle("show");
}
function menuIcon(){
    document.getElementById("but").classList.toggle("change");
}



/*<![CDATA[*/
var app = new Vue({
    el: '#body',
    data: {
        ende: true,
        displ: false,
        kartenSpieler:[],
        kartenDealer: [],
        wertSpieler: null,
        wertDealer: null,
        ergebnis: "",
        lastGame: "",
        index: 2,
        games: 0,



        message: new Date().toLocaleDateString(),
    },
    methods: {
        hit: function () {
            $.ajax({
                type: "Post",
                contentType: "application/json",
                url: "/hit/",
                data: null,
                dataType: 'json',
                success: function (data) {
                    app.kartenSpieler.push(data[app.index]);
                    app.index++;
                    app.spielerWert();
                }
            });
        },
        spielerWert: function() {
            $.ajax({
                type: "Post",
                url: "/playerValue/",
                success: function (data) {
                    app.wertSpieler = [];
                    app.wertSpieler = data;
                    if(data >= 21){
                        app.stay();
                        app.displ = false;
                    }
                }
            });
        },
        spielerKarten: function() {
            $.ajax({
                type: "Post",
                contentType: "application/json",
                url: "/playerCards/",
                data: null,
                dataType: 'json',
                success: function (data) {

                    app.kartenSpieler.push(data[0]);
                    app.kartenSpieler.push(data[1]);
                    app.spielerWert();
                }
            });
        },

        dealerAnfangsKarten: function(){
            $.ajax({
                type: "Post",
                contentType: "application/json",
                url: "/dealerStartCard/",
                data: null,
                dataType: 'json',
                success: function (data) {
                    app.kartenDealer.push(data[0]);
                    app.kartenDealer.push("");

                    switch(data[0]){
                        case "A":
                            app.wertDealer = 11;
                            break;
                        case "K": case "D": case "B": case "10":
                            app.wertDealer = 10;
                            break;
                        case "9":
                            app.wertDealer = 9;
                            break;
                        case "8":
                            app.wertDealer = 8;
                            break;
                        case "7":
                            app.wertDealer = 7;
                            break;
                        case "6":
                            app.wertDealer = 6;
                            break;
                        case "5":
                            app.wertDealer = 5;
                            break;
                        case "4":
                            app.wertDealer = 4;
                            break;
                        case "3":
                            app.wertDealer = 3;
                            break;
                        case "2":
                            app.wertDealer = 2;
                    }
                }
            });
        },
        dealerKarten: function(){
            $.ajax({
                type: "Post",
                contentType: "application/json",
                url: "/dealerCards/",
                data: null,
                dataType: 'json',
                success: function (data) {
                    app.kartenDealer = [];
                    data.map((data) => {
                        app.kartenDealer.push(data);
                    });
                    app.ergebnisAnzeige();
                    app.dealerWert();
                }
            });
        },
        dealerWert: function(){
            $.ajax({
                type: "Post",
                url: "/dealerValue/",
                success: function (data) {
                    app.wertDealer = [];
                    app.wertDealer = data;
                }
            });
        },



        neueRunde: function(){
            $.ajax({
                type: "Post",
                url: "/newRound/",
                success: function () {
                    app.kartenSpieler = [];
                    app.wertSpieler = null;
                    app.spielerKarten();

                    app.kartenDealer = [];
                    app.wertDealer = null;
                    app.dealerAnfangsKarten();

                    app.ergebnis = "";

                    app.index = 2;
                    app.games++;
                }
            });
        },
        stay: function(){
            $.ajax({
                type: "Post",
                url: "/stay/",
                success: function () {
                    if(app.games>1){
                        app.letzen5Spiele();
                    }
                    app.dealerKarten();
                }
            });
        },
        end: function(){
            $.ajax({
                type: "Post",
                url: "/end/",
                success: function () {
                    app.kartenSpieler = [];
                    app.kartenDealer = [];
                    app.wertSpieler = null;
                    app.wertDealer = null;
                    app.ergebnis = "";
                    app.lastGame = [];
                }
            });
        },
        ergebnisAnzeige: function(){
            $.ajax({
                type: "Post",
                contentType: "application/json",
                url: "/result/",
                data: null,
                dataType: 'json',
                success: function (data) {
                    app.ergebnis = data;
                }
            });
        },
        letzen5Spiele: function(){
            $.ajax({
                type: "Post",
                contentType: "application/json",
                url: "/lastGames/",
                data: null,
                dataType: 'json',
                success: function (data) {
                    app.lastGame = []
                    if(app.games === 1) {
                        app.lastGame.push(data[0]);
                    }else if(app.games === 2){
                        app.lastGame.push(data[0]);
                        app.lastGame.push(data[1]);
                    }else if(app.games === 3){
                        app.lastGame.push(data[0]);
                        app.lastGame.push(data[1]);
                        app.lastGame.push(data[2]);
                    }else if(app.games === 4){
                        app.lastGame.push(data[0]);
                        app.lastGame.push(data[1]);
                        app.lastGame.push(data[2]);
                        app.lastGame.push(data[3]);
                    }else{
                        app.lastGame.push(data[0]);
                        app.lastGame.push(data[1]);
                        app.lastGame.push(data[2]);
                        app.lastGame.push(data[3]);
                        app.lastGame.push(data[4]);
                    }
                }
            });
        },

        test: function(){
            $.ajax({
                type: "Post",
                contentType: "application/json",
                url: "/lastGames/",
                data: null,
                dataType: 'json',
                success: function (data) {

                }
            });
        },

    },
});
/*]]>*/

