/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miapp.modelo;

/**
 *
 * @author abrah
 */
public class Profesor extends Persona{
    
    private final Double salarioBase;

    public Profesor(Double salarioBase, String nombre, Integer id, String apellido) {
        super(nombre, id, apellido);
        this.salarioBase = salarioBase;
    }

    

    
    
    public void impartirClase(){
        
    }
}
