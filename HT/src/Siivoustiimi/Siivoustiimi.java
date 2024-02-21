package Siivoustiimi;

/**
 * huolehtii Jäsenet ja Kotityöt - luokkien välisestä yhteistyöstä ja välittää näitä tietoja pyydettäessä.
 * lukee ja kirjoittaa kerhon tiedostoon pyytämällä apua avustajiltaan.
 * @author jyrihuhtala
 * @version 21.2.2024
 */
public class Siivoustiimi {

    Jasenet jasenet = new Jasenet();

    /**
     * palauttaa siivoustiimin jäsenmäärän
     *
     * @return jäsenmäärä
     */
    public int getJasenia() {
        return jasenet.getLkm();
    }


    public Jasen annaJasen(int i) throws IndexOutOfBoundsException {
        return jasenet.anna(i);
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
            }

        } catch (SailoException e) {
            System.out.println(e.getMessage());
        }
    }
}