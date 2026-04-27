package mx.unison.datos;

import mx.unison.modelos.Almacen;
import mx.unison.modelos.Producto;
import mx.unison.modelos.Usuario;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Helper para ORMLite que expone DAOs tipados y permite cierre con
 * try-with-resources.
 */
public class DatabaseHelper implements AutoCloseable {
    private static final String DATABASE_URL = "jdbc:sqlite:InventarioBD.db";

    private ConnectionSource connectionSource;
    private Dao<Almacen, Integer> almacenDao;
    private Dao<Producto, Integer> productoDao;
    private Dao<Usuario, String> usuarioDao;

    public DatabaseHelper() throws SQLException {
        connectionSource = new JdbcConnectionSource(DATABASE_URL);
        TableUtils.createTableIfNotExists(connectionSource, Almacen.class);
        TableUtils.createTableIfNotExists(connectionSource, Producto.class);
        TableUtils.createTableIfNotExists(connectionSource, Usuario.class);

        almacenDao = com.j256.ormlite.dao.DaoManager.createDao(connectionSource, Almacen.class);
        productoDao = com.j256.ormlite.dao.DaoManager.createDao(connectionSource, Producto.class);
        usuarioDao = com.j256.ormlite.dao.DaoManager.createDao(connectionSource, Usuario.class);
    }

    public Dao<Almacen, Integer> getAlmacenDao() {
        return almacenDao;
    }

    public Dao<Producto, Integer> getProductoDao() {
        return productoDao;
    }

    public Dao<Usuario, String> getUsuarioDao() {
        return usuarioDao;
    }

    /**
     * Cierra el connectionSource. Implementa AutoCloseable para try-with-resources.
     */
    @Override
    public void close() throws Exception {
        if (connectionSource != null) {
            connectionSource.close();
        }
    }
}