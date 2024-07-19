package fxAloitusnakyma;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * @author jyrihuhtala
 * @version 9.2.2024
 */
public class AloitusnakymaGUIController implements ModalControllerInterface<String> {

    @FXML private Button buttonCancel;

    @FXML private Button buttonOK;

    @FXML private TextField textTiiminNimi;
    private String vastaus = null;

    /**
     * Peruuttaa aloitusikkunan avaamisen ja sulkee ohjelman.
     * * @param event
     */
    @FXML void clickCancel(MouseEvent event) {
      //  Dialogs.showMessageDialog("Vielä ei osata peruuttaa.");
        ModalController.closeStage(textTiiminNimi);

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
