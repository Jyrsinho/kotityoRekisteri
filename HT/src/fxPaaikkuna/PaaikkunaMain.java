package fxPaaikkuna;

import Siivoustiimi.Siivoustiimi;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author jyrihuhtala
 * @version 26.1.2024
 */
public class PaaikkunaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("PaaikkunaGUIView.fxml"));
            final Pane root = ldr.load();
            final PaaikkunaGUIController paaikkunaCtrl = (PaaikkunaGUIController)ldr.getController();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("paaikkuna.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Paaikkuna");

            Siivoustiimi siivoustiimi = new Siivoustiimi();
            paaikkunaCtrl.setSiivoustiimi(siivoustiimi);

            primaryStage.show();
            if ( !paaikkunaCtrl.avaa() ) Platform.exit();

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