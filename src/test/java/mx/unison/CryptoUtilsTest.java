package mx.unison;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CryptoUtilsTest {

    @Test
    void md5DevuelveHashDe32Caracteres() {
        String hash = CryptoUtils.md5("admin23");
        assertNotNull(hash);
        assertEquals(32, hash.length());
    }

    @Test
    void md5EsConsistente() {
        assertEquals(CryptoUtils.md5("productos19"), CryptoUtils.md5("productos19"));
    }
}