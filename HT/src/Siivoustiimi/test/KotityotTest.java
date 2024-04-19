package Siivoustiimi.test;
// Generated by ComTest BEGIN
import java.io.*;
import Siivoustiimi.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2024.04.19 10:27:03 // Generated by ComTest
 *
 */
@SuppressWarnings({ "PMD" })
public class KotityotTest {



  // Generated by ComTest BEGIN
  /** testLisaa42 */
  @Test
  public void testLisaa42() {    // Kotityot: 42
    Kotityot kotityot = new Kotityot(); 
    Kotityo imurointi1 = new Kotityo(), imurointi2 = new Kotityo(); 
    assertEquals("From: Kotityot line: 45", 0, kotityot.getLkm()); 
    kotityot.lisaa(imurointi1); assertEquals("From: Kotityot line: 46", 1, kotityot.getLkm()); 
    kotityot.lisaa(imurointi2); assertEquals("From: Kotityot line: 47", 2, kotityot.getLkm()); 
    kotityot.lisaa(imurointi1); assertEquals("From: Kotityot line: 48", 3, kotityot.getLkm()); 
    kotityot.lisaa(imurointi1); assertEquals("From: Kotityot line: 49", 4, kotityot.getLkm()); 
    kotityot.lisaa(imurointi1); assertEquals("From: Kotityot line: 50", 5, kotityot.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testPoistaJasenenKotityot80 */
  @Test
  public void testPoistaJasenenKotityot80() {    // Kotityot: 80
    Kotityot kotityot = new Kotityot(); 
    Kotityo imurointi21 = new Kotityo(); imurointi21.taytaKotityo(2); 
    Kotityo imurointi11 = new Kotityo(); imurointi11.taytaKotityo(1); 
    Kotityo imurointi22 = new Kotityo(); imurointi22.taytaKotityo(2); 
    Kotityo imurointi12 = new Kotityo(); imurointi12.taytaKotityo(1); 
    Kotityo imurointi23 = new Kotityo(); imurointi23.taytaKotityo(2); 
    kotityot.lisaa(imurointi21); 
    kotityot.lisaa(imurointi11); 
    kotityot.lisaa(imurointi22); 
    kotityot.lisaa(imurointi12); 
    kotityot.lisaa(imurointi23); 
    assertEquals("From: Kotityot line: 92", 3, kotityot.poistaJasenenKotityot(2)); assertEquals("From: Kotityot line: 92", 2, kotityot.getLkm()); 
    assertEquals("From: Kotityot line: 93", 0, kotityot.poistaJasenenKotityot(3)); assertEquals("From: Kotityot line: 93", 2, kotityot.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta137 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta137() throws SailoException {    // Kotityot: 137
    Kotityot kotityot = new Kotityot(); 
    Kotityo imurointi21 = new Kotityo(); imurointi21.taytaKotityo(2); 
    Kotityo imurointi11 = new Kotityo(); imurointi11.taytaKotityo(1); 
    Kotityo imurointi22 = new Kotityo(); imurointi22.taytaKotityo(2); 
    Kotityo imurointi12 = new Kotityo(); imurointi12.taytaKotityo(1); 
    Kotityo imurointi23 = new Kotityo(); imurointi23.taytaKotityo(2); 
    String tiedNimi = "testiTiedosto"; 
    File ftied = new File(tiedNimi+".dat"); 
    ftied.delete(); 
    try {
    kotityot.lueTiedostosta(tiedNimi); 
    fail("Kotityot: 150 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    kotityot.lisaa(imurointi21); 
    kotityot.lisaa(imurointi11); 
    kotityot.lisaa(imurointi22); 
    kotityot.lisaa(imurointi12); 
    kotityot.lisaa(imurointi23); 
    try {
    kotityot.tallenna(); 
    } catch (IOException e) {
    throw new RuntimeException(e); 
    }
    kotityot = new Kotityot(); 
    kotityot.lueTiedostosta(tiedNimi); 
    Iterator<Kotityo> i = kotityot.iterator(); 
    assertEquals("From: Kotityot line: 164", imurointi21.toString(), i.next().toString()); 
    assertEquals("From: Kotityot line: 165", imurointi11.toString(), i.next().toString()); 
    assertEquals("From: Kotityot line: 166", imurointi22.toString(), i.next().toString()); 
    assertEquals("From: Kotityot line: 167", imurointi12.toString(), i.next().toString()); 
    assertEquals("From: Kotityot line: 168", imurointi23.toString(), i.next().toString()); 
    assertEquals("From: Kotityot line: 169", false, i.hasNext()); 
    kotityot.lisaa(imurointi23); 
    try {
    kotityot.tallenna(); 
    } catch (IOException e) {
    throw new RuntimeException(e); 
    }
    assertEquals("From: Kotityot line: 176", true, ftied.delete()); 
    File fbak = new File(tiedNimi+".bak"); 
    assertEquals("From: Kotityot line: 178", true, fbak.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testIterator311 */
  @Test
  public void testIterator311() {    // Kotityot: 311
    Kotityot kotityot = new Kotityot(); 
    Kotityo imurointi1 = new Kotityo(2); kotityot.lisaa(imurointi1); 
    Kotityo imurointi2 = new Kotityo(1); kotityot.lisaa(imurointi2); 
    Kotityo imurointi3 = new Kotityo(2); kotityot.lisaa(imurointi3); 
    Kotityo imurointi4 = new Kotityo(1); kotityot.lisaa(imurointi4); 
    Kotityo lakaisu = new Kotityo(2); kotityot.lisaa(lakaisu); 
    Iterator<Kotityo> i2=kotityot.iterator(); 
    assertEquals("From: Kotityot line: 323", imurointi1, i2.next()); 
    assertEquals("From: Kotityot line: 324", imurointi2, i2.next()); 
    assertEquals("From: Kotityot line: 325", imurointi3, i2.next()); 
    assertEquals("From: Kotityot line: 326", imurointi4, i2.next()); 
    assertEquals("From: Kotityot line: 327", lakaisu, i2.next()); 
    try {
    assertEquals("From: Kotityot line: 328", lakaisu, i2.next()); 
    fail("Kotityot: 328 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAnnaKotityot342 */
  @Test
  public void testAnnaKotityot342() {    // Kotityot: 342
    Kotityot kotityot = new Kotityot(); 
    Kotityo imurointi1 = new Kotityo(2); kotityot.lisaa(imurointi1); 
    Kotityo imurointi2 = new Kotityo(1); kotityot.lisaa(imurointi2); 
    Kotityo imurointi3 = new Kotityo(2); kotityot.lisaa(imurointi3); 
    Kotityo imurointi4 = new Kotityo(2); kotityot.lisaa(imurointi4); 
    Kotityo imurointi5 = new Kotityo(2); kotityot.lisaa(imurointi5); 
    Kotityo imurointi6 = new Kotityo(3); kotityot.lisaa(imurointi6); 
    List<Kotityo> loytyneet; 
    loytyneet = kotityot.annaKotityot(5); 
    assertEquals("From: Kotityot line: 354", 0, loytyneet.size()); 
    loytyneet = kotityot.annaKotityot(1); 
    assertEquals("From: Kotityot line: 356", 1, loytyneet.size()); 
    loytyneet = kotityot.annaKotityot(2); 
    assertEquals("From: Kotityot line: 358", 4, loytyneet.size()); 
    assertEquals("From: Kotityot line: 359", true, loytyneet.get(0) == imurointi1); 
    assertEquals("From: Kotityot line: 360", true, loytyneet.get(1) == imurointi3); 
    loytyneet = kotityot.annaKotityot(2); 
    assertEquals("From: Kotityot line: 362", 4, loytyneet.size()); 
  } // Generated by ComTest END
}