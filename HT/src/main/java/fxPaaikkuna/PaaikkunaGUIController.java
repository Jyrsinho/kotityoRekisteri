package fxPaaikkuna;


import Siivoustiimi.Jasen;
import Siivoustiimi.Suoritus;
import Siivoustiimi.Kotityo;
import Siivoustiimi.SailoException;
import Siivoustiimi.Siivoustiimi;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fxAloitusnakyma.AloitusnakymaGUIController;
import fxMuokkaaKotityo.MuokkaaKotityoGUIController;
import fxlisaaSuoritus.lisaaSuoritusGUIController;
import fxmuokkaaJasen.MuokkaaJasenGUIController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Luokka siivoustiimin käyttöliittymän tapahtumien hoitamiseksi
 * @author jyrihuhtala
 * @version 15.2.2024
 */
public class PaaikkunaGUIController implements ModalControllerInterface<String>, Initializable {

    @FXML
    public Label aikaNyt;

    @FXML
    private ListChooser<Jasen> listaJasenet;

    @FXML
    private ListChooser<Kotityo> listaTehty;

    @FXML
    private ListChooser<Kotityo> listaTekematta;

    @FXML
    private Button buttonLisaaJasen;

    @FXML
    private Button buttonLisaaKotityo;

    @FXML
    private Button buttonLisaaSuoritus;

    @FXML
    private MenuItem menuApua;

    @FXML
    private MenuItem menuAvaa;

    @FXML
    private MenuItem menuLisaaJasen;

    @FXML
    private MenuItem menuLisaaKotityo;

    @FXML
    private MenuItem menuLopeta;

    @FXML
    private MenuItem menuMuokkaaJasen;

    @FXML
    private MenuItem menuMuokkaaKotityo;

    @FXML
    private MenuItem menuPoistaJasen;

    @FXML
    private MenuItem menuPoistaKotityo;

    @FXML
    private MenuItem menuTallenna;

    @FXML
    private MenuItem menuTulosta;


    /**
     * Avaa jäsenen lisäys ikkunan.
     * @param event tapahtuma jasenen lisays nappulan painamiselle
     */
    @FXML
    void lisaaJasenKlikkaus(MouseEvent event) {
        uusiJasen();
    }

    /**
     * Avaa kotityön lisäys ikkunan.
     * @param event tapahtuma kotityon lisays nappulan painamiselle
     */
    @FXML
    void lisaaKotityklikkaus(MouseEvent event) throws SailoException {
        uusiKotityo();
    }

    /**
     * Avaa lisää suoritus ikkunan.
     * @param event tapahtuma suorituksen lisays nappulan painamiselle.
     */
    @FXML
    void lisaaSuoritusKlikkaus(MouseEvent event) throws SailoException, SQLException {
        uusiSuoritus();
    }


    @FXML
    void menuKlikkaaAvaa(ActionEvent event) {

        ModalController.showModal(AloitusnakymaGUIController.class.getResource("/fxml/Aloitusnakyma.fxml"), "Aloita", null, "");

    }

    @FXML
    void klikkaaApua(ActionEvent event) {
        avustus();
    }

    @FXML
    void menuKlikkaaLisaaJasen(ActionEvent event) {

        uusiJasen();
    }

    @FXML
    void menuKlikkaaLisaaKotityo(ActionEvent event) throws SailoException {
        uusiKotityo();
    }

    @FXML
    void menuKlikkaaMuokkaaJasen(ActionEvent event) throws SailoException {

        muokkaaJasen();
    }

    @FXML
    void menuKlikkaaPoistaJasen(ActionEvent event) throws SailoException {
        poistaJasen();
    }

    @FXML
    void menuKlikkaaPoistaKotityo(ActionEvent event) throws SailoException {
        poistaKotityo();
    }

    @FXML
    void menuKlikkaaMuokkaaKotityo(ActionEvent event) throws SailoException {
        muokkaaKotityo();
    }

    @FXML
    void menuKlikkaaTulosta(ActionEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata tulostaa");
    }

    @FXML
    void menuKlikkaaLopeta(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void menuklikkaaTAllenna(ActionEvent event) {

    }

    @FXML
    public void valitseKotityoTekemattaListasta(MouseEvent mouseEvent) throws SailoException {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 1) {
                kotityoKohdalla = listaTehty.getSelectedObject();
            }else if (mouseEvent.getClickCount() == 2) {
                muokkaaKotityo();
            }
        }

    }

    @FXML
    public void valitseKotityoTehtyListasta(MouseEvent mouseEvent) {
        kotityoKohdalla = listaTehty.getSelectedObject();
    }


    @FXML
    void klikkaaValitseJasen(MouseEvent event) throws SailoException {
        if (event.getButton().equals(MouseButton.PRIMARY)){
            if (event.getClickCount() == 1) {
                jasenKohdalla = listaJasenet.getSelectedObject();
            } else if (event.getClickCount() == 2) {
                muokkaaJasen();
            }
        }
    }


    public void initialize(URL url, ResourceBundle bundle) {
        try {
            alusta();
        } catch (SailoException e) {
            throw new RuntimeException(e);
        }

    }


    //----------------------------------------------------------------------

    private Siivoustiimi siivoustiimi;
    private Jasen jasenKohdalla;
    private Kotityo kotityoKohdalla;


    /**
     * Tekee alustukset. Tekee tekstikentät joihin voidaan
     * tulostaa jäsenten, kotitöiden ja suoritusten tiedot.
     */
    protected void alusta() throws SailoException {

        aikaNyt.setText(String.valueOf(LocalDate.now()));
        listaJasenet.clear();
        listaJasenet.addSelectionListener(e -> {
            try {
                naytaJasen();
            } catch (SailoException ex) {
                throw new RuntimeException(ex);
            }
        });
    }


    /**
     * Alustaa siivoustiimin lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta kerhon tiedot luetaan
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    protected String lueTiedosto(String nimi) {

        try {
            siivoustiimi.lueTiedostosta(nimi);
            hae();
            return null;
        } catch (SailoException e) {
            String virhe = e.getMessage();
            if (virhe != null) Dialogs.showMessageDialog(virhe);
            return virhe;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * @return false jos painetaan cancel.
     */
    public boolean avaa() {

        String uusinimi = AloitusnakymaGUIController.kysyNimi(null, "testitiimi");
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
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


    /**
     * Näytetään ohjelman suunnitelma erillisessä selaimessa.
     */
    private void avustus() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2024k/ht/huhtjyil");
            desktop.browse(uri);
        } catch (URISyntaxException | IOException e) {
            Dialogs.showMessageDialog("Verkkosivua ei löydy! " + e.getMessage());
        }
    }

    /**
     * @param siivoustiimi siivoustiimi jota käytetään tässä käyttöliittymässä
     */

    public void setSiivoustiimi(Siivoustiimi siivoustiimi) {
        this.siivoustiimi = siivoustiimi;
    }


    /**
     * Näyttää listasta valitun jäsenen kotityöt.
     */
    private void naytaJasen() throws SailoException {

        jasenKohdalla = listaJasenet.getSelectedObject();

        if (jasenKohdalla == null) {
            return;
        }
        haeJasenenKotityot(jasenKohdalla);
    }


    /**
     * Hakee kaikki siivosutiimin jasenoliot listaan
     */
    protected void hae() throws SailoException {

        listaJasenet.clear();
        listaTekematta.clear();
        listaTehty.clear();

        // Luodaan  Collection johon haetaan tietokannasta
        // kaikki jasentaulukon oliot.

        Collection<Jasen> kaikkiJasenet = siivoustiimi.etsiJasenet("", 1);

        for (Jasen jasen : kaikkiJasenet) {
            listaJasenet.add(jasen.getNimi(), jasen);
        }
    }


    /**
     * Hakee yhden jäsenen kaikki kotityöt ja järjestelee ne kahteen listaan.
     * Tehtyihin kotitöihin ja tekemättömiin kotitöihin.
     */
    private void haeJasenenKotityot(Jasen jasen) throws SailoException {
        listaTekematta.clear();
        listaTehty.clear();

        ArrayList<Kotityo> kotityolista = siivoustiimi.annaKotityot(jasen);

        for (Kotityo alkio : kotityolista) {
            if (alkio.suoritusOnVanhentunut()) {
                listaTekematta.add(alkio.getKotityoNimi(), alkio);
            } else {
                listaTehty.add(alkio.getKotityoNimi(), alkio);
            }
        }
    }


    /**
     * Luo uuden jäsenen
     */
    private void uusiJasen() {

        try {
            Jasen uusi = new Jasen();
            uusi = MuokkaaJasenGUIController.kysyJasen(null, uusi, siivoustiimi);
            if (uusi == null) return;
            siivoustiimi.lisaa(uusi);
            hae();
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());

        }
    }

    /**
     * Muokkaa jasenen tietoja
     * @throws SailoException
     */
    private void muokkaaJasen () throws SailoException {

        Jasen muokattava = listaJasenet.getSelectedObject();
        muokattava = MuokkaaJasenGUIController.kysyJasen(null, muokattava, siivoustiimi);
        if (muokattava == null) { return; }

        siivoustiimi.paivitaJasen(muokattava);
        hae();
    }

    /**
     * Muokkaa kotityon tietoja
     */
    private void muokkaaKotityo() throws SailoException {
        Kotityo muokattava = listaTekematta.getSelectedObject();
        if (muokattava == null) {
            muokattava = listaTehty.getSelectedObject();
        }
        muokattava = MuokkaaKotityoGUIController.kysyKotityo(null, muokattava, siivoustiimi);
        if (muokattava == null) { return; }

        siivoustiimi.paivitaKotityo(muokattava);

    }

    /**
     * Luo uuden kotityon
     */
    private void uusiKotityo() throws SailoException {

        Kotityo kottyo = new Kotityo();
        kottyo = MuokkaaKotityoGUIController.kysyKotityo(null, kottyo, siivoustiimi);
        if (kottyo == null) return;
        siivoustiimi.lisaa(kottyo);

    }


    /**
     * Luo uuden suorituksen
     */
    private void uusiSuoritus() throws SailoException, SQLException {

        Suoritus suoritus = new Suoritus();
        suoritus = lisaaSuoritusGUIController.kysySuoritus(null, suoritus, siivoustiimi);
        if (suoritus == null) return;
        siivoustiimi.lisaa(suoritus);

        int muutettavanKotityonID = suoritus.getKotityoID();
        siivoustiimi.paivitaKotityonViimeisinSuoritus(muutettavanKotityonID);
    }


    /**
     * poistaa jasenen tietorakenteesta.
     */
    private void poistaJasen() throws SailoException {
        Jasen jasen = jasenKohdalla;
        if (jasen == null) return;
        if (!Dialogs.showQuestionDialog("Poisto", "Poistetaanko: " + jasen.getNimi() + " ja kaikki jäsenen kotityöt", "Kyllä", "Ei"))
            return;
        siivoustiimi.poista(jasen);
        alusta();
        hae();
    }

    /**
     * poistaa Kotityön tietorakenteesta.
     */
    private void poistaKotityo() throws SailoException {

        if (listaTekematta.getSelectedObject() != null)kotityoKohdalla = listaTekematta.getSelectedObject();
        if (listaTehty.getSelectedObject() !=  null) kotityoKohdalla = listaTehty.getSelectedObject();
        Kotityo kotityo = kotityoKohdalla;
        if (kotityo == null) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko kotityö: " + kotityo.getKotityoNimi(), "Kyllä", "Ei") )
            return;
        siivoustiimi.poistaKotityo(kotityo);
        haeJasenenKotityot(jasenKohdalla);

    }

}




