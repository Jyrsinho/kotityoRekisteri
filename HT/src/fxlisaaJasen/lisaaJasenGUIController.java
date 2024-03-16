package fxlisaaJasen;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class lisaaJasenGUIController implements ModalControllerInterface<String> {

    @FXML private Button ButtonOK;
    @FXML private TextField tekstiKenttaNimi;
    @FXML private TextField tekstiKenttaOsoite;
    @FXML private TextField tekstiKenttaPostinumero;
    @FXML private TextField tekstiKenttaKaupunki;
    @FXML private TextField tekstiKenttaPuhelin;
    @FXML private TextField tekstiKenttaIka;

    @FXML private Button buttonCancel;

    /**
     * Peruuttaa jösenen lisäämisen ja ohjelma palaa päävalikkoon.
     * @param event
     */
    @FXML
    void klikkaaCancel(MouseEvent event) {
    ModalController.closeStage(buttonCancel);
    }

    /**
     * Tallentaa uuden jäsenen tiedostoon.
     * @param event
     */
    @FXML
    void klikkaaOK(MouseEvent event) {
        //Dialogs.showMessageDialog("Ei osata vielä tallentaa.");
        ModalController.closeStage(buttonCancel);        //  ModalController.showModal(PaaikkunaGUIController.class.getResource("PaaikkunaGUIView.fxml"), "Paaikkuna", null, "");

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
    //------------------------------------------------------------------

}
