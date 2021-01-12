package gameLogic;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {

        //TODO checken auf gleiches Bild -> Splitten? bei splitten von zwei assen -> nur jeweils EINE weitere Karte

        //create new instance of game
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);


        //initialize and declare variables
        int capital = 1000;
        int bet = 0;
        boolean active = true;
        boolean roundActive = false;
        String input;

        Component frame = null;

        System.out.println("------------------------------------------------------------------------------");
        System.out.println(" _____             _____    ______                    _____    ______      ");
        System.out.println("|     |  |        |     |  |        |   /         |  |     |  |        |   / ");
        System.out.println("|     /  |        |     |  |        |  /          |  |     |  |        |  /");
        System.out.println("|    /   |        |     |  |        | /           |  |     |  |        | /");
        System.out.println("|___|    |        |_____|  |        |-            |  |_____|  |        |-");
        System.out.println("|    \\   |        |     |  |        | \\     |     |  |     |  |        | \\");
        System.out.println("|     \\  |        |     |  |        |  \\    |     |  |     |  |        |  \\");
        System.out.println("|     |  |        |     |  |        |   \\   |     |  |     |  |        |   \\");
        System.out.println("|_____|  |______  |     |  |______  |    \\  |_____|  |     |  |______  |    \\");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Kartenwert:");
        System.out.println("Ass   "   + "\t" + ": " + "1 // 11");
        System.out.println("König "   + "\t" + ": " + "10"    + "\t" +  "  Dame "    + "\t" + ": " + "10");
        System.out.println("Bube  "   + "\t" + ": " + "10"    + "\t" +  "  Zehn "    + "\t" + ": " + "10");
        System.out.println("Neun  "   + "\t" + ": " + "9 "    + "\t" +  "  Acht "    + "\t" + ": " + "8");
        System.out.println("Sieben"   + "\t" + ": " + "7 "    + "\t" +  "  Sechs"    + "\t" + ": " + "6");
        System.out.println("Fünf  "   + "\t" + ": " + "5 "    + "\t" +  "  Vier "    + "\t" + ": " + "4");
        System.out.println("Drei  "   + "\t" + ": " + "3 "    + "\t" +  "  Zwei "    + "\t" + ": " + "2");
        System.out.println();
        System.out.println();


        //starting game
        System.out.println("Möchtest du ein Spiel starten? Ja | Nein");

        while (active) {
            input = scanner.next();
            switch(input.toLowerCase()){
                case "j": case "ja":
                    roundActive = true;
                    active = false;
                    break;
                case "n": case "nein":
                    active = false;
                    JOptionPane.showMessageDialog(frame, "Spiel ist vorbei!");
            }
        }


        while (roundActive) {

            //initialize and declare variables
            boolean finished = false;
            boolean continuing = true;


            //place bet
            System.out.println("Dein capital beträgt " + capital + " Coins");
            System.out.println();
            System.out.println("Wie viel möchtest du setzen?");
            boolean inputOK = false;
            System.out.print("Einsatz: ");
            while(!inputOK) {
                if(scanner.hasNextInt()){
                    bet = scanner.nextInt();

                    if(bet <= 100 && bet >= 5) {
                        if(bet%5 == 0){
                            if(bet <= capital){
                                inputOK = true;
                                System.out.println("Ihr Einsatz beträgt " + bet + " Coins");
                            }
                            else {
                                System.out.println("Sie haben nicht genug Coins");
                                System.out.println("capital: " + capital);
                                System.out.print("Einsatz: ");
                                scanner.reset();
                            }
                        }
                        else {
                            System.out.println("Einsatz nur in 5er Schritten möglich!");
                            System.out.print("Einsatz: ");
                           scanner.reset();
                        }
                    }
                    else {
                        System.out.println("Einsatz ist nur zwischen 5 und 100 möglich!");
                        System.out.print("Einsatz: ");
                        scanner.reset();
                    }
                }
                else{
                    System.out.println("Bitte geben Sie eine ganze Zahl ein!");
                    System.out.print("Einsatz: ");
                    scanner.next();
                }
            }
            System.out.println("--------------------------------------");


            //shuffle when number of cards lower 78
            if (game.numberCardsRemaining() <= 78) {
                game.shuffleCardDeck();
                System.out.println("Karten sind neu gemischt");
                System.out.println("--------------------------------------");
            }


            //first two cards for player and dealer
            System.out.println();
            System.out.println("Dies sind deine ersten zwei Karten: ");

            int i = 0;
            while (i < 2) {
                //card for player
                game.setDrawnCardsPlayer(game.drawCard());
                game.setTotalValuePlayer();

                //card for dealer
                game.setDrawnCardsDealer(game.drawCard());
                game.setTotalValueDealer();
                i++;
            }


            //check if blackjack for player
            if (game.isBlackjackPlayer()) {
                finished = true;
            }


            //check if one ace or two ace, second ace -> value 1
            if (game.checkAcePlayer()) {
                if (game.getNumberDrawnAcesPlayer() == 2) {
                    game.setValueAcePlayer(0);
                    game.setTotalValuePlayer();
                }
            }


            //output card + total value
            game.getDrawnCardsPlayer();
            game.setTotalValuePlayer();
            System.out.println("Wert: " + game.getTotalValuePlayer());


            //output first card of dealer
            game.getFirstCardDealer();


            //  --  draw further cards player  --  \\
            while (!finished) {
                game.setTotalValuePlayer();

                //breaking loop if total value equals 21
                if (game.getTotalValuePlayer() == 21) {
                    break;
                }

                //Hit, Stay, Double or Split
                if (game.drawnCardsPlayer.size() == 2) {
                    if (game.drawnCardsPlayer.get(0).getPicture().equals("A") && game.drawnCardsPlayer.get(1).getPicture().equals("A")) {
                        System.out.println("Noch eine Karte? (hit: h / stay: s / verdoppeln: d / splitten: split)");
                        input = scanner.next();
                    } else {
                        System.out.println("Noch eine Karte? (hit: h / stay: s / verdoppeln: d)");
                        input = scanner.next();
                    }
                } else {
                    System.out.println("Noch eine Karte? (hit: h / stay: s)");
                    input = scanner.next();
                }


                //check input
                switch (input.toLowerCase()) {
                    case "s": case "stay":
                        finished = true;
                        break;

                    case "h": case "hit":
                        //draw card
                        game.setDrawnCardsPlayer(game.drawCard());
                        game.setTotalValuePlayer();
                        System.out.println("gezogene Karte: " + game.getCurrentCardPlayer().getPicture());
                        game.getDrawnCardsPlayer();

                        //check for aces, if total value higher 21, ace value -> 1
                        if (game.getTotalValuePlayer() > 21) {
                            if (game.checkAcePlayer()) {
                                System.out.println("bla");
                                game.setValueAcePlayer();
                            } else {
                                finished = true;
                            }
                        }
                        System.out.println("Wert Hand: " + game.getTotalValuePlayer());
                        break;

                    case "d":
                        if(game.drawnCardsPlayer.size()==2) {
                            game.setDrawnCardsPlayer(game.drawCard());
                            game.setTotalValuePlayer();
                            bet = bet * 2;
                            finished = true;
                        }else {
                            System.out.println("Verdoppeln nur vor der dritten Karte möglich!");
                            System.out.println();
                        }
                        break;

                    case "split":
                        System.out.println("noch nicht implementiert!");
                }
            }
            //  --  -----------------------------  --  \\


            //  --  draw further cards dealer  --  \\
            if (game.isBlackjackDealer()) {
                System.out.print("Karten Croupiers: ");
                game.getDrawnCardsDealer();
                System.out.println("BLACKJACK");
            }

            if (game.checkAceDealer()) {
                if (game.numberDrawnAcesDealer() == 2) {
                    game.setValueAceDealer();
                    game.setTotalValueDealer();
                }
            }

            while (game.getTotalValueDealer() < 21) {
                game.setTotalValueDealer();

                if (game.setTotalValueDealer() >= 17) {
                    if (game.getTotalValueDealer() > 21) {
                        if (game.checkAceDealer()) {
                            game.setValueAceDealer();
                        }
                    } else {
                        break;
                    }
                } else if (game.getTotalValueDealer() < 17) {
                    System.out.println("Karten Croupiers: ");
                    game.getDrawnCardsDealer();
                    System.out.println("Wert Croupiers: " + game.getTotalValueDealer());

                    //Croupiers muss ziehen wenn Wert < 17 || bei >= 17 MUSS er stehen
                    while (game.getTotalValueDealer() < 17) {
                        game.setDrawnCardsDealer(game.drawCard());
                        game.setTotalValueDealer();
                    }
                }
            }
            //  --  ------------------------------  --  \\


            //  --  calculating total values  --  \\
            game.setTotalValuePlayer();
            game.setTotalValueDealer();
            //  --  ------------------------  --  \\


            //  --  result player  --  \\
            System.out.println();
            System.out.println("Ergbenis:");
            System.out.println("    Spieler: ");
            System.out.print("      ");
            game.getDrawnCardsPlayer();
            System.out.println("    Wert: " + game.getTotalValuePlayer());
            System.out.println();
            //  --  ----------------  --  \\


            //  --  result dealer  --  \\
            System.out.println("    Croupiers: ");
            System.out.print("      ");
            game.getDrawnCardsDealer();
            System.out.println("    Wert: " + game.getTotalValueDealer());
            System.out.println();
            //  --  -----------------  --  \\


            //  --  determine winner  -- \\
            System.out.println("    Spielergebnis: " + game.determineWinner());
            System.out.println();
            //  --  ------------------  -- \\


            //  --  determine profit  -- \\
            int profit = 0;
            switch(game.determineWinner()) {
                case "WIN":
                    profit = bet * 2;
                    break;
                case "BLACKJACK":
                    profit = bet * 3;
                    break;
                case "PUSH":
                    profit = bet;
                    break;
                case "BUSt": case "LOSE":
            }
            capital = capital - bet + profit;

            System.out.println("Einsatz: " + bet);
            System.out.println("Gewinn: " + profit);
            System.out.println("capital: " + capital);
            System.out.println();
            //  --  ------------------  -- \\


            //  -- ask to continue or end  -- \\


            do {
                System.out.println("Nächste Runde? ja: j / Nein: n");
                input = scanner.next();

                switch (input.toLowerCase()) {
                    case "j":
                        if(capital < 5) {
                            System.out.println("Sie haben nicht genug Coins um continuing zu spielen!");
                            roundActive = false;
                            continuing = false;
                            JOptionPane.showMessageDialog(frame, "Nicht genug Coins. Spiel ist vorbei!");
                        }else {
                            game.resetDrawnCards();
                            game.resetTotalValuePlayer();
                            game.resetTotalValueDealer();
                            continuing = false;
                        }
                        break;
                    case "n":
                        roundActive = false;
                        continuing = false;
                        JOptionPane.showMessageDialog(frame, "Spiel ist vorbei!");
                        break;
                    default:
                }
            }while(continuing);
            //  -- -------------------------------------  -- \\
        }
    }
}
