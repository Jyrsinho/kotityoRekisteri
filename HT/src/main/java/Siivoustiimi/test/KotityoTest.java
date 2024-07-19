package Siivoustiimi.test;


import Siivoustiimi.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class KotityoTest {



  @Test
  public void testinPitaisiLuodaTunnusNumerotUusilleKotitoille() {    // Kotityo: 149
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
  public void testParse226() {    // Kotityo: 226
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
}
