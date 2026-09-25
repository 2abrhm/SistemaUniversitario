/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miapp.modelo;

public class Profesor extends Persona {
    
    // Atributo privado y final
    private final double salarioBase;

    // Constructor manteniendo el orden de la clase padre (nombre, id, apellido)
    public Profesor(String nombre, int id, String apellido, double salarioBase) {
        super(nombre, id, apellido); 
        this.salarioBase = salarioBase;
    }

    // Método solicitado en la guía
    public void impartirClase() {
        System.out.println("El profesor " + getNombre() + " " + getApellido() + " está impartiendo clase.");
    }

    // Implementación obligatoria del método abstracto de Persona
    @Override
    public double calcularPago() {
        return salarioBase; // El pago del profesor es su salario base
    }

    // Al ser final, solo lleva Getter (no Setter)
    public double getSalarioBase() {
        return salarioBase;
    }
}