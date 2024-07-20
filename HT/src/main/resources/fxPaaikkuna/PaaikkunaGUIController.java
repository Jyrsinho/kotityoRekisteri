package fxPaaikkuna;


import Siivoustiimi.Siivoustiimi;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fxAloitusnakyma.AloitusnakymaGUIController;
import fxLisaaKotityo.LisaaKotityoGUIController;
import fxmuokkaaJasen.MuokkaaJasenGUIController;
//import fxlisaaSuoritus.lisaaSuoritusGUIController;
// import fxmuokkaakotityo.muokkaakotityoGUIController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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

import Siivoustiimi.Jasen;
import Siivoustiimi.SailoException;
import Siivoustiimi.Kotityo;

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
    void lisaaSuoritusKlikkaus(MouseEvent event) throws SailoException {
        uusiSuoritus();
    }


    @FXML
    void menuKlikkaaAvaa(ActionEvent event) {

        ModalController.showModal(AloitusnakymaGUIController.class.getResource("Aloitusnakyma.fxml"), "Aloita", null, "");

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
    void menuKlikkaaMuokkaaJasen(ActionEvent event) {

        //muokkaaJasen();
        Dialogs.showMessageDialog("Vielä ei osata muokata jäsentä");
    }

    @FXML
    void menuKlikkaaPoistaJasen(ActionEvent event) throws SailoException {
        poistaJasen();
    }

    @FXML
    void menuKlikkaaPoistaKotityo(ActionEvent event) {
        poistaKotityo();
    }

    @FXML
    void menuKlikkaaMuokkaaKotityo(ActionEvent event) {
        Dialogs.showMessageDialog("Vielä ei muokata kotityota");
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
        tallenna();
    }

    @FXML
    public void valitseKotityoTekemattaListasta(MouseEvent mouseEvent) {
        kotityoKohdalla = listaTehty.getSelectedObject();
    }

    @FXML
    public void valitseJasenTehtyListasta(MouseEvent mouseEvent) {
        kotityoKohdalla = listaTehty.getSelectedObject();
    }

    @FXML
    void klikkaaValitseJasen(MouseEvent event) {
        jasenKohdalla = listaJasenet.getSelectedObject();
    }


    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }


    //----------------------------------------------------------------------

    private Siivoustiimi siivoustiimi;
    private Jasen jasenKohdalla;
    private Kotityo kotityoKohdalla;


    /**
     * Tekee alustukset. Tekee tekstikentät joihin voidaan
     * tulostaa jäsenten, kotitöiden ja suoritusten tiedot.
     */
    protected void alusta() {

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


    private void tallenna() {
        try {
            siivoustiimi.tallenna();
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            ex.getMessage();
        }
    }


    /**
     * @return false jos painetaan cancel.
     */
    public boolean avaa() {

        String uusinimi = AloitusnakymaGUIController.kysyNimi(null, "siivousperhe");
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
     * Hakee kaikkien siivosutiimin jäsenten tiedot listaan
     */
    protected void hae() throws SailoException {

        listaJasenet.clear();
        listaTekematta.clear();
        listaTehty.clear();

        // Luodaan  Collection johon haetaan tietokannasta
        // kaikki jasentaulukon oliot.

        Collection<Jasen> kaikkiJasenet = siivoustiimi.etsi("", 1);

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
            if (alkio.onVanhentunut()) {
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
            uusi.rekisteroi();
            siivoustiimi.lisaa(uusi);
            hae();
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());

        }
    }

    /**
     * Luo uuden kotityon
     */
    private void uusiKotityo() throws SailoException {

        Kotityo kottyo = new Kotityo();
        kottyo = LisaaKotityoGUIController.kysyKotityo(null, kottyo, siivoustiimi);
        if (kottyo == null) return;
        kottyo.rekisteroi();
        siivoustiimi.lisaa(kottyo);

    }


    /**
     * Luo uuden suorituksen
     */
    private void uusiSuoritus() throws SailoException {
       /* todo suorituksen lisaaminen
        Suoritus suoritus = new Suoritus();
        suoritus = lisaaSuoritusGUIController.kysySuoritus(null, suoritus, siivoustiimi);
        if (suoritus == null) return;
        suoritus.rekisteroiSuoritus();
        siivoustiimi.lisaa(suoritus);
        haeJasenenKotityot(jasenKohdalla);

        */
    }

    /**
     * poistaa Kotityön tietorakenteesta.
     */
    private void poistaKotityo() {

        /* todo poistamisen tekeminen
        if (listaTekematta.getSelectedObject() != null)kotityoKohdalla = listaTekematta.getSelectedObject();
        if (listaTehty.getSelectedObject() !=  null) kotityoKohdalla = listaTehty.getSelectedObject();
        Kotityo kotityo = kotityoKohdalla;
        if (kotityo == null) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko kotityö: " + kotityo.getKotityoNimi(), "Kyllä", "Ei") )
            return;
        siivoustiimi.poistaKotityo(kotityo);
        haeJasenenKotityot(jasenKohdalla.getId());

         */
    }

    /**
     * poistaa jasenen tietorakenteesta.
     */
    private void poistaJasen() throws SailoException {
        Jasen jasen = jasenKohdalla;
        if (jasen == null) return;
        if (!Dialogs.showQuestionDialog("Poisto", "Poistetaanko: " + jasen.getNimi() + " ja kaikki jäsenen kotityöt", "Kyllä", "Ei"))
            return;
        siivoustiimi.poista(jasen.getId());
        int index = listaJasenet.getSelectedIndex();
        hae();
        listaJasenet.setSelectedIndex(index);
    }

}




