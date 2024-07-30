package Siivoustiimi.test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




import Siivoustiimi.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import java.sql.ResultSet;
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
  public void testinPitaisiParsiaJasenOlioPystyviivoinEritellystaMerkkijonosta() {
    Jasen jasen = new Jasen(); 
    jasen.parse("5    |     Timo |  Kekkila     |Talvitie 4    |   11600   |    Vantaa   |   05013899304   |   41   |"); 
    assertEquals( 5, jasen.getId());
    assertEquals(true, jasen.toString().startsWith("5|Timo|Kekkila|Talvitie 4|"));

  }

  @Test
  public void testinPitaisParsiaJasenResultsetista() throws SQLException {

    //Määritellään mockattu Resultset ja sen käyttäytyminen
    ResultSet resultSet = Mockito.mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    when(resultSet.getInt("id")).thenReturn(1);
    when(resultSet.getString("sukunimi")).thenReturn("Hemminki");
    when(resultSet.getString("etunimi")).thenReturn("Seppo");
    when(resultSet.getString("katuosoite")).thenReturn("Vilhontie 4");
    when(resultSet.getString("postinumero")).thenReturn("00100");
    when(resultSet.getString("kaupunki")).thenReturn("Vantaa");
    when(resultSet.getString("puhelinNumero")).thenReturn("0400100");
    when(resultSet.getInt("ika")).thenReturn(33);

    // Käytetään mockattua Resultsetiä
    while (resultSet.next()) {
      timo1.parse(resultSet);
    }

    //VArmistetaan arvot
    assertEquals( 1, timo1.getId());
    assertEquals("Hemminki", timo1.getSukunimi());
    assertEquals("Seppo", timo1.getEtunimi());
    assertEquals("Vilhontie 4", timo1.getKatuosoite());
    assertEquals("00100", timo1.getPostinumero());
    assertEquals("Vantaa", timo1.getKaupunki());
    assertEquals("0400100", timo1.getPuhelin());
    assertEquals(33, timo1.getIka());

    // varmistetaan että next()iä kutsuttiin kaksi kertaa.
    Mockito.verify(resultSet, Mockito.times(2)).next();
  }
}