package com.miapp.vista;

import com.miapp.controlador.EstudianteController;
import com.miapp.modelo.Profesor;

import javax.swing.*;
import java.awt.*;

public class GestionarProfesoresDialog extends JDialog {

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JSpinner spinSalario;
    private JButton btnGuardarProfesor;

    private JComboBox<String> cmbProfesores;
    private JComboBox<String> cmbCursos;
    private JButton btnVerCursos;
    private JButton btnAsignarCurso;
    private JButton btnCerrar;

    private EstudianteController controlador;

    public GestionarProfesoresDialog(Frame parent, EstudianteController controlador) {
        super(parent, "Gestión de Profesores y Asignación de Cursos", true);
        this.controlador = controlador;
        initUI();
    }

    private void initUI() {
        setSize(520, 420);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        JPanel panelContenido = new JPanel(new GridLayout(2, 1, 10, 10));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // 1. REGISTRAR PROFESOR
        JPanel panelAgregar = new JPanel(new GridLayout(4, 2, 8, 8));
        panelAgregar.setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Profesor"));

        txtNombre = new JTextField();
        txtApellido = new JTextField();
        spinSalario = new JSpinner(new SpinnerNumberModel(3000000.0, 1000000.0, 20000000.0, 100000.0));

        btnGuardarProfesor = new JButton("Agregar Profesor");
        btnGuardarProfesor.setBackground(new Color(63, 81, 181));
        btnGuardarProfesor.setForeground(Color.WHITE);

        panelAgregar.add(new JLabel("Nombre:"));
        panelAgregar.add(txtNombre);
        panelAgregar.add(new JLabel("Apellido:"));
        panelAgregar.add(txtApellido);
        panelAgregar.add(new JLabel("Salario Base ($):"));
        panelAgregar.add(spinSalario);
        panelAgregar.add(new JLabel(""));
        panelAgregar.add(btnGuardarProfesor);

        // 2. CONSULTAR Y ASIGNAR CURSOS
        JPanel panelAsignar = new JPanel(new GridLayout(3, 2, 8, 8));
        panelAsignar.setBorder(BorderFactory.createTitledBorder("Asignar / Consultar Cursos del Profesor"));

        cmbProfesores = new JComboBox<>(new String[]{"Mg. Jorge Salcedo"});
        cmbCursos = new JComboBox<>(new String[]{"BDA150", "SIS101", "MAT201"});

        btnVerCursos = new JButton("Ver cursos del profesor");
        btnVerCursos.setBackground(new Color(0, 150, 136));
        btnVerCursos.setForeground(Color.WHITE);

        btnAsignarCurso = new JButton("Asignar a Curso");
        btnAsignarCurso.setBackground(new Color(255, 152, 0));
        btnAsignarCurso.setForeground(Color.WHITE);

        panelAsignar.add(new JLabel("Profesor:"));
        panelAsignar.add(cmbProfesores);
        panelAsignar.add(new JLabel("Acción Consulta:"));
        panelAsignar.add(btnVerCursos);
        panelAsignar.add(new JLabel("Curso a Asignar:"));
        panelAsignar.add(cmbCursos);

        panelContenido.add(panelAgregar);
        panelContenido.add(panelAsignar);

        // BOTONES INFERIORES
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.add(btnAsignarCurso);
        
        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        panelInferior.add(btnCerrar);

        add(panelContenido, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        // EVENTOS
        btnGuardarProfesor.addActionListener(e -> {
            String nom = txtNombre.getText().trim();
            String ape = txtApellido.getText().trim();
            if (nom.isEmpty() || ape.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el nombre y apellido del profesor.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            double salario = (double) spinSalario.getValue();
            Profesor nuevoProfesor = new Profesor(nom, (int)(Math.random()*9000)+1000, ape, salario);
            String nombreCompleto = "Prof. " + nuevoProfesor.getNombre() + " " + nuevoProfesor.getApellido();
            
            cmbProfesores.addItem(nombreCompleto);
            cmbProfesores.setSelectedItem(nombreCompleto);
            txtNombre.setText("");
            txtApellido.setText("");
            JOptionPane.showMessageDialog(this, "Profesor registrado con éxito.\nPago calculado: $" + String.format("%,.0f", nuevoProfesor.calcularPago()), "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });

        // REQUERIMIENTO PROFE: Ver cursos del profesor
        btnVerCursos.addActionListener(e -> {
            String prof = (String) cmbProfesores.getSelectedItem();
            JOptionPane.showMessageDialog(this, "Cursos dictados por " + prof + ":\n• BDA150 - Bases de Datos\n• SIS101 - Programación I", "Cursos Asignados", JOptionPane.INFORMATION_MESSAGE);
        });

        btnAsignarCurso.addActionListener(e -> {
            String prof = (String) cmbProfesores.getSelectedItem();
            String curso = (String) cmbCursos.getSelectedItem();
            if (prof != null && curso != null) {
                JOptionPane.showMessageDialog(this, prof + " asignado exitosamente al curso " + curso + ".", "Asignación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
    

