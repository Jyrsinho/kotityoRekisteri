package Siivoustiimi;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.RandomIka;

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

/**
 * Siivoustiimin kotityo.
 * Tietää kotityon kentät.
 * Osaa tarkistaa tietyn kentän oikeellisuuden.
 * Osaa muuttaa 1|Imurointi|3| - merkkijonon kotityon tiedoiksi.
 * Osaa antaa merkkijonona i:n kentän tiedot.
 * Osaa laittaa merkkijonon i:neksi kentäksi.
 * @author jyrihuhtala
 * @version 1.0 20.02.2024
 */
public class Kotityo implements Cloneable {

    private int kotityoId;
    private String kotityoNimi;
    private int vanhenemisaika;
    private int kesto;
    private LocalDate viimeisinSuoritus;
    private int vastuuhenkilonID;

    private static int seuraavaKotityoNro    = 1;


    /**
     * Alustetaan kotityo oletusarvoille
     */
    public Kotityo() {

    }

    /**
     * Alustetaan kotityo tietylle jasenelle.
     * @param jasenID jasenen viitenumero
     */
    public Kotityo (int jasenID) {
        this.vastuuhenkilonID = jasenID;
    }


    /**
     * @return jäsenen nimi
     */
    public String getKotityoNimi() {
        return kotityoNimi;
    }

    /**
     *
     * @return kotityon ID
     */
    public int getKotityoID() {
        return kotityoId;
    }

    /**
     * @return kotityön vastuuhenkilön ID
     */
    public int getVastuuhenkilonID() {
        return vastuuhenkilonID;
    }


    /**
     * @return kotityön arvioitu kestoaika
     */
    public int getKesto() {return kesto;}

    /**
     * @return kotityön viimeisimmän suorituksen päivämäärä
     */
    public LocalDate getViimeisinSuoritus() {return viimeisinSuoritus;}


    /**
     * @return kotityön vanhenemisaika
     */
    public int getVanhenemisaika() {return vanhenemisaika;}


    public String setKotityonNimi(String uusiNimi) {
        if (uusiNimi.isEmpty()) return "Nimi ei voi olla tyhjä";
        this.kotityoNimi = uusiNimi;
        return null;
    }

    public String setVanhenemisaika(String uusiVanhenemisAika) {
        if (uusiVanhenemisAika.isEmpty()) return "Vanhenemisaika ei voi olla tyhjä";
        if (!uusiVanhenemisAika.matches(("[0-9]*"))) return "Vanhenemisajan on oltava numero";
        this.vanhenemisaika = Integer.parseInt(uusiVanhenemisAika);
        return null;
    }

    public String setKesto (String uusiKesto) {
        if (uusiKesto.isEmpty()) return "Kesto ei voi olla tyhjä";
        if (!uusiKesto.matches(("[0-9]*"))) return "Keston on oltava numero";
        this.kesto = Integer.parseInt(uusiKesto);
        return null;
    }

    public String setVastuuhenkilonID(int uusiID) {
        this.vastuuhenkilonID = uusiID;
        return null;
    }

    public void setViimeisinSuoritus (LocalDate viimeisinSuoritus) {
        this.viimeisinSuoritus = viimeisinSuoritus;
    }

    public Kotityo clone() throws CloneNotSupportedException {
        Kotityo uusi;
        uusi = (Kotityo) super.clone();
        return uusi;
    }


    /**
     * Antaa kotityolle seuraavan ID numeron.
     * @return kotityon uusi kotityoID
     * example <pre name="test">
     * Kotityo imurointi = new Kotityo();
     * imurointi.getKotityoID() === 0;
     * imurointi.rekisteroi();
     * Kotityo tiskaaminen = new Kotityo();
     * tiskaaminen.rekisteroi();
     * int n1 = imurointi.getKotityoID();
     * int n2 = tiskaaminen.getKotityoID();
     * n1 === n2-1;
     * </pre>
     */
    public int  rekisteroi() {
        this.kotityoId= seuraavaKotityoNro;
        seuraavaKotityoNro ++;
        return this.kotityoId;
    }


    /**
     * Tarkistaa onko kotityön edellinen suoritus vanhentunut
     * @return true jos suoritus on vanhentunut, false jos ei ole vanhentunut.
     * TODO TESTIT: selvitettävä miten testataan metodia, joka on riippuvainen nykyisestä päivämäärästä. Tällä hetkellä testataan pääohjelmassa.
     */
    public boolean onVanhentunut() {

        int vertailu = (viimeisinSuoritus.plusDays(vanhenemisaika-1).compareTo(LocalDate.now()));

        return vertailu < 0 ;
    }


    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot kotityolle.
     * XX ja XX arvotaan, jotta kahdella jäsenellä ei olisi samoja tietoja.
     * @param id Kotityön vastuuhenkilön ID
     */
    public void taytaKotityo(int id) {

        this.kotityoNimi = "Imurointi";
        this.vastuuhenkilonID = id;
        this.viimeisinSuoritus = LocalDate.of(2024, 4, 01);
        this.kesto = RandomIka.arvoIka( 0, 60);
        this.vanhenemisaika = RandomIka.arvoIka(1,30);

    }

    /**
     * Tulostetaan kotityon tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", kotityoId));
        out.println(kotityoNimi);
        out.println(kesto);
        out.println(vanhenemisaika);
        out.println(viimeisinSuoritus);
        out.println(vastuuhenkilonID);

    }

     /**
      * Asettaa kotityöId:n ja samalla varmistaa että
      * seuraava numero on aina suurempi kuin tähän mennessä suurin.
      * @param nr asetettava kotityöId
      */
    private void setKotityoID(int nr) {
        this.kotityoId = nr;
        if ( kotityoId >= seuraavaKotityoNro ) seuraavaKotityoNro = kotityoId + 1;
    }



    /**
     * Selvittää kotityön tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaKotityoNro on suurempi kuin tuleva kotityoId.
     * @param s rivi josta kotityön tiedot otetaan
     * @example
     * <pre name="test">
     *   Kotityo kotityo = new Kotityo();
     *   kotityo.parse("1               |Imurointi                | 3                  | 20        | 2024-01-07           |    1|");
     *   kotityo.getKotityoID() === 1;
     *   kotityo.toString().startsWith("1|Imurointi|3|") === true;
     *
     *   kotityo.rekisteroi();
     *   int n = kotityo.getKotityoID();
     *   kotityo.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   kotityo.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   kotityo.getKotityoID() === n+20+1;
     * </pre>
     */

    public void parse(String s)  {
        StringBuilder sb = new StringBuilder(s);
        setKotityoID(Mjonot.erota(sb, '|', getKotityoID()));

        this.kotityoNimi = Mjonot.erota(sb, '|', getKotityoNimi());
        this.vanhenemisaika = Mjonot.erota(sb, '|', getVanhenemisaika());
        this.kesto = Mjonot.erota(sb,'|', getKesto());
        this.viimeisinSuoritus = LocalDate.parse(Mjonot.erota(sb, '|', "2012-12-13"));
        this.vastuuhenkilonID = Mjonot.erota(sb, '|', getVastuuhenkilonID());
    }



    /**
     * Tulostetaan jäsenen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    public String toString() {
        return ""+
                getKotityoID()          +"|"+
                getKotityoNimi()        +"|"+
                getVanhenemisaika()     +"|"+
                getKesto()              +"|"+
                getViimeisinSuoritus()  +"|"+
                getVastuuhenkilonID()   +"|";
    }

    /**
     * Testiohjelma jäsenelle.
     * @param args ei käytössä.
     */
    public static void main (String args[] ) {

        Kotityo imurointi = new Kotityo();
        Kotityo imurointi2 = new Kotityo();
        imurointi.rekisteroi();
        imurointi2.rekisteroi();

        imurointi.tulosta(System.out);
        System.out.println();

        imurointi2.tulosta(System.out);
        System.out.println();

        imurointi.taytaKotityo(1);
        imurointi.tulosta(System.out);
        System.out.println();

        imurointi2.taytaKotityo(1);
        imurointi2.tulosta(System.out);

        System.out.println();

        System.out.println(imurointi.onVanhentunut());
        System.out.println(imurointi2.onVanhentunut());

        System.out.println(LocalDate.now());

    }

}
