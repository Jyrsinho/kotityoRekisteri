package Siivoustiimi;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.DateFormatterProvider;
import kanta.RandomIka;

import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.time.LocalDate;

/**
 * Siivoustiimin kotityo.
 * Tietää kotityon kentät.
 * Osaa tarkistaa tietyn kentän oikeellisuuden.
 * Osaa muuttaa 1|Imurointi|3| - merkkijonon kotityon tiedoiksi.
 * Osaa antaa merkkijonona i:n kentän tiedot.
 * Osaa laittaa merkkijonon i:neksi kentäksi.
 * @author jyrihuhtala
 * @version 1.0 20.02.2024
 */
public class Kotityo implements Cloneable, DateFormatterProvider {

    private int kotityoId;
    private String kotityoNimi;
    private int vanhenemisaika;
    private int kesto;
    private LocalDate viimeisinSuoritus = LocalDate.now();
    private int vastuuhenkilonID;


    /**
     * Alustetaan kotityo oletusarvoille
     */
    public Kotityo() {

    }

    /**
     * Alustetaan kotityo tietylle jasenelle.
     * @param vastuuhenkilonId jasenen viitenumero
     */
    public Kotityo (int vastuuhenkilonId) {
        this.vastuuhenkilonID = vastuuhenkilonId;
    }


    /**
     * @return jäsenen nimi
     */
    public String getKotityoNimi() {
        return kotityoNimi;
    }

    /**
     *
     * @return kotityon ID
     */
    public int getKotityoID() {
        return kotityoId;
    }

    /**
     * @return kotityön vastuuhenkilön ID
     */
    public int getVastuuhenkilonID() {
        return vastuuhenkilonID;
    }


    /**
     * @return kotityön arvioitu kestoaika
     */
    public int getKesto() {return kesto;}

    /**
     * @return kotityön viimeisimmän suorituksen päivämäärä
     */
    public LocalDate getViimeisinSuoritus() {return viimeisinSuoritus;}


    /**
     * @return kotityön vanhenemisaika
     */
    public int getVanhenemisaika() {return vanhenemisaika;}


    public String setKotityonNimi(String uusiNimi) {
        if (uusiNimi.isEmpty()) return "Nimi ei voi olla tyhjä";
        this.kotityoNimi = uusiNimi;
        return null;
    }


    public void setVanhenemisaika(int uusiVanhenemisAika) {
       this.vanhenemisaika = uusiVanhenemisAika;
    }


    public void setKesto (int uusiKesto) {
        this.kesto = uusiKesto;
    }


    public void setVastuuhenkilonID(int uusiID) {
        this.vastuuhenkilonID = uusiID;
    }


    public void setViimeisinSuoritus (LocalDate viimeisinSuoritus) {
        this.viimeisinSuoritus = viimeisinSuoritus;
    }


    /**
     * Asettaa kotityöId:n
     * @param nr asetettava kotityöId
     */
    private void setKotityoID(int nr) {
        this.kotityoId = nr;
    }


    public Kotityo clone() throws CloneNotSupportedException {
        Kotityo uusi;
        uusi = (Kotityo) super.clone();
        return uusi;
    }


    /**
     * formatoi viimeisimman suorituksen LocalDate esityksen merkkijonoksi
     * @return kotityon viimeisimman suorituksen pvm merkkijonona
     */
    public String getViimeisinSuoritusString() {
        return formatDate(viimeisinSuoritus);
    }

    /**
     * asettaa viimeisimman suorituksen arvoksi merkkijonona annetusta paivamaarasta LocalDate muuttujan
     * @param dateString viimeisimman suorituksen paivamaara merkkijonona.
     */
    public void setViimeisinSuoritusFromString(String dateString) {
        this.viimeisinSuoritus = parseDate(dateString);
    }



    /**
     * Tarkistaa onko kotityön edellinen suoritus vanhentunut
     * @return true jos suoritus on vanhentunut, false jos ei ole vanhentunut.
     */
    public boolean suoritusOnVanhentunut() {

        int vertailu = (viimeisinSuoritus.plusDays(vanhenemisaika-1).compareTo(LocalDate.now()));

        return vertailu < 0 ;
    }


    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot kotityolle.
     * kesto ja vanhenemisaika arvotaan, jotta kahdella jäsenellä ei olisi samoja tietoja.
     * @param vastuuHenkilonID Kotityön vastuuhenkilön ID
     */
    public void taytaKotityo(int vastuuHenkilonID) {

        this.kotityoNimi = "Imurointi";
        this.vastuuhenkilonID = vastuuHenkilonID;
        this.viimeisinSuoritus = LocalDate.of(2024, 4, 1);
        this.kesto = RandomIka.arvoIka( 0, 60);
        this.vanhenemisaika = RandomIka.arvoIka(1,30);
    }


    /**
     * Tulostetaan kotityon tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.printf("%03d%n", kotityoId);
        out.println(kotityoNimi);
        out.println(kesto);
        out.println(vanhenemisaika);
        out.println(viimeisinSuoritus);
        out.println(vastuuhenkilonID);
    }




    /**
     * Selvittää kotityön tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaKotityoNro on suurempi kuin tuleva kotityoId.
     * @param s rivi josta kotityön tiedot otetaan
     */
    public void parse(String s)  {
        StringBuilder sb = new StringBuilder(s);
        setKotityoID(Mjonot.erota(sb, '|', getKotityoID()));

        this.kotityoNimi = Mjonot.erota(sb, '|', getKotityoNimi());
        this.vanhenemisaika = Mjonot.erota(sb, '|', getVanhenemisaika());
        this.kesto = Mjonot.erota(sb,'|', getKesto());
        String dateStr = Mjonot.erota(sb, '|',getViimeisinSuoritusString());
        this.viimeisinSuoritus = parseDate(dateStr);
        this.vastuuhenkilonID = Mjonot.erota(sb, '|', getVastuuhenkilonID());

    }


    /**
     * Otetaan tiedot ResultSetistä
     * @param tulokset mistä tiedot otetaan
     * @throws SQLException Jos jokin menee vikaan
     */
    public void parse(ResultSet tulokset) throws SQLException {
        setKotityoID(tulokset.getInt("kotityoId"));
        this.kotityoNimi = tulokset.getString("kotityoNimi");
        this.vanhenemisaika = tulokset.getInt("vanhenemisaika");
        this.kesto = tulokset.getInt("kesto");
        String dateStr = tulokset.getString("viimeisinSuoritus");
        this.viimeisinSuoritus = parseDate(dateStr);
        this.vastuuhenkilonID = tulokset.getInt("vastuuhenkilonId");

    }


    /**
     * Tulostetaan jäsenen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * * Antaa tietokannan luontilausekkeen kotityotaululle
     * @return kotityotaulun luontilauseke
     */
    public String annaLuontilauseke()  {
            return "CREATE TABLE Kotityot (" +
                                "kotityoId INTEGER PRIMARY KEY AUTOINCREMENT , " +
                                "kotityoNimi VARCHAR(100) NOT NULL, " +
                                "vanhenemisaika INTEGER, " +
                                "kesto INTEGER, " +
                                "viimeisinSuoritus DATE, " +
                                "vastuuHenkilonId INTEGER, " +
                                "FOREIGN KEY (vastuuHenkilonId) REFERENCES Jasenet(id)" +
                                ")";
    }


    /**
     * Antaa kotityon lisäyslausekkeen
     * @param con tietokantayhteys
     * @return kotityon lisäyslauseke
     * @throws SQLException Jos lausekkeen luonnissa on ongelmia
     */
    public PreparedStatement annaLisayslauseke(Connection con) throws SQLException {

        PreparedStatement sql = con.prepareStatement(
                "INSERT INTO Kotityot (kotityoId, kotityoNimi, vanhenemisaika, " +
                        "kesto, viimeisinSuoritus, vastuuhenkilonID) VALUES (?, ?, ?, ?, ?, ?)");
        // Syötetään kentät näin välttääksemme SQL injektiot.
        // Käyttäjän syötteitä ei ikinä vain kirjoiteta kysely
        // merkkijonoon tarkistamatta niitä SQL injektioiden varalta!
        if ( kotityoId != 0 ) sql.setInt(1, kotityoId); else sql.setString(1, null);
        sql.setString(2, kotityoNimi);
        sql.setInt(3, vanhenemisaika);
        sql.setInt(4, kesto);
        String dateString = formatDate(viimeisinSuoritus);
        sql.setString(5, dateString);
        sql.setInt(6, vastuuhenkilonID);
        return sql;
    }


    /**
     * Antaa kotityon paivityslausekkeen tapauksissa, joissa kotityolle on asetettu uusi viimeisin suoritus
      * @param con tietokantayhteys
     * @return kotityon paivityslauseke
     * @throws SQLException jos lausekkeen luonnissa on ongelmia
     */
    public PreparedStatement annaPaivitysLauseke(Connection con, int paivitettavanKotityonID, LocalDate uusiViimeisinSuoritus) throws SQLException {

        PreparedStatement sql = con.prepareStatement(
                "UPDATE Kotityot SET viimeisinSuoritus = ? WHERE kotityoID = ? ");
        sql.setString(1, uusiViimeisinSuoritus.toString());
        sql.setInt(2, paivitettavanKotityonID);

        return sql;
    }


    public PreparedStatement annaPaivitysLauseke(Connection con) throws SQLException {
        PreparedStatement sql = con.prepareStatement(
                "UPDATE Kotityot " +
                        "SET kotityoNimi = ?," +
                        "vanhenemisaika = ?," +
                        "kesto = ?," +
                        "viimeisinSuoritus = ?," +
                        "vastuuHenkilonId = ?"+
                "WHERE kotityoID = ?"
        );
        sql.setString(1, this.kotityoNimi);
        sql.setInt(2, this.vanhenemisaika);
        sql.setInt(3, this.kesto);
        sql.setString(4, String.valueOf(this.viimeisinSuoritus));
        sql.setInt(5, this.vastuuhenkilonID);
        sql.setInt(6, this.kotityoId);
        return sql;
    }

    /*
      private int kotityoId;
    private String kotityoNimi;
    private int vanhenemisaika;
    private int kesto;
    private LocalDate viimeisinSuoritus = LocalDate.now();
    private int vastuuhenkilonID;
     */

    /**
     * Antaa kotityon poistolausekkeen
      * @param con tietokantayhteys
     * @return kotityon poistolauseke
     * @throws SQLException jos lausekkeen luonnissa on ongelmia
     */
    public PreparedStatement annaPoistolauseke(Connection con) throws SQLException {
        PreparedStatement sql = con.prepareStatement(
                "DELETE FROM Kotityot WHERE kotityoId = ?");
        sql.setInt(1, this.kotityoId);

        return sql;
    }


    /**
     * Tarkistetaan onko id muuttunut lisäyksessä
     * @param rs lisäyslauseen ResultSet
     * @throws SQLException jos tulee jotakin vikaa
     */
    public void tarkistaId(ResultSet rs) throws SQLException {
        if ( !rs.next() ) return;
        int id = rs.getInt(1);
        if ( id == kotityoId ) return;
        setKotityoID(id);
    }


    @Override
    public String toString() {
        return  getKotityoID()          +"|"+
                getKotityoNimi()        +"|"+
                getVanhenemisaika()     +"|"+
                getKesto()              +"|"+
                getViimeisinSuoritus()  +"|"+
                getVastuuhenkilonID()   +"|";
    }

    /**
     * vertaa onko parametrina tuotu kotityo sama kuin kotityo johon verrataan.
     * @param kotityo jota verrataan
     * @return true jos on sama, false jos ei.
     */
    @Override
    public boolean equals(Object kotityo) {
        return this.toString().equals(kotityo.toString());
    }


    /**
     * Testiohjelma jäsenelle.
     * @param args ei käytössä.
     */
    public static void main (String[] args) {

        Kotityo imurointi = new Kotityo();
        Kotityo imurointi2 = new Kotityo();

        imurointi.tulosta(System.out);
        System.out.println();

        imurointi2.tulosta(System.out);
        System.out.println();

        imurointi.taytaKotityo(1);
        imurointi.tulosta(System.out);
        System.out.println();

        imurointi2.taytaKotityo(1);
        imurointi2.tulosta(System.out);

        System.out.println();

        System.out.println(imurointi.suoritusOnVanhentunut());
        System.out.println(imurointi2.suoritusOnVanhentunut());

        System.out.println(LocalDate.now());

    }

}
