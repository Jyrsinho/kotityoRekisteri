package fxmuokkaakotityo;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author jyrihuhtala
 * @version 28.1.2024
 */
public class muokkaakotityoMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("muokkaakotityoGUIView.fxml"));
            final Pane root = ldr.load();
            //final muokkaakotityoGUIController muokkaakotityoCtrl = (muokkaakotityoGUIController)ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("muokkaakotityo.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("muokkaakotityo");
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