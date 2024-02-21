package Siivoustiimi;

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
    private Jasen[] alkiot;


    /**
     * Luodaan taulukko jäsenistä
     */
    public Jasenet () {
        this.alkiot = new Jasen[MAX_JASENIA];
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
       if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
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
        jasenet.lisaa(timo2);
        jasenet.lisaa(timo2);
        jasenet.lisaa(timo2);
        jasenet.lisaa(timo2);
    }
   catch (SailoException e) {
        System.err.println(e.getMessage());
   }
}
}
