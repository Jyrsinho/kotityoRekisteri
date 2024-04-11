package fxmuokkaaJasen;
import Siivoustiimi.Jasen;
import Siivoustiimi.Kotityo;
import Siivoustiimi.Siivoustiimi;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Siivoustiimi.SailoException;
import javafx.scene.control.Label;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
/**
 * @author jyrihuhtala
 * @version 10.2.2024
 */

/**
 * Ikkunassa voidaan muokata jäsenten tietoja.
 */
    public class MuokkaaJasenGUIController implements ModalControllerInterface<Jasen>, Initializable {

    public TextField editNimi;
    public TextField editOsoite;
    public TextField editPostinumero;
    public TextField editKaupunki;
    public TextField editIka;
    public TextField editPuhelin;

    @FXML private Label labelVirhe;

    @FXML private Button buttonMuokkaaKotityö;

    @FXML private Button buttonCancel;


    @FXML private Button buttonTallenna;

    @FXML private ListChooser<Kotityo> listaKotityo;



        /**
        * Haetaan jäsenlistasta kirjoitettua hakuehtoa vastaavia jäseniä.
        * @param event
        */
        @FXML void haeJasen(KeyEvent event) {

             Dialogs.showMessageDialog("Vielä ei osata hakea jäseniä");

        }


        /**
        * Avaa muokkaa kotityö ikkunan valitun kotityön kohdalta.
        * @param event
        */
        @FXML void klikkaaMuokkaaKotityo(MouseEvent event) {

            Dialogs.showMessageDialog("Vielä ei osata avata muokkaa kotityö ikkunaa");

        }


        /**
        * Poistaa valitun jösenen listasta ja tiedostosta.
        * @param event
        */
        @FXML void klikkaaPoistaJasen(MouseEvent event) {

            Dialogs.showMessageDialog("Vielä ei osata poistaa jäsentä");

        }

    /**
     * tallentaa muokattavaksi valitun jäsenen tiedot tiedostoon.
     * @param event
     */
    @FXML void klikkaaTallenna(MouseEvent event) {

        if(jasenKohdalla != null && jasenKohdalla.getNimi().trim().equals("")) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }

    @FXML void klikkaaCancel(MouseEvent event) {
        jasenKohdalla = null;
        ModalController.closeStage(labelVirhe);

    }


    /**
     * Valitaan listasta kotityö.
     * @param event
     */
        @FXML void klikkaaValitseKotityö(MouseEvent event) {

            Dialogs.showMessageDialog("Vielä ei osata valita kotityötä.");

        }




//------------------------------------------------------

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

         edits = new TextField[]{editNimi, editOsoite, editKaupunki, editPostinumero, editPuhelin, editIka};
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
        editNimi.requestFocus();

    }

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
            case 1 : virhe = jasenKohdalla.setSukunimi(s); break;
            case 2 : virhe = jasenKohdalla.setEtunimi(s); break;
            case 3 : virhe = jasenKohdalla.setKatuosoite(s); break;
            case 4 : virhe = jasenKohdalla.setKaupunki(s); break;
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

        editNimi.setText(jasen.getNimi());
        editKaupunki.setText(jasen.getKaupunki());
        editPostinumero.setText(jasen.getPostinumero());
        editOsoite.setText(jasen.getKatuosoite());
        editIka.setText(String.valueOf(jasen.getIka()));
        editPuhelin.setText(jasen.getPuhelin());


    }

    //TODO Näytä käyttäjän kotityöt listassa

    public void naytaKotityot(Jasen jasen) {
        listaKotityo.clear();
        ArrayList<Kotityo> kotityolista = siivoustiimi.annaKotityot(jasen.getId());

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
                MuokkaaJasenGUIController.class.getResource("MuokkaaJasenGUIView.fxml"),
                oletus.getEtunimi()+"" +oletus.getSukunimi(),
                modalityStage, oletus, ctrl-> ctrl.setSiivoustiimi(oletusTiimi)
        );
    }

    private void setSiivoustiimi(Siivoustiimi oletusTiimi) {
        this.siivoustiimi = oletusTiimi;
    }
}

