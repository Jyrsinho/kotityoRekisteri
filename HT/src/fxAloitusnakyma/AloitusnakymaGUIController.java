package fxAloitusnakyma;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * @author jyrihuhtala
 * @version 9.2.2024
 */
public class AloitusnakymaGUIController implements ModalControllerInterface<String> {

    @FXML private Button buttonCancel;

    @FXML private Button buttonOK;

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


    @FXML private TextField textTiiminNimi;
    private String vastaus = null;


    @FXML void avaaApua(ActionEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata avata apuikkunaa.");
    }

    @FXML void avaaJasenet(ActionEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata näyttää jäseniä.");
    }



    /**
     * Peruuttaa aloitusikkunan avaamisen ja sulkee ohjelman.
     * * @param event
     */
    @FXML void clickCancel(MouseEvent event) {
      //  Dialogs.showMessageDialog("Vielä ei osata peruuttaa.");
        ModalController.closeStage(textTiiminNimi);

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
        ModalController.closeStage(textTiiminNimi);
    }

    @FXML void menuklikkaaTAllenna(ActionEvent event) {
        Dialogs.showMessageDialog("Vielä ei osata tallentaa tiedostoa");

    }
    /**
     * Avaa käyttäjän valitseman siivoustiimi tiedoston ja ohjelma siirtyy päävalikkoon.
     * @param event
     */
    @FXML void okKlikkaus(MouseEvent event) {

        vastaus = textTiiminNimi.getText();
        ModalController.closeStage(textTiiminNimi);
    }

    @Override
    public String getResult() {
        return vastaus;
    }

    @Override
    public void setDefault(String oletus) {
     textTiiminNimi.setText(oletus);
    }

    /**
     * asettaa kursorin syöte-tekstipalkin kohdalle.
     */
    @Override
    public void handleShown() {
        textTiiminNimi.requestFocus();
    }

    /**
     * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä käytetään oletuksena
     * @return null jos painetaan cancel, muuten kirjoitettu nimi
     */

    public static String kysyNimi(Stage modalityStage, String oletus){
        return ModalController.showModal(
                AloitusnakymaGUIController.class.getResource("Aloitusnakyma.fxml"),
                "Aloitusikkuna",
                modalityStage, oletus);

    }
}
