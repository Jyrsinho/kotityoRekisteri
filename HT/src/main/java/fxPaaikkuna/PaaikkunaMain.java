package fxPaaikkuna;

import Siivoustiimi.Siivoustiimi;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

import java.net.URL;
import java.util.Objects;


/**
 * @author jyrihuhtala
 * @version 26.1.2024
 */
public class PaaikkunaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {

            URL fxmlLocation = getClass().getResource("/fxml/PaaikkunaGUIView.fxml");
            System.out.println("FXML Location: " + fxmlLocation);
            FXMLLoader ldr = new FXMLLoader(Objects.requireNonNull(fxmlLocation, "FXML file not found"));
            final Pane root = ldr.load();
            final PaaikkunaGUIController paaikkunaCtrl = ldr.getController();

            var cssLocation = getClass().getResource("/css/paaikkuna.css");
            System.out.println("CSS Location: " + cssLocation);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            scene.getStylesheets().add(Objects.requireNonNull(cssLocation, "CSS file not found").toExternalForm());
            primaryStage.setTitle("Paaikkuna");

            Siivoustiimi siivoustiimi = new Siivoustiimi();
            paaikkunaCtrl.setSiivoustiimi(siivoustiimi);

            primaryStage.show();

            Application.Parameters params = getParameters();
            if (!params.getRaw().isEmpty())
                paaikkunaCtrl.lueTiedosto(params.getRaw().getFirst());
            else if (!paaikkunaCtrl.avaa()) Platform.exit();

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