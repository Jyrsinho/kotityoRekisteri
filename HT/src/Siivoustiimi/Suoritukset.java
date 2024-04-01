package Siivoustiimi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

    public ArrayList<Suoritus> annaSuoritukset (int id) {
        ArrayList<Suoritus> loydetyt = new ArrayList<>();
        for (Suoritus suoritus : alkiot)
            if (suoritus.getKotityoID() == id) loydetyt.add(suoritus);
        return loydetyt;
    }


    /**Lisaa uuden suorituksen tietorakenteeseen.
     *
     * @param suoritus lisattava kotityo
     */
    public void lisaa (Suoritus suoritus) {
        alkiot.add(suoritus);
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

    public void lueTiedostosta(String tiedosto) throws SailoException {
        setTiedostonPerusNimi(tiedosto);

        try (BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi()))) {

            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Suoritus suoritus = new Suoritus();
                suoritus.parse(rivi); // TODO virhekäsittely
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
}
