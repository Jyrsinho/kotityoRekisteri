package Siivoustiimi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * huolehtii Jäsenet ja Kotityöt - luokkien välisestä yhteistyöstä ja välittää näitä tietoja pyydettäessä.
 * lukee ja kirjoittaa kerhon tiedostoon pyytämällä apua avustajiltaan.
 * @author jyrihuhtala
 * @version 21.2.2024
 */
public class Siivoustiimi {

    Jasenet jasenet = new Jasenet();
    Kotityot kotityot = new Kotityot();
    Suoritukset suoritukset = new Suoritukset();



    /**
     * palauttaa siivoustiimin jäsenmäärän
     * @return jäsenmäärä
     */
    public int getJasenia() {
        return jasenet.getLkm();
    }


    /**
     * Palauttaa listan siivoustiimin jäsenistä.
     * @return Palauttaa listan siivoustiimin jäsenistä.
     */
    public Jasen[] getJasenet() {return jasenet.getJasenet();}


    /**
     * Palauttaa listan siivoustiimin kotitöistä
     * @return Arraylist, joka sisältää siivoustiimin kotityöt
     */
    public Collection<Kotityo> getKotityot() {
       return kotityot.getKotityot();
    }



    /**
     * Palauttaa halutun indeksin paikalla taulukossa olevan jäsenen tiedot.
     * @param i taulukon indeksi, josta haluttua jäsentä etsitään
     * @return Palauttaa halutun indeksin paikalla taulukossa olevan jäsenen tiedot.
     * @throws IndexOutOfBoundsException jos indeksi on liian iso.
     */
    public Jasen annaJasen(int i) throws IndexOutOfBoundsException {
        return jasenet.anna(i);
    }


    /**
     * Palauttaa jäsenIDtä vastaavan jäsenen.
     * @param jasenID ID, jonka perusteella jäsentä etsitään.
     * @return jäsenIDtä vastaavan jäsenen.
     */
    public Jasen annaJasenIDPerusteella (int jasenID){
        return jasenet.annaJasenIDPerusteella(jasenID);

    }


    /**
     * Palauttaa tiettyä jäsenIdtä vastaavan jäsenen kaikki kotityöt.
     * @param jasenId jäsen, jonka kotitöitä haetaan.
     * @return tiettyä jäsenIdtä vastaavan jäsenen kaikki kotityöt.
     */
    public ArrayList<Kotityo> annaKotityot(int jasenId) {
        return kotityot.annaKotityot(jasenId);
    }


    /**
     * Palauttaa tiettyä kotityöIDtä vastaavan kotityön kaikki suoritukset.
     * @param kotityoID kotityö, jonka suorituksia haetaan.
     * @return tiettyä kotityöIDtä vastaavan kotityön kaikki suoritukset.
     */
    public ArrayList<Suoritus> annaSuoritukset(int kotityoID) {

        return suoritukset.annaSuoritukset(kotityoID);
    }


    /**
     * Etsii annettua merkkijonoa vastaavan jäsenen ID:n
     */
    public int etsiJasenenID (String jasenenNimi) {
        return jasenet.annaJasenenId(jasenenNimi);
    }


    /**
     * Etsii kaikki annetun merkkijonon sisältävät kotityöt.
     * @param text merkkijono, jonka perusteella kotitöitä etsitään.
     * @return kaikki kotityöt, jotka sisältävät annetun merkkijonon.
     */
    public Collection<Kotityo> etsiKotityot(String text) {
        return kotityot.etsiKotityot(text);
    }


    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
        jasenet.setTiedostonPerusNimi(hakemistonNimi + "nimet");
        kotityot.setTiedostonPerusNimi(hakemistonNimi + "kotityot");
        suoritukset.setTiedostonPerusNimi(hakemistonNimi + "suoritukset");
    }


    /**
     * Lisätään uusi jäsen
     * @param jasen lisättävä jäsen
     * @throws SailoException jos lisääminen ei onnistu
     * @example <pre name="test">
     * #THROWS SailoException
     * Siivoustiimi siivoustiimi = new Siivoustiimi();
     * Jasen timo1 = new Jasen();
     * Jasen timo2 = new Jasen();
     * timo1.rekisteroi(); timo2.rekisteroi();
     * siivoustiimi.getJasenia() === 0;
     * siivoustiimi.lisaa(timo1); siivoustiimi.getJasenia() ===1;
     * siivoustiimi.lisaa(timo2); siivoustiimi.getJasenia() ===2;
     * siivoustiimi.lisaa(timo1); siivoustiimi.getJasenia() ===3;
     * siivoustiimi.annaJasen(0) === timo1;
     * siivoustiimi.annaJasen(1) === timo2;
     * siivoustiimi.annaJasen(2) === timo1;
     * siivoustiimi.annaJasen(3) === timo1; #THROWS IndexOutOfBoundsException
     * siivoustiimi.lisaa(timo1); siivoustiimi.getJasenia() === 4;
     * siivoustiimi.lisaa(timo1); siivoustiimi.getJasenia() ===5;
     * </pre>
     */
    public void lisaa(Jasen jasen) throws SailoException {
        jasenet.lisaa(jasen);
    }


    /**
     * Lisaa uuden kotityon tietorakenteeseen.
     *
     * @param kotityo lisattava kotityo
     */
    public void lisaa (Kotityo kotityo) {
        kotityot.lisaa(kotityo);
    }


    /**
     * Lisaa uuden suorituksen tietorakenteeseen.
     *
     * @param suoritus lisattava suoritus
     */
    public void lisaa (Suoritus suoritus) {suoritukset.lisaa(suoritus);}


    /**
     * Korvaa jäsenen tietorakenteessa.  Ottaa jäsenen omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva jäsen.  Jos ei löydy,
     * niin lisätään uutena jäsenenä.
     * @param jasen lisättävän jäsenen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     */
    public void korvaaTaiLisaa(Jasen jasen) throws SailoException {
        jasenet.korvaaTaiLisaa(jasen);
    }

    /**
     * Korvaa kotityön tietorakenteessa.
     * Etsitään samalla tunnusluvulla oleva kotityö.
     * @param kotityo lisättävän kotityön viite.
     */
    public void korvaa(Kotityo kotityo) {
        kotityot.korvaa(kotityo);
    }


    /**
     * Poistaa jäsenen tietorakenteesta.
     * @param jasen poistettava jäsen
     * @return 1 jos poistaminen onnistuu. 0 jos poistaminen ei onnistu.
     * Siivoustiimi siivoustiimi = new Siivoustiimi();
     * Jasen jasen1 = new Jasen(); Jasen jasen2 = new Jasen();
     * siivoustiimi.getJasenia() == 2;
     * siivoustiimi.poista(jasen2);
     * siivoustiimi.getJasenia() ==1;
     */
    public int poista(Jasen jasen) {
        if (jasen == null) return 0;
        int ret = jasenet.poista(jasen.getId());
        kotityot.poistaJasenenKotityot(jasen.getId());
        return ret;
    }

    /**
     * Poistaa kotityön tietorakenteesta
     * @param kotityo poistettava kotityö
     */
    public void poistaKotityo(Kotityo kotityo) {
        if (kotityo ==null) return;
        kotityot.poista(kotityo);
    }



    /**
     * Lukee siivoustiimin tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */

    public void lueTiedostosta(String nimi) throws SailoException, FileNotFoundException {
        jasenet = new Jasenet();
        kotityot = new Kotityot();
        suoritukset = new Suoritukset();

        setTiedosto(nimi);
        jasenet.lueTiedostosta();
        kotityot.lueTiedostosta();
        suoritukset.lueTiedostosta();
    }


    /**
     * Tallentaa siivoustiimin suoritukset, kotityöt ja jäsenet kaikki omiin tiedostoihinsa.
     * @throws SailoException
     * @throws FileNotFoundException
     */
    public void tallenna() throws SailoException, FileNotFoundException {
        String virhe = "";
        try {
            jasenet.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }

        try {
            kotityot.tallenna();
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            suoritukset.tallenna();
        }catch (SailoException | IOException e) {
            virhe = e.getMessage();
        }   if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }



    /**
     * testiohjelma
     * @param args ei käytössä
     */
    public static void main(String[] args) {

        Siivoustiimi siivoustiimi = new Siivoustiimi();

        try {
            Jasen timo1 = new Jasen();
            Jasen timo2 = new Jasen();
            timo1.rekisteroi();
            timo1.taytaJasen();
            timo2.rekisteroi();
            timo2.taytaJasen();

            siivoustiimi.lisaa(timo1);
            siivoustiimi.lisaa(timo2);

            System.out.println("============= Siivoustiimi testi =================");

            for (int i = 0; i<siivoustiimi.getJasenia(); i++) {
                Jasen jasen = siivoustiimi.annaJasen(i);
                System.out.println("Jäsen paikassa: " + i);
                jasen.tulosta(System.out);
                System.out.println();
            }

        } catch (SailoException e) {
            System.out.println(e.getMessage());
        }
    }

}