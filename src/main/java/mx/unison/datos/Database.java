package mx.unison.datos;

import mx.unison.modelos.Almacen;
import mx.unison.modelos.Producto;
import mx.unison.modelos.Usuario;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Clase de compatibilidad que expone operaciones de la base de datos usando
 * ORMLite (delegando a DatabaseHelper).
 * Mantiene la API de la versión anterior (métodos CRUD y autenticación).
 */
public class Database {

    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public Database() {
        // Aseguramos la inicialización de tablas y usuarios por defecto
        try {
            init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inicializa esquemas y usuarios por defecto usando DAOs.
     */
    private void init() throws SQLException {
        DatabaseHelper dbHelper = null;
        try {
            dbHelper = new DatabaseHelper();
            // DatabaseHelper ya crea tablas en su constructor
            // (TableUtils.createTableIfNotExists)
            // Insertar usuarios por defecto si no existen
            Dao<Usuario, String> usuarioDao = (Dao<Usuario, String>) dbHelper.getUsuarioDao();
            if (usuarioDao.queryForId("ADMIN") == null) {
                Usuario u = new Usuario();
                u.nombre = "ADMIN";
                u.password = mx.unison.utileria.CryptoUtils.md5("admin23");
                u.rol = "ADMIN";
                usuarioDao.create(u);
            }
            if (usuarioDao.queryForId("PRODUCTOS") == null) {
                Usuario u = new Usuario();
                u.nombre = "PRODUCTOS";
                u.password = mx.unison.utileria.CryptoUtils.md5("productos19");
                u.rol = "PRODUCTOS";
                usuarioDao.create(u);
            }
            if (usuarioDao.queryForId("ALMACENES") == null) {
                Usuario u = new Usuario();
                u.nombre = "ALMACENES";
                u.password = mx.unison.utileria.CryptoUtils.md5("almacenes11");
                u.rol = "ALMACENES";
                usuarioDao.create(u);
            }

            // Rellenar fechas de creación si están vacías
            // Al utilizar ORMLite no ejecutamos SQL directo; hacemos una revisión simple y
            // actualizamos
            Dao<Producto, Integer> productoDao = dbHelper.getProductoDao();
            for (Producto p : productoDao.queryForAll()) {
                // Usamos el getter y setter porque los campos son privados
                if (p.getFechaCreacion() == null || p.getFechaCreacion().isEmpty()) {
                    p.setFechaCreacion(LocalDateTime.now().format(ISO));
                    productoDao.update(p);
                }
            }

            Dao<Almacen, Integer> almacenDao = dbHelper.getAlmacenDao();
            for (Almacen a : almacenDao.queryForAll()) {
                // Asumiendo que en Almacen.java el campo se llama fechaHoraCreacion
                // y tiene sus respectivos Getter y Setter
                if (a.getFechaHoraCreacion() == null || a.getFechaHoraCreacion().isEmpty()) {
                    a.setFechaHoraCreacion(LocalDateTime.now().format(ISO));
                    almacenDao.update(a);
                }
            }

        } catch (Exception e) {
            throw new SQLException(e);
        } finally {
            if (dbHelper != null) {
                try {
                    dbHelper.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    /**
     * Autentica un usuario (por nombre y contraseña en texto plano).
     * Devuelve Usuario si las credenciales son correctas; null en caso contrario.
     */
    public Usuario authenticate(String nombre, String passwordPlain) {
        DatabaseHelper dbHelper = null;
        try {
            dbHelper = new DatabaseHelper();
            Dao<Usuario, String> usuarioDao = (Dao<Usuario, String>) dbHelper.getUsuarioDao();
            Usuario u = usuarioDao.queryForId(nombre);
            if (u != null) {
                String hash = mx.unison.utileria.CryptoUtils.md5(passwordPlain);
                if (u.password != null && u.password.equals(hash)) {
                    // Actualizar fecha de último inicio
                    u.fechaHoraUltimoInicio = LocalDateTime.now().format(ISO);
                    usuarioDao.update(u);
                    return u;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                try {
                    dbHelper.close();
                } catch (Exception ignored) {
                }
            }
        }
        return null;
    }

    /**
     * Lista todos los almacenes.
     */
    public List<Almacen> listAlmacenes() throws SQLException {
        DatabaseHelper dbHelper = null;
        try {
            dbHelper = new DatabaseHelper();
            Dao<Almacen, Integer> almacenDao = (Dao<Almacen, Integer>) dbHelper.getAlmacenDao();
            return almacenDao.queryForAll();
        } catch (Exception e) {
            throw new SQLException(e);
        } finally {
            if (dbHelper != null) {
                try {
                    dbHelper.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    /**
     * Inserta un almacén y devuelve el id generado.
     */
    public int insertAlmacen(String nombre, String ubicacion, String usuario) throws SQLException {
        DatabaseHelper dbHelper = null;
        try {
            dbHelper = new DatabaseHelper();
            Dao<Almacen, Integer> almacenDao = (Dao<Almacen, Integer>) dbHelper.getAlmacenDao();
            Almacen almacen = new Almacen();
            almacen.setNombre(nombre);
            almacen.setUbicacion(ubicacion);
            almacen.setFechaHoraCreacion(LocalDateTime.now().format(ISO));
            almacen.setFechaHoraUltimaMod(LocalDateTime.now().format(ISO));
            almacen.setUltimoUsuario(usuario);
            almacenDao.create(almacen);
            return almacen.getId(); // ORMLite llena el id generado en el objeto
        } catch (Exception e) {
            throw new SQLException(e);
        } finally {
            if (dbHelper != null) {
                try {
                    dbHelper.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    /**
     * Actualiza un almacén por id.
     */
    public void updateAlmacen(int id, String nombre, String ubicacion, String usuario) throws SQLException {
        DatabaseHelper dbHelper = null;
        try {
            dbHelper = new DatabaseHelper();
            Dao<Almacen, Integer> almacenDao = (Dao<Almacen, Integer>) dbHelper.getAlmacenDao();
            Almacen a = almacenDao.queryForId(id);
            if (a != null) {
                a.setNombre(nombre);
                a.setUbicacion(ubicacion);
                a.setFechaHoraUltimaMod(LocalDateTime.now().format(ISO));
                a.setUltimoUsuario(usuario);
                almacenDao.update(a);
            }
        } catch (Exception e) {
            throw new SQLException(e);
        } finally {
            if (dbHelper != null) {
                try {
                    dbHelper.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    /**
     * Elimina un almacén por id.
     */
    public void deleteAlmacen(int id) throws SQLException {
        DatabaseHelper dbHelper = null;
        try {
            dbHelper = new DatabaseHelper();
            Dao<Almacen, Integer> almacenDao = (Dao<Almacen, Integer>) dbHelper.getAlmacenDao();
            almacenDao.deleteById(id);
        } catch (Exception e) {
            throw new SQLException(e);
        } finally {
            if (dbHelper != null) {
                try {
                    dbHelper.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    /**
     * Lista productos. Ajusta campos de almacén si existe la relación.
     */
    public List<Producto> listProductos() throws SQLException {
        DatabaseHelper dbHelper = null;
        try {
            dbHelper = new DatabaseHelper();
            Dao<Producto, Integer> productoDao = (Dao<Producto, Integer>) dbHelper.getProductoDao();
            List<Producto> productos = productoDao.queryForAll();
            // Normalizar campos almacenId/almacenNombre si existe la relación
            for (Producto p : productos) {
                if (p.getAlmacen() != null) {
                    p.setId(p.getAlmacen().getId());
                    p.setNombre(p.getAlmacen().getNombre());
                }
            }
            return productos;
        } catch (Exception e) {
            throw new SQLException(e);
        } finally {
            if (dbHelper != null) {
                try {
                    dbHelper.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    /**
     * Inserta un producto y devuelve el id generado (o -1 en error).
     */
    public int insertProducto(Producto prod, String usuario) throws SQLException {
        DatabaseHelper dbHelper = null;
        try {
            dbHelper = new DatabaseHelper();
            Dao<Producto, Integer> productoDao = (Dao<Producto, Integer>) dbHelper.getProductoDao();

            Producto p = new Producto();
            p.setNombre(prod.getNombre());
            p.setDescripcion(prod.getDescripcion());
            p.setCantidad(prod.getCantidad());
            p.setPrecio(prod.getPrecio());
            // Si viene almacenId, intentar recuperar la entidad
            if (prod.getId() > 0) {
                Dao<Almacen, Integer> almacenDao = (Dao<Almacen, Integer>) dbHelper.getAlmacenDao();
                Almacen a = almacenDao.queryForId(prod.getId());
                p.setAlmacen(a);
                if (a != null) {
                    p.setId(a.getId());
                    p.setNombre(a.getNombre());
                }
            }
            p.setFechaCreacion(LocalDateTime.now().format(ISO));
            p.setFechaModificacion(LocalDateTime.now().format(ISO));
            p.setUltimoUsuario(usuario);

            productoDao.create(p);
            return p.getId();
        } catch (Exception e) {
            throw new SQLException(e);
        } finally {
            if (dbHelper != null) {
                try {
                    dbHelper.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    /**
     * Actualiza un producto existente.
     */
    public void updateProducto(Producto prod, String usuario) throws SQLException {
        DatabaseHelper dbHelper = null;
        try {
            dbHelper = new DatabaseHelper();
            Dao<Producto, Integer> productoDao = (Dao<Producto, Integer>) dbHelper.getProductoDao();
            Producto existing = productoDao.queryForId(prod.getId());
            if (existing != null) {
                existing.setNombre(prod.getNombre());
                existing.setDescripcion(prod.getDescripcion());
                existing.setCantidad(prod.getCantidad());
                existing.setPrecio(prod.getPrecio());
                if (prod.getId() > 0) {
                    Dao<Almacen, Integer> almacenDao = (Dao<Almacen, Integer>) dbHelper.getAlmacenDao();
                    Almacen a = almacenDao.queryForId(prod.getId());
                    existing.setAlmacen(a);
                    if (a != null) {
                        existing.setId(a.getId());
                        existing.setNombre(a.getNombre());
                    }
                } else {
                    existing.setAlmacen(null);
                    existing.setId(0);
                    existing.setNombre(null);
                }
                existing.setFechaModificacion(LocalDateTime.now().format(ISO));
                existing.setUltimoUsuario(usuario);
                productoDao.update(existing);
            }
        } catch (Exception e) {
            throw new SQLException(e);
        } finally {
            if (dbHelper != null) {
                try {
                    dbHelper.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    /**
     * Elimina un producto por id.
     */
    public void deleteProducto(int id) throws SQLException {
        DatabaseHelper dbHelper = null;
        try {
            dbHelper = new DatabaseHelper();
            Dao<Producto, Integer> productoDao = (Dao<Producto, Integer>) dbHelper.getProductoDao();
            productoDao.deleteById(id);
        } catch (Exception e) {
            throw new SQLException(e);
        } finally {
            if (dbHelper != null) {
                try {
                    dbHelper.close();
                } catch (Exception ignored) {
                }
            }
        }
    }
}