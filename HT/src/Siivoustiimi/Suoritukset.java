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

    /**
     * Tallentaa suoritukset tiedostoon.  // TODO Kesken.
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
