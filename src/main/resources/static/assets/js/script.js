function submitPot() {
    $.post("/einsatz", {"pot":app.pot}, function(result){});
}


/*<![CDATA[*/
var app = new Vue({
    el: '#body',
    data: {
        ende: true,
        displ: false,
        potValue: true,
        hitstay: false,

        kartenSpieler:[],
        kartenDealer: [],

        wertSpieler: null,
        wertDealer: null,

        ergebnis: "",
        lastGame: "",
        msg: "",

        lastGameSize: 0,
        index: 2,
        games: 0,
        counter: 0,
        pot: 0,
        capital: 0,

        message: new Date().toLocaleDateString(),
    },
    methods: {

        stay: function(){
            $.ajax({
                type: "Post",
                url: "/stay/",
                success: function () {

                    app.dealerKarten();
                    app.reset();

                    app.hitstay = false;
                    app.displ = false;
                }
            });
        },

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
        neueRundeZwei: function() {
          app.displ = true;
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

                    app.lastGamesSize();

                    app.ergebnis = "";

                    app.index = 2;
                    app.games++;

                    app.displ = true;
                }
            });
        },

        start: function() {
            app.ende = false;
            app.submit_value();
            app.hitstay = false;
            app.letzen5Spiele();
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

                    app.ende = true;
                    app.displ = false;
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



        ergebnisAnzeige: function(){
            $.ajax({
                type: "Post",
                contentType: "application/json",
                url: "/result/",
                data: null,
                dataType: 'json',
                success: function (data) {
                    if( data === "W") {
                        app.ergebnis = "WIN";
                    }else if( data === "L") {
                        app.ergebnis = "LOSE";
                    }else if( data === "P") {
                        app.ergebnis = "PUSH";
                    }else if( data === "B") {
                        app.ergebnis = "BUST";
                    }else if( data === "21"){
                        app.ergebnis = "BLACKJACk";
                    }

                    app.calculateCapital();
                    app.letzen5Spiele();
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
                    if(app.lastGameSize >= 5) {
                        app.lastGame.push(data[0]);
                        app.lastGame.push(data[1]);
                        app.lastGame.push(data[2]);
                        app.lastGame.push(data[3]);
                        app.lastGame.push(data[4]);
                    }else if(app.lastGameSize === 4) {
                        app.lastGame.push(data[0]);
                        app.lastGame.push(data[1]);
                        app.lastGame.push(data[2]);
                        app.lastGame.push(data[3]);
                    }else if(app.lastGameSize === 3) {
                        app.lastGame.push(data[0]);
                        app.lastGame.push(data[1]);
                        app.lastGame.push(data[2]);
                    }else if(app.lastGameSize === 2) {
                        app.lastGame.push(data[0]);
                        app.lastGame.push(data[1]);
                    }else if(app.lastGameSize === 1) {
                        app.lastGame.push(data[0]);
                    }else{
                        app.lastGame = [];
                    }

                }
            });
        },
        lastGamesSize: function(){
            $.ajax({
                type: "Post",
                contentType: "application/json",
                url: "/lastGamesSize/",
                data: null,
                dataType: 'json',
                success: function (data) {
                    app.lastGameSize = data;
                }
            });
        },

        reset: function() {
            app.counter -= app.counter;
            app.potValue = true;
            app.pot -= app.pot;
        },

        submit_value: function(){
            if(app.counter <=100) {
                app.msg = "";
                app.potValue = true;
                app.hitstay = true;
                app.pot = app.counter;
                submitPot();
            }else {
                app.potValue = false;
                app.msg = "Einsatz darf nicht über 100 sein"
            }
            app.getCapital();
        },

        getCapital: function(){
            $.ajax({
                type: "Post",
                contentType: "application/json",
                url: "/capital/",
                data: null,
                dataType: 'json',
                success: function (data) {
                   app.capital = data;
                }
            });
        },

        calculateCapital: function(){
            $.ajax({
                type: "Post",
                contentType: "application/json",
                url: "/calculateCapital/",
                data: null,
                dataType: 'json',
                success: function (data) {
                    app.capital = data;
                }
            });
        },
    },
});
/*]]>*/

