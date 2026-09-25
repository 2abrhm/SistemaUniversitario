package com.miapp.vista;

import com.miapp.controlador.EstudianteController;

import javax.swing.*;
import java.awt.*;

public class InscribirCursoDialog extends JDialog {

    private JLabel lblEstudianteSeleccionado;
    private JLabel lblProfesorAsignado;
    private JComboBox<String> cmbCursos;
    private JButton btnVerEstudiantesCurso;
    private JButton btnInscribir;
    private JButton btnCancelar;

    private EstudianteController controlador;
    private int idEstudiante;
    private String nombreEstudiante;

    public InscribirCursoDialog(Frame parent, EstudianteController controlador, int idEstudiante, String nombreEstudiante) {
        super(parent, "Gestión de Cursos e Inscripciones", true);
        this.controlador = controlador;
        this.idEstudiante = idEstudiante;
        this.nombreEstudiante = nombreEstudiante;

        initUI();
    }

    private void initUI() {
        setSize(480, 280);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(4, 1, 8, 8));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        lblEstudianteSeleccionado = new JLabel("Estudiante Seleccionado: " + nombreEstudiante + " (ID: " + idEstudiante + ")");
        lblEstudianteSeleccionado.setFont(lblEstudianteSeleccionado.getFont().deriveFont(Font.BOLD));

        cmbCursos = new JComboBox<>(new String[]{"BDA150 - Bases de Datos", "SIS101 - Programación I", "MAT201 - Cálculo I", "WEB302 - Desarrollo Web"});
        
        lblProfesorAsignado = new JLabel("Profesor asignado al curso: Mg. Jorge Salcedo");
        lblProfesorAsignado.setForeground(new Color(33, 150, 243));

        btnVerEstudiantesCurso = new JButton("Ver estudiantes del curso");
        btnVerEstudiantesCurso.setBackground(new Color(0, 150, 136));
        btnVerEstudiantesCurso.setForeground(Color.WHITE);

        JPanel panelCursoAccion = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelCursoAccion.add(cmbCursos);
        panelCursoAccion.add(btnVerEstudiantesCurso);

        panelForm.add(lblEstudianteSeleccionado);
        panelForm.add(new JLabel("Seleccionar Curso:"));
        panelForm.add(panelCursoAccion);
        panelForm.add(lblProfesorAsignado);

        // Panel Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnInscribir = new JButton("Inscribir en Curso");
        btnInscribir.setBackground(new Color(255, 152, 0));
        btnInscribir.setForeground(Color.WHITE);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnInscribir);
        panelBotones.add(btnCancelar);

        add(panelForm, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // 1. EVENTO PARA VER ESTUDIANTES DEL CURSO
        btnVerEstudiantesCurso.addActionListener(e -> {
            String cursoSeleccionado = (String) cmbCursos.getSelectedItem();
            if (controlador != null) {
                controlador.buscarEstudiantesPorCurso(cursoSeleccionado);
            }
            dispose(); 
        });

        // 2. EVENTO PARA INSCRIBIR AL ESTUDIANTE EN EL CURSO (PASO 2)
        btnInscribir.addActionListener(e -> {
            if (idEstudiante == -1) {
                JOptionPane.showMessageDialog(this, 
                    "Debe seleccionar un estudiante en la tabla principal antes de inscribir.", 
                    "Atención", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            String cursoSeleccionado = (String) cmbCursos.getSelectedItem();
            if (controlador != null) {
                controlador.inscribirEstudianteEnCurso(idEstudiante, cursoSeleccionado);
            }
            dispose();
        });
    }
}