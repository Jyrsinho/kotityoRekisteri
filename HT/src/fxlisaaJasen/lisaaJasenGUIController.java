package fxlisaaJasen;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fxPaaikkuna.PaaikkunaGUIController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class lisaaJasenGUIController implements ModalControllerInterface<String> {

    @FXML private Button ButtonOK;

    @FXML private Button buttonCancel;

    /**
     * Peruuttaa jösenen lisäämisen ja ohjelma palaa päävalikkoon.
     * @param event
     */
    @FXML
    void klikkaaCancel(MouseEvent event) {
        ModalController.showModal(PaaikkunaGUIController.class.getResource("PaaikkunaGUIView.fxml"), "Paaikkuna", null, "");
    }

    /**
     * Tallentaa uuden jäsenen tiedostoon.
     * @param event
     */
    @FXML
    void klikkaaOK(MouseEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä tallentaa.");
        ModalController.showModal(PaaikkunaGUIController.class.getResource("PaaikkunaGUIView.fxml"), "Paaikkuna", null, "");

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