package Controller;

import BlackJack.GuiWindow.MainWindow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import BlackJack.gameLogic.Application;
import BlackJack.Config;

import javax.swing.*;
import java.util.ArrayList;


@RestController
public class BlackJackRest {

    ArrayList<String> playerCards = new ArrayList<>();
    ArrayList<String> dealerCards = new ArrayList<>();
    ArrayList<String> lastFiveGames = new ArrayList<>();
    int i;
    int numberPlayer;
    int index = 2;
    int potPlayer;

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
        index = 2;
        playerCards.clear();
        dealerCards.clear();
        Application.spiel.resetDrawnCards();
        Application.spiel.resetTotalValuePlayer();
        Application.spiel.resetTotalValueDealer();
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
        playerCards.clear();
        dealerCards.clear();
        index = 2;
        Application.spiel.resetDrawnCards();
        i = 0;
        if(Application.spiel.numberCardsRemaining() <= 78 || Application.spiel.numberCardsRemaining() <= 81) {
            Application.spiel.shuffleCardDeck();
        }
        while (i < 2) {
            //Card for Player
            Application.spiel.setDrawnCardsPlayer(Application.spiel.drawCard());
            Application.spiel.setTotalValuePlayer();
            playerCards.add(Application.spiel.drawnCardsPlayer.get(i).getPicture());


            //Cards for Croupiers
            Application.spiel.setDrawnCardsDealer(Application.spiel.drawCard());
            Application.spiel.setTotalValueDealer();
            dealerCards.add(Application.spiel.drawnCardsDealer.get(i).getPicture());

            i++;
        }
        if (Application.spiel.checkAcePlayer()) {
            if (Application.spiel.getNumberDrawnAcesPlayer() == 2) {
                Application.spiel.setValueAcePlayer(0);
                Application.spiel.setTotalValuePlayer();
            }
        }
        if (Application.spiel.checkAceDealer()) {
            if (Application.spiel.numberDrawnAcesDealer() == 2) {
                Application.spiel.setValueAceDealer();
                Application.spiel.setTotalValueDealer();
            }
        }

        numberPlayer = 2;
    }

    @PostMapping("hit")
    public ArrayList<String> hitCard(){
        if(Application.spiel.numberCardsRemaining() <= 78 || Application.spiel.numberCardsRemaining() <= 81) {
            Application.spiel.shuffleCardDeck();
        }
        Application.spiel.setDrawnCardsPlayer(Application.spiel.drawCard());
        numberPlayer++;
        playerCards.add(Application.spiel.drawnCardsPlayer.get(numberPlayer -1).getPicture());
        Application.spiel.setTotalValuePlayer();

        if (Application.spiel.getTotalValuePlayer() > 21) {
            if (Application.spiel.checkAcePlayer()) {
                System.out.println("bla");
                Application.spiel.setValueAcePlayer();
            }
        }

        return playerCards;
    }


// Player Methods
    @PostMapping("playerCards")
    public ArrayList<String> playerCards(){
        return playerCards;
    }

    @PostMapping("playerValue")
    public int playerValue() {
        return  Application.spiel.getTotalValuePlayer();
    }


// Dealer Methods
    @PostMapping("dealerStartCard")
    public ArrayList<String> dealerStartCard(){
        return dealerCards;
    }

    @PostMapping("dealerCards")
    public ArrayList<String> dealerCards() {
        while (Application.spiel.getTotalValueDealer() < 21) {
            Application.spiel.setTotalValueDealer();

            if (Application.spiel.setTotalValueDealer() >= 17) {
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
                    dealerCards.add(Application.spiel.drawnCardsDealer.get(index).getPicture());
                    Application.spiel.setTotalValueDealer();

                    index++;
                }
            }
        }
        return dealerCards;
    }

    @PostMapping("dealerValue")
    public int dealerValue() {
        return Application.spiel.getTotalValueDealer();
    }


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
    public int lastGamesSize(){
        return Config.getInstance().getLastGames().size();
    }


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

    @PostMapping("minimize")
    public void minimize(){
        Application.getWindow().setState(JFrame.ICONIFIED);
    }
}
