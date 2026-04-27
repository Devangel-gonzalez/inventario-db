package mx.unison.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import mx.unison.datos.DatabaseHelper;
import mx.unison.modelos.Almacen;
import mx.unison.navegacion.NavigationManager;

import java.sql.SQLException;

public class AlmacenesController {

    @FXML
    private TableView<Almacen> almacenesTable;
    @FXML
    private TableColumn<Almacen, Integer> idColumn;
    @FXML
    private TableColumn<Almacen, String> nombreColumn;
    @FXML
    private TableColumn<Almacen, String> ubicacionColumn;
    @FXML
    private TableColumn<Almacen, String> fechaCreacionColumn;
    @FXML
    private TableColumn<Almacen, String> fechaUltimaModColumn;
    @FXML
    private TableColumn<Almacen, String> ultimoUsuarioColumn;

    private ObservableList<Almacen> almacenesList = FXCollections.observableArrayList();
    private DatabaseHelper dbHelper;

    public AlmacenesController() throws SQLException {
        dbHelper = new DatabaseHelper();
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getId()));
        nombreColumn.setCellValueFactory(data -> data.getValue().nombreProperty());
        ubicacionColumn.setCellValueFactory(data -> data.getValue().ubicacionProperty());
        fechaCreacionColumn.setCellValueFactory(data -> data.getValue().fechaHoraCreacionProperty());
        fechaUltimaModColumn.setCellValueFactory(data -> data.getValue().fechaHoraUltimaModProperty());
        ultimoUsuarioColumn.setCellValueFactory(data -> data.getValue().ultimoUsuarioProperty());

        loadAlmacenes();
    }

    private void loadAlmacenes() {
        try {
            almacenesList.setAll(dbHelper.getAlmacenDao().queryForAll());
            almacenesTable.setItems(almacenesList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdd() {
        System.out.println("Abrir formulario para agregar un almacén...");
    }

    @FXML
    private void handleEdit() {
        Almacen selected = almacenesTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Modificar almacén con ID: " + selected.getId());
        }
    }

    @FXML
    private void handleDelete() {
        Almacen selected = almacenesTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                dbHelper.getAlmacenDao().delete(selected);
                almacenesList.remove(selected);
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