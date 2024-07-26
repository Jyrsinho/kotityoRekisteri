package Siivoustiimi.test;

import Siivoustiimi.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class KotityotTest {

    Siivoustiimi siivoustiimi1;
    private Kotityot kotityot;
    private String tiedNimi;
    private File ftied;

    Kotityo imurointi1;


    @BeforeEach
    public void setUp() throws Exception {
        siivoustiimi1 = new Siivoustiimi();
        tiedNimi = "testi";
        ftied = new File(tiedNimi + ".db");
        ftied.delete();
        kotityot = new Kotityot(tiedNimi);
        siivoustiimi1.lueTiedostosta(tiedNimi);

    }

    @AfterEach
    public void tearDown() {
        ftied.delete();
    }

    @Test
    public void testPitaisiLisataKotitoitaEriHenkiloille() throws SailoException {
        Collection<Kotityo> loytyneet = kotityot.annaKotityot(1);
        assertEquals(0, loytyneet.size());

        Kotityo imurointi1 = new Kotityo();
        imurointi1.taytaKotityo(1);
        kotityot.lisaa(imurointi1);
        Kotityo imurointi2 = new Kotityo(1);
        imurointi2.taytaKotityo(1);
        kotityot.lisaa(imurointi2);

        loytyneet = kotityot.annaKotityot(1);
        assertEquals(2, loytyneet.size());
        loytyneet = kotityot.annaKotityot(2);
        assertEquals(0, loytyneet.size());

    }

    @Test
    public void testPitaisiOsataPalauttaaKaikkiTiiminKotityot() throws SailoException {
        Collection<Kotityo> loytyneet = kotityot.annaKotityot(1);
        assertEquals(0, loytyneet.size());

        Kotityo imurointi1 = new Kotityo();
        imurointi1.taytaKotityo(1);
        kotityot.lisaa(imurointi1);
        Kotityo imurointi2 = new Kotityo();
        imurointi2.taytaKotityo(1);
        kotityot.lisaa(imurointi2);

        Kotityo imurointi3 = new Kotityo();
        imurointi3.taytaKotityo(2);
        kotityot.lisaa(imurointi3);

        loytyneet = kotityot.annaKotityot(2);
        assertEquals(1, loytyneet.size());

        loytyneet = kotityot.annaKaikkiKotityot();

        assertEquals(3, loytyneet.size());
    }

    @Test
    public void testPitaisiPoistaaKotitoitaTietokannasta() throws SailoException {
        Kotityo imurointi1 = new Kotityo();
        imurointi1.taytaKotityo(1);
        kotityot.lisaa(imurointi1);
        Collection<Kotityo> loytyneet = kotityot.annaKotityot(1);
        assertEquals(1, loytyneet.size());


        kotityot.poistaKotityo(imurointi1);
        loytyneet = kotityot.annaKotityot(1);
        assertEquals(0, loytyneet.size());

    }
}