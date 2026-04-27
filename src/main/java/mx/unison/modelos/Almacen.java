package mx.unison.modelos;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.property.*;

@DatabaseTable(tableName = "almacenes")
public class Almacen {

    @DatabaseField(generatedId = true)
    private int id; // Cambiado a private por buena práctica

    @DatabaseField(canBeNull = false)
    private String nombre;

    @DatabaseField
    private String ubicacion;

    @DatabaseField(columnName = "fecha_hora_creacion")
    private String fechaHoraCreacion;

    @DatabaseField(columnName = "fecha_hora_ultima_modificacion")
    private String fechaHoraUltimaMod;

    @DatabaseField(columnName = "ultimo_usuario_en_modificar")
    private String ultimoUsuario;

    // CONSTRUCTOR VACÍO (Indispensable)
    public Almacen() {
    }

    // GETTERS Y SETTERS (Útiles para el DAO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(String fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public String getFechaHoraUltimaMod() {
        return fechaHoraUltimaMod;
    }

    public void setFechaHoraUltimaMod(String fechaHoraUltimaMod) {
        this.fechaHoraUltimaMod = fechaHoraUltimaMod;
    }

    public String getUltimoUsuario() {
        return ultimoUsuario;
    }

    public void setUltimoUsuario(String ultimoUsuario) {
        this.ultimoUsuario = ultimoUsuario;
    }

    // ... agrega los demás setters si planeas editar datos ...

    // PROPIEDADES PARA JAVAFX (Data Binding)
    public IntegerProperty idProperty() {
        return new SimpleIntegerProperty(id);
    }

    public StringProperty nombreProperty() {
        return new SimpleStringProperty(nombre);
    }

    public StringProperty ubicacionProperty() {
        return new SimpleStringProperty(ubicacion);
    }

    public StringProperty fechaHoraCreacionProperty() {
        return new SimpleStringProperty(fechaHoraCreacion);
    }

    public StringProperty fechaHoraUltimaModProperty() {
        return new SimpleStringProperty(fechaHoraUltimaMod);
    }

    public StringProperty ultimoUsuarioProperty() {
        return new SimpleStringProperty(ultimoUsuario);
    }

    // --- GETTERS Y SETTERS NORMALES (Para lógica de negocio y DAO) ---

}
/*
 * package mx.unison.modelos;
 * 
 * import com.j256.ormlite.field.DatabaseField;
 * import com.j256.ormlite.table.DatabaseTable;
 * 
 * import javafx.beans.property.IntegerProperty;
 * import javafx.beans.property.SimpleIntegerProperty;
 * import javafx.beans.property.SimpleStringProperty;
 * import javafx.beans.property.StringProperty;
 * 
 * @DatabaseTable(tableName = "almacenes")
 * public class Almacen {
 * public Almacen() {
 * // Constructor vacío requerido por ORMLite
 * }
 * 
 * @DatabaseField(generatedId = true)
 * public int id;
 * 
 * @DatabaseField(canBeNull = false)
 * public String nombre;
 * 
 * @DatabaseField
 * public String ubicacion;
 * 
 * @DatabaseField(columnName = "fecha_hora_creacion")
 * public String fechaHoraCreacion;
 * 
 * @DatabaseField(columnName = "fecha_hora_ultima_modificacion")
 * public String fechaHoraUltimaMod;
 * 
 * @DatabaseField(columnName = "ultimo_usuario_en_modificar")
 * public String ultimoUsuario;
 * 
 * public StringProperty nombreProperty() {
 * return new SimpleStringProperty(nombre);
 * }
 * 
 * public StringProperty ubicacionProperty() {
 * return new SimpleStringProperty(ubicacion);
 * }
 * 
 * // Repite para los demás campos (IntegerProperty para ID, ObjectProperty para
 * // fechas)
 * public IntegerProperty idProperty() {
 * return new SimpleIntegerProperty(this.id);
 * }
 * 
 * // También asegúrate de tener este getter normal:
 * public int getId() {
 * return this.id;
 * }
 * 
 * public StringProperty fechaHoraCreacionProperty() {
 * return new SimpleStringProperty(fechaHoraCreacion);
 * }
 * 
 * public StringProperty fechaHoraUltimaModProperty() {
 * return new SimpleStringProperty(fechaHoraUltimaMod);
 * }
 * 
 * public StringProperty ultimoUsuarioProperty() {
 * return new SimpleStringProperty(ultimoUsuario);
 * }
 * 
 * }
 */