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
public class Jasenet implements Iterable<Jasen> {


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
     * Iteraattori kaikkien jasenten lapikaymiseen
     * @return jaseniteraattori
     *
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     *
     *  Jasenet jasenet = new Jasenet();
     *  Jasen timo1 = new Jasen(); jasenet.lisaa(timo1);
     *  Jasen timo2 = new Jasen(); jasenet.lisaa(timo2);
     *  Jasen timo3 = new Jasen(); jasenet.lisaa(timo3);
     *  Jasen timo4 = new Jasen(); jasenet.lisaa(timo4);
     *  Jasen timo5 = new Jasen(); jasenet.lisaa(timo5);
     *
     *  Iterator<Jasen> i2=jasenet.iterator();
     *  i2.next() === timo1;
     *  i2.next() === timo2;
     *  i2.next() === timo3;
     *  i2.next() === timo4;
     *  i2.next() === timo5;
     * </pre>
     */
    public class JasenetIterator implements Iterator<Jasen> {
        private int kohdalla = 0;


        /**
         * Onko olemassa vielä seuraavaa jäsentä
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä jäseniä
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava jäsen
         * @return seuraava jäsen
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Jasen next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }


    /**
     * Palautetaan iteraattori jäsenistään.
     * @return jäsen iteraattori
     */
    @Override
    public Iterator<Jasen> iterator() {
        return new JasenetIterator();
    }


    /**
     *
     * @param args ei köytössä
     */
    public static void main (String []args) {
    }

}
