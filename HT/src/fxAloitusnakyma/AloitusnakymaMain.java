package fxAloitusnakyma;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author jyrihuhtala
 * @version 26.1.2024
 */
public class AloitusnakymaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("AloitusnakymaGUIView.fxml"));
            final Pane root = ldr.load();
            //final AloitusnakymaGUIController aloitusnakymaCtrl = (AloitusnakymaGUIController)ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("aloitusnakyma.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Aloitusnakyma");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}