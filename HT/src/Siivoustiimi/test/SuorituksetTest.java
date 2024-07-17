package Siivoustiimi.test;
import java.sql.SQLException;
import java.util.*;
import java.io.*;
import static org.junit.Assert.*;
import org.junit.*;
import Siivoustiimi.*;


public class SuorituksetTest {

  Siivoustiimi testiTiimi;
  private Suoritukset suoritukset;
  private String tiedNimi;
  private File ftied;

  @Before
  public void setUp() throws Exception {
  testiTiimi = new Siivoustiimi();
  tiedNimi = "testi";
  ftied = new File(tiedNimi+".db");
  ftied.delete();
  suoritukset = new Suoritukset(tiedNimi);
  testiTiimi.lueTiedostosta(tiedNimi);
  }


  @After
  public void siivoa() {
    ftied.delete();
  }


  @Test
  public void testAnnaSuoritukset30() throws SailoException, SQLException {    // Suoritukset: 30
    Suoritus suoritus1 = new Suoritus(2);
    suoritukset.lisaa(suoritus1);
    Suoritus suoritus2 = new Suoritus(1);
    suoritukset.lisaa(suoritus2);
    Suoritus suoritus3 = new Suoritus(2);
    suoritukset.lisaa(suoritus3);
    Suoritus suoritus4 = new Suoritus(2);
    suoritukset.lisaa(suoritus4);
    Suoritus suoritus5 = new Suoritus(2);
    suoritukset.lisaa(suoritus5);
    Suoritus suoritus6 = new Suoritus(3);
    suoritukset.lisaa(suoritus6);
    List<Suoritus> loytyneet;
    loytyneet = suoritukset.annaSuoritukset(5);
    assertEquals("From: Suoritukset line: 42", 0, loytyneet.size());
    loytyneet = suoritukset.annaSuoritukset(1);
    assertEquals("From: Suoritukset line: 44", 1, loytyneet.size());
    loytyneet = suoritukset.annaSuoritukset(2);
    assertEquals("From: Suoritukset line: 46", 4, loytyneet.size());
    assertEquals("From: Suoritukset line: 47", true, loytyneet.get(0) == suoritus1);
    assertEquals("From: Suoritukset line: 48", true, loytyneet.get(1) == suoritus3);
    loytyneet = suoritukset.annaSuoritukset(2);
    assertEquals("From: Suoritukset line: 50", 4, loytyneet.size());
  }


/*
package Siivoustiimi.test;
import static org.junit.Assert.*;
import org.junit.*;
import Siivoustiimi.*;

import java.io.File;
import java.util.Collection;


public class KotityotTest {

    Siivoustiimi siivoustiimi1;
    private Kotityot kotityot;
    private String tiedNimi;
    private File ftied;


    @Before
    public void setUp() throws Exception {
        siivoustiimi1 = new Siivoustiimi();
        tiedNimi = "testi";
        ftied = new File(tiedNimi + ".db");
        ftied.delete();
        kotityot = new Kotityot(tiedNimi);
        siivoustiimi1.lueTiedostosta(tiedNimi);
    }

    @After
    public void tearDown() throws Exception {
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

        loytyneet = kotityot.annaKotityot(2);
        assertEquals(1, loytyneet.size());

        loytyneet = kotityot.annaKaikkiKotityot();

        assertEquals(3, loytyneet.size());
    }

}
 */

}