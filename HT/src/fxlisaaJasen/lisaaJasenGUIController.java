package fxlisaaJasen;

import fi.jyu.mit.fxgui.Dialogs;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class lisaaJasenGUIController {

    @FXML private Button ButtonOK;

    @FXML private Button buttonCancel;

    /**
     * Peruuttaa jösenen lisäämisen ja ohjelma palaa päävalikkoon.
     * @param event
     */
    @FXML
    void klikkaaCancel(MouseEvent event) {
         Dialogs.showMessageDialog("Ei osata vielä peruuttaa.");
    }

    /**
     * Tallentaa uuden jäsenen tiedostoon.
     * @param event
     */
    @FXML
    void klikkaaOK(MouseEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä tallentaa.");
    }

}