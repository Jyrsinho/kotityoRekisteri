package Siivoustiimi;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Suoritukset implements Iterable<Suoritus> {
    private String tiedostonPerusNimi = "";

    private boolean muutettu = false;

    private final Collection<Suoritus> alkiot = new ArrayList<Suoritus>();


    /**
     * Luodaan Arraylist suorituksista
     */
    public Suoritukset() {

    }



    /**
     * Palauttaa kaikki yhden kotityön suoritukset ArrayList muodossa. Etsitään
     * kotityön ID:n perusteella.
     * @param id Kotityön id
     * @return Palauttaa kaikki yhden kotityön suoritukset ArrayList muodossa.
     * @example <pre name="test">
     * #import java.util.*;
     * Suoritukset suoritukset = new Suoritukset();
     * Suoritus suoritus1 = new Suoritus(2); suoritukset.lisaa(suoritus1);
     * Suoritus suoritus2 = new Suoritus(1); suoritukset.lisaa(suoritus2);
     * Suoritus suoritus3 = new Suoritus(2); suoritukset.lisaa(suoritus3);
     * Suoritus suoritus4 = new Suoritus(2); suoritukset.lisaa(suoritus4);
     * Suoritus suoritus5 = new Suoritus(2); suoritukset.lisaa(suoritus5);
     * Suoritus suoritus6 = new Suoritus(3); suoritukset.lisaa(suoritus6);
     *
     * List<Suoritus> loytyneet;
     * loytyneet = suoritukset.annaSuoritukset(5);
     * loytyneet.size() === 0;
     * loytyneet = suoritukset.annaSuoritukset(1);
     * loytyneet.size() === 1;
     * loytyneet = suoritukset.annaSuoritukset(2);
     * loytyneet.size() === 4;
     * loytyneet.get(0) == suoritus1 === true;
     * loytyneet.get(1) == suoritus3 === true;
     * loytyneet = suoritukset.annaSuoritukset(2);
     * loytyneet.size() === 4;
     * </pre>
     */
    public ArrayList<Suoritus> annaSuoritukset (int id) {
        ArrayList<Suoritus> loydetyt = new ArrayList<>();
        for (Suoritus suoritus : alkiot)
            if (suoritus.getKotityoID() == id) loydetyt.add(suoritus);
        return loydetyt;
    }


    /**Lisaa uuden suorituksen tietorakenteeseen.
     * @param suoritus lisattava kotityo
     * @example <pre name="test">
     * Suoritukset suoritukset = new Suoritukset();
     * Suoritus suoritus1 = new Suoritus(), suoritus2 = new Suoritus();
     * suoritukset.getLkm() === 0;
     * suoritukset.lisaa(suoritus1); suoritukset.getLkm() ===1;
     * suoritukset.lisaa(suoritus2); suoritukset.getLkm() ===2;
     * suoritukset.lisaa(suoritus1); suoritukset.getLkm() ===3;
     * suoritukset.lisaa(suoritus1); suoritukset.getLkm() ===4;
     * suoritukset.lisaa(suoritus1); suoritukset.getLkm() ===5;
     * </pre>
     */
    public void lisaa (Suoritus suoritus) {
        alkiot.add(suoritus);
        muutettu = true;
    }

    /**
     * Palauttaa siivoustiimin suoritusten lukumaaran
     * @return suoritusten lukumaara
     */
    public int getLkm() {
        return alkiot.size();
    }

    /**
     * Asettaa tiedoston perusnimen ilman tarkenninta
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


    @Override
    public Iterator<Suoritus> iterator() {
        return alkiot.iterator();
    }


    /**
     * Lukee suoritukset tiedostosta.
     *
     * @param tiedosto tiedoston nimi
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #THROWS java.io.IOException
     * #import java.io.*;
     * #import java.util.Iterator;
     * Suoritukset suoritukset = new Suoritukset();
     * Suoritus suoritus21 = new Suoritus(); suoritus21.taytaSuoritus(2,1);
     * Suoritus suoritus11 = new Suoritus(); suoritus11.taytaSuoritus(1,2);
     * Suoritus suoritus22 = new Suoritus(); suoritus22.taytaSuoritus(2,1);
     * Suoritus suoritus12 = new Suoritus(); suoritus12.taytaSuoritus(1,1);
     * Suoritus suoritus23 = new Suoritus(); suoritus23.taytaSuoritus(2,2);
     * String tiedNimi = "testiTiedosto";
     * File ftied = new File(tiedNimi+".dat");
     * ftied.delete();
     * suoritukset.lueTiedostosta(tiedNimi); #THROWS SailoException
     * suoritukset.lisaa(suoritus21);
     * suoritukset.lisaa(suoritus11);
     * suoritukset.lisaa(suoritus22);
     * suoritukset.lisaa(suoritus12);
     * suoritukset.lisaa(suoritus23);
     *  try {
     *           suoritukset.tallenna();
     *       } catch (IOException e) {
     *           throw new RuntimeException(e);
     *       }
     * suoritukset = new Suoritukset();
     * suoritukset.lueTiedostosta(tiedNimi);
     * Iterator<Suoritus> i = suoritukset.iterator();
     * i.next().toString() === suoritus21.toString();
     * i.next().toString() === suoritus11.toString();
     * i.next().toString() === suoritus22.toString();
     * i.next().toString() === suoritus12.toString();
     * i.next().toString() === suoritus23.toString();
     * i.hasNext() === false;
     * suoritukset.lisaa(suoritus23);
     *  try {
     *           suoritukset.tallenna();
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
                Suoritus suoritus = new Suoritus();
                suoritus.parse(rivi);
                lisaa(suoritus);
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
     * Tallentaa suoritukset tiedostoon.
     * @throws SailoException jos tallennus epäonnistuu
     */
    public void tallenna() throws SailoException, IOException {
        if ( !muutettu ) return;
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());

        fbak.delete();
        ftied.renameTo(fbak);

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Suoritus suoritus : this) {
                fo.println(suoritus.toString());
            }
        }catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
        muutettu = false;
    }
}
