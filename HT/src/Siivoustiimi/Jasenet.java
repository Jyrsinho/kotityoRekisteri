package Siivoustiimi;

import java.io.*;
import java.security.PublicKey;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * pitää yllä varsinaista jäsenrekisteriä, eli osaa
 * lisätä ja poistaa jäsenen.
 * lukee ja kirjoittaa jäsenistön tiedostoon
 * osaa etsiä ja lajitella.
 * @author jyrihuhtala
 * version 21.2.2024
 */
public class Jasenet implements Iterable<Jasen>{
    private static final int MAX_JASENIA = 5;
    int lkm = 0;
    private String tiedostonNimi = "";
    private Jasen[] alkiot = new Jasen[MAX_JASENIA];
    private String kokoNimi = "";
    private String tiedostonPerusNimi = "nimet";
    private boolean muutettu = false;


    /**
     * Luodaan taulukko jäsenistä
     */
    public Jasenet() {

    }


    /**
     * palauttaa annettua merkkijonoa vastaavan jäsenen ID:n
     * @param jasenenNimi jäsenen nimi
     * @return jäsenen ID:n
     * @example <pre name="test">
     * Jasenet jasenet = new Jasenet();
     * Jasen uusi = new Jasen(); Jasen uusi2 = new Jasen(); Jasen uusi3 = new Jasen();
     * uusi.taytaJasen(); uusi2.taytaJasen(); uusi3.taytaJasen();
     * uusi.rekisteroi(); uusi2.rekisteroi(); uusi3.rekisteroi();
     * uusi2.setEtunimi("Kari"); uusi2.setSukunimi("Karila");
     * jasenet.lisaa(uusi); jasenet.lisaa(uusi2); jasenet.lisaa(uusi3);
     * jasenet.annaJasenenId("Kari Karila");
     * </pre>
     */
    public int annaJasenenId(String jasenenNimi) {
        for (Jasen jasen: alkiot) {
            if (jasen.getNimi().equals(jasenenNimi)) return jasen.getId();
        }
        return -1;
    }

    public Jasen[] getJasenet() {
        return alkiot;
    }

    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     *
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }


    /**
     * Palauttaa varakopiotiedoston nimen
     *
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }

    /**
     * Palauttaa Kerhon koko nimen
     * @return Kerhon koko nimi merkkijononna
     */
    public String getKokoNimi() {
        return kokoNimi;
    }


    /**
     * Lukee jäsenistön tiedostosta.  Kesken.
     *
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        setTiedostonPerusNimi(hakemisto);

        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {

            String rivi;

            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Jasen jasen = new Jasen();
                jasen.parse(rivi); // voisi olla virhekäsittely
                lisaa(jasen);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }


    /**
     * Tallentaa jäsenistön tiedostoon.  Kesken.
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     *
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }


    /**
     * Tallentaa jäsenistön tiedostoon.  Kesken.
     *
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException, FileNotFoundException {

        if (!muutettu) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Jasen jasen : this) {
                fo.println(jasen.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }


    public int etsiId(int id) {
        for (int i = 0; i < lkm; i++) {
            if (id == alkiot[i].getId()) return 1;
        }
        return -1;
    }


    /**
     * Poistaa tiedostosta valitun jäsenen.
     */
    public int poista(int id) {

        int ind = etsiId(id);
        if (ind < 0) return 0;
        lkm--;
        for (int i =ind; i <lkm; i++) {
            alkiot[i] = alkiot[i+1];
        }

        alkiot[lkm] = null;
        muutettu = true;
        return 1;
        }


    /**
     * Palauttaa kerhon jäsenten lukumääärämn
     * @return jäsenten lukumäärä
     */
    public int getLkm() {
        return lkm;
    }

    public Jasen anna(int i) throws IndexOutOfBoundsException {
        if (i <0 || this.lkm <=i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }

    /**
     * Lisää uuden jäsenen tietorakenteeseen. Ottaa jäsenen omistukseensa.
     * @param jasen lisättävän jäsenen viite.
     * @throws SailoException jos tietorakenne on jo täynnä.
     * @example <pre name="test">
     * #THROWS SailoException
     * Jasenet jasenet = new Jasenet();
     * Jasen timo1 = new Jasen(), timo2 = new Jasen();
     * jasenet.getLkm() === 0;
     * jasenet.lisaa(timo1); jasenet.getLkm() ===1;
     * jasenet.lisaa(timo2); jasenet.getLkm() ===2;
     * jasenet.lisaa(timo1); jasenet.getLkm() ===3;
     * jasenet.anna(0) === timo1;
     * jasenet.anna(1) === timo2;
     * jasenet.anna(2) === timo1;
     * jasenet.anna(1) ==timo1 === false;
     * jasenet.anna(1) ==timo2 === true;
     * jasenet.anna(3) ===timo1; #THROWS IndexOutOfBoundsException
     * jasenet.lisaa(timo1); jasenet.getLkm() ===4;
     * jasenet.lisaa(timo1); jasenet.getLkm() ===5;
     * </pre>
     */
    public void lisaa (Jasen jasen)  {
       if (lkm >= alkiot.length) {
           Jasen [] alkiot2 = new Jasen [alkiot.length+ alkiot.length];

           for (int i= 0; i < alkiot.length; i++) {
               alkiot2[i] = alkiot[i];
           }
         alkiot = alkiot2;
       }
        this.alkiot[this.lkm] = jasen;
        this.lkm++;
        muutettu = true;
    }

    /**
     * Korvaa jäsenen tietorakenteessa.
     * Etsitään samalla tunnusluvalla oleva jäsen. Jos sellaista ei löydy
     * niin lisätään uutena jäsenenä.
     * @param jasen lisättävä jäsen
     * #PACKAGEIMPORT
     * Jasenet jasenet = new Jasenet();
     * Jasen timo1 = new Jasen, timo2 = New Jasen();
     * timo1.rekisteroi(); timo2.rekisteroi();
     * jasenet.getLkm === 0;
     * jasenet.korvaaTaiLisaa(timo1); jasenet.getLkm ===1;
     * jasenet.korvaaTaiLisaa(timo1); jasenet.getLkm ===2;
     * Jasen timo3 = timo1.clone();
     * timo3.setKatuosoite("Majavatie3");
     * Iterator<Jasen> it = jasenet.iterator();
     * it.next() === timo1 === true;
     * jasenet.korvaaTaiLisaa(timo3); jasenet.getLkm()===2;
     * it = jasenet.iterator();
     * Jasen j0 = it.next();
     * j0 === timo3;
     * j0 ===timo3 === true;
     * j0 ===timo1 === false;
     */
    public void korvaaTaiLisaa(Jasen jasen) throws SailoException {
        int id = jasen.getId();
        for (int i = 0; i < lkm; i++) {
            if ( alkiot[i].getId() == id ) {
                alkiot[i] = jasen;
                muutettu = true;
                return;
            }
        }
        lisaa(jasen);
    }


    /**
     * TODO Testit
     * Luokka jäsenten iteroimiseksi.
     * @example
     */
    public class JasenetIterator implements Iterator<Jasen> {
        private int kohdalla = 0;


        /**
         * Onko olemassa vielä seuraavaa jäsentä
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä jäseniä
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava jäsen
         * @return seuraava jäsen
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Jasen next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }


    /**
     * Palautetaan iteraattori jäsenistään.
     * @return jäsen iteraattori
     */
    @Override
    public Iterator<Jasen> iterator() {
        return new JasenetIterator();
    }


    /**
     *
     * @param args ei köytössä
     */
    public static void main (String []args) {
    Jasenet jasenet = new Jasenet();

    try {
        jasenet.lueTiedostosta("siivoustiimi");
    }catch (SailoException e) {
        System.err.println(e.getMessage());
    }

        Jasen timo1 = new Jasen();
    Jasen timo2 = new Jasen();

    timo1.rekisteroi();
    timo1.taytaJasen();
    timo2.rekisteroi();
    timo2.taytaJasen();


        jasenet.lisaa(timo1);
        jasenet.lisaa(timo2);

        for (int i = 0; i < jasenet.getLkm(); i++) {
            Jasen jasen = jasenet.anna(i);
            System.out.println("Jäsen indeksi: " + i);
            jasen.tulosta(System.out);
            System.out.println();
    }
    try {
        jasenet.tallenna();
    } catch (SailoException | FileNotFoundException e) {
        e.printStackTrace();
    }

    System.out.println(jasenet.annaJasenenId("Timo Kekkila"));
    //System.out.println(jasenet.annaJasenenId("Jyri Huhtala"));
    }

}
