package BlackJack.gameLogic;

import java.util.ArrayList;


public class GameCards {
    ArrayList<Cards> deck = new ArrayList<>();

    GameCards() {
        deck.add(new Cards("A", 11, 24));
        deck.add(new Cards("K", 10, 24));
        deck.add(new Cards("D", 10, 24));
        deck.add(new Cards("B", 10, 24));
        deck.add(new Cards("10", 10, 24));
        deck.add(new Cards("9", 9, 24));
        deck.add(new Cards("8", 8, 24));
        deck.add(new Cards("7", 7, 24));
        deck.add(new Cards("6", 6, 24));
        deck.add(new Cards("5", 5, 24));
        deck.add(new Cards("4", 4, 24));
        deck.add(new Cards("3", 3, 24));
        deck.add(new Cards("2", 2, 24));
    }
}
