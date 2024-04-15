package Siivoustiimi.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import Siivoustiimi.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2024.04.15 20:59:08 // Generated by ComTest
 *
 */
@SuppressWarnings({ "PMD" })
public class SiivoustiimiTest {



  // Generated by ComTest BEGIN
  /** 
   * testLisaa81 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa81() throws SailoException {    // Siivoustiimi: 81
    Siivoustiimi siivoustiimi = new Siivoustiimi(); 
    Jasen timo1 = new Jasen(); 
    Jasen timo2 = new Jasen(); 
    timo1.rekisteroi(); timo2.rekisteroi(); 
    assertEquals("From: Siivoustiimi line: 87", 0, siivoustiimi.getJasenia()); 
    siivoustiimi.lisaa(timo1); assertEquals("From: Siivoustiimi line: 88", 1, siivoustiimi.getJasenia()); 
    siivoustiimi.lisaa(timo2); assertEquals("From: Siivoustiimi line: 89", 2, siivoustiimi.getJasenia()); 
    siivoustiimi.lisaa(timo1); assertEquals("From: Siivoustiimi line: 90", 3, siivoustiimi.getJasenia()); 
    assertEquals("From: Siivoustiimi line: 91", timo1, siivoustiimi.annaJasen(0)); 
    assertEquals("From: Siivoustiimi line: 92", timo2, siivoustiimi.annaJasen(1)); 
    assertEquals("From: Siivoustiimi line: 93", timo1, siivoustiimi.annaJasen(2)); 
    try {
    assertEquals("From: Siivoustiimi line: 94", timo1, siivoustiimi.annaJasen(3)); 
    fail("Siivoustiimi: 94 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    siivoustiimi.lisaa(timo1); assertEquals("From: Siivoustiimi line: 95", 4, siivoustiimi.getJasenia()); 
    siivoustiimi.lisaa(timo1); assertEquals("From: Siivoustiimi line: 96", 5, siivoustiimi.getJasenia()); 
  } // Generated by ComTest END
}