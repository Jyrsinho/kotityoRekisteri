package Siivoustiimi.test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



import Siivoustiimi.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class JasenTest {

  private Jasen timo1;
  private Kanta kanta;

  @BeforeEach
  public void alustus() {
    timo1 = new Jasen();
    timo1.annaLuontilauseke();

  }


  @Test
  public void testinPitaisiLuodaUusilleJasenilleUniikitTunnusLuvut() {
    //timo1 = new Jasen();
    assertEquals(0, timo1.getId());
    timo1.rekisteroi();
    timo1.taytaJasen();
    Jasen timo2 = new Jasen(); 
    timo2.rekisteroi(); 
    int n1 = timo1.getId(); 
    int n2 = timo2.getId(); 
    assertEquals( n2-1, n1);
  }


  @Test
  public void testinPitaisiParsiaJasenOlioPystyviivoinEritellystaMerkkijonosta() {
    Jasen jasen = new Jasen(); 
    jasen.parse("5    |     Timo |  Kekkila     |Talvitie 4    |   11600   |    Vantaa   |   05013899304   |   41   |"); 
    assertEquals( 5, jasen.getId());
    assertEquals(true, jasen.toString().startsWith("5|Timo|Kekkila|Talvitie 4|"));
    jasen.rekisteroi(); 
    int n = jasen.getId(); 
    jasen.parse(""+(n+20));  // Otetaan merkkijonosta vain jasenen ID
    jasen.rekisteroi();  // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
    assertEquals(n+20+1, jasen.getId());
  }

  @Test
  public void testinPitaisParsiaJasenResultsetista() throws SQLException {






  }




}