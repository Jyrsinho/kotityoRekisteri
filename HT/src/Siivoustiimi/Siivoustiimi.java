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
     * Lisätään uusi jäsen
     * @param jasen lisättävä jäsen
     * @throws SailoException jos lisääminen ei onnistu
     */
    public void lisaa (Jasen jasen) throws SailoException {
        jasenet.lisaa(jasen);
    }

    public static void main(String[] args) {

    }
}