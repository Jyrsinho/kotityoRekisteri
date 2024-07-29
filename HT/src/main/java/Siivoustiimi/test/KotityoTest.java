package Siivoustiimi.test;


import Siivoustiimi.*;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


public class KotityoTest {



  @Test
  public void testinPitaisiLuodaTunnusNumerotUusilleKotitoille() {
    Kotityo imurointi = new Kotityo();
    assertEquals(0, imurointi.getKotityoID());
    imurointi.rekisteroi();
    Kotityo tiskaaminen = new Kotityo();
    tiskaaminen.rekisteroi();
    int n1 = imurointi.getKotityoID();
    int n2 = tiskaaminen.getKotityoID();
    assertEquals(n2 - 1, n1);
  }

  @Test
  public void testinPitaisParsiaUusiKotityoPystyviivoinErotellustaMerkkijonosta() {
    Kotityo kotityo = new Kotityo();
    kotityo.parse("1               |Imurointi                | 3                  | 20        | 2024-01-07           |    1|");
    assertEquals(1, kotityo.getKotityoID());
    assertEquals(true, kotityo.toString().startsWith("1|Imurointi|3|"));
    kotityo.rekisteroi();
    int n = kotityo.getKotityoID();
    kotityo.parse("" + (n + 20));  // Otetaan merkkijonosta vain tunnusnumero
    kotityo.rekisteroi();  // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
    assertEquals(n + 20 + 1, kotityo.getKotityoID());

  }

  @Test
  public void testinPitaisiParsiaUusiKotityoResultSetObjektista() throws SQLException {
    Kotityo kotityo = new Kotityo(1);
    ResultSet rs = Mockito.mock(ResultSet.class);

    when(rs.next()).thenReturn(true).thenReturn(false);

    when(rs.getInt("kotityoId")).thenReturn(1);
    when(rs.getString("kotityoNimi")).thenReturn("Imurointi");
    when(rs.getInt("vanhenemisaika")).thenReturn(7);
    when(rs.getInt("kesto")).thenReturn(15);
    when(rs.getString("viimeisinSuoritus")).thenReturn("2024-08-22");
    when(rs.getInt("vastuuhenkilonId")).thenReturn(1);

    while (rs.next()) {
      kotityo.parse(rs);
    }

    assertEquals(1, kotityo.getKotityoID());
    assertEquals("Imurointi", kotityo.getKotityoNimi());
    assertEquals(7, kotityo.getVanhenemisaika());
    assertEquals(15, kotityo.getKesto());
    LocalDate expected = LocalDate.of(2024, 8, 22);
    assertEquals(expected, kotityo.getViimeisinSuoritus());
    assertEquals(1, kotityo.getVastuuhenkilonID());


  }

  @Test
  public void testinPitaisiArvioidaKotityonSuoritusVanhentuneeksi() {
    Kotityo kotityo = new Kotityo();
    kotityo.taytaKotityo(1);
    kotityo.setVanhenemisaika(5);

    kotityo.setViimeisinSuoritus(LocalDate.now().minusDays(5));

    assertTrue(kotityo.suoritusOnVanhentunut());

  }

  @Test
  public void testinPitaisiArvioidaKotityonSuoritusEiVanhentuneeksi() {
    Kotityo kotityo = new Kotityo();
    kotityo.taytaKotityo(1);
    kotityo.setVanhenemisaika(5);

    kotityo.setViimeisinSuoritus(LocalDate.now().minusDays(4));

    assertFalse(kotityo.suoritusOnVanhentunut());
  }


}
