package Siivoustiimi;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * huolehtii Jäsenet ja Kotityöt - luokkien välisestä yhteistyöstä ja välittää näitä tietoja pyydettäessä.
 * lukee ja kirjoittaa kerhon tiedostoon pyytämällä apua avustajiltaan.
 * @author jyrihuhtala
 * @version 21.2.2024
 */
public class Siivoustiimi {

    private Jasenet jasenet;
    private Kotityot kotityot;
    private Suoritukset suoritukset;



    /**
     * Poistaa jasenistosta ja kotitoista valitun jasenen ja hanen kotityonsa.
     * @param jasen joka mukaan poistetaan
     * @return montako j�sent� poistettiin
     */
    public int poista (Jasen jasen) throws SailoException {
        jasenet.poistaJasen(jasen);
        int poistettavienMaara = 0;
        ArrayList<Kotityo> poistettavanJasenenKotityot = annaKotityot(jasen);

        for (Kotityo kotityo: poistettavanJasenenKotityot) {
            kotityot.poistaKotityo(kotityo);
            poistettavienMaara++;
        }
            return poistettavienMaara;
    }

    /**
     * Antaa Kotityot-luokalle tehtavaksi poistaa kotityo tietokannasta.
     * @param kotityo, joka poistetaan
     * @throws SailoException jos jotain menee pieleen
     */
    public void poistaKotityo(Kotityo kotityo) throws SailoException {
        kotityot.poistaKotityo(kotityo);
    }


    /**
     * Lisaa Siivoustiimiin uuden jasenen
     * @param jasen lisattava jasen
     * @throws SailoException jos lisaysta ei voida tehda
     */
    public void lisaa(Jasen jasen) throws SailoException {
        jasenet.lisaa(jasen);
    }

    /**
     * Lisaa uuden kotityon tietorakenteeseen.
     * @param kotityo lisattava kotityo
     */
    public void lisaa (Kotityo kotityo) throws SailoException {
        kotityot.lisaa(kotityo);
    }


    /**
     * Lisaa uuden suorituksen tietorakenteeseen.
     * @param suoritus lisattava suoritus
     */
    public void lisaa (Suoritus suoritus) throws SailoException {suoritukset.lisaa(suoritus);}


    /**
     * Palauttaa jasenet listassa
     * @param hakuehto hakuehto
     * @param k etsittavan kentan indeksi
     * @return jasenet listassa
     * @throws SailoException jos tietokannan kanssa ongelmia
     * @example
    */
    public Collection<Jasen> etsiJasenet(String hakuehto, int k) throws SailoException {
        return jasenet.etsi(hakuehto,k);
    }



    /**
     * Palauttaa tiettyä jäsenIdtä vastaavan jäsenen kaikki kotityöt.
     * @param jasen jjäsen, jonka kotitöitä haetaan.
     * @return tiettyä jäsenIdtä vastaavan jäsenen kaikki kotityöt.
     */
    public ArrayList<Kotityo> annaKotityot(Jasen jasen) throws SailoException {
        return kotityot.annaKotityot(jasen.getId());
    }

    /**
     * Palauttaa siivoustiimin kaikki kotityot
     * @return siivoustiimin kaikki kotityot
     * @throws SailoException jos jotain menee pieleen
     */
    public ArrayList<Kotityo> annaKaikkiKotityot() throws SailoException {
        return kotityot.annaKaikkiKotityot();
    }


    /**
     * Palauttaa tiettyä kotityöIDtä vastaavan kotityön kaikki suoritukset.
     * @param kotityoID kotityö, jonka suorituksia haetaan.
     * @return tiettyä kotityöIDtä vastaavan kotityön kaikki suoritukset.
     */
    public ArrayList<Suoritus> annaSuoritukset(int kotityoID) throws SailoException {

        return suoritukset.annaSuoritukset(kotityoID);
    }


    /**
     * Luo tietokannan. Jos annettu tiedosto on jo olemassa ja
     * sisaltaa tarvitut taulut, ei luoda mitaan
     * @param nimi tietokannan nimi
     * @throws SailoException jos tietokannan luominen ep�onnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException, SQLException {
        jasenet = new Jasenet(nimi);
        kotityot = new Kotityot(nimi);
        suoritukset = new Suoritukset(nimi);
    }

    /**
     * Paivittaa annetun kotityon viimeisimmaksi suoritukseksi tietokannasta loydetun
     * suorituksen, joka on kronologisesti uusin.
     */
    public void paivitaKotityonViimeisinSuoritus(int muutettavanKotityonID) throws SailoException, SQLException {
        // Hae kaikki paivitettavan kotityon suoritukset
        annaSuoritukset(muutettavanKotityonID);

        // Tarkista mikä niistä on ajallisesti uusin.

        // Aseta se paivitettavan kotityon viimeisimmaksi suoritukseksi.

        // Kotityo-luokkaan pitaa tehda SQL lauseen antava annaPaivitysLause() metodi.
    }



    /**
     * testiohjelma
     * @param args ei käytössä
     */
    public static void main(String[] args) throws SailoException {

        try {
            new File("testi.db").delete();
            Siivoustiimi siivoustiimi = new Siivoustiimi();
            siivoustiimi.lueTiedostosta("testi");

            Jasen aku1 = new Jasen(), aku2 = new Jasen();
            aku1.taytaJasen();
            aku2.taytaJasen();

            siivoustiimi.lisaa(aku1);
            siivoustiimi.lisaa(aku2);
            int id1 = aku1.getId();
            int id2 = aku2.getId();
            Kotityo imurointi11 = new Kotityo(id1); imurointi11.taytaKotityo(id1);  siivoustiimi.lisaa(imurointi11);
            Kotityo imurointi12 = new Kotityo(id1); imurointi12.taytaKotityo(id1);  siivoustiimi.lisaa(imurointi12);
            Kotityo imurointi21 = new Kotityo(id2); imurointi21.taytaKotityo(id2);  siivoustiimi.lisaa(imurointi21);
            Kotityo imurointi22 = new Kotityo(id2); imurointi22.taytaKotityo(id2);  siivoustiimi.lisaa(imurointi22);
            Kotityo imurointi23 = new Kotityo(id2); imurointi23.taytaKotityo(id2);  siivoustiimi.lisaa(imurointi23);

            Suoritus imurointi3 = new Suoritus();
            imurointi3.taytaSuoritus(2,1);
            siivoustiimi.lisaa(imurointi3);

            System.out.println("============= Kerhon testi =================");

            Collection<Jasen> jasenet = siivoustiimi.etsiJasenet("", -1);
            int i = 0;
            for (Jasen jasen : jasenet) {
                System.out.println("Jasen paikassa: " + i);
                jasen.tulosta(System.out);
                List<Kotityo> loytyneet = siivoustiimi.annaKotityot(jasen);
                for (Kotityo kotityo : loytyneet)
                    kotityo.tulosta(System.out);
                i++;
            }

        } catch (SailoException | SQLException ex ) {
            System.out.println(ex.getMessage());
        }

        new File("kokeilu.db").delete();
    }

}