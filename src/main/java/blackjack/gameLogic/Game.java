package blackjack.gameLogic;

import java.util.ArrayList;

public class Game {
    GameCards cardDeck = new GameCards();
    public ArrayList<Cards> drawnCardsPlayer = new ArrayList<>();
    public ArrayList<Cards> drawnCardsDealer = new ArrayList<>();


    public Game() {}

//  --- determine card ---  \\

    /** draw random card */
    public Cards drawCard() {
        if (cardDeck.deck.size() == 0) {
            System.out.println("Kartendeck ist leer!");
            return null;
        }
        int max = cardDeck.deck.size(), min = 0;
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
    public void shuffleCardDeck() { cardDeck = new GameCards(); }

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

    /** adding drawn card to player deck */
    public void setDrawnCardsPlayer(Cards card) { addCard(drawnCardsPlayer, card); }

    /** sets value of ace to 1 of player */
    public void setValueAcePlayer() { setValueAce(drawnCardsPlayer); }

    /** calculating total value of player */
    public void setTotalValuePlayer() { setTotalValue(drawnCardsPlayer); }

    /** Output total value player */
    public int getTotalValuePlayer() { return setTotalValue(drawnCardsPlayer); }

    /** returns number of aces in deck of player */
    public int getNumberDrawnAcesPlayer() { return getNumberDrawnAces(drawnCardsPlayer); }

    /** checking of blackjack */
    boolean isBlackjackPlayer() { return isBlackjack(drawnCardsPlayer); }

    /** determines whether there is an ace */
    public boolean checkAcePlayer() { return checkAce(drawnCardsPlayer); }

    /** manually sets value of ace for player at index*/
    public void setValueAcePlayer(int index) { setValueAce(drawnCardsPlayer, index); }

//  ------------------------  \\
//  --- Dealer methods ---  \\
    
    /** adding drawn card to dealer deck */
    public void setDrawnCardsDealer(Cards card) { addCard(drawnCardsDealer, card); }

    /** sets value of ace for dealer */
    public void setValueAceDealer() { setValueAce(drawnCardsDealer); }

    /** calculating total value dealer */
    public void setTotalValueDealer() { setTotalValue(drawnCardsDealer); }

    /** outputs the dealer´s total value */
    public int getTotalValueDealer() { return setTotalValue(drawnCardsDealer); }

    /** returns number of aces in dealer´s deck */
    public int getNumberDrawnAcesDealer() { return getNumberDrawnAces(drawnCardsDealer); }

    /** checking if Blackjack */ //return drawnCardsDealer.get(0).getValue() + drawnCardsDealer.get(1).getValue() == 21;
    boolean isBlackjackDealer() { return isBlackjack(drawnCardsDealer); }

    /** determines if there is an ace */
    public boolean checkAceDealer() { return checkAce(drawnCardsDealer); }

//  --------------------------  \\
//  --- reset methods ---  \\

    /** clearing player and dealer deck -> new round */
    public void resetDrawnCards() {
        drawnCardsPlayer.clear();
        drawnCardsDealer.clear();
    }

    /** set total value of player and dealer to 0 -> new round */
    public void resetTotalValue() {
        setTotalValuePlayer();
        setTotalValueDealer();
    }

//  -----------------------------  \\
//  --- determine winner ---  \\

    /** determines the winner */
    public String determineWinner() {
    return
        isBlackjackPlayer() ?                                                                                           //check if player has blackjack
            (isBlackjackDealer() ? "P" : "21")
        : (getTotalValuePlayer() < 21 ?                                                                                 //check if total value player below 21
            (getTotalValueDealer() < 21 ?                                                                               //if total value dealer lower 21, compare both values
                (getTotalValuePlayer() > getTotalValueDealer() ? "W"                                                    //if total value player higher total value dealer -> win
                    : (getTotalValuePlayer() < getTotalValueDealer() ? "L" : "P")                                       //if total value player lower total value dealer -> lose : else total value player equal total value dealer -> push
                )
                : (getTotalValueDealer() > 21 ? "W" : "L")                                                              //if total value dealer higher 21 -> win : else total value dealer equals 21 -> lose
            )
            : (getTotalValuePlayer() > 21 ? "B"                                                                         //check if total value player higher 21
                : (getTotalValueDealer() == 21 ? "P" : "W")                                                             //check if total value of 21 with three or more cards, compare with dealer : if total value dealer equals 21 -> push : else total value dealer equals not 21 -> win
            )
        );
    }

//  --------------------------  \\
//  --- common methods ---  \\

    /** add drawn Card to Player or Dealer cards*/
    private void addCard(ArrayList<Cards> drawnCards, Cards cards){ drawnCards.add(cards); }
    
    /** set value of Ace for either Player or Dealer*/
    private void setValueAce(ArrayList<Cards> drawnCards) {
        if (drawnCards.size() > 2) {
            for (Cards card : drawnCards) {
                if (card.getPicture().equals("A") && card.getValue() == 11) {
                    card.setValue(1);
                    if (getTotalValuePlayer() < 21) {
                        return;
                    }
                }
            }
        }
    }

    /** set value of Ace for either Player or Dealer manually at index*/
    private void setValueAce(ArrayList<Cards> drawnCards, int index) { drawnCards.get(index).setValue(1); }

    /** set total value of either Player or Dealer drawn cards*/
    private int setTotalValue(ArrayList<Cards> drawnCards) {
        int totalValue = 0;
        for (Cards card : drawnCards) {
            totalValue += card.getValue();
        }
        return totalValue;
    }

    /** return total value of either player or dealer cards*/
    private int getTotalValue(ArrayList<Cards> drawnCards) { return setTotalValue(drawnCards); }

    /** return number of aces in either player or dealer cards*/
    private int getNumberDrawnAces(ArrayList<Cards> drawnCards) {
        int numberDrawnAces = 0;
        for (Cards card : drawnCards) {
            if (card.getPicture().equals("A")) {
                numberDrawnAces++;
            }
        }
        return numberDrawnAces;
    }

    /** check for Blackjack after first 2 cards*/
    private boolean isBlackjack(ArrayList<Cards> drawnCards) {
        if(drawnCards.size() == 2) {
            return getTotalValue(drawnCards) == 21;
        }
        return false;
    }

    /** check for Ace in either player or dealer cards*/
    private boolean checkAce(ArrayList<Cards> drawnCards) {
        for (Cards card : drawnCards) {
            if (card.getPicture().equals("A") && card.getValue() == 11) {
                return true;
            }
        }
        return false;
    }

//  -------------------------------------  \\
    
}