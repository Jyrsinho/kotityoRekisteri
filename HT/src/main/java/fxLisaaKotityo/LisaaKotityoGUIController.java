package fxLisaaKotityo;
import Siivoustiimi.Kotityo;
import Siivoustiimi.Siivoustiimi;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Siivoustiimi.SailoException;
import Siivoustiimi.Jasen;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

/**
 * @author jyrihuhtala
 * @version 28.1.2024
 */
public class LisaaKotityoGUIController implements ModalControllerInterface<Kotityo>, Initializable {


    public TextField editNimi;
    public Label labelVirhe;
    public DatePicker tehtyViimeksiKalenteri;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonOK;

    @FXML
    private ComboBoxChooser<Jasen> selectVastuuhenkilo;

    @FXML
    private ComboBoxChooser<Integer> selectVanhenemisaika;

    @FXML
    private ComboBoxChooser<Integer> selectKesto;


    /**
     * Peruuttaa kotityön lisäämisen ja ohjelma palaa päävalikkoon.
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

        asetaKotityolleUudetArvot();

        if (uusikotityo != null && uusikotityo.getKotityoNimi().trim().equals("")) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
        else if (uusikotityo != null && uusikotityo.getViimeisinSuoritus() == null) {
            naytaVirhe("Kalenterista on valittava arvo");
            return;
        }

        ModalController.closeStage(labelVirhe);

    }


    // ============================================================================================


    private Siivoustiimi siivoustiimi;
    private TextField edits[];
    private Kotityo uusikotityo;
    private final int[] kestovaihtoehdot = {5,10,15,30,60};
    private final int[] vanhenemisaikaVaihtoehdot = {1,2,3,5,7,15,30,60,180};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alusta();

    }


    /**
     * Tekee tarvittavat muut alustukset.
     */
    public void alusta() {

        lisaaArvotKestoVaihtoehdotComboBoxiin();
        lisaaArvotVanhenemisAikaComboboxiin();

        edits = new TextField[]{editNimi};
        int i = 0;

        for (TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased(e -> kasitteleMuutosKotityohon(k, (TextField) (e.getSource())));
        }

        selectVastuuhenkilo.setOnAction(event -> {
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


    /**
     * Asettaa valintakursorin editNimikohtaan ja hakee suorituksenajoituskalenteriin oletusarvoksi taman paivan.
     */
    @Override
    public void handleShown() {
        editNimi.requestFocus();
        tehtyViimeksiKalenteri.setValue(LocalDate.now());
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
     * Hakee mahdolliset valittavat arvot kestoajanvalitsemiscomboboxiin.
     */
    private void lisaaArvotKestoVaihtoehdotComboBoxiin() {
        for (Integer kesto : kestovaihtoehdot) {
            selectKesto.add(kesto);
        }
    }

    /**
     *  Hakee mahdolliset valittavat arvot vanhenemisajanvalitsemiscomboboxiin.
    */
        private void lisaaArvotVanhenemisAikaComboboxiin() {
            for (Integer vanhenemisaika : vanhenemisaikaVaihtoehdot) {
                selectVanhenemisaika.add(vanhenemisaika);
            }
        }


     /**
     * Luodaan lista näytettäväksi Comboboxissa. Lisätään comboboxiin siivoustiimin jäsenet
     * @param oletustiimi siivoustiimi, jonka jäsenet näytetään choiceboxissa.
     */
    private void naytaTiimi(Siivoustiimi oletustiimi) throws SailoException {

        Collection<Jasen> kaikkiJasenet = oletustiimi.etsiJasenet("%",1);

        for (Jasen jasen : kaikkiJasenet) {
            selectVastuuhenkilo.add(jasen.getNimi(), jasen);
        }

    }


    /**
     * Asettaa käyttäjän tekstikkunassa valitsemat arvot uudelle kotityölle.
     */

    private void kasitteleMuutosKotityohon(int k, TextField edit) {
        if (uusikotityo == null) return;

        String s = edit.getText();
        String virhe = null;
        if (k == 1) {
            virhe = uusikotityo.setKotityonNimi(s);
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
     * Asetetaan kotityolle uudet arvot, jotka haetaan comboboxeista.
      */
    public void asetaKotityolleUudetArvot() {
        uusikotityo.setViimeisinSuoritus(tehtyViimeksiKalenteri.getValue());
        uusikotityo.setVastuuhenkilonID(selectVastuuhenkilo.getValue().getObject().getId());
        uusikotityo.setKesto(selectKesto.getValue().getObject());
        uusikotityo.setVanhenemisaika(selectVanhenemisaika.getValue().getObject());
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
                LisaaKotityoGUIController.class.getResource("/fxml/LisaaKotityo.fxml"),
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
        try {
            naytaTiimi(oletusTiimi);
        } catch (SailoException e) {
            throw new RuntimeException(e);
        }
    }


}

