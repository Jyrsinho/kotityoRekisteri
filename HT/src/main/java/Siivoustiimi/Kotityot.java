package Siivoustiimi;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import static Siivoustiimi.Kanta.alustaKanta;

/**
 * pitää yllä varsinaista kotityö-rekisteriä, eli osaa lisätä ja poistaa kotityön.
 * lukee ja kirjoittaa kotityöt tiedostoon
 * osaa etsiä ja lajitella
 * @author jyrihuhtala
 * version 12.7.2024
 */
public class Kotityot {

    private static Kotityo apuKotityo = new Kotityo();
    private Kanta kanta;


    /**
     * Tarkistetaan etta kannassa on kotitoiden tarvitsema taulu
     * @param nimi tietokannan nimi
     * @throws SailoException jos jokin menee pieleen
     */
    public Kotityot(String nimi) throws SailoException {
        kanta = alustaKanta(nimi);
        try ( Connection con = kanta.annaKantayhteys() ) {
            DatabaseMetaData meta = con.getMetaData();
            try ( ResultSet taulu = meta.getTables(null, null, "Kotityot", null) ) {
                if ( !taulu.next() ) {
                    // Luodaan kotityot taulu
                    try ( PreparedStatement sql = con.prepareStatement(apuKotityo.annaLuontilauseke()) ) {
                        sql.execute();
                    }
                }
            }

        } catch ( SQLException e ) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
    }

    /**
     * Lisää uuden kotityon siivoustiimille
     * @param kotityo lisattava kotityo
     * @throws SailoException jos tietokantayhteyden kanssa ongelmia
     */
    public void lisaa(Kotityo kotityo) throws SailoException {
        try ( Connection con = kanta.annaKantayhteys(); PreparedStatement sql = kotityo.annaLisayslauseke(con) ) {
            sql.executeUpdate();
            try ( ResultSet rs = sql.getGeneratedKeys() ) {
                kotityo.tarkistaId(rs);
            }
        } catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
    }

    /**
     * Paivittaa annetulle kotityolle uudet arvot
     * @param muokattava
     * @throws SailoException
     */
    public void paivita(Kotityo muokattava) throws SailoException {
        try (Connection con = kanta.annaKantayhteys();
            PreparedStatement sql = muokattava.annaPaivitysLauseke(con) )
        {
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Paivittaa kotityo taulukon arvoja
     * @param paivitettavanKotityonID paivitettavan kotityon ID
     * @param viimeisinSuoritus pvm, joka asetetaan paivitettavan kotityon viimeisimmaksi suoritukseksi.
     */
    public void paivita(int paivitettavanKotityonID, LocalDate viimeisinSuoritus) throws SailoException {
        // Luodaan apuKotityo DAO
        Kotityo DAOKotityo = new Kotityo();

        // Käsketään sitä yrittää luoda päivityslause, joka päivittää Kotityot taulukkoon paivitettavalle kotityolle
        // parametrina annetun päivämäärän viimeisimmäksi SUoritukseksi.
        try (Connection con = kanta.annaKantayhteys();
            PreparedStatement sql = DAOKotityo.annaPaivitysLauseke(con, paivitettavanKotityonID, viimeisinSuoritus) ) {
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa" + e.getMessage());
        }
    }


    /**
     * Poistaa kotityon siivoustiimista
     * @param kotityo poistettava kotityo
     * @throws SailoException jos tietokantayhteyden kanssa ongelmia
     */
    public void poistaKotityo(Kotityo kotityo) throws SailoException {
        try (Connection con = kanta.annaKantayhteys();
             PreparedStatement sql = kotityo.annaPoistolauseke(con) ) {
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa" + e.getMessage());
        }
    }



    /**
     * Haetaan kaikki jasenen kotityot
     * @param vastuuhenkilonID jasenen tunnusnumero jonka perusteella kotitoita haetaan
     * @return tietorakenne jossa viiteet loydettyihin kotitoihin
     * @throws SailoException jos kotitoiden hakeminen tietokannasta epaonnistuu
     */
    public ArrayList<Kotityo> annaKotityot(int vastuuhenkilonID) throws SailoException {
        ArrayList<Kotityo> loydetyt = new ArrayList<>();

        try ( Connection con = kanta.annaKantayhteys();
              PreparedStatement sql = con.prepareStatement("SELECT * FROM Kotityot WHERE vastuuHenkilonID = ?")
        ) {
            sql.setInt(1, vastuuhenkilonID);
            try ( ResultSet tulokset = sql.executeQuery() )  {
                while ( tulokset.next() ) {
                    Kotityo kottyo = new Kotityo();
                    kottyo.parse(tulokset);
                    loydetyt.add(kottyo);
                }
            }

        } catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
        return loydetyt;
    }


    /**
     * Palauttaa tietokannasta annettua kotityoIDtä vastaavan olion
     * @param kotityonID kotityoID
     * @return kotityoIDtä vastaava olio tietokannasta
     * @throws SailoException jos jotain menee pieleen
     * @throws SQLException jos jotain menee pieleen
     */
    public Kotityo annaKotityo(int kotityonID) throws SailoException, SQLException {
        Kotityo kotityo = new Kotityo();

        try (Connection con = kanta.annaKantayhteys();
            PreparedStatement sql = con.prepareStatement("SELECT * FROM Kotityot WHERE kotityoId = ?")
        ){
            sql.setInt(1, kotityonID);
            try ( ResultSet tulokset = sql.executeQuery() )  {
                while ( tulokset.next() ) {
                    kotityo.parse(tulokset);
                }
            }
        }catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan " + e.getMessage());
        }
        return kotityo;
    }


    //TODO TARVITAANKO TÄHÄN ERIKSEEN OMAA METODIA. VOISIKO YLEISEN ANNAKOTITYÖT METODIN MUOKATA TARVITTAESSA PALAUTTAMAAN KAIKKI TIIMIN KOTITYÖT.
    //TODO REFAKTOROI!!!
    /**
     * Haetaan kaikki  kotityot
     * @return tietorakenne jossa viiteet loydettyihin kotitoihin
     * @throws SailoException jos kotitoiden hakeminen tietokannasta epaonnistuu
     */
    public ArrayList<Kotityo> annaKaikkiKotityot() throws SailoException {
        ArrayList<Kotityo> loydetyt = new ArrayList<>();

        try ( Connection con = kanta.annaKantayhteys();
              PreparedStatement sql = con.prepareStatement("SELECT * FROM Kotityot")
        ) {
            try ( ResultSet tulokset = sql.executeQuery() )  {
                while ( tulokset.next() ) {
                    Kotityo kottyo = new Kotityo();
                    kottyo.parse(tulokset);
                    loydetyt.add(kottyo);
                }
            }

        } catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
        return loydetyt;
    }


    /**
     * Testiohjelma harrastuksille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        try {
            Kotityot kotityot = new Kotityot("kokeilu");
            Kotityo imuroiminen1 = new Kotityo();
            imuroiminen1.taytaKotityo(2);
            Kotityo imuroiminen2 = new Kotityo();
            imuroiminen2.taytaKotityo(1);
            Kotityo imuroiminen3 = new Kotityo();
            imuroiminen3.taytaKotityo(2);
            Kotityo imuroiminen4 = new Kotityo();
            imuroiminen4.taytaKotityo(1);

            kotityot.lisaa(imuroiminen1);
            kotityot.lisaa(imuroiminen2);
            kotityot.lisaa(imuroiminen3);
            kotityot.lisaa(imuroiminen4);

            System.out.println("============= Kotityot testi =================");

            List<Kotityo> kotityot2;

            kotityot2 = kotityot.annaKotityot(2);

            for (Kotityo kotityo : kotityot2) {
                System.out.print(kotityo.getVastuuhenkilonID() + " ");
                kotityo.tulosta(System.out);
            }

            new File("kokeilu.db").delete();
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}