package Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import BlackJack.Application;
import BlackJack.Config;

import java.util.ArrayList;


@RestController
public class BlackJackRest {

    ArrayList<String> playerCards = new ArrayList<>();
    ArrayList<String> dealerCards = new ArrayList<>();
    ArrayList<String> lastFiveGames = new ArrayList<String>();
    int i;
    int numberPlayer;
    int index = 2;



    @PostMapping("/setTheme")
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


    @PostMapping("/newRound")
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


    @PostMapping("/hit")
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
    @PostMapping("playerCards")
    public ArrayList<String> playerCards(){
        return playerCards;
    }
    @PostMapping("playerValue")
    public int playerValue() {
        return  Application.spiel.getTotalValuePlayer();
    }





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





    @PostMapping("end")
    public void end() {
        index = 2;
        playerCards.clear();
        dealerCards.clear();
        Application.spiel.resetDrawnCards();
        Application.spiel.resetTotalValuePlayer();
        Application.spiel.resetTotalValueDealer();
        Application.spiel.shuffleCardDeck();
    }
    @PostMapping("stay")
    public void stay() {

    }
    @PostMapping("result")
    public String result(){
        if(lastFiveGames.size() < 6) {
            lastFiveGames.add(Application.spiel.determineWinner());
        }else if(lastFiveGames.size() == 6) {
            lastFiveGames.remove(0);
            lastFiveGames.add(Application.spiel.determineWinner());
        }
        System.out.println(lastFiveGames);
        return "\"" +Application.spiel.determineWinner()+ "\"";
    }

    @PostMapping("lastGames")
    public ArrayList<String> lastFiveGames (){
        return lastFiveGames;
    }

}
