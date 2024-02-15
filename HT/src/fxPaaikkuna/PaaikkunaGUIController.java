package fxPaaikkuna;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fxAloitusnakyma.AloitusnakymaGUIController;
import fxlisaaJasen.lisaaJasenGUIController;
import fxlisaaKotityo.lisaaKotityoGUIController;
import fxlisaaSuoritus.lisaaSuoritusGUIController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

/**
 * @author jyrihuhtala
 * @version 15.2.2024
 */
public class PaaikkunaGUIController implements ModalControllerInterface<String>  {

    @FXML private ListChooser<?> listaJasenet;

    @FXML private ListChooser<?> listaTehty;

    @FXML private ListChooser<?> listaTekematta;

    @FXML private Button buttonLisaaJasen;

    @FXML private Button buttonLisaaKotityo;

    @FXML private Button buttonLisaaSuoritus;

    @FXML private MenuItem menuApua;

    @FXML private MenuItem menuAvaa;

    @FXML private MenuItem menuLisaaJasen;

    @FXML private MenuItem menuLisaaKotityo;

    @FXML private MenuItem menuLopeta;

    @FXML private MenuItem menuMuokkaaJasen;

    @FXML private MenuItem menuMuokkaaKotityo;

    @FXML private MenuItem menuPoistaJasen;

    @FXML private MenuItem menuPoistaKotityo;


    @FXML private MenuItem menuTallenna;

    @FXML private MenuItem menuTulosta;

    /**
     * Avaa jäsenen lisäys ikkunan.
     * @param event
     */
    @FXML void lisaaJasenKlikkaus(MouseEvent event) {

      ModalController.showModal(lisaaJasenGUIController.class.getResource("lisaaJasenGUIView.fxml"), "Lisää Jäsen",null, "");

    }

    /**
     * Avaa kotityön lisäys ikkunan.
     * @param event
     */
    @FXML void lisaaKotityklikkaus(MouseEvent event) {
        ModalController.showModal(lisaaKotityoGUIController.class.getResource("lisaaKotityoGUIView.fxml"),"Lisää Kotityö",null,"");
    }

    /**
     * Avaa lisää suoritus ikkunan.f
     * @param event
     */
    @FXML void lisaaSuoritusKlikkaus(MouseEvent event) {

        ModalController.showModal(lisaaSuoritusGUIController.class.getResource("lisaaSuoritusGUIView.fxml"), "Lisää Suoritus", null, "");
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
    @FXML void menuKlikkaaAvaa(ActionEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata avata tiedostoa.");
    }

    @FXML void menuKlikkaaLisaaJasen(ActionEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata lisätä jäsentä");
    }
    @FXML void menuKlikkaaLisaaKotityo(ActionEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata avata Lisaa Kotityo-ikkunaa");
    }

    @FXML void menuKlikkaaMuokkaaJasen(ActionEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata avata Muokkaa Jasen-ikkunaa");
    }

    @FXML void menuKlikkaaPoistaJasen(ActionEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata poistaa jäsentä.");
    }

    @FXML void menuKlikkaaMuokkaaKotityo(ActionEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata avata Muokkaa Kotityö-ikkunaa");
    }

    @FXML void menuKlikkaaPoistaKotityo(ActionEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata poistaa kotitöitä.");
    }

    @FXML void menuKlikkaaTulosta(ActionEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata tulostaa");
    }
    @FXML void menuKlikkaaLopeta(ActionEvent event) {
        Platform.exit();
    }

    @FXML void menuklikkaaTAllenna(ActionEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata tallentaa tiedostoa");

    }

    /**
     * @return false jos painetaan cancel.
     */
    public boolean avaa() {

        String uusinimi = AloitusnakymaGUIController.kysyNimi(null, "siivousperhe");
        if (uusinimi == null) return false;
       // lueTiedosto(uusinimi); EI OSATA VIELÄ
        return true;
    }

    @Override
    public String getResult() {
        return null;
    }

    @Override
    public void setDefault(String s) {

    }

    @Override
    public void handleShown() {

    }
}

