package Siivoustiimi.test;
// Generated by ComTest BEGIN
import java.util.*;
import java.io.*;
import java.util.Iterator;
import static org.junit.Assert.*;
import org.junit.*;
import Siivoustiimi.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2024.04.19 10:27:17 // Generated by ComTest
 *
 */
@SuppressWarnings({ "PMD" })
public class SuorituksetTest {



  // Generated by ComTest BEGIN
  /** testAnnaSuoritukset30 */
  @Test
  public void testAnnaSuoritukset30() {    // Suoritukset: 30
    Suoritukset suoritukset = new Suoritukset(); 
    Suoritus suoritus1 = new Suoritus(2); suoritukset.lisaa(suoritus1); 
    Suoritus suoritus2 = new Suoritus(1); suoritukset.lisaa(suoritus2); 
    Suoritus suoritus3 = new Suoritus(2); suoritukset.lisaa(suoritus3); 
    Suoritus suoritus4 = new Suoritus(2); suoritukset.lisaa(suoritus4); 
    Suoritus suoritus5 = new Suoritus(2); suoritukset.lisaa(suoritus5); 
    Suoritus suoritus6 = new Suoritus(3); suoritukset.lisaa(suoritus6); 
    List<Suoritus> loytyneet; 
    loytyneet = suoritukset.annaSuoritukset(5); 
    assertEquals("From: Suoritukset line: 42", 0, loytyneet.size()); 
    loytyneet = suoritukset.annaSuoritukset(1); 
    assertEquals("From: Suoritukset line: 44", 1, loytyneet.size()); 
    loytyneet = suoritukset.annaSuoritukset(2); 
    assertEquals("From: Suoritukset line: 46", 4, loytyneet.size()); 
    assertEquals("From: Suoritukset line: 47", true, loytyneet.get(0) == suoritus1); 
    assertEquals("From: Suoritukset line: 48", true, loytyneet.get(1) == suoritus3); 
    loytyneet = suoritukset.annaSuoritukset(2); 
    assertEquals("From: Suoritukset line: 50", 4, loytyneet.size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testLisaa63 */
  @Test
  public void testLisaa63() {    // Suoritukset: 63
    Suoritukset suoritukset = new Suoritukset(); 
    Suoritus suoritus1 = new Suoritus(), suoritus2 = new Suoritus(); 
    assertEquals("From: Suoritukset line: 66", 0, suoritukset.getLkm()); 
    suoritukset.lisaa(suoritus1); assertEquals("From: Suoritukset line: 67", 1, suoritukset.getLkm()); 
    suoritukset.lisaa(suoritus2); assertEquals("From: Suoritukset line: 68", 2, suoritukset.getLkm()); 
    suoritukset.lisaa(suoritus1); assertEquals("From: Suoritukset line: 69", 3, suoritukset.getLkm()); 
    suoritukset.lisaa(suoritus1); assertEquals("From: Suoritukset line: 70", 4, suoritukset.getLkm()); 
    suoritukset.lisaa(suoritus1); assertEquals("From: Suoritukset line: 71", 5, suoritukset.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta135 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta135() throws SailoException {    // Suoritukset: 135
    Suoritukset suoritukset = new Suoritukset(); 
    Suoritus suoritus21 = new Suoritus(); suoritus21.taytaSuoritus(2,1); 
    Suoritus suoritus11 = new Suoritus(); suoritus11.taytaSuoritus(1,2); 
    Suoritus suoritus22 = new Suoritus(); suoritus22.taytaSuoritus(2,1); 
    Suoritus suoritus12 = new Suoritus(); suoritus12.taytaSuoritus(1,1); 
    Suoritus suoritus23 = new Suoritus(); suoritus23.taytaSuoritus(2,2); 
    String tiedNimi = "testiTiedosto"; 
    File ftied = new File(tiedNimi+".dat"); 
    ftied.delete(); 
    try {
    suoritukset.lueTiedostosta(tiedNimi); 
    fail("Suoritukset: 149 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    suoritukset.lisaa(suoritus21); 
    suoritukset.lisaa(suoritus11); 
    suoritukset.lisaa(suoritus22); 
    suoritukset.lisaa(suoritus12); 
    suoritukset.lisaa(suoritus23); 
    try {
    suoritukset.tallenna(); 
    } catch (IOException e) {
    throw new RuntimeException(e); 
    }
    suoritukset = new Suoritukset(); 
    suoritukset.lueTiedostosta(tiedNimi); 
    Iterator<Suoritus> i = suoritukset.iterator(); 
    assertEquals("From: Suoritukset line: 163", suoritus21.toString(), i.next().toString()); 
    assertEquals("From: Suoritukset line: 164", suoritus11.toString(), i.next().toString()); 
    assertEquals("From: Suoritukset line: 165", suoritus22.toString(), i.next().toString()); 
    assertEquals("From: Suoritukset line: 166", suoritus12.toString(), i.next().toString()); 
    assertEquals("From: Suoritukset line: 167", suoritus23.toString(), i.next().toString()); 
    assertEquals("From: Suoritukset line: 168", false, i.hasNext()); 
    suoritukset.lisaa(suoritus23); 
    try {
    suoritukset.tallenna(); 
    } catch (IOException e) {
    throw new RuntimeException(e); 
    }
    assertEquals("From: Suoritukset line: 175", true, ftied.delete()); 
    File fbak = new File(tiedNimi+".bak"); 
    assertEquals("From: Suoritukset line: 177", true, fbak.delete()); 
  } // Generated by ComTest END
}