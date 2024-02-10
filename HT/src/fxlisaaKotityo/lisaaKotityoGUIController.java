package fxlisaaKotityo;
import fi.jyu.mit.fxgui.Dialogs;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ChoiceBox;

/**
 * @author jyrihuhtala
 * @version 28.1.2024
 */
public class lisaaKotityoGUIController {
    // TODO


    @FXML private Button buttonCancel;

    @FXML private Button buttonOK;

    @FXML private ChoiceBox<?> vetovalikkoKesto;

    @FXML private ChoiceBox<?> vetovalikkoVanhenemisaika;

    @FXML private ChoiceBox<?> vetovalikkoVastuuhenkilo;

    /**
     * Peruuttaa kotityön lisäämisen ja ohjelma palaa päävalikkoon.
     * @param event
     */
    @FXML void klikkaaCancel(MouseEvent event) {

            Dialogs.showMessageDialog("Ei osata vielä peruuttaa.");

    }
    /**
     * Tallentaa uuden kotityön tiedostoon.
     * @param event
     */
    @FXML void klikkaaOK(MouseEvent event) {

            Dialogs.showMessageDialog("Ei osata vielä tallentaa.");
        }

    /**
     * avaa vetovalikon, josta voidaan valita kotityölle oletuskestoaika.
      * @param event
     */
    @FXML void klikkaaKesto(MouseEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä avata kestovalikkoa.");

    }

    /**
     * avaa vetovalikon, josta voidaan valita kotityölle vanhenemisaika.
     * @param event
     */
    @FXML void klikkaaVanhenemisaika(MouseEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä avata vanhenemisaikavalikkoa.");

    }

    /**
     * avaa vetivalikon, josta voidaan valita kotityölle vastuuhenkilö.
     * @param event
     */
    @FXML void klikkaaVastuuhenkilo(MouseEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä avata vastuuhenkilövalikkoa.");

    }

    }


