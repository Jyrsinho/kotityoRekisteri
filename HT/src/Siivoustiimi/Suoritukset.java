package Siivoustiimi;

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
            if (suoritus.getSuorittajanID() == id) loydetyt.add(suoritus);
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
}
