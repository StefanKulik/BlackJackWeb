package spielLogikTest;

import org.junit.Test;
import BlackJack.gameLogic.Cards;

import static org.junit.Assert.assertEquals;


public class KarteTest {

    @Test
   public void testClone() {
        Cards karte = new Cards("König",10,20);
        Cards clone = karte.clone();

        assertEquals(karte.getPicture(), clone.getPicture());
        assertEquals(karte.getValue(), clone.getValue());
        assertEquals(karte.getNumber(), clone.getNumber());

        clone.setValue(5);
        assertEquals(5,clone.getValue());
        assertEquals(10,karte.getValue());
    }

    @Test
    public void testSetWert() {
        Cards karte = new Cards("König",10,20);
        karte.setValue(5);
        assertEquals(5,karte.getValue());
    }

    @Test
    public void testReduceAnzahl() {
        Cards karte = new Cards("König",10,20);
        karte.reduceNumber();
        assertEquals(19,karte.getNumber());
    }

    @Test
    public void testGetBild() {
        Cards karte = new Cards("König",10,20);
        assertEquals("König",karte.getPicture());
    }

    @Test
    public void testGetWert() {
        Cards karte = new Cards("König",10,20);
        assertEquals(10,karte.getValue());
    }

    @Test
    public void testGetAnzahl() {
        Cards karte = new Cards("König",10,20);
        assertEquals(20,karte.getNumber());
    }
}
