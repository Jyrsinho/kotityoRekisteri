package Siivoustiimi;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.DateFormatterProvider;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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
public class Suoritus implements DateFormatterProvider {

    private int suoritusID;
    private int suoritusAika;
    private LocalDate suoritusPvm;
    private int kotityoID;
    private int suorittajaID;



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
     * Asettaa suoritukselle kestoajann
     * @param kesto kauanko suoritus kesti ajallisesti
     */
    public void setKesto (int kesto) {
        this.suoritusAika = kesto;
    }


    /**
     * formatoi Suorituspaivamaaran LocalDate esityksen merkkijonoksi
     * @return suorituspaivamaara merkkijonona
     */
    public String getSuoritusPvmString() {
        return formatDate(suoritusPvm);
    }


    /**
     * formatoi Suorituspaivamaaran merkkijono esityksen Local Date arvoksi
     * @param dateString suorituspaivamaara merkkijonona
     */
    public void setSuoritusPvmFromString(String dateString) {
        this.suoritusPvm = parseDate(dateString);
    }


    /**
     * Asettaa suoritusId:n
     * @param nro asetettava suoritusID
     */
    private void setSuoritusID(int nro) {
        this.suoritusID = nro;
    }


    /**
     * Antaa tietokannan luontilausekkeen suoritustaululle
     * @return suoritustaulun luontilauseke
     */
    public String annaLuontiLauseke() {
        return "CREATE TABLE Suoritukset (" +
                "suoritusID INTEGER NOT NULL PRIMARY KEY , " +
                "suoritusAika INTEGER, " +
                "suoritusPvm VARCHAR(100), " +
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
                "INSERT INTO Suoritukset (suoritusAika, suoritusPvm," +
                    "kotityoID, suorittajaID) VALUES (?,?,?,?)");


        sql.setInt(1, suoritusAika);
        String dateString = formatDate(suoritusPvm);
        sql.setString(2, dateString);
        sql.setInt(3, kotityoID);
        sql.setInt(4, suorittajaID);

        return sql;
    }

    /**
     * Antaa suorituksen poistolausekkeen
     * @param con tietokantayhteys
     * @return suorituksen poistolauseke
     * @throws SQLException jos lausekkeen luomisessa on ongelmia
     */
    public PreparedStatement annapoistoLauseke (Connection con) throws SQLException {
        PreparedStatement sql = con.prepareStatement(
                "DELETE from Suoritukset WHERE suoritusID = ?");
        sql.setInt(1, this.suoritusID);

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
        this.suoritusPvm = parseDate(dateStr);
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
        String dateStr = tulokset.getString("suoritusPvm");
        this.suoritusPvm = parseDate(dateStr);
        this.kotityoID = tulokset.getInt("kotityoID");
        this.suorittajaID = tulokset.getInt("suorittajaID");
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


    }

}



