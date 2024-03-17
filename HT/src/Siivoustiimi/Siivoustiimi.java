package Siivoustiimi;

import java.net.IDN;
import java.util.ArrayList;
import java.util.List;

/**
 * huolehtii Jäsenet ja Kotityöt - luokkien välisestä yhteistyöstä ja välittää näitä tietoja pyydettäessä.
 * lukee ja kirjoittaa kerhon tiedostoon pyytämällä apua avustajiltaan.
 * @author jyrihuhtala
 * @version 21.2.2024
 */
public class Siivoustiimi {

    Jasenet jasenet = new Jasenet();
    Kotityot kotityot = new Kotityot();

    //Kotityot kotityot = new Kotityot();
    //Suoritukset suoritukset = new Suoritukset();

    /**
     * palauttaa siivoustiimin jäsenmäärän
     *
     * @return jäsenmäärä
     */
    public int getJasenia() {
        return jasenet.getLkm();
    }


    /**
     * palauttaa listan yhden jäsenen kotitöistä
     * @param jasenId
     * @return
     */
    public List<Kotityo> getKotityot(int jasenId) {
        return kotityot.annaKotityot(jasenId);
    }


    /**
     * Palauttaa halutun indeksin paikalla taulukossa olevan jäsenen tiedot.
     * @param i
     * @return Palauttaa halutun indeksin paikalla taulukossa olevan jäsenen tiedot.
     * @throws IndexOutOfBoundsException jos indeksi on liian iso.
     */
    public Jasen annaJasen(int i) throws IndexOutOfBoundsException {
        return jasenet.anna(i);
    }

    public ArrayList<Kotityo> annaKotityot(int jasenId) {
        return kotityot.annaKotityot(jasenId);
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
     * siivoustiimi.lisaa(timo1); #THROWS SailoException
     * </pre>
     */
    public void lisaa(Jasen jasen) throws SailoException {
        jasenet.lisaa(jasen);
    }

    public void lisaa (Kotityo kotityo) {
        kotityot.lisaa(kotityo);
    }

    /**
     * Lukee siivoustiimin tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        jasenet.lueTiedostosta(nimi);
    }

    /**
     * Tallettaa siivoustiimin tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SailoException {
        jasenet.talleta();
        // TODO: yritä tallettaa toinen vaikka toinen epäonnistuisi
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