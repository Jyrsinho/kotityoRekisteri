package Siivoustiimi;

import fi.jyu.mit.ohj2.Mjonot;

import java.io.PrintStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    private LocalDate suoritusPvm;
    private int kotityoID;
    private int suorittajaID;

    private static int seuraavaSuoritusNro = 1;

    /**
     * Alustetaan suoritus oletusarvoille.
     */
    public Suoritus () {
    }

    /**
     * Alustetaan suoritus tietylle kotityölle.
     * @param kotityoID kotityön viitenumero
     */
    public Suoritus (int kotityoID){
        this.kotityoID= kotityoID;
    }

    /**
     * palauttaa suorituksen ID:n
     * @return palauttaa suorituksen ID:n
     */
    public int getSuoritusID() {
        return suoritusID;
    }

    /**
     * Palauttaa suorituksen omistavan kotityön ID:N
     * @return Palauttaa suorituksen omistavan kotityön ID:N
     */
    public int getKotityoID() {
        return kotityoID;
    }

    /**
     * Palauttaa suorituksen suoritusajan.
     * @return Palauttaa suorituksen suoritusajan.
     */
    public int getsuoritusAika() {
        return suoritusAika;
    }

    /**
     * Palauttaa suorittajan tekijän ID:n
     * @return Palauttaa suorittajan tekijän ID:n
     */
    public int getSuorittajaID() {
        return suorittajaID;
    }

    /**
     * Palauttaa viimeisimmän suorituksen päivämäärän merkkijonona.
     *
     * @return Palauttaa viimeisimmän suorituksen päivämäärän merkkijonona.
     */
    public LocalDate getViimeisinSuoritus() {
        return suoritusPvm;
    }


    /**
     * Asettaa suoritukselle parametrina annetun tekijän ID:n
     * @param suorittajaID suorituksen tehneen jäsenen ID
     */
    public void setSuorittajaID(int suorittajaID) {
        this.suorittajaID = suorittajaID;
    }


    /**
     * Asettaa suoritukselle parametrina annetun kotityön ID:n
     * @param kotityoID suoritusta koskevan kotityön ID.
     */
    public void setKotityoID (int kotityoID) {
        this.kotityoID = kotityoID;
    }


    /**
     * Asettaa suoritukselle suorituksen ajankohdan päivämäärän merkkijonoarvona
     * @param suoritusPvm suoritusta koskeva päivämäärä merkkijonona.
     */
    public void setTekoaika (LocalDate suoritusPvm) {
        this.suoritusPvm = suoritusPvm;
    }


    /**
     *
     * @param kesto
     */
    public void setKesto (int kesto) {
        this.suoritusAika = kesto;
    }


    /**
     * Antaa tietokannan luontilausekkeen suoritustaululle
     * @return suoritustaulun luontilauseke
     */
    public String annaLuontiLauseke() {
        return "CREATE TABLE Suoritukset (" +
                "suoritusID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "suoritusAika INTEGER, " +
                "suoritusPvm DATE, " +
                "kotityoID INTEGER, " +
                "suorittajaID INTEGER, " +
                "FOREIGN KEY (kotityoID) REFERENCES Kotityot(kotityoID), " +
                "FOREIGN KEY (suorittajaID) REFERENCES Jasenet(jasenId)" +
                ")";

    }

    /**
     * Antaa suorituksen lisayslausekkeen
     * @param con tietokantayhteys
     * @return suorituksen lisayslauseke
     * @throws SQLException Jos lausekkeen luonnnissa on ongelmia
    */
    public PreparedStatement annaLisayslauseke (Connection con) throws SQLException {
        PreparedStatement sql = con.prepareStatement(
                "INSERT INTO Suoritukset (suoritusID, suoritusAika, suoritusPvm," +
                    "kotityoID, suorittajaID) VALUES (?,?,?,?,?)");

        if (suoritusID != 0) sql.setInt(1, suoritusID);
        else sql.setString(1, null);
        sql.setInt(2, suoritusAika);
        sql.setDate(3, Date.valueOf(suoritusPvm));
        sql.setInt(4, kotityoID);
        sql.setInt(5, suorittajaID);

        return sql;

    }


    /**
     * Tarkistetaan onko id muuttunut lisayksessa
     * @param rs lisayslausekkeen ResultSet
     * @throws SQLException jos tulee jotakin vikaa
     */
    public void tarkistaId (ResultSet rs) throws SQLException {
        if (!rs.next()) return;
        int id = rs.getInt(1);
        if (id == suoritusID) return;
        setSuoritusID(id);
    }


    /**
     * Antaa suoritukselle seuraavan tunnusnumeron.
     * @return suorituksen uusi tunnusnumero
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
        suoritusPvm = LocalDate.of(2023,7,5);
        kotityoID = kotityontunnusnumero;
        suorittajaID = tekijanId;
    }

    /**
     * Selvittää suorituksen tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaSuoritusNro on suurempi kuin tuleva kotityoId.
     * @param s rivi josta suorituksen tiedot otetaan
     */
    public void parse(String s)  {
        StringBuilder sb = new StringBuilder(s);
        setSuoritusID(Mjonot.erota(sb, '|', getKotityoID()));
        this.suoritusAika = Mjonot.erota(sb, '|', getsuoritusAika());
        String dateStr = Mjonot.erota(sb, '|',"");
        this.suoritusPvm = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.kotityoID = Mjonot.erota(sb,'|', getKotityoID());
        this.suorittajaID = Mjonot.erota(sb, '|', getSuorittajaID());

    }


    /**
     * Otetaan tiedot ResultSetista
     * @param tulokset mistä tiedot otetaan
     * @throws SQLException Jos jokin menee vikaan
     */
    public void parse(ResultSet tulokset) throws SQLException {
        setSuoritusID(tulokset.getInt("suoritusID"));
        this.suoritusAika = tulokset.getInt("suoritusAika");
        this.suoritusPvm = tulokset.getDate("suoritusPvm").toLocalDate();
        this.kotityoID = tulokset.getInt("kotityoID");
        this.suorittajaID = tulokset.getInt("suorittajaID");
    }


    /**
     * Asettaa suoritusId:n ja samalla varmistaa että
     * seuraava Suoritusnumero on aina suurempi kuin tähän mennessä suurin.
     * @param nro asetettava suoritusID
     */
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


    public String toString() {
        return  getSuoritusID()             +"|"+
                getsuoritusAika()           +"|"+
                getViimeisinSuoritus() +"|"+
                getKotityoID()              +"|"+
                getSuorittajaID()           +"|";
    }


    /**
     * vertaa onko parametrina tuotu suoritus sama kuin suoritus johon verrataan.
     * @param suoritus jota verrataan
     * @return true jos on sama, false jos ei.
     */
    @Override
    public boolean equals(Object suoritus) {
        return this.toString().equals(suoritus.toString());
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



