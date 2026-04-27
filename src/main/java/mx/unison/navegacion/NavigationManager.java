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
            // La ruta debe reflejar la estructura de carpetas completa
            String path = "/mx/unison/vistas/" + fxmlFile + ".fxml";
            var resource = NavigationManager.class.getResource(path);

            if (resource == null) {
                throw new RuntimeException("No se encontró el archivo FXML en: " + path);
            }

            Parent root = FXMLLoader.load(resource);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}