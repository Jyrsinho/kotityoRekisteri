package Siivoustiimi;

import java.io.Serial;

public class SailoException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Poikkeuksen muodostaja jolle tuoda poikkeuksessa käytettävä viesti
     * @param viesti Poikkeuksen viesti
     */
    public SailoException (String viesti) {
        super(viesti);
    }
}
