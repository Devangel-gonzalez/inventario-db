package mx.unison.controladores;

import javafx.fxml.FXML;
import mx.unison.navegacion.NavigationManager;

public class HomeController {

    @FXML
    private void handleProductos() {
        NavigationManager.navigateTo("Productos");
    }

    @FXML
    private void handleAlmacenes() {
        NavigationManager.navigateTo("Almacenes");
    }

    @FXML
    private void handleLogout() {
        NavigationManager.navigateTo("Login");
    }
}