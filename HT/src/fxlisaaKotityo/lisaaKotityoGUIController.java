package fxlisaaKotityo;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fxPaaikkuna.PaaikkunaGUIController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ChoiceBox;

/**
 * @author jyrihuhtala
 * @version 28.1.2024
 */
public class lisaaKotityoGUIController implements ModalControllerInterface<String> {
    // TODO


    @FXML private Button buttonCancel;

    @FXML private Button buttonOK;

    @FXML private ComboBox<?> vetovalikkoKesto;

    @FXML private ComboBox<?> vetovalikkoVanhenemisaika;

    @FXML private ComboBox<?> vetovalikkoVastuuhenkilo;

    /**
     * Peruuttaa kotityön lisäämisen ja ohjelma palaa päävalikkoon.
     * @param event
     */
    @FXML void klikkaaCancel(MouseEvent event) {

        ModalController.closeStage(buttonCancel);


    }
    /**
     * Tallentaa uuden kotityön tiedostoon.
     * @param event
     */
    @FXML void klikkaaOK(MouseEvent event) {

        Dialogs.showMessageDialog("Ei osata vielä tallentaa.");
        ModalController.closeStage(buttonCancel);

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

    @Override
    public String getResult() {
        return null;
    }

    @Override
    public void setDefault(String s) {

    }

    @Override
    public void handleShown() {

    }
}


