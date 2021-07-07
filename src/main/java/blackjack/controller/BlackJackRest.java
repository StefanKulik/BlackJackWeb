package blackjack.controller;

import blackjack.gameLogic.Cards;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import blackjack.application.Application;
import blackjack.application.Config;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


@RestController
public class BlackJackRest {

    ArrayList<String> lastFiveGames = new ArrayList<>();
    int i;
    int potPlayer;
    static Point compCoords;


    @PostMapping("setTheme")
    public String setTheme(@RequestParam(name = "theme") String theme) {


        switch (theme) {
            case "DARK":
                Config.getInstance().setTheme(Config.Theme.DARK);
                break;
            case "LIGHT":
                Config.getInstance().setTheme(Config.Theme.LIGHT);
                break;
        }

        Config.getInstance().saveConfig();
        Config.getInstance().loadConfig();


        return "\"ok\"";
    }


// Round Methods
    @PostMapping("end")
    public void end() {
        Application.spiel.resetDrawnCards();
        Application.spiel.resetTotalValue();
        Application.spiel.shuffleCardDeck();

        Config.getInstance().loadConfig();
        lastFiveGames = Config.getInstance().getLastGames();
        if(lastFiveGames.size()>5){
            lastFiveGames.remove(0);
        }
        Config.getInstance().setLastGames(lastFiveGames);
        Config.getInstance().saveConfig();
    }

    @PostMapping("exit")
    public void exit() {
        System.exit(0);
    }

    @PostMapping("stay")
    public void stay() { }

    @PostMapping("newRound")
    public void newRound() {
        Application.spiel.resetDrawnCards();
        i = 0;
        if(Application.spiel.numberCardsRemaining() <= 78 || Application.spiel.numberCardsRemaining() <= 81) {
            Application.spiel.shuffleCardDeck();
        }
        while (i < 2) {
            //Card for Player
            Application.spiel.setDrawnCardsPlayer(Application.spiel.drawCard());
            Application.spiel.setTotalValuePlayer();


            //Cards for Croupiers
            Application.spiel.setDrawnCardsDealer(Application.spiel.drawCard());
            Application.spiel.setTotalValueDealer();

            i++;
        }
        if (Application.spiel.checkAcePlayer()) {
            if (Application.spiel.getNumberDrawnAcesPlayer() == 2) {
                Application.spiel.setValueAcePlayer(0);
                Application.spiel.setTotalValuePlayer();
            }
        }
        if (Application.spiel.checkAceDealer()) {
            if (Application.spiel.getNumberDrawnAcesDealer() == 2) {
                Application.spiel.setValueAceDealer();
                Application.spiel.setTotalValueDealer();
            }
        }
    }

    @PostMapping("hit")
    public ArrayList<Cards> hitCard(){
        if(Application.spiel.numberCardsRemaining() <= 78 || Application.spiel.numberCardsRemaining() <= 81) {
            Application.spiel.shuffleCardDeck();
        }
        Application.spiel.setDrawnCardsPlayer(Application.spiel.drawCard());
        Application.spiel.setTotalValuePlayer();

        if (Application.spiel.getTotalValuePlayer() > 21) {
            if (Application.spiel.checkAcePlayer()) {
                System.out.println("bla");
                Application.spiel.setValueAcePlayer();
            }
        }
            return Application.spiel.drawnCardsPlayer;
//        return playerCards;
    }



// Player Methods
    @PostMapping("playerCards")
    public ArrayList<Cards> playerCards(){ return Application.spiel.drawnCardsPlayer; }

    @PostMapping("playerValue")
    public int playerValue() { return  Application.spiel.getTotalValuePlayer(); }



// Dealer Methods
    @PostMapping("dealerStartCard")
    public ArrayList<Cards> dealerStartCard(){ return Application.spiel.drawnCardsDealer; }

    @PostMapping("dealerCards")
    public ArrayList<Cards> dealerCards() {
        while (Application.spiel.getTotalValueDealer() < 21) {
            Application.spiel.setTotalValueDealer();

            if (Application.spiel.getTotalValueDealer() >= 17) {
                if (Application.spiel.getTotalValueDealer() > 21) {
                    if (Application.spiel.checkAceDealer()) {
                        Application.spiel.setValueAceDealer();
                    }
                }else {
                    break;
                }
            } else if (Application.spiel.getTotalValueDealer() < 17) {
                //Croupiers must hit when Value < 17 || bei >= 17 must stay
                while (Application.spiel.getTotalValueDealer() < 17) {
                    Application.spiel.setDrawnCardsDealer(Application.spiel.drawCard());
                    Application.spiel.setTotalValueDealer();
                }
            }
        }
        return Application.spiel.drawnCardsDealer;
    }

    @PostMapping("dealerValue")
    public int dealerValue() { return Application.spiel.getTotalValueDealer(); }



// Result Methods
    @PostMapping("result")
    public String result(){
        Config.getInstance().loadConfig();
        lastFiveGames = Config.getInstance().getLastGames();
        if(lastFiveGames.size() < 6) {
            lastFiveGames.add(Application.spiel.determineWinner());
        }else if(lastFiveGames.size() == 6) {
            lastFiveGames.remove(0);
            lastFiveGames.add(Application.spiel.determineWinner());
        }
        Config.getInstance().setLastGames(lastFiveGames);
        Config.getInstance().saveConfig();
        System.out.println(lastFiveGames);
        return "\"" +Application.spiel.determineWinner()+ "\"";
    }

    @PostMapping("lastGames")
    public ArrayList<String> lastFiveGames (){
        Config.getInstance().loadConfig();
        return Config.getInstance().getLastGames();
    }

    @PostMapping("/lastGamesSize")
    public int lastGamesSize(){ return Config.getInstance().getLastGames().size(); }



// Einsatz, Pot, Capital Methods
    @PostMapping("einsatz")
    public void einsatz(@RequestParam(name = "pot") int pot) {
        System.out.println(pot);
        potPlayer = pot;
        Config.getInstance().setCapital(Config.getInstance().getCapital() - pot);

        Config.getInstance().saveConfig();
        Config.getInstance().loadConfig();

        System.out.println(Config.getInstance().getCapital());
    }

    @PostMapping("capital")
    public int capital() {
        Config.getInstance().loadConfig();
        System.out.println("Vor Runde: " + Config.getInstance().getCapital());
        return Config.getInstance().getCapital();
    }

    @PostMapping("calculateCapital")
    public int calculateCapital() {
        String result = Application.spiel.determineWinner();
        System.out.println(result);
        switch(result) {
            case "W":
                System.out.println(Config.getInstance().getCapital());
                Config.getInstance().setCapital(Config.getInstance().getCapital() + potPlayer * 2);
                System.out.println("Gewinn: " + potPlayer*2);
                System.out.println(Config.getInstance().getCapital());
                break;
            case "P":
                System.out.println(Config.getInstance().getCapital());
                Config.getInstance().setCapital(Config.getInstance().getCapital() + potPlayer);
                System.out.println("Gewinn: " + potPlayer);
                System.out.println(Config.getInstance().getCapital());
                break;
            case "21":
                System.out.println(Config.getInstance().getCapital());
                Config.getInstance().setCapital(Config.getInstance().getCapital() + potPlayer * 3);
                System.out.println("Gewinn: " + potPlayer*3);
                System.out.println(Config.getInstance().getCapital());
                break;
            case "B": case "L":
                System.out.println("Gewinn: " + -potPlayer);
        }

        Config.getInstance().saveConfig();
        Config.getInstance().loadConfig();
        System.out.println("Nach Runde: " + Config.getInstance().getCapital());
        return Config.getInstance().getCapital();
    }



// window methods
    @PostMapping("minimize")
    public void minimize(){
        try {
            Application.getWindow().setExtendedState(JFrame.ICONIFIED);
        }
        catch(NullPointerException e) {
            System.out.println("NullPointerException caught");
        }
    }

    @PostMapping("vergrößern")
    public void vergroessern(){
        try {
            Application.getWindow().setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        catch(NullPointerException e) {
            System.out.println("NullPointerException caught");
        }

    }

    @PostMapping("verkleinern")
    public void verkleinern(){
        try {
            Application.getWindow().setSize(1200,675);
            Application.getWindow().setLocationRelativeTo(null);
        }
        catch(NullPointerException e) {
            System.out.println("NullPointerException caught");
        }

    }
}
