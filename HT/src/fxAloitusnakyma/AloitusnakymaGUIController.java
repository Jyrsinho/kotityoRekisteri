package fxAloitusnakyma;
import fi.jyu.mit.fxgui.Dialogs;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * @author jyrihuhtala
 * @version 9.2.2024
 */
public class AloitusnakymaGUIController {

    @FXML private Button buttonCancel;

    @FXML private Button buttonOK;

    @FXML private TextField textTiiminNimi;

    /**
     * Avaa käyttäjän valitseman siivoustiimi tiedoston ja ohjelma siirtyy päävalikkoon.
     * @param event
     */
    @FXML void clickCancel(MouseEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata peruuttaa.");

    }

    /**
     * Peruuttaa aloitusikkunan avaamisen ja sulkee ohjelman.
     * @param event
     */
    @FXML void okKlikkaus(MouseEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata lukea tiedostoa");
    }

}
