package Siivoustiimi;


import java.io.OutputStream;
import java.io.PrintStream;
import static kanta.RandomNumero.arvoNumero;
import static kanta.RandomIka.arvoIka;

/**
 * Siivoustiimin jäsen.
 * Tietää jäsenen kentät (sukunimi, etunimi, osoite)
 * Osaa tarkistaa tietyn kentän oikeellisuuden.
 * Osaa muuttaa 1|Simo Siivooja|...| - merkkijonon
 * jäsenen tiedoiksi.
 * Osaa antaa merkkijonona i:n kentän tiedot.
 * Osaa laittaa merkkijonon i:neksi kentäksi.
 * @author jyrihuhtala
 * @version 1.0 20.02.2024
 */
public class Jasen {

   private int id;
   private String sukunimi;
   private String etunimi;
   private String katuosoite;
   private String postinumero;
   private String kaupunki;
   private String puhelinNumero;
   private int ika;

   private static int seuraavaNro    = 1;


    /**
     * @return jasenen nimi
     */
   public String getNimi() {
       return etunimi +" "+ sukunimi;
   }

    /**
     *
     * @return jasenen ID
     */
   public int getId() {
       return id;
   }


    /**
     * Antaa jäsenelle seuraavan tunnusnumeron.
     * @return jäsenen uusi tunnusnumero
     * @example <pre name="test">
     * Jasen timo1 = new Jasen();
     * timo1.getId() === 0;
     * timo1.rekisteroi();
     * Jasen timo2 = new Jasen();
     * timo2.rekisteroi();
     * int n1 = timo1.getId();
     * int n2 = timo2.getId();
     * n1 === n2-1;
     * </pre>
     */
   public int  rekisteroi() {
       this.id= seuraavaNro;
       seuraavaNro ++;
       return this.id;
   }


    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot jäsenelle.
     * Ikä ja puhelinnumero arvotaan, jotta kahdella jäsenellä ei olisi samoja tietoja.
     */
   public void taytaJasen() {

       sukunimi = "Kekkila";
       etunimi= "Timo";
       katuosoite = "Talvitie 4";
       postinumero = "11600";
       kaupunki = "Vantaa";
       puhelinNumero = arvoNumero();
       ika = arvoIka(15,99);
   }


    /**
     * Tulostetaan henkilön tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {;
       out.println(String.format("%03d", id));
       out.println(etunimi);
       out.println(sukunimi);
       out.println(katuosoite);
       out.println(postinumero);
       out.println(kaupunki);
       out.println(puhelinNumero);
       out.println(ika);

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

    Jasen timo = new Jasen();
    Jasen timo2 = new Jasen();
    timo.rekisteroi();
    timo2.rekisteroi();

    timo.tulosta(System.out);
    System.out.println();

    timo2.tulosta(System.out);
    System.out.println();

    timo.taytaJasen();
    timo.tulosta(System.out);
    System.out.println();

    timo2.taytaJasen();
    timo2.tulosta(System.out);


}

}