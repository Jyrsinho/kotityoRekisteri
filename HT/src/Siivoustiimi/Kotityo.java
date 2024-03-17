package Siivoustiimi;

import kanta.RandomIka;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

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
public class Kotityo {

    private int kotityoId;
    private String kotityoNimi;
    private int vanhenemisaika;
    private int kesto;
    private Date viimeisinSuoritus;
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

    public int getVastuuhenkilonID() {
        return vastuuhenkilonID;
    }


    /**
     * Antaa kotityolle seuraavan ID numeron.
     * @return kotityon uusi kotityoID
     * example <pre name="test">
     * Kotityo imurointi = new Kotityo();
     * imurointi.getId() === 0;
     * imurointi.rekisteroi();
     * Kotityo tiskaaminen = new Kotityo();
     * tiskaaminen.rekisteroi();
     * int n1 = imurointi.getId();
     * int n2 = tiskaaminen.getId();
     * n1 === n2-1;
     * </pre>
     */
    public int  rekisteroi() {
        this.kotityoId= seuraavaKotityoNro;
        seuraavaKotityoNro ++;
        return this.kotityoId;
    }

    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot kotityolle.
     * XX ja XX arvotaan, jotta kahdella jäsenellä ei olisi samoja tietoja.
     */
    public void taytaKotityo(int id) {

        this.kotityoNimi = "Imurointi";
        this.vastuuhenkilonID = id;
        this.viimeisinSuoritus = new Date(2024-1-1);
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
     * Tulostetaan jäsenen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Testiohjelma jäsenelle.
     * @param args ei käytössä.
     */
    public static void main (String args[]) {

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


    }

}
