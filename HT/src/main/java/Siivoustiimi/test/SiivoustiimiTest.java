package Siivoustiimi.test;


import Siivoustiimi.Jasen;
import Siivoustiimi.Kotityo;
import Siivoustiimi.Siivoustiimi;
import Siivoustiimi.Suoritus;
import Siivoustiimi.SailoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class SiivoustiimiTest {

    Siivoustiimi testitiimi;
    String tiedNimi;
    File ftied;
    Jasen jasen1;
    Kotityo imurointi1;
    Kotityo imurointi2;

    @BeforeEach
    void setUp() throws SailoException, SQLException {
        testitiimi = new Siivoustiimi();
        tiedNimi = "testi";
        ftied = new File(tiedNimi + ".db");
        ftied.delete();
        testitiimi.lueTiedostosta(tiedNimi);

        jasen1= new Jasen();
        jasen1.taytaJasen();

        imurointi1 = new Kotityo();
        imurointi1.taytaKotityo(1);
        imurointi2 = new Kotityo();
        imurointi2.taytaKotityo(1);
    }

    @AfterEach
    void tearDown() throws SQLException {

        ftied.delete();
    }

    @Test
    public void testinPitaisiPoistaaKaikkiPoistettavanJasenenKotityot() throws SailoException {

        testitiimi.lisaa(jasen1);
        testitiimi.lisaa(imurointi1);
        testitiimi.lisaa(imurointi2);

        Collection<Jasen> loytyneetJasenet = testitiimi.etsiJasenet("",1);
        assertEquals(1,loytyneetJasenet.size());
        Collection<Kotityo> loytyneetKotityot = testitiimi.annaKaikkiKotityot();
        assertEquals(2,loytyneetKotityot.size());

        testitiimi.poista(jasen1);

        loytyneetJasenet = testitiimi.etsiJasenet("",1);
        assertEquals(0,loytyneetJasenet.size());
        loytyneetKotityot = testitiimi.annaKaikkiKotityot();
        assertEquals(0,loytyneetKotityot.size());

    }

    @Test
    public void testinPitaisiPoistaaYksiKotityoTietokannasta() throws SailoException {
        testitiimi.lisaa(imurointi1);
        testitiimi.lisaa(imurointi2);

        Collection<Kotityo> loytyneetKotityot = testitiimi.annaKaikkiKotityot();
        assertEquals(2,loytyneetKotityot.size());

        testitiimi.poistaKotityo(imurointi1);
        loytyneetKotityot = testitiimi.annaKaikkiKotityot();
        assertEquals(1,loytyneetKotityot.size());
    }

    @Test
    public void testinPitaisiPaivittaaKotityonViimeisinSuoritus() throws SailoException, SQLException {
        testitiimi.lisaa(imurointi1);
        imurointi1.setVanhenemisaika(7);
        imurointi1.setViimeisinSuoritus(LocalDate.now().minusDays(8));
        assertTrue(imurointi1.suoritusOnVanhentunut());

        Suoritus suoritus = new Suoritus();
        suoritus.taytaSuoritus(1, imurointi1.getKotityoID());
        suoritus.setTekoaika(LocalDate.now());

        testitiimi.paivitaKotityonViimeisinSuoritus(imurointi1.getKotityoID());
        assertFalse(imurointi1.suoritusOnVanhentunut());

    }

}