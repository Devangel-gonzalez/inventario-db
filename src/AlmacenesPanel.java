package mx.unison;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AlmacenesPanel extends JPanel {
    private final Database db;
    private final Vistas vistas; // (Corregido) Referencia a Vistas para obtener el usuario actual
    private final Runnable onGoBack;
    private JTable table;
    private DefaultTableModel model;

    public AlmacenesPanel(Database db, Vistas vistas, Runnable onGoBack) { // (Corregido) Recibir referencia a Vistas
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
                Almacen a = new Almacen();
                a.id = id;
                openForm(a);
            }
        });

        JButton del = new JButton("Eliminar");
        del.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                int id = (int) model.getValueAt(r, 0);
                int opt = JOptionPane.showConfirmDialog(this,
                        "¿Seguro que desea eliminar el almacén?",
                        "Confirmar", JOptionPane.YES_NO_OPTION);
                if (opt == JOptionPane.YES_OPTION) {
                    db.deleteAlmacen(id);
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
        model = new DefaultTableModel(new Object[] { "ID", "Nombre", "Ubicación", "Creado", "Últ.Mod", "Últ.Usuario" },
                0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadData() {
        model.setRowCount(0);
        List<Almacen> list = db.listAlmacenes();
        for (Almacen a : list) {
            model.addRow(new Object[] {
                    a.id, a.nombre, a.ubicacion,
                    a.fechaHoraCreacion, a.fechaHoraUltimaMod, a.ultimoUsuario
            });
        }
    }

    private void openForm(Almacen a) { // (Corregido) Se agrego funcionalidad al metodo openForm
        JTextField txtNombre = new JTextField(20);
        JTextField txtUbicacion = new JTextField(20);

        if (a != null) {
            txtNombre.setText(a.nombre);
            txtUbicacion.setText(a.ubicacion != null ? a.ubicacion : "");
        }

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.add(new JLabel("Nombre del almacén:"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("Ubicación:"));
        formPanel.add(txtUbicacion);

        String title = (a == null) ? "Nuevo Almacén" : "Editar Almacén";
        int result = JOptionPane.showConfirmDialog(this, formPanel, title, JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String ubicacion = txtUbicacion.getText().trim();
            String usuario = vistas.getCurrentUser();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (a == null) {
                db.insertAlmacen(nombre, ubicacion, usuario);
            } else {
                db.updateAlmacen(a.id, nombre, ubicacion, usuario);
            }
            loadData();
        }
    }
}
