package mx.unison;

import javax.swing.*;

import mx.unison.controladores.AlmacenesPanel;
import mx.unison.controladores.HomeController;
import mx.unison.controladores.LoginController;
import mx.unison.controladores.ProductosPanel;

import java.awt.*;

public class Vistas extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel container = new JPanel(cardLayout);
    private final Database db = new Database();

    private String currentUser; // (Corregido) Variable para almacenar el nombre del usuario actual

    public Vistas() {
        setTitle("Sistema de Inventario - Cliente");
        setSize(1000, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Login login = new Login(db, user -> showHome(user));
        Home home = new Home(
                () -> showPanel("PRODUCTOS"),
                () -> showPanel("ALMACENES"));
        ProductosPanel productos = new ProductosPanel(db, this, () -> showPanel("INICIO"));
        AlmacenesPanel almacenes = new AlmacenesPanel(db, this, () -> showPanel("INICIO"));
        container.add(login, "LOGIN");
        container.add(home, "INICIO");
        container.add(productos, "PRODUCTOS");
        container.add(almacenes, "ALMACENES");

        add(container);
        cardLayout.show(container, "LOGIN");
    }

    private void showHome(String usuarioNombre) {
        this.currentUser = usuarioNombre; // (Corregido) Almacenar el nombre del usuario que inició sesión
        cardLayout.show(container, "INICIO");
    }

    private void showPanel(String name) {
        cardLayout.show(container, name);
    }

    public String getCurrentUser() {
        return currentUser;
    } // (Corregido) Método para obtener el nombre del usuario actual
}
