package Siivoustiimi;

import java.util.ArrayList;
import java.util.Collection;

public class Suoritukset {
    private String tiedostonNimi = "";
    private final Collection<Suoritus> alkiot = new ArrayList<Suoritus>();

    /**
     * Luodaan Arraylist suorituksista
     */
    public Suoritukset() {

    }

    /**Lisaa uuden suorituksen tietorakenteeseen.
     *
     * @param suoritus lisattava kotityo
     */
    public void lisaa (Suoritus suoritus) {
        alkiot.add(suoritus);
    }


}
