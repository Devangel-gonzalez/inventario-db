package mx.unison.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import mx.unison.datos.Database;
import mx.unison.navegacion.NavigationManager;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    private final Database db = new Database();

    @FXML
    private void handleLogin() {
        String user = txtUsuario.getText();
        String password = txtPassword.getText();

        if (db.authenticate(user, password) != null) {
            System.out.println("Inicio exitoso con usuario: " + user);
            NavigationManager.navigateTo("Home");
        } else {
            new Alert(Alert.AlertType.ERROR, "Credenciales incorrectas").showAndWait();
        }
    }
}