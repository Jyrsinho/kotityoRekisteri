package Siivoustiimi.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import Siivoustiimi.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2024.02.21 15:36:27 // Generated by ComTest
 *
 */
@SuppressWarnings({ "PMD" })
public class JasenTest {



  // Generated by ComTest BEGIN
  /** testInt49 */
  @Test
  public void testInt49() {    // Jasen: 49
    Jasen timo1 = new Jasen(); 
    assertEquals("From: Jasen line: 51", 0, timo1.getId()); 
    timo1.rekisteroi(); 
    Jasen timo2 = new Jasen(); 
    timo2.rekisteroi(); 
    int n1 = timo1.getId(); 
    int n2 = timo2.getId(); 
    assertEquals("From: Jasen line: 57", n2-1, n1); 
  } // Generated by ComTest END
}