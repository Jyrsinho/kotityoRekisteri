package Siivoustiimi;


import static Siivoustiimi.Kanta.alustaKanta;

import java.io.*;
import java.sql.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * pitää yllä varsinaista jäsenrekisteriä, eli osaa
 * lisätä ja poistaa jäsenen.
 * lukee ja kirjoittaa jäsenistön tiedostoon
 * osaa etsiä ja lajitella.
 * @author jyrihuhtala
 * version 9.6.2024
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
     *
     * @param args ei köytössä
     */
    public static void main (String []args) {
    }

}
