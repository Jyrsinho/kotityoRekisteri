package fxlisaaSuoritus;


import Siivoustiimi.Kotityo;
import Siivoustiimi.Siivoustiimi;
import Siivoustiimi.Suoritus;
import Siivoustiimi.Jasen;
import Siivoustiimi.SailoException;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fxLisaaKotityo.LisaaKotityoGUIController;
import fxPaaikkuna.PaaikkunaGUIController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * @author jyrihuhtala
 * @version 28.1.2024
 */
public class lisaaSuoritusGUIController implements ModalControllerInterface<Suoritus>, Initializable {

    @FXML private Label labelVirhe;
    @FXML private ChoiceBox kestoValinta;
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
     * Lisää käyttäjän antaman suorituksen tietokantaan.
     * @param event OK painikkeen klikkaaminen
     */
    @FXML
    void clickedOK(MouseEvent event) {

        kasitteleMuutosKotityohon();
        if (uusiSuoritus != null && tekijaValinta.getValue() == null || kotityoValinta.getValue() == null) {
            naytaVirhe("Kaikkiin kenttiin on valittava arvo");
            return;
        }
            kasittelemuutoksetSuoritukseen(uusiSuoritus);
        try {
            siivoustiimi.tallenna();
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
        }
        ModalController.closeStage(labelVirhe);

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
        kalenteriValinta.setValue(LocalDate.now());
    }


    /**
     * Näyttää mahdollisen virheen
     * @param virhe, joka näytetään
     */
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
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

    private void naytaKestoVaihtoehdot() {
        ObservableList<Integer> optionsList = FXCollections.observableArrayList();
        int[] kestoaikaTaulukko = {5,10,15,30,60};
        for (int alkio: kestoaikaTaulukko) {
            optionsList.add(alkio);
        }
        kestoValinta.setItems(optionsList);
    }

    /**
     * Asettaa lisättävälle suoritukselle käyttäjän valitsemat arvot.
     * @param uusiSuoritus suoritus, jota muokataan
     */
    private void kasittelemuutoksetSuoritukseen(Suoritus uusiSuoritus) {
        uusiSuoritus.setKotityoID(kotityoValinta.getValue().getKotityoID());
        uusiSuoritus.setTekoaika(String.valueOf(kalenteriValinta.getValue()));
        uusiSuoritus.setSuorittajaID(tekijaValinta.getValue().getId());
        uusiSuoritus.setKesto((Integer) kestoValinta.getValue());

    }

    /**
     * vertaillaan suoritusta koskevan kotityön nykyistä viimeisintä suoritusta tässä ikkunassa
     * asetettavan suorituksen suorituspäivämäärään. Jos tässä ikkunassa asetettava arvo on myöhemmin
     * kuin suoritusta koskevan kotityön viimeisin suoritus, tulee tässä ikkunassa asetettavasta viimeisimmästä
     * suorituksesta tätä suoritusta koskevan kotityön viimeisin suoritus.
     */
    private void kasitteleMuutosKotityohon() {
        int vertailu = kalenteriValinta.getValue().compareTo(kotityoValinta.getValue().getViimeisinSuoritus());

        if (vertailu > 0) {
            kotityoValinta.getValue().setViimeisinSuoritus(kalenteriValinta.getValue());
        }
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
        naytaKestoVaihtoehdot();

    }

}