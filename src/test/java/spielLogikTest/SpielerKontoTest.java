package spielLogikTest;

import org.junit.Test;
import gameLogic.SpielerKonto;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class SpielerKontoTest {

    @Test
    public void getKontoStand() {
        SpielerKonto k = new SpielerKonto(1000);
        assertEquals(1000,k.getKontoStand());
    }

    @Test
    public void aktualisiereStand() {
        SpielerKonto k = new SpielerKonto(1000);
        k.aktualisiereStand(20);
        assertEquals(1020,k.getKontoStand());
    }
}
