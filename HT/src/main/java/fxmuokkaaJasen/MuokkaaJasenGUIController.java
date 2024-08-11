package fxmuokkaaJasen;

import Siivoustiimi.Jasen;
import Siivoustiimi.Kotityo;
import Siivoustiimi.SailoException;
import Siivoustiimi.Siivoustiimi;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fxMuokkaaKotityo.MuokkaaKotityoGUIController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Ikkunassa voidaan muokata jäsenten tietoja.
 */
    public class MuokkaaJasenGUIController implements ModalControllerInterface<Jasen>, Initializable {

    public TextField editEtunimi;
    public TextField editOsoite;
    public TextField editPostinumero;
    public TextField editKaupunki;
    public TextField editIka;
    public TextField editPuhelin;
    public TextField editSukunimi;


    @FXML
    private Label labelVirhe;

    @FXML private Button buttonMuokkaaKotityö;

    @FXML private Button buttonCancel;

    @FXML private Button buttonOK;

    @FXML private ListChooser<Kotityo> listaKotityo;


    /**
     * Avaa muokkaa kotityö ikkunan valitun kotityön kohdalta.
     * @param event
     */
    @FXML void klikkaaMuokkaaKotityo(MouseEvent event) throws SailoException {
        Kotityo muokattava = listaKotityo.getSelectedObject();
        muokattava = MuokkaaKotityoGUIController.kysyKotityo(null, muokattava, siivoustiimi);
        if (muokattava == null) { return; }
        siivoustiimi.paivitaKotityo(muokattava);
    }


    /**
     * tallentaa muokattavaksi valitun jäsenen tiedot tiedostoon.
     * @param event ookoon klikkaaminen
     */
    @FXML void klikkaaOK(MouseEvent event) {


        if (jasenKohdalla != null && jasenKohdalla.getEtunimi().trim().equals("") || jasenKohdalla.getSukunimi().trim().equals("")) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }


    @FXML void klikkaaCancel(MouseEvent event) {
        jasenKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }


    @FXML void valitseKotityoListasta(MouseEvent event) throws SailoException {
        Kotityo muokattava = listaKotityo.getSelectedObject();

        if (event.getButton().equals(MouseButton.PRIMARY)){
            if (event.getClickCount() == 1) {
                muokattava = listaKotityo.getSelectedObject();
            } else if (event.getClickCount() == 2) {
                muokattava = listaKotityo.getSelectedObject();
                muokattava = MuokkaaKotityoGUIController.kysyKotityo(null, muokattava, siivoustiimi);
                siivoustiimi.paivitaKotityo(muokattava);
            }
        }
        naytaKotityot(jasenKohdalla);
    }




//------------------------------------------------------

    private Kotityo kotityoKohdalla;
    private Jasen jasenKohdalla;
    private TextField edits[];
    private Siivoustiimi siivoustiimi;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alusta();
    }

    /**
     * Tekee tarvittavat muut alustukset.
     */
    protected void alusta() {

         edits = new TextField[]{editEtunimi, editSukunimi, editOsoite, editKaupunki, editPostinumero, editPuhelin, editIka};
         int i = 0;
         for (TextField edit : edits) {
                 final int k = ++i;
                 edit.setOnKeyReleased( e -> kasitteleMuutosJaseneen(k, (TextField)(e.getSource())));
         }
    }

    @Override
    public Jasen getResult() {
        return jasenKohdalla;
    }


    @Override
    public void setDefault(Jasen oletus) {
        jasenKohdalla = oletus;
        naytaJasen(oletus);
    }


    @Override
    public void handleShown() {
        editEtunimi.requestFocus();

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
     * Käsitellään jäseneen tullut muutos
     * @param edit muuttunut kenttä
     */
    private void kasitteleMuutosJaseneen(int k, TextField edit) {
        if (jasenKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        switch (k) {
            case 1 : virhe = jasenKohdalla.setEtunimi(s); break;
            case 2 : virhe = jasenKohdalla.setSukunimi(s); break;
            case 3 : virhe = jasenKohdalla.setKatuosoite(s); break;
            case 4 : virhe = jasenKohdalla.setKaupunki(s); break;
            case 5 : virhe = jasenKohdalla.setPostinumero(s); break;
            case 6 : virhe = jasenKohdalla.setPuhelin(s); break;
            case 7 : virhe = jasenKohdalla.setIka(s); break;
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
     * Näytetään jäsenen tiedot TextField komponentteihin
     * @param jasen näytettävä jäsen
     */
    public void naytaJasen(Jasen jasen) {

        if (jasen == null) return;

        editEtunimi.setText(jasen.getEtunimi());
        editSukunimi.setText(jasen.getSukunimi());
        editKaupunki.setText(jasen.getKaupunki());
        editPostinumero.setText(jasen.getPostinumero());
        editOsoite.setText(jasen.getKatuosoite());
        if (jasen.getIka()==0) {
            editIka.setText("");
        }
        else {
            editIka.setText(String.valueOf(jasen.getIka()));
        }
        editPuhelin.setText(jasen.getPuhelin());


    }

    /**
     * Näyttää kaikki jäsenen kotityöt listassa
     * @param jasen, jonka kotityöt näytetään
     */
    public void naytaKotityot(Jasen jasen) throws SailoException {
        listaKotityo.clear();
        ArrayList<Kotityo> kotityolista = siivoustiimi.annaKotityot(jasen);

        for (Kotityo alkio : kotityolista) {
            listaKotityo.add(alkio.getKotityoNimi(), alkio);
        }
    }


    /**
     * Luodaan jäsenen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataan näytetään oletuksena
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */

    public static Jasen kysyJasen(Stage modalityStage, Jasen oletus, Siivoustiimi oletusTiimi) {
        return ModalController.<Jasen, MuokkaaJasenGUIController>showModal(
                MuokkaaJasenGUIController.class.getResource("/fxml/MuokkaaJasenGUIView.fxml"),
                oletus.getEtunimi()+" " +oletus.getSukunimi(),
                modalityStage, oletus, ctrl-> {
                    try {
                        ctrl.setSiivoustiimi(oletusTiimi);
                    } catch (SailoException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    private void setSiivoustiimi(Siivoustiimi oletusTiimi) throws SailoException {
        this.siivoustiimi = oletusTiimi;
        naytaKotityot(jasenKohdalla);
    }

}

