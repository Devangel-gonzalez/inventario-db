package mx.unison.modelos;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.property.*;

@DatabaseTable(tableName = "productos")
public class Producto {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String nombre;

    @DatabaseField
    private String descripcion;

    @DatabaseField(canBeNull = false)
    private int cantidad;

    @DatabaseField
    private double precio;

    @DatabaseField(columnName = "almacen_id", foreign = true, foreignAutoRefresh = true)
    private Almacen almacen;

    // Estos campos parecen ser auxiliares para la vista, los mantenemos como
    // String/int
    @DatabaseField
    private int almacenId;

    @DatabaseField
    private String almacenNombre;

    @DatabaseField(columnName = "fecha_hora_creacion")
    private String fechaCreacion;

    @DatabaseField(columnName = "fecha_hora_ultima_modificacion")
    private String fechaModificacion;

    @DatabaseField(columnName = "ultimo_usuario_en_modificar")
    private String ultimoUsuario;

    // CONSTRUCTOR VACÍO (Obligatorio para ORMLite)
    public Producto() {
    }

    // --- PROPIEDADES PARA JAVAFX (Resuelve los errores del Controller) ---

    public IntegerProperty idProperty() {
        return new SimpleIntegerProperty(id);
    }

    public StringProperty nombreProperty() {
        return new SimpleStringProperty(nombre);
    }

    public StringProperty descripcionProperty() {
        return new SimpleStringProperty(descripcion);
    }

    public IntegerProperty cantidadProperty() {
        return new SimpleIntegerProperty(cantidad);
    }

    public DoubleProperty precioProperty() {
        return new SimpleDoubleProperty(precio);
    }

    public StringProperty fechaCreacionProperty() {
        return new SimpleStringProperty(fechaCreacion);
    }

    public StringProperty fechaModificacionProperty() {
        return new SimpleStringProperty(fechaModificacion);
    }

    public StringProperty ultimoUsuarioProperty() {
        return new SimpleStringProperty(ultimoUsuario);
    }

    // --- GETTERS Y SETTERS NORMALES (Para lógica de negocio y DAO) ---

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUltimoUsuario() {
        return ultimoUsuario;
    }

    public void setUltimoUsuario(String ultimoUsuario) {
        this.ultimoUsuario = ultimoUsuario;
    }
}
/*
 * package mx.unison.modelos;
 * 
 * import com.j256.ormlite.field.DatabaseField;
 * import com.j256.ormlite.table.DatabaseTable;
 * 
 * @DatabaseTable(tableName = "productos")
 * public class Producto {
 * 
 * @DatabaseField(generatedId = true)
 * public int id;
 * 
 * @DatabaseField(canBeNull = false)
 * public String nombre;
 * 
 * @DatabaseField
 * public String descripcion;
 * 
 * @DatabaseField(canBeNull = false)
 * public int cantidad;
 * 
 * @DatabaseField
 * public double precio;
 * 
 * @DatabaseField(columnName = "almacen_id", foreign = true)
 * public Almacen almacen;
 * 
 * @DatabaseField
 * public int almacenId;
 * 
 * @DatabaseField
 * public String almacenNombre;
 * 
 * @DatabaseField(columnName = "fecha_hora_creacion")
 * public String fechaCreacion;
 * 
 * @DatabaseField(columnName = "fecha_hora_ultima_modificacion")
 * public String fechaModificacion;
 * 
 * @DatabaseField(columnName = "ultimo_usuario_en_modificar")
 * public String ultimoUsuario;
 * }
 */