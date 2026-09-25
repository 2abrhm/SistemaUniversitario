/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miapp.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author abrah
 */
public class Curso {
    
    private String codigo;
    private int creditos;
    
    private List<Estudiante> estudiantesInscritos;
    
    public Curso(String codigo, int creditos){
        this.codigo = codigo;
        this.creditos = creditos;
        this.estudiantesInscritos = new ArrayList();
    }
    
    public boolean agregarEstudiante(Estudiante estudiante){
        if (estudiante != null && ! estudiantesInscritos.contains(estudiante)){
            return true;
        }
        return false;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public List<Estudiante> getEstudiantesInscritos() {
        return estudiantesInscritos;
    }

    public void setEstudiantesInscritos(List<Estudiante> estudiantesInscritos) {
        this.estudiantesInscritos = estudiantesInscritos;
    }
    
   @Override
    public String toString(){
        return codigo + " (" + creditos + " cr.";
    }
    
}
