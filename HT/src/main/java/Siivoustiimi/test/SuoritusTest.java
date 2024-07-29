package Siivoustiimi.test;
import Siivoustiimi.*;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class SuoritusTest {

  Suoritus suoritus1;

  @BeforeEach
  public void setUp() {
  suoritus1 = new Suoritus();
}



  @Test
  public void testinPitaisiPystyaParsimaanOlioPystyviivoinErotellustaMerkkijonosta() {

    suoritus1.parse("1     |30     | 2024-01-22      | 1   |    1|");


    assertEquals(1, suoritus1.getSuoritusID());
    assertEquals(true, suoritus1.toString().startsWith("1|30|"));
    assertEquals(30, suoritus1.getsuoritusAika());
    LocalDate expected = LocalDate.of(2024,1,22);
    assertEquals(expected,suoritus1.getViimeisinSuoritus());
    assertEquals(1, suoritus1.getKotityoID());
    assertEquals(1, suoritus1.getSuorittajaID());

  }

  @Test
  public void testinPitaisiPystyaParsimaanOlioResultsetista() throws SQLException {

    ResultSet rs = Mockito.mock(ResultSet.class);

    when(rs.next()).thenReturn(true).thenReturn(false);

    when(rs.getInt("suoritusID")).thenReturn(1);
    when(rs.getInt("suoritusAika")).thenReturn(20);
    when(rs.getString("suoritusPvm")).thenReturn("2024-01-22");
    when(rs.getInt("kotityoID")).thenReturn(2);
    when(rs.getInt("suorittajaID")).thenReturn(3);

    while (rs.next()) {
      suoritus1.parse(rs);
    }

    assertEquals(1, suoritus1.getSuoritusID());
    assertEquals(20, suoritus1.getsuoritusAika());

    LocalDate expected = LocalDate.of(2024,1,22);
    assertEquals(expected, suoritus1.getViimeisinSuoritus());
    assertEquals(2, suoritus1.getKotityoID());
    assertEquals(3, suoritus1.getSuorittajaID());


  }


  }


