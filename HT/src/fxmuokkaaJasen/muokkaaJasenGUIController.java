package fxmuokkaaJasen;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
/**
 * @author jyrihuhtala
 * @version 10.2.2024
 */

/**
 * Ikkunassa voidaan muokata jäsenten tietoja.
 */
    public class muokkaaJasenGUIController {

        @FXML private Button buttonMuokkaaKotityö;

        @FXML private Button buttonPoistaJasen;

        @FXML private Button buttonTallenna;

        @FXML private Button buttonuusiJasen;

        @FXML private ListChooser<?> naytaKotityo;

        @FXML private TextField textHaku;

        @FXML private ListChooser<?> valitseJasen;

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

        Dialogs.showMessageDialog("Vielä ei osata valita jäsentä.");

    }

    /**
     * Valitaan listasta kotityö.
     * @param event
     */
        @FXML void klikkaaValitseKotityö(MouseEvent event) {

            Dialogs.showMessageDialog("Vielä ei osata valita kotityötä.");


        }

    }

