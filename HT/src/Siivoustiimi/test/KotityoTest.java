package Siivoustiimi.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import Siivoustiimi.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2024.03.28 19:37:41 // Generated by ComTest
 *
 */
@SuppressWarnings({ "PMD" })
public class KotityoTest {



  // Generated by ComTest BEGIN
  /** testInt95 */
  @Test
  public void testInt95() {    // Kotityo: 95
    Kotityo imurointi = new Kotityo(); 
    assertEquals("From: Kotityo line: 97", 0, imurointi.getKotityoID()); 
    imurointi.rekisteroi(); 
    Kotityo tiskaaminen = new Kotityo(); 
    tiskaaminen.rekisteroi(); 
    int n1 = imurointi.getKotityoID(); 
    int n2 = tiskaaminen.getKotityoID(); 
    assertEquals("From: Kotityo line: 103", n2-1, n1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse158 */
  @Test
  public void testParse158() {    // Kotityo: 158
    Kotityo kotityo = new Kotityo(); 
    kotityo.parse("1               |Imurointi                | 3                  | 20        | 7.1.2024           |    1|"); 
    assertEquals("From: Kotityo line: 161", 1, kotityo.getKotityoID()); 
    assertEquals("From: Kotityo line: 162", true, kotityo.toString().startsWith("1|Imurointi|3|")); 
    kotityo.rekisteroi(); 
    int n = kotityo.getKotityoID(); 
    kotityo.parse(""+(n+20));  // Otetaan merkkijonosta vain tunnusnumero
    kotityo.rekisteroi();  // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
    assertEquals("From: Kotityo line: 168", n+20+1, kotityo.getKotityoID()); 
  } // Generated by ComTest END
}