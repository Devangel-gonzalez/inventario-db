package mx.unison.modelos;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "usuarios")
public class Usuario {
    @DatabaseField(id = true)
    public String nombre;

    @DatabaseField(canBeNull = false)
    public String password;

    @DatabaseField(columnName = "fecha_hora_ultimo_inicio")
    public String fechaHoraUltimoInicio;

    @DatabaseField(canBeNull = false)
    public String rol; // ADMIN, PRODUCTOS, ALMACENES
}