package Siivoustiimi;


import java.io.OutputStream;
import java.io.PrintStream; /**
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
     * Antaa jäsenelle seuraavan tunnusnumeron.
     * @return jäsenen uusi tunnusnumero
     */
   public int  rekisteroi() {
       this.id= seuraavaNro;
       seuraavaNro ++;
       return this.id;
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
    Jasen jaana = new Jasen();
    timo.rekisteroi();
    jaana.rekisteroi();
    timo.tulosta(System.out);
    jaana.tulosta(System.out);

/*
    timo.taytaJasen();
    timo.tulosta(System.out);

    jaana.taytaJasen();
    jaana.tulosta(System.out);

 */
}

}