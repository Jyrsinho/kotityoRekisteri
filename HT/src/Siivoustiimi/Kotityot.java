package Siivoustiimi;

import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * pitää yllä varsinaista kotityö-rekisteriä, eli osaa lisätä ja poistaa kotityön.
 * lukee ja kirjoittaa kotityöt tiedostoon
 * osaa etsiä ja lajitella
 * @author jyrihuhtala
 * version 28.3.2024
 */
public class Kotityot implements Iterable<Kotityo> {

    private boolean muutettu = false;
    private String tiedostonPerusNimi = "";

    private final Collection<Kotityo> alkiot = new ArrayList<Kotityo>();

    /**
     * Luodaan Arraylist kotitöistä
     */
    public Kotityot() {

    }


    /**
     * Lisaa uuden kotityon tietorakenteeseen.
     *
     * @param kotityo lisattava kotityo
     */
    public void lisaa(Kotityo kotityo) {
        alkiot.add(kotityo);
        muutettu = true;

    }


    /**
     * Lukee kotityot tiedostosta.  TODO tarkista testit.
     *
     * @param tiedosto tiedoston nimi
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #THROWS java.io.IOException
     * #import java.io.*;
     * Kotityot kotityot = new Kotityot();
     * Kotityo imurointi21 = new Kotityo(); imurointi21.taytaKotityo(2);
     * Kotityo imurointi11 = new Kotityo(); imurointi11.taytaKotityo(1);
     * Kotityo imurointi22 = new Kotityo(); imurointi22.taytaKotityo(2);
     * Kotityo imurointi12 = new Kotityo(); imurointi12.taytaKotityo(1);
     * Kotityo imurointi23 = new Kotityo(); imurointi23.taytaKotityo(2);
     * String tiedNimi = "testiTiedosto";
     * File ftied = new File(tiedNimi+".dat");
     * ftied.delete();
     * kotityot.lueTiedostosta(tiedNimi); #THROWS SailoException
     * kotityot.lisaa(imurointi21);
     * kotityot.lisaa(imurointi11);
     * kotityot.lisaa(imurointi22);
     * kotityot.lisaa(imurointi12);
     * kotityot.lisaa(imurointi23);
     *  try {
     *           kotityot.tallenna();
     *       } catch (IOException e) {
     *           throw new RuntimeException(e);
     *       }
     * kotityot = new Kotityot();
     * kotityot.lueTiedostosta(tiedNimi);
     * Iterator<Kotityo> i = kotityot.iterator();
     * i.next().toString() === imurointi21.toString();
     * i.next().toString() === imurointi11.toString();
     * i.next().toString() === imurointi22.toString();
     * i.next().toString() === imurointi12.toString();
     * i.next().toString() === imurointi23.toString();
     * i.hasNext() === false;
     * kotityot.lisaa(imurointi23);
     *  try {
     *           kotityot.tallenna();
     *       } catch (IOException e) {
     *           throw new RuntimeException(e);
     *       }
     * ftied.delete() === true;
     * File fbak = new File(tiedNimi+".bak");
     * fbak.delete() === true;
     * </pre>
     */


    public void lueTiedostosta(String tiedosto) throws SailoException {
        setTiedostonPerusNimi(tiedosto);

        try (BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi()))) {

            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Kotityo kotityo = new Kotityo();
                kotityo.parse(rivi); // TODO virhekäsittely
                lisaa(kotityo);
            }
            muutettu = false;

        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }


    /**
      *  Luetaan aikaisemmin annetun nimisestä tiedostosta
      * @throws SailoException jos tulee poikkeus
      */
    public void lueTiedostosta() throws SailoException, FileNotFoundException {
        lueTiedostosta(getTiedostonPerusNimi());
    }



    /**
     * Tallentaa kotityöt tiedostoon.  // TODO Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException, IOException {
        if ( !muutettu ) return;
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());

        fbak.delete();
        ftied.renameTo(fbak);

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Kotityo kotityo : this) {
                fo.println(kotityo.toString());
            }
        }catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
        throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
    }
        muutettu = false;
}


    /**
     * Asettaa tiedoston perusnimen imlan tarkenninta
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }


    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }



    /**
     * Palauttaa siivoustiimin kotitoiden lukumaaran
     * @return kotitöiden lukumaara
     */
    public int getLkm() {
        return alkiot.size();
    }

    /**
     * Iteraattori kaikkien kotitöiden läpikäymiseen
     * @return kotityöiteraattori
     *
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     *
     *  Kotityot kotityot = new Kotityot();
     *  Kotityo imurointi1 = new Kotityo(2); kotityot.lisaa(imurointi1);
     *  Kotityo imurointi2 = new Kotityo(1); kotityot.lisaa(imurointi2);
     *  Kotityo imurointi3 = new Kotityo(2); kotityot.lisaa(imurointi3);
     *  Kotityo imurointi4 = new Kotityo(1); kotityot.lisaa(imurointi4);
     *  Kotityo lakaisu = new Kotityo(2); kotityot.lisaa(lakaisu);
     *
     *  Iterator<Kotityo> i2=kotityot.iterator();
     *  i2.next() === imurointi1;
     *  i2.next() === imurointi2;
     *  i2.next() === imurointi3;
     *  i2.next() === imurointi4;
     *  i2.next() === lakaisu;
     *  i2.next() === lakaisu;  #THROWS NoSuchElementException
     *
     * </pre>
     */

    @Override
    public Iterator<Kotityo> iterator() {
        return alkiot.iterator();
    }


    /**
     * Haetaan kaikki yhden jasenen kotityot
     * @param id jasenen tunnusnumero jota vastaan kotitoita haetaan.
     * @return tietorakenne, jossa viitteet löydettyihin kotitoihin.
     * @example <pre name="test">
     * #import java.util.*;
     * Kotityot kotityot = new Kotityot();
     * Kotityo imurointi1 = new Kotityo(2); kotityot.lisaa(imurointi1);
     * Kotityo imurointi2 = new Kotityo(1); kotityot.lisaa(imurointi2);
     * Kotityo imurointi3 = new Kotityo(2); kotityot.lisaa(imurointi3);
     * Kotityo imurointi4 = new Kotityo(2); kotityot.lisaa(imurointi4);
     * Kotityo imurointi5 = new Kotityo(2); kotityot.lisaa(imurointi5);
     * Kotityo imurointi6 = new Kotityo(3); kotityot.lisaa(imurointi6);
     *
     * List<Kotityo> loytyneet;
     * loytyneet = kotityot.annaKotityot(5);
     * loytyneet.size() === 0;
     * loytyneet = kotityot.annaKotityot(1);
     * loytyneet.size() === 1;
     * loytyneet = kotityot.annaKotityot(2);
     * loytyneet.size() === 4;
     * loytyneet.get(0) == imurointi1 === true;
     * loytyneet.get(1) == imurointi3 === true;
     * loytyneet = kotityot.annaKotityot(2);
     * loytyneet.size() === 4;
     * </pre>
     *
     */
    public ArrayList<Kotityo> annaKotityot (int id) {
        ArrayList<Kotityo> loydetyt = new ArrayList<Kotityo>();
        for (Kotityo kottyo : alkiot)
            if (kottyo.getVastuuhenkilonID() == id) loydetyt.add(kottyo);
        return loydetyt;
    }

    /**
     * Testiohjelma harrastuksille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kotityot kotityot = new Kotityot();

        try {
            kotityot.lueTiedostosta("kotityot");
        }catch (SailoException e) {
            System.err.println(e.getMessage());
        }

        Kotityo imurointi = new Kotityo();
        imurointi.taytaKotityo(1);
        Kotityo imurointi2 = new Kotityo();
        imurointi2.taytaKotityo(1);
        Kotityo imurointi3 = new Kotityo();
        imurointi3.taytaKotityo(2);
        Kotityo imurointi4 = new Kotityo();
        imurointi4.taytaKotityo(2);

        kotityot.lisaa(imurointi);
        kotityot.lisaa(imurointi2);
        kotityot.lisaa(imurointi3);
        kotityot.lisaa(imurointi4);

        System.out.println("============= Kotityöt testi =================");

        List<Kotityo> kotityot2 = kotityot.annaKotityot(2);

        for (Kotityo kottyo : kotityot2) {
            System.out.print(kottyo.getVastuuhenkilonID() + " \n");
            kottyo.tulosta(System.out);
        }

        try {
            kotityot.tallenna();
        } catch (SailoException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}