function menuOpen() {
    document.getElementById("menu-bar").classList.toggle("show");
}
function menuIcon(){
    document.getElementById("but").classList.toggle("change");
}



/*<![CDATA[*/
var app3 = new Vue({
    el: '#message',
    data: {
        message: new Date().toLocaleDateString(),
    }
});
/*]]>*/


/*<![CDATA[*/
var app = new Vue({
    el: '#spielfeld',
    data: {
        ende: true,
        displ: false,
        kartenSpieler:[],
        kartenDealer: [],
        wertSpieler: null,
        wertDealer: null,
        ergebnis: "",
        index: 2,
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
                url: "/spielerWert/",
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
                url: "/spielerKarten/",
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
                url: "/dealerAnfangsKarten/",
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
                url: "/dealerKarten/",
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
                url: "/dealerWert/",
                success: function (data) {
                    app.wertDealer = [];
                    app.wertDealer = data;
                }
            });
        },



        neueRunde: function(){
            $.ajax({
                type: "Post",
                url: "/neueRunde/",
                success: function () {
                    app.kartenSpieler = [];
                    app.wertSpieler = null;
                    app.spielerKarten();

                    app.kartenDealer = [];
                    app.wertDealer = null;
                    app.dealerAnfangsKarten();

                    app.ergebnis = "";

                    app.index = 2;
                }
            });
        },
        stay: function(){
            $.ajax({
                type: "Post",
                url: "/stay/",
                success: function () {
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
                }
            });
        },
        ergebnisAnzeige: function(){
            $.ajax({
                type: "Post",
                contentType: "application/json",
                url: "/ergebnis/",
                data: null,
                dataType: 'json',
                success: function (data) {
                    app.ergebnis = data;
                }
            });
        },



    },
});
/*]]>*/

