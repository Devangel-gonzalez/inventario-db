package mx.unison;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.unison.navegacion.NavigationManager;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Ajuste de ruta: Debe coincidir con la estructura de paquetes en resources
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/unison/vistas/Login.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("Sistema de Inventario - Reingeniería AI");

            // Se establece el diseño único y atractivo (1200x800) solicitado [cite: 11]
            Scene scene = new Scene(root, 1200, 800);
            primaryStage.setScene(scene);

            // Inicializar el gestor de navegación para cambios de interfaz [cite: 10]
            NavigationManager.setStage(primaryStage);

            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Error al cargar la interfaz inicial: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Punto de entrada principal para lanzar la aplicación JavaFX [cite: 9]
        launch(args);
    }
}

/*
 * package mx.unison;
 * 
 * import javafx.application.Application;
 * import javafx.fxml.FXMLLoader;
 * import javafx.scene.Parent;
 * import javafx.scene.Scene;
 * import javafx.stage.Stage;
 * import mx.unison.navegacion.NavigationManager;
 * 
 * public class App extends Application {
 * 
 * @Override
 * public void start(Stage primaryStage) throws Exception {
 * Parent root = FXMLLoader.load(getClass().getResource("/vistas/Login.fxml"));
 * primaryStage.setTitle("Sistema de Inventario");
 * primaryStage.setScene(new Scene(root, 1200, 800));
 * NavigationManager.setStage(primaryStage);
 * primaryStage.show();
 * }
 * 
 * public static void main(String[] args) {
 * launch(args);
 * 
 * }
 * 
 * }
 */