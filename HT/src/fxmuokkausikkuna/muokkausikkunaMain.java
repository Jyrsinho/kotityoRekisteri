package fxmuokkausikkuna;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author jyrihuhtala
 * @version 27.1.2024
 */
public class muokkausikkunaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("muokkausikkunaGUIView.fxml"));
            final Pane root = ldr.load();
            //final muokkausikkunaGUIController muokkausikkunaCtrl = (muokkausikkunaGUIController)ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("muokkausikkuna.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("muokkausikkuna");
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