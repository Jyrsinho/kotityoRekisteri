package Siivoustiimi.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import Siivoustiimi.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2024.03.17 17:51:02 // Generated by ComTest
 *
 */
@SuppressWarnings({ "PMD" })
public class SuoritusTest {



  // Generated by ComTest BEGIN
  /** testInt38 */
  @Test
  public void testInt38() {    // Suoritus: 38
    Suoritus suoritus1 = new Suoritus(); 
    assertEquals("From: Suoritus line: 40", 0, suoritus1.getSuoritusID()); 
    suoritus1.rekisteroiSuoritus(); 
    Suoritus suoritus2 = new Suoritus(); 
    suoritus2.rekisteroiSuoritus(); 
    int n1 = suoritus1.getSuoritusID(); 
    int n2 = suoritus2.getSuoritusID(); 
    assertEquals("From: Suoritus line: 46", n2-1, n1); 
  } // Generated by ComTest END
}