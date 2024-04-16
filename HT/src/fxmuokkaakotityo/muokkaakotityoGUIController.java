package fxmuokkaakotityo;


import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fxPaaikkuna.PaaikkunaGUIController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * @author jyrihuhtala
 * @version 10.2.2024
 */
public class muokkaakotityoGUIController implements ModalControllerInterface<String> {
    public TextField editTextVanhenee;
    public TextField editTextKesto;
    public TextField editNimi;
    @FXML
    private Button buttonPoistaKotityo;

    @FXML
    private Button buttonTallenna;

    @FXML private Button buttonCancel;

    @FXML
    private Button buttonUusiKotityo;

    @FXML
    private TextField hakuKentta;

    @FXML
    private ListChooser<?> kotityoValikko;

    @FXML
    private ChoiceBox<?> valitseTekija;


    /**
     * Kirjoittamalla hakukenttään voidaan kotitöiden joukosta hakea syötettä vastaavia kotitöitä.
     * @param event
     */
    @FXML void haeKotityo(KeyEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata hakea kotitöitä");
    }

    /**
     * Avaa vetovalikon, josta voidaan valita kotityön arvioitu kestoaika.
     * @param event
     */
    @FXML void klikkaaKesto(MouseEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata muokata kestoa");
    }


    @FXML void klikkaaCancel(MouseEvent event) {

        ModalController.closeStage(buttonCancel);

    }
    /**
     * Tallentaa valittuun kotityöhön tehdyt muutokset
     * @param event
     */
    @FXML void klikkaaTallenna(MouseEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata tallentaa kotityötä");

        ModalController.closeStage(buttonCancel);
    }
    /**
     * Avaa vetovalikon, josta voidaan valita kotityölle tekijä.
     * @param event
     */
    @FXML void klikkaaTekija(MouseEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata muokata tekijää");

    }

    /**
     * Avaa lisää kotityö ikkunan.
     * @param event
     */
    @FXML void klikkaaUusiKotityo(MouseEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata avata lisää kotityö ikkunaa");

    }

    /**
     * Valitsee taulukosta muokattavaksi halutun kotityön.
     * @param event
     */
    @FXML
    void klikkaaValitseKotityo(MouseEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata valita kotityötä muokattavaksi");

    }

    /**
     * Poistaa valitun kotityön listalta ja tiedostosta.
     * @param event
     */
    @FXML
    void klikkaapoistaKotityo(MouseEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata poistaa kotityötä");

    }

    /**
     * Avaa vetovalikon, josta voidaa valita kotityölle haluttu vanhenemisaika.
     * @param event
     */
    @FXML
    void klikkaavetovalikkoVanhenee(MouseEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata muokata vanhenemisaikaa");

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