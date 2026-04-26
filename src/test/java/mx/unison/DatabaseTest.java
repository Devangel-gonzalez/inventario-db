package mx.unison;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private Database db;

    @BeforeEach
    void setUp() {
        db = new Database();
    }

    @Test
    void authenticateConCredencialesCorrectasDevuelveUsuario() {
        Usuario u = db.authenticate("ADMIN", "admin23");
        assertNotNull(u);
        assertEquals("ADMIN", u.nombre);
        assertEquals("ADMIN", u.rol);
    }

    @Test
    void authenticateConPasswordIncorrectaDevuelveNull() {
        assertNull(db.authenticate("ADMIN", "malpassword"));
    }

    @Test
    void insertAlmacenDevuelveIdValido() {
        int id = db.insertAlmacen("Almacén Test", "Hermosillo", "TEST_USER");
        assertTrue(id > 0);
    }

    @Test
    void insertProductoDevuelveIdValido() {
        Producto p = new Producto();
        p.nombre = "Producto Test";
        p.cantidad = 10;
        p.precio = 99.99;
        int id = db.insertProducto(p, "TEST_USER");
        assertTrue(id > 0);
    }

    @Test
    void listAlmacenesNoLanzaExcepcionYDevuelveDatos() {
        assertDoesNotThrow(() -> {
            var lista = db.listAlmacenes();
            assertNotNull(lista);
        });
    }

    @Test
    void listProductosNoLanzaExcepcionYDevuelveDatos() {
        assertDoesNotThrow(() -> {
            var lista = db.listProductos();
            assertNotNull(lista);
        });
    }

    @Test
    void deleteAlmacenNoLanzaExcepcion() {
        assertDoesNotThrow(() -> db.deleteAlmacen(999));
    }
}