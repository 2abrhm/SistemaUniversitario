package com.miapp.vista;

import com.miapp.controlador.EstudianteController;
import com.miapp.utilidades.EstadoMatricula;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class EstudianteView extends JFrame {

    private static final int ANCHO_VENTANA = 1100;
    private static final int ALTO_VENTANA = 650;

    private static final String[] COLUMNAS_TABLA = {"ID", "Nombre", "Apellido", "Carrera", "Promedio", "Estado"};


    
    // Búsquedas y Filtros
    private JTextField txtBuscarNombre;
    private JComboBox<String> cmbCarrera;
    private JComboBox<Object> cmbFiltroEstado;
    private JButton btnBuscar;
    private JButton btnLimpiar;

    // Botones de Ventanas Emergentes y Acciones
    private JButton btnNuevoEstudiante;
    private JButton btnGestionarCursos;
    private JButton btnGestionarProfesores;
    private JButton btnCambiarEstado;

    // Tabla y Estado
    private JTable tblResultados;
    private DefaultTableModel modeloTabla;
    private JLabel lblEstado;
    private JLabel lblTotalEstudiantes;

    private EstudianteController controlador;

    public EstudianteView() {
        initComponentes();
        initEventos();
    }

    private void initComponentes() {
        setTitle("Sistema de Gestión Académica — MVC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(ANCHO_VENTANA, ALTO_VENTANA);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ── BARRA SUPERIOR (Búsquedas, Filtros y Acciones) ──────────────────
        JPanel panelControles = new JPanel(new GridLayout(2, 1, 5, 5));
        panelControles.setBorder(BorderFactory.createTitledBorder("Panel de Gestión y Consultas"));

        // Fila 1: Búsqueda y Filtros
        JPanel filaFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        txtBuscarNombre = new JTextField(12);
        cmbCarrera = new JComboBox<>();
        cmbCarrera.addItem("Todas las carreras");

        cmbFiltroEstado = new JComboBox<>();
        cmbFiltroEstado.addItem("Todos los estados");
        for (EstadoMatricula e : EstadoMatricula.values()) {
            cmbFiltroEstado.addItem(e);
        }

        btnBuscar = crearBoton("Buscar", new Color(59, 139, 212));
        btnLimpiar = crearBoton("Limpiar", new Color(158, 158, 158));

        filaFiltros.add(new JLabel("Nombre:"));
        filaFiltros.add(txtBuscarNombre);
        filaFiltros.add(new JLabel("Carrera:"));
        filaFiltros.add(cmbCarrera);
        filaFiltros.add(new JLabel("Estado:"));
        filaFiltros.add(cmbFiltroEstado);
        filaFiltros.add(btnBuscar);
        filaFiltros.add(btnLimpiar);

        // Fila 2: Botones de Ventanas Emergentes / Acciones Modularizadas
        JPanel filaAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        btnNuevoEstudiante = crearBoton("+ Nuevo Estudiante", new Color(103, 58, 183));
        btnGestionarCursos = crearBoton("Inscribir / Cursos", new Color(255, 152, 0));
        btnGestionarProfesores = crearBoton("Profesores", new Color(63, 81, 181));
        btnCambiarEstado = crearBoton("Cambiar Estado de Selección", new Color(0, 150, 136));

        filaAcciones.add(btnNuevoEstudiante);
        filaAcciones.add(btnGestionarCursos);
        filaAcciones.add(btnGestionarProfesores);
        filaAcciones.add(btnCambiarEstado);

        panelControles.add(filaFiltros);
        panelControles.add(filaAcciones);

        // ── TABLA DE RESULTADOS ────────────────────────────────────────────────
        modeloTabla = new DefaultTableModel(COLUMNAS_TABLA, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblResultados = new JTable(modeloTabla);
        tblResultados.setRowHeight(26);
        tblResultados.getTableHeader().setReorderingAllowed(false);
        tblResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tblResultados);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados / Lista de Estudiantes"));

        // ── BARRA INFERIOR (Estado) ────────────────────────────────────────────
        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
        lblEstado = new JLabel("Listo para consultar.");
        lblEstado.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));

        lblTotalEstudiantes = new JLabel("Total: 0");
        lblTotalEstudiantes.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        lblTotalEstudiantes.setForeground(Color.BLUE);

        panelInferior.add(lblEstado, BorderLayout.WEST);
        panelInferior.add(lblTotalEstudiantes, BorderLayout.EAST);

        add(panelControles, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(btn.getFont().deriveFont(Font.BOLD));
        return btn;
    }

    private void initEventos() {
        // Abrir ventana emergente para registrar estudiante
      btnNuevoEstudiante.addActionListener((ActionEvent e) -> {
            AgregarEstudianteDialog dialog = new AgregarEstudianteDialog(this, controlador);
            dialog.setVisible(true);
            actualizarTotalEstudiantes();
        });

        
        btnGestionarProfesores.addActionListener(e -> {
            GestionarProfesoresDialog dialog = new GestionarProfesoresDialog(this, controlador);
            dialog.setVisible(true);
        });
        
        btnGestionarCursos.addActionListener(e -> {
            int fila = tblResultados.getSelectedRow();
            if (fila == -1) {
                mostrarError("Seleccione un estudiante de la tabla para inscribir a un curso.");
                return;
            }
            int id = (int) modeloTabla.getValueAt(fila, 0);
            String nombre = modeloTabla.getValueAt(fila, 1) + " " + modeloTabla.getValueAt(fila, 2);

            InscribirCursoDialog dialog = new InscribirCursoDialog(this, controlador, id, nombre);
            dialog.setVisible(true);
        });
        
        btnCambiarEstado.addActionListener(e -> {
            int fila = tblResultados.getSelectedRow();
            if (fila == -1) {
                mostrarError("Seleccione un estudiante de la tabla para cambiar su estado.");
                return;
            }
            int id = (int) modeloTabla.getValueAt(fila, 0);
            String nombre = modeloTabla.getValueAt(fila, 1) + " " + modeloTabla.getValueAt(fila, 2);
            Object estadoObj = modeloTabla.getValueAt(fila, 5);
            EstadoMatricula estadoActual = (estadoObj instanceof EstadoMatricula) ? (EstadoMatricula) estadoObj : null;

            CambiarEstadoDialog dialog = new CambiarEstadoDialog(this, controlador, id, nombre, estadoActual, () -> {
                if (controlador != null) {
                    controlador.buscarEstudiante(modeloTabla.getValueAt(fila, 1).toString());
                }
            });
            dialog.setVisible(true);
        });

        // Búsquedas
       btnBuscar.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                String nombre = txtBuscarNombre.getText().trim();
                String carrera = (String) cmbCarrera.getSelectedItem();
                Object estadoObj = cmbFiltroEstado.getSelectedItem();

                // 1. Prioridad: Búsqueda por Nombre
                if (!nombre.isEmpty()) {
                    controlador.buscarEstudiante(nombre);
                } 
                // 2. Búsqueda por Carrera
                else if (carrera != null && !carrera.equals("Todas las carreras")) {
                    controlador.buscarEstudiantePorCarrera(carrera);
                } 
                // 3. Búsqueda por Estado de Matrícula (Enum)
                else if (estadoObj instanceof EstadoMatricula) {
                    controlador.buscarEstudiantesPorEstado((EstadoMatricula) estadoObj);
                } 
                // Si no especificó ningún filtro
                else {
                    mostrarError("Ingrese un nombre o seleccione un filtro (Carrera o Estado) para buscar.");
                }
            }
        });

        btnLimpiar.addActionListener((ActionEvent e) -> {
            txtBuscarNombre.setText("");
            cmbCarrera.setSelectedIndex(0);
            cmbFiltroEstado.setSelectedIndex(0);
            limpiarTabla();
            lblEstado.setText("Búsqueda limpiada.");
        });

        // Acciones para Cursos, Profesores y Estado
        btnGestionarCursos.addActionListener(e -> {
            int fila = tblResultados.getSelectedRow();
            if (fila == -1) {
                mostrarError("Seleccione un estudiante en la tabla para gestionarle cursos.");
            } else {
                mostrarMensaje("Módulo de Cursos listo para asociar con la selección.");
            }
        });

        btnGestionarProfesores.addActionListener(e -> {
            mostrarMensaje("Módulo de Profesores disponible.");
        });

        btnCambiarEstado.addActionListener(e -> {
            int fila = tblResultados.getSelectedRow();
            if (fila == -1) {
                mostrarError("Seleccione un estudiante en la tabla para cambiar su estado.");
            } else {
                mostrarMensaje("Módulo para actualizar estado de matrícula listo.");
            }
        });
    }

    public void cargarCarreras() {
        if (controlador != null) {
            for (String carrera : controlador.obtenerCarrerasUnicas()) {
                cmbCarrera.addItem(carrera);
            }
        }
    }

    public void mostrarEstudiante(Object[] fila) {
        limpiarTabla();
        modeloTabla.addRow(fila);
        lblEstado.setText("Se encontró 1 registro.");
    }

    public void mostrarEstudiantes(List<Object[]> filas) {
        limpiarTabla();
        if (filas == null || filas.isEmpty()) {
            lblEstado.setText("No hay resultados.");
            return;
        }
        for (Object[] fila : filas) {
            modeloTabla.addRow(fila);
        }
        lblEstado.setText("Se encontraron " + filas.size() + " registros.");
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Atención", JOptionPane.WARNING_MESSAGE);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void setControlador(EstudianteController controlador) {
        this.controlador = controlador;
        cargarCarreras();
        actualizarTotalEstudiantes();
    }

    private void actualizarTotalEstudiantes() {
        int total = (controlador != null) ? controlador.obtenerTotalEstudiantes() : 0;
        lblTotalEstudiantes.setText("Total de estudiantes: " + total);
    }

    private void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }
}