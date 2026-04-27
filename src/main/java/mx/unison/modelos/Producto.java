package mx.unison.modelos;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "productos")
public class Producto {
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false)
    public String nombre;

    @DatabaseField
    public String descripcion;

    @DatabaseField(canBeNull = false)
    public int cantidad;

    @DatabaseField
    public double precio;

    @DatabaseField(columnName = "almacen_id", foreign = true)
    public Almacen almacen;

    @DatabaseField
    public int almacenId;

    @DatabaseField
    public String almacenNombre;

    @DatabaseField(columnName = "fecha_hora_creacion")
    public String fechaCreacion;

    @DatabaseField(columnName = "fecha_hora_ultima_modificacion")
    public String fechaModificacion;

    @DatabaseField(columnName = "ultimo_usuario_en_modificar")
    public String ultimoUsuario;
}