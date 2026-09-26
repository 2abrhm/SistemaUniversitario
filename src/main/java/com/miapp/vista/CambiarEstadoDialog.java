package com.miapp.vista;

import com.miapp.controlador.EstudianteController;
import com.miapp.utilidades.EstadoMatricula;

import javax.swing.*;
import java.awt.*;

public class CambiarEstadoDialog extends JDialog {

    private JLabel lblEstudiante;
    private JComboBox<EstadoMatricula> cmbEstados;
    private JButton btnActualizar;
    private JButton btnCancelar;

    private EstudianteController controlador;
    private int idEstudiante;
    private Runnable alActualizar;

    public CambiarEstadoDialog(Frame parent, EstudianteController controlador, int idEstudiante, String nombreEstudiante, EstadoMatricula estadoActual, Runnable alActualizar) {
        super(parent, "Actualizar Estado de Matrícula", true);
        this.controlador = controlador;
        this.idEstudiante = idEstudiante;
        this.alActualizar = alActualizar;

        initUI(nombreEstudiante, estadoActual);
    }

    private void initUI(String nombreEstudiante, EstadoMatricula estadoActual) {
        setSize(400, 200);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(2, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        lblEstudiante = new JLabel(nombreEstudiante);
        cmbEstados = new JComboBox<>(EstadoMatricula.values());
        if (estadoActual != null) {
            cmbEstados.setSelectedItem(estadoActual);
        }

        panelForm.add(new JLabel("Estudiante:"));
        panelForm.add(lblEstudiante);
        panelForm.add(new JLabel("Nuevo Estado:"));
        panelForm.add(cmbEstados);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnActualizar = new JButton("Guardar Cambio");
        btnActualizar.setBackground(new Color(0, 150, 136));
        btnActualizar.setForeground(Color.WHITE);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnActualizar);
        panelBotones.add(btnCancelar);

        add(panelForm, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

     btnActualizar.addActionListener(e -> {
    EstadoMatricula nuevoEstado = (EstadoMatricula) cmbEstados.getSelectedItem();
    if (controlador != null) {
        controlador.cambiarEstadoEstudiante(idEstudiante, nuevoEstado);
    }
    if (alActualizar != null) {
        alActualizar.run(); // Refresca la tabla en la vista principal
    }
    dispose();
});
    }
}