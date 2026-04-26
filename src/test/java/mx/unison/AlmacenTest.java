package mx.unison;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AlmacenTest {
    @Test
    void crearAlmacenNoLanzaExcepcion() {
        Almacen a = new Almacen();
        a.nombre = "Test";
        assertNotNull(a);
    }
}