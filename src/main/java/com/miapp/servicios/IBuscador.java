/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.miapp.servicios;

/**
 *
 * @author taidy
 */

public interface IBuscador {
    void buscarEstudiante(String criterio);
    void buscarEstudiantePorCarrera(String carrera);
    void cargarDatos();
    void buscarEstudiantesPorCurso(String codigoCurso);
    void buscarEstudiantePorEstado(String estadoMatricula);
}
