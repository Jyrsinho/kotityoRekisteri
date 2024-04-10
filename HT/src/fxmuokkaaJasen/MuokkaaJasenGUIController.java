package fxmuokkaaJasen;
import Siivoustiimi.Jasen;
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

import java.io.FileNotFoundException;
import java.net.URL;
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
    @FXML private Button buttonMuokkaaKotityö;

    @FXML private Button buttonCancel;

    @FXML private Button buttonPoistaJasen;

    @FXML private Button buttonTallenna;

    @FXML private Button buttonuusiJasen;

    @FXML private ListChooser<?> naytaKotityo;

    @FXML private TextField textHaku;

    @FXML private ListChooser<Jasen> listaJasenet;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alusta();
    }

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

        Dialogs.showMessageDialog("Vielä ei osata tallentaa");
        ModalController.closeStage(buttonCancel);

    }

    @FXML void klikkaaCancel(MouseEvent event) {
        ModalController.closeStage(buttonCancel);

    }

    /**
     * Avaa Lisää jäsen ikkunan
     * @param event
     */
    @FXML void klikkaaUusiJasen(MouseEvent event) {

        Dialogs.showMessageDialog("Vielä ei osata avata Lisää Jäsen ikkunaa");
        }

    /**
     * Valitaan listasta jäsen.
     * @param event
     */
    @FXML void klikkaaValitseJasen(MouseEvent event) {


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

    private Siivoustiimi siivoustiimi;

    private String siivoustiiminnimi = "siivousperhe";

    /**
     * Tekee tarvittavat muut alustukset.
     */
    protected void alusta() {

    }


    /**
     * @param siivoustiimi siivoustiimi jota käytetään tässä käyttöliittymässä
     */

    public void setSiivoustiimi(Siivoustiimi siivoustiimi) {
        this.siivoustiimi = siivoustiimi;
        naytaJasen(jasenKohdalla);
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
    }




    /**
     * Luodaan jäsenen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * TODO: korjattava toimimaan
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataan näytetään oletuksena
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */

    public static Jasen kysyJasen(Stage modalityStage, Jasen oletus) {
        return ModalController.showModal(
                MuokkaaJasenGUIController.class.getResource("MuokkaaJasenGUIView.fxml"),
                "Muokkaa Jasen",
                modalityStage, oletus, null
        );

    }

}

