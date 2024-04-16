package fxlisaaSuoritus;


import Siivoustiimi.Kotityo;
import Siivoustiimi.Siivoustiimi;
import Siivoustiimi.Suoritus;
import Siivoustiimi.Jasen;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fxLisaaKotityo.LisaaKotityoGUIController;
import fxPaaikkuna.PaaikkunaGUIController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * @author jyrihuhtala
 * @version 28.1.2024
 */
public class lisaaSuoritusGUIController implements ModalControllerInterface<Suoritus>, Initializable {

    @FXML private Button buttonCancel;

    @FXML private Button buttonOK;

    @FXML private ChoiceBox<Kotityo> kotityoValinta;

    @FXML private DatePicker kalenteriValinta;

    @FXML private ChoiceBox<Jasen> tekijaValinta;



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

    // ================================================================================================================================

    private Suoritus uusiSuoritus;
    private Siivoustiimi siivoustiimi;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alusta();
    }

    @Override
    public Suoritus getResult() {
        return uusiSuoritus;
    }

    @Override
    public void setDefault(Suoritus oletus) {
        uusiSuoritus = oletus;
    }

    @Override
    public void handleShown() {

    }

    public void alusta() {

    }

    /**
     * Luodaan lista näytettäväksi Choiceboxissa. Lisätään choiceboxiin siivousttiimin jäsenten
     * nimet.
     * @param oletustiimi siivoustiimi, jonka jäsenet näytetään choiceboxissa.
     */
    private void naytaTiimi(Siivoustiimi oletustiimi) {

        ObservableList<Jasen> optionsList = FXCollections.observableArrayList();
        Jasen[] jasenlista = oletustiimi.getJasenet();
        for (Jasen jasen: jasenlista) {
            optionsList.add(jasen);
        }

        // Create a ChoiceBox and set its items
        tekijaValinta.setItems(optionsList);

    }

    /**
     * Luodaan lista kotitöistä näytettäväksi Choiceboxissa. Lisätään choiceboxiin siivousttiimin kotitöiden
     * nimet.
     * @param oletustiimi siivoustiimi, jonka jäsenet näytetään choiceboxissa.
     */
    private void naytaKotityot(Siivoustiimi oletustiimi) {
        ObservableList<Kotityo> optionsList = FXCollections.observableArrayList();
        Collection<Kotityo> kotityolista = oletustiimi.getKotityot();
        for (Kotityo kotityo : kotityolista) {
            optionsList.add(kotityo);
        }
        kotityoValinta.setItems(optionsList);
    }


    /**
     * Luodaan Suorituksen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataan näytetään oletuksena
     * @param oletusTiimi mihin siivoustiimiin muutoksia tehdään
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */

    public static Suoritus kysySuoritus(Stage modalityStage, Suoritus oletus, Siivoustiimi oletusTiimi) {
        return ModalController.<Suoritus, lisaaSuoritusGUIController>showModal(
                lisaaSuoritusGUIController.class.getResource("lisaaSuoritusGUIView.fxml"),
                "Uusi Suoritus",
                modalityStage, oletus, ctrl->ctrl.setSiivoustiimi(oletusTiimi)
        );
    }

    /**
     * Asetetaan ikkunassa käytettäväksi siivoustiimiksi parametrina tuotu siivoustiimi.
     * @param oletusTiimi siivoustiimi, jota käytetään.
     */
    private void setSiivoustiimi(Siivoustiimi oletusTiimi) {
        this.siivoustiimi = oletusTiimi;

        naytaTiimi(oletusTiimi); //lataa Tekijä-valikkoon kaikki tiimin jäsenet.
        naytaKotityot(oletusTiimi); // lataa Kotityo-valikkoon kaikki tiimin kotityöt.
    }

}