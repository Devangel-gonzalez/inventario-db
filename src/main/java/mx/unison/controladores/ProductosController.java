package mx.unison.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import mx.unison.datos.DatabaseHelper;
import mx.unison.modelos.Producto;
import mx.unison.navegacion.NavigationManager;

import java.sql.SQLException;

public class ProductosController {

    @FXML
    private TableView<Producto> productosTable;
    @FXML
    private TableColumn<Producto, Integer> idColumn;
    @FXML
    private TableColumn<Producto, String> nombreColumn;
    @FXML
    private TableColumn<Producto, String> descripcionColumn;
    @FXML
    private TableColumn<Producto, Integer> cantidadColumn;
    @FXML
    private TableColumn<Producto, Double> precioColumn;

    private ObservableList<Producto> productosList = FXCollections.observableArrayList();
    private final DatabaseHelper dbHelper;

    public ProductosController() throws SQLException {
        dbHelper = new DatabaseHelper();
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        nombreColumn.setCellValueFactory(data -> data.getValue().nombreProperty());
        descripcionColumn.setCellValueFactory(data -> data.getValue().descripcionProperty());
        cantidadColumn.setCellValueFactory(data -> data.getValue().cantidadProperty().asObject());
        precioColumn.setCellValueFactory(data -> data.getValue().precioProperty().asObject());

        loadProductos();
    }

    private void loadProductos() {
        try {
            productosList.setAll(dbHelper.getProductoDao().queryForAll());
            productosTable.setItems(productosList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdd() {
        System.out.println("Agregar nuevo producto...");
    }

    @FXML
    private void handleEdit() {
        Producto selected = productosTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Editar producto con ID: " + selected.getId());
        }
    }

    @FXML
    private void handleDelete() {
        Producto selected = productosTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                dbHelper.getProductoDao().delete(selected);
                productosList.remove(selected);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleGoBack() {
        NavigationManager.navigateTo("Home");
    }
}