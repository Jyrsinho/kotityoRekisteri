package Siivoustiimi;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import static Siivoustiimi.Kanta.alustaKanta;

/**
 * Siivoustiimin suoritukset, joka osaa mm. lisata uuden suorituksen
 * @author jyrihuhtala
 * @version 16.7.2024
 */

public class Suoritukset  {

    private static Suoritus apuSuoritus = new Suoritus();
    private Kanta kanta;


    /**
     * Tarkistetaan, että tietokannassa on suoritusten tarvitsema taulu
     * @param nimi tietokannan nimi
     * @throws SailoException jos jokin menee pieleen
     */
    public Suoritukset(String nimi) throws SailoException {
        kanta = alustaKanta(nimi);
        try (Connection con = kanta.annaKantayhteys() ) {
            DatabaseMetaData meta = con.getMetaData();
            try (ResultSet taulu = meta.getTables(null, null, "Suoritukset", null)) {
                if (!taulu.next()) {
                    try (PreparedStatement sql = con.prepareStatement(apuSuoritus.annaLuontiLauseke())) {
                        sql.execute();
                    }
                }
            }
        } catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
    }


    /**
     * Lisataan uusi suoritus siivoustiimiin
     * @param suoritus lisattava suoritus
     * @throws SailoException jos tietokantayhteyden kanssa ongelmia
     */
    public void lisaa(Suoritus suoritus) throws SailoException {
        try (Connection con = kanta.annaKantayhteys();
             PreparedStatement sql = suoritus.annaLisayslauseke(con)) {
            sql.executeUpdate();
            try (ResultSet rs = sql.getGeneratedKeys()){
                suoritus.tarkistaId(rs);
            }
        }catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
    }


    /**
     * Haetaan kaikki kotityon suoritukset
     * @return Arraylist tietorakenne, jossa viitteet löydettyihin suorituksiin
     * @throws SailoException jos suoritusten hakeminen tietokannasta epaonnistuu
     */
    public ArrayList<Suoritus> annaSuoritukset (int kotityoID) throws SailoException {
       ArrayList<Suoritus> loydetyt = new ArrayList<>();

       try (Connection con = kanta.annaKantayhteys();
            PreparedStatement sql = con.prepareStatement
                    ("SELECT * FROM Suoritukset WHERE kotityoID = ?")
            ) {
           sql.setInt(1, kotityoID);
           try (ResultSet tulokset = sql.executeQuery() ) {
               while (tulokset.next()) {
                   Suoritus suoritus = new Suoritus();
                   suoritus.parse(tulokset);
                   loydetyt.add(suoritus);
               }
           }

       } catch (SQLException e) {
           throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
       }
       return loydetyt;
    }


    /**
     * Palauttaa kaikki suoritukset
     * @return Arraylist tietorakenne, jossa viitteet kaikkiin suorituksiin.
     * @throws SailoException jos suoritusten hakeminen tietokannasta epaonnistuu
     * @throws SQLException jos suoritusten hakeminen tietokannasta epaonnistuu
     */
    public ArrayList<Suoritus> annaSuoritukset() throws SailoException, SQLException {
        ArrayList<Suoritus> loydetyt = new ArrayList<>();

        try (Connection con = kanta.annaKantayhteys();
            PreparedStatement sql  = con.prepareStatement("SELECT * FROM Suoritukset")
        ) {
            try (ResultSet tulokset = sql.executeQuery() ) {
                while (tulokset.next()) {
                    Suoritus suoritus = new Suoritus();
                    suoritus.parse(tulokset);
                    loydetyt.add(suoritus);
                }
            }
        } catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
            return loydetyt;
    }



    /**
     * Testiohjelma suorituksille
     * @param args ei kaytossa
     */
    public static void main(String[] args)  {
        try {
            Suoritukset suoritukset = new Suoritukset("kokeilu");
            Suoritus testiSuoritus1 = new Suoritus();
            testiSuoritus1.taytaSuoritus(1,2);
            Suoritus testiSuoritus2 = new Suoritus();
            testiSuoritus2.taytaSuoritus(1,2);
            Suoritus testiSuoritus3 = new Suoritus();
            testiSuoritus3.taytaSuoritus(1,2);
            Suoritus testiSuoritus4 = new Suoritus();
            testiSuoritus4.taytaSuoritus(1,2);

            suoritukset.lisaa(testiSuoritus1);
            suoritukset.lisaa(testiSuoritus2);
            suoritukset.lisaa(testiSuoritus3);
            suoritukset.lisaa(testiSuoritus4);

            System.out.println("============= Suoritukset testi =================");

            ArrayList<Suoritus> suoritukset2;

            suoritukset2 = suoritukset.annaSuoritukset(2);

            for (Suoritus suoritus : suoritukset2) {
                System.out.print(suoritus.getKotityoID() + " ");
                suoritus.tulosta(System.out);
            }

            new File("kokeilu.db").delete();
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
