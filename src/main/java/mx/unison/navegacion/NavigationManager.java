package mx.unison.navegacion;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationManager {
    private static Stage stage;

    public static void setStage(Stage mainStage) {
        stage = mainStage;
    }

    public static void navigateTo(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(NavigationManager.class.getResource("/vistas/" + fxmlFile + ".fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}