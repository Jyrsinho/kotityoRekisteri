package Siivoustiimi;


import fi.jyu.mit.ohj2.Mjonot;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


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
public class Jasen implements Cloneable {

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
     * @return jasenen etunimi
     */
   public String getEtunimi() {return etunimi;}


    /**
     * @return jasenen sukunimi
     */
    public String getSukunimi() {return sukunimi;}

    /**
     * @return jasenen koko nimi
     */
   public String getNimi() {
       return etunimi +" "+ sukunimi;
   }


    /**
     * @return jasenen ID
     */
   public int getId() {
       return id;
   }


    /**
     * @return jasenen katuosoite
     */
   public String getKatuosoite() {return katuosoite;}


    /**
     * @return jasenen postinumero
     */
    public String getPostinumero() {return postinumero;}


    /**
     * @return jasenen kaupunki
     */
    public String getKaupunki() {return kaupunki;}


    /**
     * @return jasenen puhelinnumero
     */
    public String getPuhelin() {return puhelinNumero;}


    /**
     * @return jasenen ika
     */
    public int getIka() {return ika;}


    public void setId(int numero) {
        this.id = numero;
        if (id >= seuraavaNro) seuraavaNro = id+1;
    }


    public String setEtunimi(String etunimi) {
        this.etunimi = etunimi;
        return null;
    }


    public String setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
        return null;
    }


    public String setKatuosoite (String katuosoite) {
        this.katuosoite = katuosoite;
        return null;
    }


    public String setKaupunki (String kaupunki) {
        this.kaupunki = kaupunki;
        return null;
    }


    public String setPostinumero (String postinumero) {
        if (!postinumero.matches(("[0-9]*"))) return "Postinumeron on oltava numero";
        this.postinumero = postinumero;
        return null;
    }


    public String setIka (String ika) {
        if (!ika.matches(("[0-9]*"))) return "Iän on oltava numero";
        this.ika = Integer.parseInt(ika);
        return null;
    }


    public String setPuhelin (String puhelin) {
        if (!puhelin.matches(("[0-9]*"))) return "puhelinnumeron on oltava numero";
        this.puhelinNumero = puhelin;
        return null;
    }

     /**
     * Antaa tietokannan luontilausekkeen jäsentaululle
     * @return jäsentaulun luontilauseke
     */
     public String annaLuontilauseke() {
                 return "CREATE TABLE Jasenet (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT , " +
                         "sukunimi VARCHAR(20) NOT NULL, " +
                         "etunimi VARCHAR(20) NOT NULL, " +
                         "katuosoite VARCHAR(100), " +
                         "postinumero VARCHAR(5), " +
                         "kaupunki VARCHAR(100), " +
                         "puhelinNumero VARCHAR(100), " +
                         "ika INTEGER " +
                         ")";

                     }


     /**
     * Antaa jasenen lisayslausekkeen
     * @param con tietokantayhteys
     * @return jasenen lisayslauseke
     * @throws SQLException Jos lausekkeen luonnissa on ongelmia
     */
    public PreparedStatement annaLisayslauseke(Connection con)
            throws SQLException {
        PreparedStatement sql = con.prepareStatement("INSERT INTO Jasenet" +
                "(id, sukunimi, etunimi, katuosoite, postinumero, kaupunki, " +
                "puhelinnumero, ika) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

        // Syotetaan kentat nain valttaaksemme SQL injektiot.
        // Kayttajin syotteita ei ikina vain kirjoiteta kysely
        // merkkijonoon tarkistamatta niita SQL injektioiden varalta!
        if ( id != 0 ) sql.setInt(1, id); else sql.setString(1, null);
        sql.setString(2, sukunimi);
        sql.setString(3, etunimi);
        sql.setString(4, katuosoite);
        sql.setString(5, postinumero);
        sql.setString(6, kaupunki);
        sql.setString(7, puhelinNumero);
        sql.setInt(8, ika);

        return sql;
    }


    /**
     * Tarkistetaan onko id muuttunut lisäyksessä
     * @param rs lisäyslauseen ResultSet
     * @throws SQLException jos tulee jotakin vikaa
     */
    public void tarkistaId(ResultSet rs) throws SQLException {
        if ( !rs.next() ) return;
        int jasenId = rs.getInt(1);
        if ( jasenId == id ) return;
        setId(jasenId);
     }


    /**
     * Kloonaa jäsenen
     * @return kloonin jäsenestä
     * @throws CloneNotSupportedException jos kloonia ei tueta
     */
    public Jasen clone() throws CloneNotSupportedException {
       Jasen uusi;
       uusi = (Jasen) super.clone();
       return uusi;
    }


    /**
     * Antaa jäsenelle seuraavan Id:n.
     */
   public int rekisteroi() {
       this.id= seuraavaNro;
       seuraavaNro ++;
       return this.id;
   }



    /**
     * Selvittää jäsenen tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param s rivi josta jäsenen tiedot otetaan
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
     * Ottaa jäsenen tiedot ResultSetistä
     * @param tulokset mistä tiedot otetaan
     * @throws SQLException jos jokin menee väärin
     */
     public void parse(ResultSet tulokset) throws SQLException {
               setId(tulokset.getInt("id"));
               sukunimi = tulokset.getString("sukunimi");
               etunimi = tulokset.getString("etunimi");
               katuosoite = tulokset.getString("katuosoite");
               postinumero = tulokset.getString("postinumero");
               kaupunki = tulokset.getString("kaupunki");
               puhelinNumero =tulokset.getString("puhelinNumero");
               ika = tulokset.getInt("ika");
           }


    /**
     * Palauttaa k:tta jasenen kenttaa vastaavan kysymyksen
     * @param k kuinka monennen kentan kysymys palautetaan (0-alkuinen)
     * @return k:netta kenttaa vastaava kysymys
     */
    public String getKysymys(int k) {
        return switch (k) {
            case 0 -> "id";
            case 1 -> "sukunimi";
            case 2 -> "etunimi";
            case 3 -> "katuosoite";
            case 4 -> "postinumero";
            case 5 -> "kaupunki";
            case 6 -> "puhelinnumero";
            case 7 -> "ika";
            default -> "AALIO";
        };
    }

    /*
        private int id;
        private String sukunimi;
        private String etunimi;
        private String katuosoite;
        private String postinumero;
        private String kaupunki;
        private String puhelinNumero;
        private int ika;
     */


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


    /**
     * Tulostetaan jäsenen tiedot
     */
   public String toString() {

       return  getId()          +"|"+
               getEtunimi()     +"|"+
               getSukunimi()    +"|"+
               getKatuosoite()  +"|"+
               getPostinumero() +"|"+
               getKaupunki()    +"|"+
               getPuhelin()     +"|"+
               getIka()         +"|";

   }


    /**
     * vertaa onko parametrina tuotu jasen sama kuin jasen johon verrataan.
     * @param jasen jota verrataan
     * @return true jos on sama, false jos ei.
     */
    @Override
    public boolean equals(Object jasen) {
        return this.toString().equals(jasen.toString());
    }


    /**
     * Testiohjelma jäsenelle.
     * @param args ei käytössä.
     */
    public static void main (String[] args) {

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