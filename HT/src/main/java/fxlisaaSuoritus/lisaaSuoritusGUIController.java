package fxlisaaSuoritus;


import Siivoustiimi.*;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * @author jyrihuhtala
 * @version 28.1.2024
 */
public class lisaaSuoritusGUIController implements ModalControllerInterface<Suoritus>, Initializable {

    @FXML private Label labelVirhe;
    @FXML private ComboBoxChooser<Integer> kestoValinta;
    @FXML private Button buttonCancel;

    @FXML private Button buttonOK;

    @FXML private ComboBoxChooser<Kotityo> kotityoValinta;

    @FXML private DatePicker kalenteriValinta;

    @FXML private ComboBoxChooser<Jasen> tekijaValinta;



    /**
     * peruuttuu suorituksen lisäämisen ja ohjelma palaa päävalikkoon.
     * @param event
     */
    @FXML
    void clickedCancel(MouseEvent event) {

        ModalController.closeStage(buttonCancel);

    }


    /**
     * Lisää käyttäjän antaman suorituksen tietokantaan.
     * @param event OK painikkeen klikkaaminen
     */
    @FXML
    void clickedOK(MouseEvent event) {

        if (uusiSuoritus != null && tekijaValinta.getValue() == null || kotityoValinta.getValue() == null) {
            naytaVirhe("Kaikkiin kenttiin on valittava arvo");
            return;
        }

        kasittelemuutoksetSuoritukseen(uusiSuoritus);

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
    private void naytaTiimi(Siivoustiimi oletustiimi) throws SailoException {

        Collection<Jasen> jasenlista = oletustiimi.etsiJasenet("%",1);
        for (Jasen jasen: jasenlista) {
            tekijaValinta.add(jasen.getNimi(), jasen);
        }
    }


    /**
     * Luodaan lista kotitöistä näytettäväksi Choiceboxissa. Lisätään choiceboxiin siivousttiimin kotitöiden
     * nimet.
     * @param oletustiimi siivoustiimi, jonka jäsenet näytetään choiceboxissa.
     */
    private void naytaKotityot(Siivoustiimi oletustiimi) throws SailoException {

        Collection<Kotityo> kotityolista = oletustiimi.annaKaikkiKotityot();
        for (Kotityo kotityo : kotityolista) {
            kotityoValinta.add(kotityo.getKotityoNimi(), kotityo);
        }
    }


    private void naytaKestoVaihtoehdot() {
        int[] kestoaikaTaulukko = {5,10,15,30,60};
        for (int alkio: kestoaikaTaulukko) {
            kestoValinta.add(alkio);
        }
    }


    /**
     * Asettaa lisättävälle suoritukselle käyttäjän valitsemat arvot.
     * @param uusiSuoritus suoritus, jota muokataan
     */
    private void kasittelemuutoksetSuoritukseen(Suoritus uusiSuoritus) {
        uusiSuoritus.setKotityoID(kotityoValinta.getValue().getObject().getKotityoID());
        uusiSuoritus.setTekoaika(kalenteriValinta.getValue());
        uusiSuoritus.setSuorittajaID(tekijaValinta.getValue().getObject().getId());
        uusiSuoritus.setKesto(kestoValinta.getValue().getObject());
        tarkistaOnkoViimeisinSuoritus();

    }

    /**
     * vertaillaan suoritusta koskevan kotityön nykyistä viimeisintä suoritusta tässä ikkunassa
     * asetettavan suorituksen suorituspäivämäärään. Jos tässä ikkunassa asetettava arvo on myöhemmin
     * kuin suoritusta koskevan kotityön viimeisin suoritus, tulee tässä ikkunassa asetettavasta viimeisimmästä
     * suorituksesta tätä suoritusta koskevan kotityön viimeisin suoritus.
     */
    private void tarkistaOnkoViimeisinSuoritus() {
        int vertailu = kalenteriValinta.getValue().compareTo(kotityoValinta.getValue().getObject().getViimeisinSuoritus());

        if (vertailu > 0) {
            kotityoValinta.getValue().getObject().setViimeisinSuoritus(kalenteriValinta.getValue());
        }
    }


    /**
     * Luodaan Suorituksen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataan näytetään oletuksena
     * @param oletusTiimi mihin siivoustiimiin muutoksia tehdään
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */

    public static Suoritus kysySuoritus(Stage modalityStage, Suoritus oletus, Siivoustiimi oletusTiimi) throws SailoException {
        return ModalController.<Suoritus, lisaaSuoritusGUIController>showModal(
                lisaaSuoritusGUIController.class.getResource("/fxml/lisaaSuoritusGUIView.fxml"),
                "Uusi Suoritus",
                modalityStage, oletus, ctrl-> {
                    try {
                        ctrl.setSiivoustiimi(oletusTiimi);
                    }catch (SailoException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Virhe");
                        alert.setHeaderText("Tietokantavirhe");
                        alert.setContentText("Siivoustiimin asetuksessa tapahtui virhe: " + e.getMessage());
                        alert.showAndWait();
                    }
                }

        );
    }


    /**
     * Asetetaan ikkunassa käytettäväksi siivoustiimiksi parametrina tuotu siivoustiimi.
     * @param oletusTiimi siivoustiimi, jota käytetään.
     */
    private void setSiivoustiimi(Siivoustiimi oletusTiimi) throws SailoException {
        this.siivoustiimi = oletusTiimi;

        naytaTiimi(oletusTiimi); //lataa Tekijä-valikkoon kaikki tiimin jäsenet.
        naytaKotityot(oletusTiimi); // lataa Kotityo-valikkoon kaikki tiimin kotityöt.
        naytaKestoVaihtoehdot();

    }

}