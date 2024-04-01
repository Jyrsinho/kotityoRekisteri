package Siivoustiimi;

import fi.jyu.mit.ohj2.Mjonot;

import java.io.PrintStream;

import static kanta.RandomNumero.rand;

/**
 * Siivoustiimin suoritus.
 * Tietää suorituksen kentät
 * Osaa tarkistaa tietyn kentän oikeellisuuden.
 * Osaa muuttaa 1|30|20-1-2024|1|1| - merkkijonon
 * suorituksen tiedoiksi.
 * Osaa antaa merkkijonona i:n kentän tiedot.
 * Osaa laittaa merkkijonon i:neksi kentäksi.
 * @author jyrihuhtala
 * @version 1.0 20.02.2024
 */
public class Suoritus {

    private int suoritusID;
    private int suoritusAika;
    private String suoritusPvm;
    private int kotityoID;
    private int suorittajaID;

    private static int seuraavaSuoritusNro = 1;

    public Suoritus () {

    }

    public int getSuoritusID() {
        return suoritusID;
    }

    public int getKotityoID() {
        return kotityoID;
    }

    public int getsuoritusAika() {
        return suoritusAika;
    }

    public int getSuorittajaID() {
        return suorittajaID;
    }


    /**
     * Antaa suoritukselle seuraavan tunnusnumeron.
     * @return suorituksen uusi tunnusnumero
     * @example <pre name="test">
     * Suoritus suoritus1 = new Suoritus();
     * suoritus1.getSuoritusID() === 0;
     * suoritus1.rekisteroiSuoritus();
     * Suoritus suoritus2 = new Suoritus();
     * suoritus2.rekisteroiSuoritus();
     * int n1 = suoritus1.getSuoritusID();
     * int n2 = suoritus2.getSuoritusID();
     * n1 === n2-1;
     * </pre>
     */
    public int  rekisteroiSuoritus() {
        this.suoritusID= seuraavaSuoritusNro;
        seuraavaSuoritusNro ++;
        return this.suoritusID;
    }


    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot suoritukselle.
     * Suoritusaika arvotaan, jotta kahdella suorituksella ei olisi samoja tietoja.
     * @param tekijanId kotityön suorittajan ID
     * @param kotityontunnusnumero suoritetun kotityö id
     */
    public void taytaSuoritus(int tekijanId, int kotityontunnusnumero) {
        suoritusAika = rand(10,50);
        suoritusPvm = "12-12-2022";
        kotityoID = kotityontunnusnumero;
        suorittajaID = tekijanId;
    }

    public void parse(String s)  {
        StringBuilder sb = new StringBuilder(s);
        setSuoritusID(Mjonot.erota(sb, '|', getKotityoID()));

        this.suoritusAika = Mjonot.erota(sb, '|', getsuoritusAika());
        this.suoritusPvm = Mjonot.erota(sb, '|', getViimeisinSuoritus());
        this.kotityoID = Mjonot.erota(sb,'|', getKotityoID());
        this.suorittajaID = Mjonot.erota(sb, '|', getSuorittajanID());

    }

    private void setSuoritusID(int nro) {
        this.suoritusID = nro;
        if (suoritusID >= seuraavaSuoritusNro) seuraavaSuoritusNro = suoritusID +1;
    }


    /**
     * Tulostetaan suorituksen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {;
        out.println(String.format("%03d", suoritusID));
        out.println(suoritusAika);
        out.println(suoritusPvm);
        out.println(kotityoID);
        out.println(suorittajaID);

    }

    public int getSuorittajanID() {
        return suorittajaID;
    }

    public String getViimeisinSuoritus() {
        return suoritusPvm;
    }




    /**
     * Testiohjelma suoritukselle.
     * @param args ei käytössä.
     */
    public static void main (String args[]) {

        Suoritus suoritus1 = new Suoritus();
        Suoritus suoritus2 = new Suoritus();
        suoritus1.rekisteroiSuoritus();
        suoritus2.rekisteroiSuoritus();

        suoritus1.tulosta(System.out);
        System.out.println();

        suoritus2.tulosta(System.out);
        System.out.println();

        suoritus1.taytaSuoritus(1,2);
        suoritus1.tulosta(System.out);
        System.out.println();

        suoritus2.taytaSuoritus(2,1);
        suoritus2.tulosta(System.out);


    }

}



