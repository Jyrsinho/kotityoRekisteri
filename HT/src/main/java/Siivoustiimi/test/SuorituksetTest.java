package Siivoustiimi.test;

import Siivoustiimi.SailoException;
import Siivoustiimi.Siivoustiimi;
import Siivoustiimi.Suoritukset;
import Siivoustiimi.Suoritus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SuorituksetTest {

  Siivoustiimi testiTiimi;
  private Suoritukset suoritukset;
  private String tiedNimi;
  private File ftied;
  Suoritus suoritus1;
  Suoritus suoritus2;

  @BeforeEach
  public void setUp() throws Exception {
  testiTiimi = new Siivoustiimi();
  tiedNimi = "testi";
  ftied = new File(tiedNimi+".db");
  ftied.delete();
  suoritukset = new Suoritukset(tiedNimi);
  testiTiimi.lueTiedostosta(tiedNimi);
  suoritus1 = new Suoritus();
  suoritus2 = new Suoritus();
  }


  @AfterEach
  public void siivoa() {
    ftied.delete();
  }


  @Test
  public void testinPitaisiRekisteroidaUudetSuoritukset() throws SailoException {

    suoritus1.taytaSuoritus(1,1);
    suoritukset.lisaa(suoritus1);
    assertEquals(1,suoritus1.getSuoritusID());

    suoritus2.taytaSuoritus(1,1);
    suoritukset.lisaa(suoritus2);
    assertEquals(2,suoritus2.getSuoritusID());


  }


  @Test
  public void testPitaisiLisataSuorituksia() throws SailoException {
    Collection<Suoritus> loytyneet = suoritukset.annaSuoritukset(1);
    assertEquals(0, loytyneet.size());

    suoritus1.taytaSuoritus(1,1);
    suoritukset.lisaa(suoritus1);
    suoritus2.taytaSuoritus(1,1);
    suoritukset.lisaa(suoritus2);

    loytyneet = suoritukset.annaSuoritukset(1);
    assertEquals(2, loytyneet.size());
  }


  @Test
  public void testPitaisiLisataSuorituksiaEriKotitoille() throws SailoException {
    suoritus1.taytaSuoritus(1,1);
    suoritukset.lisaa(suoritus1);

    suoritus2.taytaSuoritus(2,2);
    suoritukset.lisaa(suoritus2);

    Collection<Suoritus> loytyneet = suoritukset.annaSuoritukset(1);
    assertEquals(1, loytyneet.size());

  }


  @Test
  public void testPitaisiOsataPalauttaaKaikkiSuoritukset() throws SailoException, SQLException {
    suoritus1.taytaSuoritus(1,1);
    suoritukset.lisaa(suoritus1);

    suoritus2.taytaSuoritus(2,2);
    suoritukset.lisaa(suoritus2);

    Suoritus suoritus3 = new Suoritus();
    suoritus3.taytaSuoritus(1,1);
    suoritukset.lisaa(suoritus3);

    Collection<Suoritus> loytyneet = suoritukset.annaSuoritukset();
    assertEquals(3, loytyneet.size());
  }


  @Test
  public void testAnnaSuorituksetPitaisiPalauttaaKaikkiTietynKotityonSuoritukset() throws SailoException {

    suoritus1.taytaSuoritus(1,1);
    suoritukset.lisaa(suoritus1);
    suoritus2.taytaSuoritus(2,2);
    suoritukset.lisaa(suoritus2);

    Collection<Suoritus>loytyneet = suoritukset.annaSuoritukset(1);
    assertEquals(1, loytyneet.size());

  }

  @Test
  public void testPitaisiPoistaaSuorituksiaTietokannasta() throws SailoException, SQLException {
    suoritus1.taytaSuoritus(1,1);
    suoritukset.lisaa(suoritus1);
    Collection<Suoritus>loytyneet = suoritukset.annaSuoritukset(1);
    assertEquals(1, loytyneet.size());

    suoritukset.poistaSuoritus(suoritus1);
    loytyneet = suoritukset.annaSuoritukset(1);
    assertEquals(0, loytyneet.size());
  }

  @Test
  public void testPitaisiPalauttaaSuorituksetKronologisessaJarjestyksessa() throws SailoException {
    suoritus1.taytaSuoritus(1,1);
    LocalDate suoritus1Aika= LocalDate.of(2020,1,1);
    suoritus1.setTekoaika(suoritus1Aika);
    suoritukset.lisaa(suoritus1);


    suoritus2.taytaSuoritus(2,1);
    LocalDate suoritus2Aika= LocalDate.of(2024,2,2);
    suoritus2.setTekoaika(suoritus2Aika);
    suoritukset.lisaa(suoritus2);

    ArrayList<Suoritus> loytyneet = suoritukset.annaSuoritukset(1);
    assertEquals(suoritus2Aika, loytyneet.getFirst().getSuoritusPvm());
  }


}