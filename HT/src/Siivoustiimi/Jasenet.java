package Siivoustiimi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * pitää yllä varsinaista jäsenrekisteriä, eli osaa
 * lisätä ja poistaa jäsenen.
 * lukee ja kirjoittaa jäsenistön tiedostoon
 * osaa etsiä ja lajitella.
 * @author jyrihuhtala
 * version 21.2.2024
 */
public class Jasenet {
    private static final int MAX_JASENIA = 5;
    int lkm = 0;
    private String tiedostonNimi = "";
    private Jasen[] alkiot= new Jasen[MAX_JASENIA];


    /**
     * Luodaan taulukko jäsenistä
     */
    public Jasenet () {

    }

    /**
     * Lukee jäsenistön tiedostosta.  Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/nimet.dat";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }

    /**
     * Tallentaa jäsenistön tiedostoon.  Kesken.
     * @throws SailoException jos talletus epäonnistuu
     * @param tiedostonNimi tallenenttavan tiedoston nimi.
     */
    public void tallenna(String tiedostonNimi) throws SailoException, FileNotFoundException {
        File ftied = new File(tiedostonNimi+"/nimet.dat");
        try {PrintStream fo = new PrintStream(new FileOutputStream(ftied , false));
            for (int i = 0; i < getLkm(); i++) {
                Jasen jasen = anna(i);
                fo.println(jasen.toString());
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto" + ftied.getAbsolutePath() + " ei aukea.");
        }

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
     * jasenet.lisaa(timo1); #THROWS SailoException
     * </pre>
     */
    public void lisaa (Jasen jasen) throws SailoException {
       if (lkm >= alkiot.length) {
           Jasen [] alkiot2 = new Jasen [alkiot.length+ alkiot.length];

           for (int i= 0; i < alkiot.length; i++) {
               alkiot2[i] = alkiot[i];
           }
         alkiot = alkiot2;
       }
        this.alkiot[this.lkm] = jasen;
        this.lkm++;
    }

    /**
     *
     * @param args ei köytössä
     */
    public static void main (String []args) {
    Jasenet jasenet = new Jasenet();

    Jasen timo1 = new Jasen();
    Jasen timo2 = new Jasen();

    timo1.rekisteroi();
    timo1.taytaJasen();
    timo2.rekisteroi();
    timo2.taytaJasen();

    try {
        jasenet.lisaa(timo1);
        jasenet.lisaa(timo2);

        for (int i = 0; i < jasenet.getLkm(); i++) {
            Jasen jasen = jasenet.anna(i);
            System.out.println("Jäsen indeksi: " + i);
            jasen.tulosta(System.out);
        }
    }
   catch (SailoException e) {
        System.err.println(e.getMessage());
   }
    try {
        jasenet.tallenna("siivoustiimi");
    } catch (SailoException | FileNotFoundException e) {
        e.printStackTrace();
    }

    }
}
