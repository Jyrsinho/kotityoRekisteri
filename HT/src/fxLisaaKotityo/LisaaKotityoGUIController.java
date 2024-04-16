package fxLisaaKotityo;
import Siivoustiimi.Kotityo;
import Siivoustiimi.Siivoustiimi;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Siivoustiimi.SailoException;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

/**
 * @author jyrihuhtala
 * @version 28.1.2024
 */
public class LisaaKotityoGUIController implements ModalControllerInterface<Kotityo>, Initializable {


    public TextField editNimi;
    public Label labelVirhe;
    public TextField editVanhenemisaika;
    public TextField editKesto;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonOK;

    @FXML
    private ChoiceBox<String> selectVastuuhenkilo;

    /**
     * Peruuttaa kotityön lisäämisen ja ohjelma palaa päävalikkoon.
     *
     * @param event
     */
    @FXML
    void klikkaaCancel(MouseEvent event) {
        uusikotityo = null;
        ModalController.closeStage(buttonCancel);


    }

    /**
     * Lisää uuden kotityön tietorakenteeseen.
     * @param event OK painikkeen klikkaaminen
     */

    @FXML
    void klikkaaOK(MouseEvent event) {


        if (uusikotityo != null && uusikotityo.getKotityoNimi().trim().equals("")) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }

        try {
            siivoustiimi.tallenna();
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ModalController.closeStage(labelVirhe);

    }


    // ============================================================================================


    private Siivoustiimi siivoustiimi;
    private TextField edits[];
    private Kotityo uusikotityo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alusta();

    }


    /**
     * Tekee tarvittavat muut alustukset.
     */
    public void alusta() {
        edits = new TextField[]{editNimi, editVanhenemisaika, editKesto};
        int i = 0;
        for (TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased(e -> kasitteleMuutosKotityohon(k, (TextField) (e.getSource())));
        }
        selectVastuuhenkilo.setOnAction(event -> {
            String valittuVastuuHenkilo = selectVastuuhenkilo.getValue();
            lisaaKotityolleVastuuhenkilo(valittuVastuuHenkilo);
        });
    }

    @Override
    public Kotityo getResult() {
        return uusikotityo;
    }


    @Override
    public void setDefault(Kotityo oletus) {

        uusikotityo = oletus;
    }


    @Override
    public void handleShown() {
        editNimi.requestFocus();
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

        ObservableList<String> optionsList = FXCollections.observableArrayList();

        for (int i = 0; i < oletustiimi.getJasenia(); i++) {
            optionsList.add(oletustiimi.annaJasen(i).getNimi());
        }

        // Create a ChoiceBox and set its items
        selectVastuuhenkilo.setItems(optionsList);

    }

    /**F
     * Asettaa käyttäjän ikkunassa valitsemat arvot uudelle kotityölle.
     */
    private void kasitteleMuutosKotityohon(int k, TextField edit) {
        if (uusikotityo == null) return;

        String s = edit.getText();
        String virhe = null;
        switch (k) {
            case 1:
                virhe = uusikotityo.setKotityonNimi(s);
                break;
            case 2:
                virhe = uusikotityo.setVanhenemisaika(s);
                break;
            case 3:
                virhe = uusikotityo.setKesto(s);
                break;
            default:
        }
                if (virhe == null) {
                    Dialogs.setToolTipText(edit,"");
                    edit.getStyleClass().removeAll("virhe");
                    naytaVirhe(virhe);
                } else {
                    Dialogs.setToolTipText(edit,virhe);
                    edit.getStyleClass().add("virhe");
                    naytaVirhe(virhe);
                }
        }

    /**
     * asetetaan lisättävälle kotityölle vastuuhenkilö kysymällä siivoustiimiltä vastuuhenkilön nimeä
     * vastaava ID.
     * @param vastuuhenkilo
     */
    public void lisaaKotityolleVastuuhenkilo(String vastuuhenkilo) {
            int vastuuhenkilonID = siivoustiimi.etsiJasenenID(vastuuhenkilo);
            uusikotityo.setVastuuhenkilonID(vastuuhenkilonID);
        }


    /**
     * Luodaan kotityön kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus        mitä dataan näytetään oletuksena
     * @param oletusTiimi mihin siivoustiimiin muutoksia tehdään
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */

    public static Kotityo kysyKotityo(Stage modalityStage, Kotityo oletus, Siivoustiimi oletusTiimi) {
        return ModalController.<Kotityo, LisaaKotityoGUIController>showModal(
                LisaaKotityoGUIController.class.getResource("LisaaKotityo.fxml"),
                "Uusi Kotityö",
                modalityStage, oletus, ctrl->ctrl.setSiivoustiimi(oletusTiimi)
        );
    }

    /**
     * Asetetaan ikkunassa käytettäväksi siivoustiimiksi parametrina tuotu siivoustiimi.
     * @param oletusTiimi siivoustiimi, jota käytetään.
     */
    private void setSiivoustiimi(Siivoustiimi oletusTiimi) {
        this.siivoustiimi = oletusTiimi;

        naytaTiimi(oletusTiimi); //lataa vastuuhenkilö-valikkoon kaikki tiimin jäsenet.
    }


}

