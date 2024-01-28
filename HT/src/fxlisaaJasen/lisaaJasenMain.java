package fxlisaaJasen;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author jyrihuhtala
 * @version 28.1.2024
 */
public class lisaaJasenMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("lisaaJasenGUIView.fxml"));
            final Pane root = ldr.load();
            //final lisaaJasenGUIController lisaajasenCtrl = (lisaaJasenGUIController)ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("lisaajasen.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("lisaaJasen");
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