/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miapp.vista;

import com.miapp.controlador.EstudianteController;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author abrah
 */
public class AgregarEstudianteDialog extends JDialog{
    
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JComboBox<String> cmbCarrera;
    private JSpinner spinPromedio;
    private JButton btnGuardar;
    private JButton btnCancelar;

    private EstudianteController controlador;
    private EstudianteView vistaPrincipal;

    public AgregarEstudianteDialog(EstudianteView parent, EstudianteController controlador) {
        super(parent, "Registrar Nuevo Estudiante", true); // 'true' bloquea la ventana de atrás (modal)
        this.vistaPrincipal = parent;
        this.controlador = controlador;

        initUI();
    }

    private void initUI() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        // Panel del formulario
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        txtNombre = new JTextField();
        txtApellido = new JTextField();
        cmbCarrera = new JComboBox<>();
        cmbCarrera.addItem("Seleccionar...");

        if (controlador != null) {
            for (String c : controlador.obtenerCarrerasUnicas()) {
                cmbCarrera.addItem(c);
            }
        }

        spinPromedio = new JSpinner(new SpinnerNumberModel(3.0, 0.0, 5.0, 0.1));

        panelForm.add(new JLabel("Nombre:"));
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Apellido:"));
        panelForm.add(txtApellido);
        panelForm.add(new JLabel("Carrera:"));
        panelForm.add(cmbCarrera);
        panelForm.add(new JLabel("Promedio Inicial:"));
        panelForm.add(spinPromedio);

        // Panel de Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(76, 175, 80));
        btnGuardar.setForeground(Color.WHITE);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(244, 67, 54));
        btnCancelar.setForeground(Color.WHITE);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelForm, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnCancelar.addActionListener(e -> dispose());

        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String carrera = (String) cmbCarrera.getSelectedItem();
            double promedio = (double) spinPromedio.getValue();

            if (nombre.isEmpty() || apellido.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (carrera == null || carrera.equals("Seleccionar...")) {
                JOptionPane.showMessageDialog(this, "Seleccione una carrera válida.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (controlador.agregarEstudiante(nombre, apellido, carrera, promedio)) {
                dispose(); // Cierra el diálogo emergente tras guardar
            }
        });
    }
}    
    

