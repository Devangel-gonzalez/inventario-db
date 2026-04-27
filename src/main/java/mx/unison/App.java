package mx.unison;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.unison.navegacion.NavigationManager;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/vistas/Login.fxml"));
        primaryStage.setTitle("Sistema de Inventario");
        primaryStage.setScene(new Scene(root, 1200, 800));
        NavigationManager.setStage(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}