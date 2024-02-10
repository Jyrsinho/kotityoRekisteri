package fxPaaikkuna;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
/**
 * @author jyrihuhtala
 * @version 26.1.2024
 */
public class PaaikkunaGUIController {
    @FXML private ListChooser<?> listaJasenet;

    @FXML private ListChooser<?> listaTehty;

    @FXML private ListChooser<?> listaTekematta;

    @FXML private Button buttonLisaaJasen;

    @FXML private Button buttonLisaaKotityo;

    @FXML private Button buttonLisaaSuoritus;

    /**
     * Avaa jäsenen lisäys ikkunan.
     * @param event
     */
    @FXML void lisaaJasenKlikkaus(MouseEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä avata lisää jäsen ikkunaa.");
    }

    /**
     * Avaa kotityön lisäys ikkunan.
     * @param event
     */
    @FXML void lisaaKotityklikkaus(MouseEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä avata lisää kotityö ikkunaa");
    }

    /**
     * Avaa lisää suoritus ikkunan.
     * @param event
     */
    @FXML void lisaaSuoritusKlikkaus(MouseEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä avata lisää suoritus ikkunaa");
    }


    /**
     * Tuplaklikkaus valitsee kotityön muokattavaksi.
     * @param event
     */
    @FXML void klikkaaTehty(MouseEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä avata valita kotityötä.");

    }

    /**
     * Tuplaklikkaus valitsee kotityön muokattavaksi.
     * @param event
     */
    @FXML
    void klikkaaTekematta(MouseEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä avata valita kotityötä.");

    }

    /**
     * Tuplaklikkaus valitsee jäsenen muokattavaksi.
     * @param event
     */
    @FXML
    void klikkaaValitseJasen(MouseEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä valita jäsentä");

    }
}

