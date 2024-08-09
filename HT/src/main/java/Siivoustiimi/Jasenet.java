package Siivoustiimi;


import static Siivoustiimi.Kanta.alustaKanta;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/**
 * pitää yllä varsinaista jäsenrekisteriä, eli osaa
 * lisätä ja poistaa jäsenen.
 * lukee ja kirjoittaa jäsenistön tiedostoon
 * osaa etsiä ja lajitella.
 * @author jyrihuhtala
 * version 12.7.2024
 */
public class Jasenet  {


    private Kanta kanta;
    private static Jasen apujasen = new Jasen();


    /**
     * Tarkistetaan että kannassa jäsenten tarvitsema taulu
     * @param nimi tietokannan nimi
     * @throws SailoException jos jokin menee pieleen
     */
    public Jasenet(String nimi) throws SailoException {
        kanta = alustaKanta(nimi);
        try ( Connection con = kanta.annaKantayhteys() ) {
            // Hankitaan tietokannan metadata ja tarkistetaan siitä onko
            // Jasenet nimistä taulua olemassa.
            // Jos ei ole, luodaan se. Ei puututa tässä siihen, onko
            // mahdollisesti olemassa olevalla taululla oikea rakenne,
            // käyttäjä saa kuulla siitä virheilmoituksen kautta
            DatabaseMetaData meta = con.getMetaData();

            try ( ResultSet taulu = meta.getTables(null, null, "Jasenet", null) ) {
                if ( !taulu.next() ) {
                    // Luodaan Jasenet taulu
                    try ( PreparedStatement sql = con.prepareStatement(apujasen.annaLuontilauseke()) ) {
                        sql.execute();
                    }
                }
            }

        } catch ( SQLException e ) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
    }


    /**
     * Lisaa uuden jasenen tietorakenteeseen.  Ottaa jasenen omistukseensa.
     * @param jasen lisattavan jasenen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo taynna
     */
    public void lisaa(Jasen jasen) throws SailoException {
        try ( Connection con = kanta.annaKantayhteys(); PreparedStatement sql = jasen.annaLisayslauseke(con) ) {
            sql.executeUpdate();
            try ( ResultSet rs = sql.getGeneratedKeys() ) {
                jasen.tarkistaId(rs);
            }

        } catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
    }

    /**
     * Paivittaa jsenen tiedot
     * @param jasen, jonka tiedot paivitettaan
     * @throws SailoException
     */
    public void paivita(Jasen jasen) throws SailoException {
        try (Connection con = kanta.annaKantayhteys();
        PreparedStatement sql = jasen.annaPaivitysLauseke(con) ) {
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
    }


    /**
     * Poistaa jasenen tietorakenteesta.
     * @param jasen Poistettava jasen
     * @throws SailoException jos jotain menee pieleen.
     */
    public void poistaJasen(Jasen jasen) throws SailoException {
        try (Connection con = kanta.annaKantayhteys(); PreparedStatement sql = jasen.annaPoistoLauseke(con) ) {
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa" + e.getMessage());
        }
    }


    /**
     * Palauttaa jasenet listassa
     * @param hakuehto hakuehto
     * @param k etsittavan kentan indeksi
     * @return jasenet listassa
     * @throws SailoException jos tietokannan kanssa ongelmia
     */
    public Collection<Jasen> etsi(String hakuehto, int k) throws SailoException {
        String ehto = hakuehto;
        String kysymys = apujasen.getKysymys(k);
        if ( k < 0 ) { kysymys = apujasen.getKysymys(0); ehto = ""; }
        // Avataan yhteys tietokantaan try .. with lohkossa.
        try ( Connection con = kanta.annaKantayhteys();
              PreparedStatement sql = con.prepareStatement("SELECT * FROM Jasenet WHERE " + kysymys + " LIKE ?") ) {
            ArrayList<Jasen> loytyneet = new ArrayList<>();

            sql.setString(1, "%" + ehto + "%");
            try ( ResultSet tulokset = sql.executeQuery() ) {
                while ( tulokset.next() ) {
                    Jasen j = new Jasen();
                    j.parse(tulokset);
                    loytyneet.add(j);
                }
            }
            return loytyneet;
        } catch ( SQLException e ) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
    }



    /**
     * Testiohjelma jasenistolle
     * @param args ei kaytossa
     */
    public static void main(String[] args)  {
        try {
            new File("kokeilu.db").delete();
            Jasenet jasenet = new Jasenet("kokeilu");
            Jasen aku = new Jasen(), aku2 = new Jasen();
            aku.taytaJasen();
            //aku2.rekisteroi();
            aku2.taytaJasen();
            jasenet.lisaa(aku);
            jasenet.lisaa(aku2);
            aku2.tulosta(System.out);
            System.out.println("============= J�senet testi =================");
            int i = 0;
            for (Jasen jasen:jasenet.etsi("", -1)) {
                System.out.println("J�sen nro: " + i++);
                jasen.tulosta(System.out);
            }
            new File("kokeilu.db").delete();
        } catch ( SailoException ex ) {
            System.out.println(ex.getMessage());
        }
        }

    }


