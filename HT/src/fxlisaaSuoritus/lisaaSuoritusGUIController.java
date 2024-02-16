package fxlisaaSuoritus;


import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fxPaaikkuna.PaaikkunaGUIController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;

/**
 * @author jyrihuhtala
 * @version 28.1.2024
 */
public class lisaaSuoritusGUIController implements ModalControllerInterface<String> {

    @FXML private Button buttonCancel;

    @FXML private Button buttonOK;

    @FXML private ChoiceBox<?> choiceboxKotityo;

    @FXML private DatePicker kalenteriValinta;

    @FXML private ChoiceBox<?> tekijaValinta;

    /**
     * peruuttuu suorituksen lisäämisen ja ohjelma palaa päävalikkoon.
     * @param event
     */
    @FXML
    void clickedCancel(MouseEvent event) {
        // Dialogs.showMessageDialog("Vielä ei osata peruuttaa.");
        ModalController.closeStage(buttonCancel);

    }

    /**
     * Valitsee vetovalikosta kotityön suorituspäivämäärän.
     * @param event
     */
    @FXML
    void clickedKalenteriValinta(MouseEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata valita kalenterista suorituspäivämäärää.");

    }

    /**
     * Tallentaa käyttäjän lisäämän suorituksen tietokantaan.
     * @param event
     */
    @FXML
    void clickedOK(MouseEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata tallentaa.");
        ModalController.closeStage(buttonCancel);


    }

    /**
     *
     *vetovalikosta voidaan valita joku tiimin jäsenistä tallennettavan suorituksen tekijäksi.
     * @param event
     */
    @FXML
    void clickedTekijaValinta(MouseEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata valita tekijää.");

    }

    /**
     * vetovalikosta valitaan mikä siivoustiimin kotitöistä merkitään suoritetuksi.
     * @param event
     */
    @FXML
    void kotityoVetoValikkoKlikkaus(MouseEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata valita suoritettavaa kotityötä.");

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