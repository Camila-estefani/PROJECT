package camila.quispe.view;

import camila.quispe.controller.ComputadoraController;
import camila.quispe.model.Computadora;
import camila.quispe.model.TipoEquipo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.event.ListSelectionEvent;

public class FrmComputadoras extends JFrame {

    private final ComputadoraController controller;

    private JPanel mainPanel;
    private JTextField txtCodigo, txtMarca, txtModelo, txtRam, txtAlmacenamiento;
    private JComboBox<TipoEquipo> cmbTipoEquipo;
    private JRadioButton rbtnWin, rbtnMac, rbtnLinux;
    private ButtonGroup bgSO;
    private JCheckBox chkEstadoActivo; // Se mantiene por requisito de JCheckBox
    private JButton btnNuevo, btnGuardar, btnActualizar, btnEliminar;
    private JTable tblComputadoras;
    private DefaultTableModel tableModel;

    public FrmComputadoras() {
        this.controller = new ComputadoraController();
        setTitle("Inventario de Computadoras - CRUD AWS RDS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        configurarTabla();
        cargarDatosComboBox();
        listarDatosEnTabla();

        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        txtCodigo = new JTextField(5);
        txtMarca = new JTextField(15);
        txtModelo = new JTextField(15);
        txtRam = new JTextField(5);
        txtAlmacenamiento = new JTextField(5);
        cmbTipoEquipo = new JComboBox<>();
        tblComputadoras = new JTable();

        rbtnWin = new JRadioButton("Windows");
        rbtnMac = new JRadioButton("macOS");
        rbtnLinux = new JRadioButton("Linux");
        bgSO = new ButtonGroup();
        bgSO.add(rbtnWin);
        bgSO.add(rbtnMac);
        bgSO.add(rbtnLinux);
        rbtnWin.setSelected(true);

        chkEstadoActivo = new JCheckBox("Activo", true);

        btnNuevo = new JButton("NUEVO");
        btnGuardar = new JButton("GUARDAR (Crear)");
        btnActualizar = new JButton("ACTUALIZAR");
        btnEliminar = new JButton("ELIMINAR"); // Eliminación Física

        mainPanel = new JPanel(new BorderLayout(5, 5));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos de la Computadora"));
        formPanel.add(new JLabel("Marca:")); formPanel.add(txtMarca);
        formPanel.add(new JLabel("Modelo:")); formPanel.add(txtModelo);
        formPanel.add(new JLabel("RAM (GB):")); formPanel.add(txtRam);
        formPanel.add(new JLabel("Almac. (GB):")); formPanel.add(txtAlmacenamiento);
        formPanel.add(new JLabel("Tipo Equipo:")); formPanel.add(cmbTipoEquipo);

        JPanel soPanel = new JPanel();
        soPanel.add(rbtnWin); soPanel.add(rbtnMac); soPanel.add(rbtnLinux);
        formPanel.add(new JLabel("Sistema Operativo:")); formPanel.add(soPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnNuevo);
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(chkEstadoActivo);

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(new JScrollPane(tblComputadoras), BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // Listeners
        btnNuevo.addActionListener(e -> limpiarCampos());
        btnGuardar.addActionListener(e -> guardarComputadora());
        btnActualizar.addActionListener(e -> actualizarComputadora());
        btnEliminar.addActionListener(e -> eliminar()); // Eliminación Física
        tblComputadoras.getSelectionModel().addListSelectionListener(this::cargarDatosSeleccionados);
    }

    private void configurarTabla() {
        String[] columnas = {"Código", "Tipo", "Marca", "Modelo", "SO", "RAM (GB)", "Almac. (GB)", "Estado"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblComputadoras.setModel(tableModel);
    }

    private void cargarDatosComboBox() {
        List<TipoEquipo> tipos = controller.listarTiposEquipo();
        cmbTipoEquipo.removeAllItems();
        for (TipoEquipo tipo : tipos) {
            cmbTipoEquipo.addItem(tipo);
        }
    }

    private void listarDatosEnTabla() {
        tableModel.setRowCount(0);
        List<Computadora> lista = controller.listarComputadoras();

        for (Computadora c : lista) {
            Object[] fila = new Object[8];
            fila[0] = c.getCodigo();
            fila[1] = c.getTipoEquipo().getNombre();
            fila[2] = c.getMarca();
            fila[3] = c.getModelo();
            fila[4] = c.getSistemaOperativo();
            fila[5] = c.getRamGB();
            fila[6] = c.getAlmacenamientoGB();
            fila[7] = c.isEstadoActivo() ? "Activo" : "Inactivo";
            tableModel.addRow(fila);
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtRam.setText("");
        txtAlmacenamiento.setText("");
        cmbTipoEquipo.setSelectedIndex(-1);
        bgSO.clearSelection();
        rbtnWin.setSelected(true);
        chkEstadoActivo.setSelected(true);
        tblComputadoras.clearSelection();
    }

    private void cargarDatosSeleccionados(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int filaSeleccionada = tblComputadoras.getSelectedRow();
            if (filaSeleccionada >= 0) {
                try {
                    int codigo = (int) tableModel.getValueAt(filaSeleccionada, 0);
                    String tipoNombre = (String) tableModel.getValueAt(filaSeleccionada, 1);
                    String marca = (String) tableModel.getValueAt(filaSeleccionada, 2);
                    String modelo = (String) tableModel.getValueAt(filaSeleccionada, 3);
                    String so = (String) tableModel.getValueAt(filaSeleccionada, 4);
                    int ram = (int) tableModel.getValueAt(filaSeleccionada, 5);
                    int alm = (int) tableModel.getValueAt(filaSeleccionada, 6);
                    boolean estado = tableModel.getValueAt(filaSeleccionada, 7).equals("Activo");

                    txtCodigo.setText(String.valueOf(codigo));
                    txtMarca.setText(marca);
                    txtModelo.setText(modelo);
                    txtRam.setText(String.valueOf(ram));
                    txtAlmacenamiento.setText(String.valueOf(alm));
                    chkEstadoActivo.setSelected(estado);

                    for (int i = 0; i < cmbTipoEquipo.getItemCount(); i++) {
                        if (cmbTipoEquipo.getItemAt(i).getNombre().equals(tipoNombre)) {
                            cmbTipoEquipo.setSelectedIndex(i);
                            break;
                        }
                    }

                    if (so.contains("Windows")) rbtnWin.setSelected(true);
                    else if (so.contains("macOS")) rbtnMac.setSelected(true);
                    else if (so.contains("Linux") || so.contains("Ubuntu")) rbtnLinux.setSelected(true);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al cargar datos de la fila.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private Computadora obtenerDatosFormulario(boolean isNew) throws NumberFormatException, IllegalStateException {
        if (txtMarca.getText().trim().isEmpty() || txtModelo.getText().trim().isEmpty() || cmbTipoEquipo.getSelectedItem() == null) {
            throw new IllegalStateException("Debe completar todos los campos principales (Marca, Modelo, Tipo Equipo).");
        }

        Computadora comp = new Computadora();

        if (!isNew && !txtCodigo.getText().isEmpty()) {
            comp.setCodigo(Integer.parseInt(txtCodigo.getText()));
        }

        comp.setMarca(txtMarca.getText().trim());
        comp.setModelo(txtModelo.getText().trim());
        comp.setRamGB(Integer.parseInt(txtRam.getText().trim()));
        comp.setAlmacenamientoGB(Integer.parseInt(txtAlmacenamiento.getText().trim()));
        comp.setTipoEquipo((TipoEquipo) cmbTipoEquipo.getSelectedItem());
        comp.setEstadoActivo(chkEstadoActivo.isSelected());

        String so = "";
        if (rbtnWin.isSelected()) so = rbtnWin.getText();
        else if (rbtnMac.isSelected()) so = rbtnMac.getText();
        else if (rbtnLinux.isSelected()) so = rbtnLinux.getText();
        comp.setSistemaOperativo(so.isEmpty() ? "No Especificado" : so);

        if (isNew) {
            comp.setFechaRegistro(LocalDate.now());
        }

        return comp;
    }

    private void guardarComputadora() {
        try {
            Computadora nuevaComp = obtenerDatosFormulario(true);

            if (controller.guardarComputadora(nuevaComp)) {
                JOptionPane.showMessageDialog(this, "Computadora guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                listarDatosEnTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar. Verifique conexión a AWS.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "RAM y Almacenamiento deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarComputadora() {
        if (tblComputadoras.getSelectedRow() < 0 || txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un registro para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Computadora compActualizada = obtenerDatosFormulario(false);

            if (controller.actualizarComputadora(compActualizada)) {
                JOptionPane.showMessageDialog(this, "Registro actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                listarDatosEnTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "RAM y Almacenamiento deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        int filaSeleccionada = tblComputadoras.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fila para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int codigo = (int) tableModel.getValueAt(filaSeleccionada, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de ELIMINAR permanentemente la computadora con código " + codigo + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.eliminar(codigo)) {
                JOptionPane.showMessageDialog(this, "Eliminación exitosa.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                listarDatosEnTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al intentar eliminar. El código podría no existir.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}