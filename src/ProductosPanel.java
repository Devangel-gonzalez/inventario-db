package mx.unison;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductosPanel extends JPanel { // (Corregido) Se cambio el nombre de la clase
    private final Database db;
    private final Vistas vistas; // (Corregido) Referencia a Vistas para obtener el usuario actual
    private final Runnable onGoBack;
    private JTable table;
    private DefaultTableModel model;

    public ProductosPanel(Database db, Vistas vistas, Runnable onGoBack) { // (Corregido) Recibir referencia a Vistas
        this.db = db;
        this.vistas = vistas; // (Corregido) Almacenar referencia a Vistas
        this.onGoBack = onGoBack;
        setLayout(new BorderLayout());
        initTop();
        initTable();
        loadData();
    }

    private void initTop() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton back = new JButton("Regresar");
        back.addActionListener(e -> onGoBack.run());

        JButton add = new JButton("Agregar");
        add.addActionListener(e -> openForm(null));

        JButton edit = new JButton("Modificar");
        edit.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                int id = (int) model.getValueAt(r, 0);
                Producto p = new Producto();
                p.id = id;
                openForm(p);
            }
        });

        JButton del = new JButton("Eliminar");
        del.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                int id = (int) model.getValueAt(r, 0);
                int opt = JOptionPane.showConfirmDialog(this,
                        "¿Seguro que desea eliminar el producto?",
                        "Confirmar", JOptionPane.YES_NO_OPTION);
                if (opt == JOptionPane.YES_OPTION) {
                    db.deleteProducto(id);
                    loadData();
                }
            }
        });

        top.add(back);
        top.add(add);
        top.add(edit);
        top.add(del);
        add(top, BorderLayout.NORTH);
    }

    private void initTable() {
        model = new DefaultTableModel(new Object[] {
                "ID", "Nombre", "Descripción", "Cantidad", "Precio", "Almacén",
                "Creado", "Últ.Mod", "Últ.Usuario" }, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadData() {
        model.setRowCount(0);
        List<Producto> productos = db.listProductos();
        for (Producto p : productos) {
            model.addRow(new Object[] {
                    p.id, p.nombre, p.descripcion, p.cantidad, p.precio,
                    p.almacenNombre, p.fechaCreacion, p.fechaModificacion, p.ultimoUsuario
            });
        }
    }

    private void openForm(Producto p) { // (Corregido) Se agrego funcionalidad al metodo openForm
        JTextField txtNombre = new JTextField(20);
        JTextField txtDescripcion = new JTextField(20);
        JTextField txtCantidad = new JTextField(20);
        JTextField txtPrecio = new JTextField(20);
        JTextField txtAlmacenId = new JTextField(20);

        if (p != null) {
            txtNombre.setText(p.nombre);
            txtDescripcion.setText(p.descripcion != null ? p.descripcion : "");
            txtCantidad.setText(String.valueOf(p.cantidad));
            txtPrecio.setText(String.valueOf(p.precio));
            txtAlmacenId.setText(p.almacenId > 0 ? String.valueOf(p.almacenId) : "");
        }

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.add(new JLabel("Nombre del producto:"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("Descripción:"));
        formPanel.add(txtDescripcion);
        formPanel.add(new JLabel("Cantidad:"));
        formPanel.add(txtCantidad);
        formPanel.add(new JLabel("Precio:"));
        formPanel.add(txtPrecio);
        formPanel.add(new JLabel("ID Almacén:"));
        formPanel.add(txtAlmacenId);

        String title = (p == null) ? "Nuevo Producto" : "Editar Producto";
        int result = JOptionPane.showConfirmDialog(this, formPanel, title, JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String usuario = vistas.getCurrentUser();

            int cantidad = 0;
            double precio = 0.0;
            int almacenId = 0;

            try {
                cantidad = Integer.parseInt(txtCantidad.getText().trim());
                precio = Double.parseDouble(txtPrecio.getText().trim());
                if (!txtAlmacenId.getText().trim().isEmpty()) {
                    almacenId = Integer.parseInt(txtAlmacenId.getText().trim());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cantidad, precio y ID almacén deben ser números válidos",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Producto prod = new Producto();
            prod.id = (p != null) ? p.id : 0;
            prod.nombre = nombre;
            prod.descripcion = descripcion;
            prod.cantidad = cantidad;
            prod.precio = precio;
            prod.almacenId = almacenId;

            if (p == null) {
                db.insertProducto(prod, usuario);
            } else {
                db.updateProducto(prod, usuario);
            }
            loadData();
        }
    }
}
