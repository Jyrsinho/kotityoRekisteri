package Siivoustiimi;


import fi.jyu.mit.ohj2.Mjonot;

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
     *
     * @return jasenen etunimi
     */
   public String getEtunimi() {return etunimi;}


    public String getSukunimi() {return sukunimi;}

    /**
     * @return jasenen koko nimi
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
     *
     * @return jasenen katuosoite
     */
   public String getKatuosoite() {return katuosoite;}

    public String getPostinumero() {return postinumero;}

    public String getKaupunki() {return kaupunki;}

    public String getPuhelin() {return puhelinNumero;}

    public int getIka() {return ika;}




    /**
     * Antaa jäsenelle seuraavan Id:n.
     * @return jäsenen uusi Id
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


   public void setId(int numero) {
       this.id = numero;
       if (id >= seuraavaNro) seuraavaNro = id+1;
   }

    /**
     * Selvittää jäsenen tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param s rivi josta jäsenen tiedot otetaan
     * @example
     * <pre name="test">
     *   Jasen jasen = new Jasen();
     *   jasen.parse("5    |     Timo |  Kekkila     |Talvitie 4    |   11600   |    Vantaa   |   05013899304   |   41   |");
     *   jasen.getId() === 5;
     *   jasen.toString().startsWith("5|Timo|Kekkila|Talvitie 4|") === true;
     *
     *   jasen.rekisteroi();
     *   int n = jasen.getId();
     *   jasen.parse(""+(n+20));       // Otetaan merkkijonosta vain jasenen ID
     *   jasen.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   jasen.getId() === n+20+1;
     * </pre>
     */

    public void parse(String s) {
        StringBuilder sb = new StringBuilder(s);
        setId(Mjonot.erota(sb, '|', getId()));
        this.etunimi = Mjonot.erota(sb, '|', getEtunimi());
        this.sukunimi = Mjonot.erota(sb, '|', getSukunimi());
        this.katuosoite = Mjonot.erota(sb,'|', getKatuosoite());
        this.postinumero = Mjonot.erota(sb, '|', getPostinumero());
        this.kaupunki = Mjonot.erota(sb, '|', getKaupunki());
        this.puhelinNumero = Mjonot.erota(sb,'|', getPuhelin());
        this.ika = Mjonot.erota(sb,'|', getIka());
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
    public void tulosta(PrintStream out) {
       out.printf("%03d%n", id);
       out.println(etunimi);
       out.println(sukunimi);
       out.println(katuosoite);
       out.println(postinumero);
       out.println(kaupunki);
       out.println(puhelinNumero);
       out.println(ika);

    }


    //TODO viimeistele
    /**
     * Tulostetaan jäsenen tiedot
     */
   public String toString() {

       return ""+
               getId()          +"|"+
               getEtunimi()     +"|"+
               getSukunimi()    +"|"+
               getKatuosoite()  +"|"+
               getPostinumero() +"|"+
               getKaupunki()    +"|"+
               getPuhelin()     +"|"+
               getIka()         +"|";


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