package spielLogikTest;

import org.junit.Test;
import BlackJack.gameLogic.Cards;
import BlackJack.gameLogic.Game;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;


public class SpielTest{

    @Test
   public void testZieheKarte() {
        assertTrue(true);

        Game s = new Game();
        s.kartenDeck.deck.clear();
        Cards karte = new Cards("König", 10, 2);
        s.kartenDeck.deck.add(karte);
        System.out.println(s.kartenDeck.deck.size());

        assertEquals(2, karte.getNumber());
        s.drawCard();
        assertEquals(1, karte.getNumber());
        s.drawCard();
        assertEquals(0,karte.getNumber());
        assertThrows(IndexOutOfBoundsException.class, s::drawCard);

        assertEquals(0,s.kartenDeck.deck.size());
        System.out.println(s.kartenDeck.deck.size());
    }


    @Test
    public void testAnzahlKartenVerbleibend() {
        Game s = new Game();
        System.out.println(s.numberCardsRemaining());
        s.drawCard();
        System.out.println(s.numberCardsRemaining());
        assertEquals(311, s.numberCardsRemaining());
    }

    @Test
    public void testGetGezogeneKartenSpieler() {
        Game s = new Game();
        s.kartenDeck.deck.clear();
        Cards k = new Cards("Ass",11,24);
        s.kartenDeck.deck.add(k);
        s.setDrawnCardsPlayer(s.drawCard());
    }

}
