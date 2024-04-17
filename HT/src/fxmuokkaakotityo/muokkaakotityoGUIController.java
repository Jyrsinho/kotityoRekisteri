package fxmuokkaakotityo;


import Siivoustiimi.Kotityo;
import Siivoustiimi.Siivoustiimi;
import Siivoustiimi.Jasen;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author jyrihuhtala
 * @version 10.2.2024
 */
public class muokkaakotityoGUIController implements ModalControllerInterface<Kotityo>, Initializable {

    @FXML private TextField editTextVanhenee;
    @FXML private TextField editTextKesto;
    @FXML private TextField editNimi;
    @FXML private Label labelVirhe;

    @FXML private Button buttonPoistaKotityo;

    @FXML private Button buttonOK;

    @FXML private Button buttonCancel;

    @FXML private Button buttonUusiKotityo;

    @FXML private TextField hakuKentta;

    @FXML private ListChooser<Kotityo> kotityoValikko;

    @FXML private ChoiceBox<Jasen> valitseTekija;


    /**
     * Kirjoittamalla hakukenttään voidaan kotitöiden joukosta hakea syötettä vastaavia kotitöitä.
     * @param event
     */
    @FXML void haeKotityo(KeyEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata hakea kotitöitä");
    }



    @FXML void klikkaaCancel(MouseEvent event) {

        ModalController.closeStage(buttonCancel);

    }
    /**
     * Tallentaa valittuun kotityöhön tehdyt muutokset
     * @param event
     */
    @FXML void klikkaaOk(MouseEvent event) {

        ModalController.closeStage(labelVirhe);
    }


    /**
     * Avaa lisää kotityö ikkunan.
     * @param event
     */
    @FXML void klikkaaUusiKotityo(MouseEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata avata lisää kotityö ikkunaa");
        //
    }


    /**
     * Poistaa valitun kotityön listalta ja tiedostosta.
     * @param event
     */
    @FXML
    void klikkaapoistaKotityo(MouseEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata poistaa kotityötä");

    }


    //============================================================================================================

    private Siivoustiimi siivoustiimi;
    private TextField edits[];
    private Kotityo uusikotityo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public Kotityo getResult() {
        return null;
    }

    @Override
    public void setDefault(Kotityo oletus) {
    }

    @Override
    public void handleShown() {

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

        for (int i = 0; i < oletustiimi.getJasenia(); i++) {
            optionsList.add(oletustiimi.annaJasen(i));
        }

        // Create a ChoiceBox and set its items
        valitseTekija.setItems(optionsList);

    }

    /**
     * Hakee Listchooseriin siivoustiimin kaikki kotityöt näytettäväksi.
     * @param oletustiimi, jonka kotityöt näytetään
     */
    private void haeKaikkiKotityot (Siivoustiimi oletustiimi) {


        for (Kotityo alkio: siivoustiimi.getKotityot()) {
            kotityoValikko.add(alkio.getKotityoNimi(), alkio);
        }

   }


    /**
     * Luodaan kotityön kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus        mitä dataan näytetään oletuksena
     * @param oletusTiimi mihin siivoustiimiin muutoksia tehdään
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */

    public static Kotityo kysyKotityo(Stage modalityStage, Kotityo oletus, Siivoustiimi oletusTiimi) {
        return ModalController.<Kotityo, muokkaakotityoGUIController>showModal(
                muokkaakotityoGUIController.class.getResource("muokkaakotityo.fxml"),
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
        haeKaikkiKotityot(oletusTiimi); //hakee Listchooseriin kaikki tiimin kotityöt.
    }

}