package fxPaaikkuna;

import Siivoustiimi.Siivoustiimi;
import fi.jyu.mit.fxgui.*;
import fxAloitusnakyma.AloitusnakymaGUIController;
import fxLisaaKotityo.LisaaKotityoGUIController;
import fxmuokkaaJasen.MuokkaaJasenGUIController;
import fxlisaaSuoritus.lisaaSuoritusGUIController;
import fxmuokkaakotityo.muokkaakotityoGUIController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Siivoustiimi.Jasen;
import Siivoustiimi.Suoritus;
import Siivoustiimi.SailoException;
import Siivoustiimi.Kotityo;

/**
 * Luokka siivoustiimin käyttöliittymän tapahtumien hoitamiseksi
 * @author jyrihuhtala
 * @version 15.2.2024
 */
public class PaaikkunaGUIController implements ModalControllerInterface<String>, Initializable {

    @FXML public Label aikaNyt;

    @FXML private ScrollPane panelJasen;

    @FXML private ScrollPane panelKotityo;

    @FXML private ScrollPane panelSuoritus;

    @FXML private ListChooser<Jasen> listaJasenet;

    @FXML private ListChooser<Suoritus> listaTehty;

    @FXML private ListChooser<Kotityo> listaTekematta;

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
     *
     * @param event
     */
    @FXML
    void lisaaJasenKlikkaus(MouseEvent event) {

        // ModalController.showModal(lisaaJasenGUIController.class.getResource("lisaaJasenGUIView.fxml"), "Lisää Jäsen",null, "");
        uusiJasen();

    }

    /**
     * Avaa kotityön lisäys ikkunan.
     *
     * @param event
     */
    @FXML
    void lisaaKotityklikkaus(MouseEvent event) {
        //  ModalController.showModal(lisaaKotityoGUIController.class.getResource("lisaaKotityoGUIView.fxml"),"Lisää Kotityö",null,"");
        uusiKotityo();
    }

    /**
     * Avaa lisää suoritus ikkunan.f
     *
     * @param event
     */
    @FXML
    void lisaaSuoritusKlikkaus(MouseEvent event) {

        //ModalController.showModal(lisaaSuoritusGUIController.class.getResource("lisaaSuoritusGUIView.fxml"), "Lisää Suoritus", null, "");
        uusiSuoritus();
    }


    /**
     * Tuplaklikkaus valitsee kotityön muokattavaksi.
     *
     * @param event
     */
    @FXML
    void klikkaaTehty(MouseEvent event) {
        //  Dialogs.showMessageDialog("Ei osata vielä avata valita kotityötä.");

    }

    /**
     * Tuplaklikkaus valitsee kotityön muokattavaksi.
     *
     * @param event
     */
    @FXML
    void klikkaaTekematta(MouseEvent event) {
        //   Dialogs.showMessageDialog("Ei osata vielä avata valita kotityötä.");

    }

    /**
     * Tuplaklikkaus valitsee jäsenen muokattavaksi.
     *
     * @param event
     */
    @FXML
    void klikkaaValitseJasen(MouseEvent event) {

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

        // ModalController.showModal(lisaaJasenGUIController.class.getResource("lisaaJasenGUIView.fxml"), "Lisää Jäsen", null, "");
        uusiJasen();
    }

    @FXML
    void menuKlikkaaLisaaKotityo(ActionEvent event) {
       // ModalController.showModal(lisaaKotityoGUIController.class.getResource("lisaaKotityoGUIView.fxml"), "Lisää Kotityö", null, "");
       uusiKotityo();
    }

    @FXML
    void menuKlikkaaMuokkaaJasen(ActionEvent event) {

        muokkaaJasen();
    }

    @FXML
    void menuKlikkaaPoistaJasen(ActionEvent event) {
        //Dialogs.showMessageDialog("Vielä ei osata poistaa jäsentä.");
        poistaJasen();
    }

    @FXML
    void menuKlikkaaPoistaKotityo(ActionEvent event) {
        poistaKotityo();
    }

    @FXML
    void menuKlikkaaMuokkaaKotityo(ActionEvent event) {

        ModalController.showModal(muokkaakotityoGUIController.class.getResource("muokkaakotityoGUIView.fxml"), "Muokkaa kotityö", null, "");
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
    void menuklikkaaTAllenna(ActionEvent event) throws FileNotFoundException {
        tallenna();
    }


    public void initialize(URL url, ResourceBundle bundle) {
        //
        alusta();
    }


    //----------------------------------------------------------------------

    private String siivoustiiminnimi = "siivousperhe";

    private Siivoustiimi siivoustiimi;
    private Jasen jasenKohdalla;
    private Kotityo kotityoKohdalla;
    private Suoritus suoritusKohdalla;

    private TextArea areaJasen = new TextArea();
    private TextArea areaKotityo = new TextArea();
    private TextArea areaSuoritus = new TextArea();

    /**
     * Tekee alustukset. Tekee tekstikentät joihin voidaan
     * tulostaa jäsenten, kotitöiden ja suoritusten tiedot.
     */
    protected void alusta() {


        panelKotityo.setContent(areaKotityo);
        panelKotityo.setFitToHeight(true);

        panelSuoritus.setContent(areaSuoritus);
        panelSuoritus.setFitToHeight(true);

        listaJasenet.clear();
        listaJasenet.addSelectionListener(e -> naytaJasen());

        listaTekematta.clear();
        listaTekematta.addSelectionListener(e -> naytaKotityo());

        listaTehty.clear();
        listaTehty.addSelectionListener(e -> naytaSuoritus());
    }


    /**
     * Alustaa siivoustiimin lukemalla sen valitun nimisestä tiedostosta
     *
     * @param nimi tiedosto josta kerhon tiedot luetaan
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    protected String lueTiedosto(String nimi) {
        siivoustiiminnimi = nimi;
        try {
            siivoustiimi.lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage();
            if (virhe != null) Dialogs.showMessageDialog(virhe);
            return virhe;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void tallenna() throws FileNotFoundException {
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
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }

    /**
     * @param siivoustiimi siivoustiimi jota käytetään tässä käyttöliittymässä
     */

    public void setSiivoustiimi(Siivoustiimi siivoustiimi) {
        this.siivoustiimi = siivoustiimi;
        naytaJasen();
        naytaKotityo();
        naytaSuoritus();
    }

    /**
     * Avaa ikkunan, jossa jäseneen asetetaan uudet tiedot
     */
    private void muokkaaJasen(){
        if (jasenKohdalla == null) return;
        try {
            Jasen jasen;
            jasen = MuokkaaJasenGUIController.kysyJasen(null, jasenKohdalla.clone(), siivoustiimi);
            if (jasen == null) return;
            siivoustiimi.korvaaTaiLisaa(jasen);
            hae(jasen.getId());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }

    /*
    private void muokkaaKotityo(){
        if (kotityoKohdalla == null) return;
        try {
            Kotityo kotityo;
            kotityo =  muokkaakotityoGUIController.kysyKotityo(null, kotityoKohdalla.clone(), siivoustiimi);
            if (kotityo== null) return;
            siivoustiimi.korvaa(kotityo);
            hae(kotityo.getKotityoID());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }

     */


    /**
     * Näyttää listasta valitun jäsenen tiedot, tilapäisesti yhteen isoon edit-kenttään
     */

    private void naytaJasen() {
        jasenKohdalla = listaJasenet.getSelectedObject();

        if (jasenKohdalla == null) {
            return;
        }

        haeJasenenKotityot(jasenKohdalla.getId());
    }




    /**
     * Näyttää listasta valitun kotityön tiedot, tilapäisesti yhteen isoon edit-kenttään
     */
    private void naytaKotityo() {

        kotityoKohdalla = listaTekematta.getSelectedObject();

        if (kotityoKohdalla == null) return;

        haeKotityonSuoritukset(kotityoKohdalla.getKotityoID());


        areaKotityo.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaKotityo)) {
            kotityoKohdalla.tulosta(os);
        }
    }

    /**
     * Näyttää listasta valitun suorituksen tiedot, tilapäisesti yhteen isoon edit-kenttään
     */
    private void naytaSuoritus() {

        suoritusKohdalla = listaTehty.getSelectedObject();

        if (suoritusKohdalla == null) return;

        areaSuoritus.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaSuoritus)) {
            suoritusKohdalla.tulosta(os);

        }
    }


    /**
     * Hakee jäsenten tiedot listaan
     *
     * @param jnro jäsenen numero, joka aktivoidaan haun jälkeen
     */
    protected void hae(int jnro) {

        listaJasenet.clear();


        for (int i = 0; i < siivoustiimi.getJasenia(); i++) {
            Jasen jasen = siivoustiimi.annaJasen(i);
            listaJasenet.add(jasen.getSukunimi()+" "+ jasen.getEtunimi(), jasen);
        }
    }

    /**
     * Hakee yhden jäsenen kotityöt listaan tekemättömistä kotitöistä.
     */
    private void haeJasenenKotityot(int jasenID) {
        listaTekematta.clear();

        ArrayList<Kotityo> kotityolista = siivoustiimi.annaKotityot(jasenID);

        for (Kotityo alkio : kotityolista) {
            listaTekematta.add(alkio.getKotityoNimi(), alkio);
        }
    }

    /**
     * Hakee valitun kotityön suoritukset listaan tehdyistä suorituksista.
     * @param kotityoID
     */
    private void haeKotityonSuoritukset(int kotityoID) {
        listaTehty.clear();

        ArrayList<Suoritus> suorituslista = siivoustiimi.annaSuoritukset(kotityoID);

        for (Suoritus alkio : suorituslista) {
            listaTehty.add(alkio.getViimeisinSuoritus(), alkio);
        }
    }


    /**
     * Luo uuden jäsenen
     */
    private void uusiJasen() {
        try {
            Jasen uusi = new Jasen();
            uusi = MuokkaaJasenGUIController.kysyJasen(null, uusi, siivoustiimi);
            if (uusi==null) return;
            uusi.rekisteroi();
            siivoustiimi.lisaa(uusi);
            hae(uusi.getId());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
    }

    /**
     * Luo uuden kotityon
     */
    private void uusiKotityo() {
        Kotityo kottyo = new Kotityo();
        kottyo = LisaaKotityoGUIController.kysyKotityo(null, kottyo, siivoustiimi);
        if (kottyo == null) return;
        kottyo.rekisteroi();
        siivoustiimi.lisaa(kottyo);
        naytaJasen();
        haeJasenenKotityot(jasenKohdalla.getId());

    }


    /**
     * Luo uuden suorituksen
     */
    private void uusiSuoritus() {
        Suoritus suoritus = new Suoritus();
        suoritus = lisaaSuoritusGUIController.kysySuoritus(null, suoritus, siivoustiimi);
        if (suoritus == null) return;
        suoritus.rekisteroiSuoritus();
        siivoustiimi.lisaa(suoritus);
        haeJasenenKotityot(jasenKohdalla.getId());
    }

    /**
     * poistaa Kotityön tietorakenteesta.
     */
    private void poistaKotityo() {

        Kotityo kotityo = kotityoKohdalla;
        if (kotityo == null) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko kotityö: " + kotityo.getKotityoNimi(), "Kyllä", "Ei") )
            return;
        siivoustiimi.poistaKotityo(kotityo);
        hae(jasenKohdalla.getId());
    }

    /**
     * poistaa jasenen tietorakenteesta.
     */
    private void poistaJasen() {
        Jasen jasen = jasenKohdalla;
        if (jasen==null) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko kotityö: " + jasen.getNimi(), "Kyllä", "Ei") )
            return;
        siivoustiimi.poista(jasen);
        int index = listaJasenet.getSelectedIndex();
        hae(0);
        listaJasenet.setSelectedIndex(index);
    }
}




