package Siivoustiimi.test;
import Siivoustiimi.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SuoritusTest {



  @Test
  public void testinPitaisPystyaAntamaanSuorituksilleUudetTunnusluvut() {
    Suoritus suoritus1 = new Suoritus(); 
    assertEquals(0, suoritus1.getSuoritusID());
    suoritus1.rekisteroiSuoritus(); 
    Suoritus suoritus2 = new Suoritus(); 
    suoritus2.rekisteroiSuoritus(); 
    int n1 = suoritus1.getSuoritusID(); 
    int n2 = suoritus2.getSuoritusID(); 
    assertEquals(n2-1, n1);
  }


  @Test
  public void testinPitaisiPystyaParsimaanOlioPystyviivoinErotellustaMerkkijonosta() {
    Suoritus suoritus = new Suoritus(); 
    suoritus.parse("1     |30     | 2024-01-22      | 1       1|");
    assertEquals(1, suoritus.getSuoritusID());
    assertEquals(true, suoritus.toString().startsWith("1|30|"));

    assertEquals(30, suoritus.getsuoritusAika());
    assertEquals(1, suoritus.getSuorittajaID());
    assertEquals(1, suoritus.getKotityoID());

  }
}

//"yyyy-MM-dd