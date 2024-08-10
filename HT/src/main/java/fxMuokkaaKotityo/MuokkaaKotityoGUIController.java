package fxMuokkaaKotityo;
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
public class MuokkaaKotityoGUIController implements ModalControllerInterface<Kotityo>, Initializable {


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
        kotityoKohdalla = null;
        ModalController.closeStage(buttonCancel);
    }


    /**
     * Lisää uuden kotityön tietorakenteeseen.
     * @param event OK painikkeen klikkaaminen
     */

    @FXML
    void klikkaaOK(MouseEvent event) {

        asetaKotityolleUudetArvot();
        ModalController.closeStage(labelVirhe);

    }


    // ============================================================================================


    private Siivoustiimi siivoustiimi;
    private TextField edits[];
    private ComboBoxChooser editCombos[];
    private Kotityo kotityoKohdalla;
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
        return kotityoKohdalla;
    }


    @Override
    public void setDefault(Kotityo oletus) {
        kotityoKohdalla = oletus;


    }


    /**
     * Asettaa valintakursorin editNimikohtaan ja hakee suorituksenajoituskalenteriin oletusarvoksi taman paivan.
     */
    @Override
    public void handleShown() {
        editNimi.requestFocus();
    }


    /**
     * Asettaa Comboboxchoosereihin kotityon voimassa olevat arvot.
     * @param kotityo
     */
    public void naytaKotityo(Kotityo kotityo) throws SailoException {
        if (kotityo == null) return;

        editNimi.setText(kotityo.getKotityoNimi());
        tehtyViimeksiKalenteri.setValue(kotityo.getViimeisinSuoritus());

        selectVanhenemisaika.setSelectedIndex(etsioikeaindeksi(vanhenemisaikaVaihtoehdot, kotityoKohdalla.getVanhenemisaika()));
        selectKesto.setSelectedIndex(etsioikeaindeksi(kestovaihtoehdot, kotityoKohdalla.getKesto()));
        selectVastuuhenkilo.setSelectedIndex(etsiOikeanVastuuHenkilonIndeksi(kotityoKohdalla.getVastuuhenkilonID()));
    }

    /**
     * Apumetodi, joka etsii vastuuhenkilonvalintacomboboxin oikean indeksin, josta loytyy kotityon
     * nykyinen vastuuhenkilo
     * @return comboboxin indeksi, joka vastaa muokattavan kotityon nykyista vastuuhenkiloa
     */
    public int etsiOikeanVastuuHenkilonIndeksi(int vastuuHenkilonID) throws SailoException {

        Collection<Jasen> kaikkiJasenet = siivoustiimi.etsiJasenet("",1);

        List<Jasen> lajiteltavalista = new ArrayList<>(kaikkiJasenet);

       lajiteltavalista.sort((jasen1, jasen2) -> Integer.compare(jasen1.getId(), jasen2.getId()));

        for (int i = 0; i < lajiteltavalista.size(); i++) {
            if (lajiteltavalista.get(i).getId() == vastuuHenkilonID){
                return i;
            }
        }
        return -1;
    }


    /**
     * Asettaa comboboxeihin kotityon nykyisen arvon, jos sillä sellainen on
     * @param vaihtoehdot taulukko, joka sisältää mahdolliset arvot
     * @return vaihtoehdot taulukon indeksi, jossa kotityon nykyinen arvo on.
     */
    public int etsioikeaindeksi(int[] vaihtoehdot, int etsittavaArvo) {
        int indeksi = 0;
        for (int i = 0; i < vaihtoehdot.length; i++) {
            if (vaihtoehdot[i] == etsittavaArvo) {
                indeksi = i;
                break;
            }
        }
        return indeksi;
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
        if (kotityoKohdalla == null) return;

        String s = edit.getText();
        String virhe = null;
        if (k == 1) {
            virhe = kotityoKohdalla.setKotityonNimi(s);
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
        try {
            kotityoKohdalla.setViimeisinSuoritus(tehtyViimeksiKalenteri.getValue());
        } catch (NullPointerException e) {
            Dialogs.setToolTipText(labelVirhe,"Kalenteriin on valittava arvo");
        }

        try {
            kotityoKohdalla.setVastuuhenkilonID(selectVastuuhenkilo.getValue().getObject().getId());
        } catch (NullPointerException e) {
            Dialogs.setToolTipText(labelVirhe, "Kotityölle on valittava vastuuhenkilo");
        }

        if (selectVastuuhenkilo.getValue().getObject() == null) return;
        kotityoKohdalla.setKesto(selectKesto.getValue().getObject());
        if (selectKesto.getValue().getObject() == null) return;
        kotityoKohdalla.setVanhenemisaika(selectVanhenemisaika.getValue().getObject());
        if (selectVanhenemisaika.getValue().getObject() == null) return;
    }


    /**
     * Luodaan kotityön kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus        mitä dataan näytetään oletuksena
     * @param oletusTiimi mihin siivoustiimiin muutoksia tehdään
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */
    public static Kotityo kysyKotityo(Stage modalityStage, Kotityo oletus, Siivoustiimi oletusTiimi) {
        return ModalController.<Kotityo, MuokkaaKotityoGUIController>showModal(
                MuokkaaKotityoGUIController.class.getResource("/fxml/MuokkaaKotityo.fxml"),
                oletus.getKotityoNimi(),
                modalityStage, oletus, ctrl->ctrl.setSiivoustiimi(oletusTiimi, oletus)
        );
    }

    /**
     * Asetetaan ikkunassa käytettäväksi siivoustiimiksi parametrina tuotu siivoustiimi.
     * @param oletusTiimi siivoustiimi, jota käytetään.
     */
    private void setSiivoustiimi(Siivoustiimi oletusTiimi, Kotityo oletus) {
        this.siivoustiimi = oletusTiimi;
        try {
            naytaTiimi(oletusTiimi);
            naytaKotityo(oletus);
        } catch (SailoException e) {
            throw new RuntimeException(e);
        }
    }


}

