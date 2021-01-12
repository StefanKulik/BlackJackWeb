package gameLogic;

import java.util.ArrayList;

public class Game {
    GameCards cardDeck = new GameCards();
    public ArrayList<Cards> drawnCardsPlayer = new ArrayList<>();
    public ArrayList<Cards> drawnCardsDealer = new ArrayList<>();


    public Game() {}

//  --- determine card ---  \\

    /** output current drawn card */
    public Cards getCurrentCardPlayer() {
        return drawnCardsPlayer.get(drawnCardsPlayer.size() - 1);
    }

    /** draw random card */
    public Cards drawCard() {
        if (cardDeck.deck.size() == 0) {
            System.out.println("Kartendeck ist leer!");
            return null;
        }
        int max = cardDeck.deck.size();
        int min = 0;
        int randomInteger = (int) (Math.random() * max - min) + min;
        try {
            if (cardDeck.deck.get(randomInteger).getNumber() < 1) {
                cardDeck.deck.remove(randomInteger);
            }
            cardDeck.deck.get(randomInteger).reduceNumber();
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        return cardDeck.deck.get(randomInteger).clone();
    }

    /** shuffle card deck to have enough cards again */
    public void shuffleCardDeck() {
        cardDeck = new GameCards();
    }

    /** determines how many cards are left in the deck */
    public int numberCardsRemaining() {
        int numberCards = 0;
        for (int i = 0; i < cardDeck.deck.size(); i++) {
            numberCards += cardDeck.deck.get(i).getNumber();
        }
        return numberCards;
    }
//  ------------------------  \\


//  --- player methods ---  \\

    /** deals all cards from the player deck */
    public void getDrawnCardsPlayer() {
        int count = 0;
        while (count < drawnCardsPlayer.size()) {

            System.out.print("\t" + drawnCardsPlayer.get(count).getPicture());
            count += 1;
        }
        System.out.println();
        System.out.println();
    }

//    public String getEinzelneKarteSpieler(int index) { //bestimmte Karte wird zurückgegeben
//        return gezogeneKartenSpieler.get(index).getBild();
//    }

    /** Output total value player */
    public int getTotalValuePlayer() {
        return setTotalValuePlayer();
    }

//    public int getGezogeneKartenAnzahlSpieler() { //gibt Anzahl an Karten im Spielerdeck aus
//        return gezogeneKartenSpieler.size();
//    }

    /** adding drawn card to player deck */
    public void setDrawnCardsPlayer(Cards card) {
        drawnCardsPlayer.add(card);
    }

    /** sets value of ace to 1 of player */
    public void setValueAcePlayer() {
        if (drawnCardsPlayer.size() > 2) {
            for (Cards card : drawnCardsPlayer) {
                if (card.getPicture().equals("A") && card.getValue() == 11) {
                    card.setValue(1);
                    if (getTotalValuePlayer() < 21) {
                        return;
                    }
                }
            }
        }
    }

    /** manually sets value of ace for player */
    public void setValueAcePlayer(int index) {
        drawnCardsPlayer.get(index).setValue(1);
    }

    /** calculating total value of player */
    public int setTotalValuePlayer() {
        int totalValuePlayer = 0;
        for (Cards card : drawnCardsPlayer) {
            totalValuePlayer += card.getValue();
        }
        return totalValuePlayer;
    }

    /** returns number of aces in deck of player */
    public int getNumberDrawnAcesPlayer() {
        int numberDrawnAces = 0;
        for (Cards card : drawnCardsPlayer) {
            if (card.getPicture().equals("A")) {
                numberDrawnAces++;
            }
        }
        return numberDrawnAces;
    }

    /** checking of blackjack */
    boolean isBlackjackPlayer() {
        if(drawnCardsPlayer.size() == 2) {
            return getTotalValuePlayer() == 21;
        }
        return false;
    }

    /** determines whether there is an ace */
    public boolean checkAcePlayer() {
        for (Cards card : drawnCardsPlayer) {
            if (card.getPicture().equals("A") && card.getValue() == 11) {
                return true;
            }
        }
        return false;
    }
//  ------------------------  \\


//  --- Dealer methods ---  \\

    /** deals all cards from the dealer deck */
    public void getDrawnCardsDealer() {
        int coutn = 0;
        while (coutn < drawnCardsDealer.size()) {
            System.out.print("\t" + drawnCardsDealer.get(coutn).getPicture());
            coutn += 1;
        }
        System.out.println("");
        System.out.println("");
    }

    /** deals the dealer´s first drawn card */
    public void getFirstCardDealer() {
        System.out.println("");
        System.out.println("Erste Karte Dealers: " + drawnCardsDealer.get(0).getPicture());
        System.out.println("");
    }

//    public String getEinzelneKarteCroupiers(int index) { //bestimme Karte wird zurückgegeben
//        return gezogeneKartenCroupiers.get(index).getBild();
//    }

    /** outputs the dealer´s total value */
    public int getTotalValueDealer() {
        return setTotalValueDealer();
    }

//    public int getGezogeneKartenAnzahlDealers() { //gibt Anzahl an Karten im Dealerdeck aus
//        return gezogeneKartenDealer.size();
//    }

    /** adding drawn card to dealer deck */
    public void setDrawnCardsDealer(Cards card) {
        drawnCardsDealer.add(card);
    }

    /** sets value of ace for dealer */
    public void setValueAceDealer() {
        if (drawnCardsDealer.size() > 2) {
            for (Cards card : drawnCardsDealer) {
                if (card.getPicture().equals("A") && card.getValue() == 11) {
                    card.setValue(1);
                    if (getTotalValuePlayer() < 21) {
                        return;
                    }
                }
            }
        }
    }

    /** calculating total value dealer */
    public int setTotalValueDealer() {
        int totalValueDealer = 0;
        for (Cards card : drawnCardsDealer) {
            totalValueDealer += card.getValue();
        }
        return totalValueDealer;
    }

    /** returns number of aces in dealer´s deck */
    public int numberDrawnAcesDealer() {
        int numberDrawnAces = 0;
        for (Cards card : drawnCardsDealer) {
            if (card.getPicture().equals("Ass")) {
                numberDrawnAces++;
            }
        }
        return numberDrawnAces;
    }

    /** checking if Blackjack */
    boolean isBlackjackDealer() {
        return drawnCardsDealer.get(0).getValue() + drawnCardsDealer.get(1).getValue() == 21;
    }

    /** determines if there is an ace */
    public boolean checkAceDealer() {
        for (Cards card : drawnCardsDealer) {
            if (card.getPicture().equals("A") && card.getValue() == 11) {
                return true;
            }
        }
        return false;
    }
//  --------------------------  \\


//  --- reset methods ---  \\

    /** clearing player and dealer deck -> new round */
    public void resetDrawnCards() {
        drawnCardsPlayer.clear();
        drawnCardsDealer.clear();
    }

    /** set total value dealer to 0 -> new round */
    public void resetTotalValueDealer() {
        setTotalValuePlayer();
    }

    /** set total value player to 0 -> new round */
    public void resetTotalValuePlayer() {
        setTotalValuePlayer();
    }
//  -----------------------------  \\


//  --- determine winner ---  \\

    /** determines the winner */
    public String determineWinner() {
        String result = "";

    //check if player has blackjack
        if (isBlackjackPlayer()) {
        //if dealer blackhack -> push
            if (isBlackjackDealer()) {
                result = "P";
            }
        //if dealer no blackjack -> blackjack
            else if (!isBlackjackDealer()) {
                result = "21";
            }
        }
    //check if total value player below 21
        else if (getTotalValuePlayer() < 21) {
        //if total value dealer lower 21, compare both values
            if (getTotalValueDealer() < 21) {
            //if total value player higher total value dealer -> win
                if (getTotalValuePlayer() > getTotalValueDealer()) {
                    result = "W";
                }
            //if total value player lower total value dealer -> lose
                else if (getTotalValuePlayer() < getTotalValueDealer()) {
                    result = "L";
                }
            //if total value player equal total value dealer -> push
                else {
                    result = "P";
                }
            }
        //if total value dealer higher 21 -> win
            else if (getTotalValueDealer() > 21) {
                result = "W";
            }
        //if total value dealer equals 21 -> lose
            else {
                result = "L";
            }
        }
    //check if total value player higher 21
        else if (getTotalValuePlayer() > 21) {
        //if total value dealer lower 21 -> bust
                result = "B";
        }
    //check if total value of 21 with three or more cards, compare with dealer
        else {
        //if total value dealer equals 21 -> push
            if (getTotalValueDealer() == 21) {
                result = "P";
            }
        //if total value equals not 21 -> win
            else {
                result = "W";
            }
        }
    //reset result
        return result;
    }
//  --------------------------  \\
}