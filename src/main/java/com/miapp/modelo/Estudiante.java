package com.miapp.modelo;

import com.miapp.servicios.Inscribible;
import com.miapp.utilidades.EstadoMatricula;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo: representa la entidad Estudiante.
 */
public class Estudiante extends Persona implements Inscribible {  

    private static int totalEstudiantes = 0;
    public static final int PROMEDIO_MINIMO = 0;
    public static final int PROMEDIO_MAXIMO = 5;
    public static final int MAX_MATERIAS = 7;
    
    // ── Atributos de instancia ────────────────────────────────────────────────
    
    private List<String> cursosInscritos;
    private String carrera;
    private double promedio;
    private EstadoMatricula estado;

    // ── Constructor ───────────────────────────────────────────────────────────

    public Estudiante(String nombre, int id, String apellido, String carrera, double promedio) {
        super(nombre, id, apellido);
        this.carrera = carrera;
        this.estado = EstadoMatricula.ACTIVO;
        this.cursosInscritos = new ArrayList<>();
   
        if (promedio >= PROMEDIO_MINIMO && promedio <= PROMEDIO_MAXIMO) {
            this.promedio = promedio;
        } else {
            this.promedio = 0.0;
        }
        
        totalEstudiantes++;
    }
        
    @Override
    public boolean inscribir(Curso curso) {
        if (curso != null && cursosInscritos.size() < MAX_MATERIAS) {
            cursosInscritos.add(curso.toString()); // Convierte el objeto Curso a String
            return true;
        }
        return false;   
    }

    // Métodos para gestionar cursos mediante texto (utilizados por el controlador)
    public void agregarCurso(String curso) {
        if (curso != null && !cursosInscritos.contains(curso) && cursosInscritos.size() < MAX_MATERIAS) {
            cursosInscritos.add(curso);
        }
    }

    public boolean estaInscritoEn(String curso) {
        if (curso == null) return false;
        for (String c : cursosInscritos) {
            if (c.toLowerCase().contains(curso.toLowerCase()) || curso.toLowerCase().contains(c.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
        
    @Override
    public double calcularPago() {
        return 1500000.0; 
    }

    // ── Métodos estáticos (de clase) ──────────────────────────────────────────

    public static int getTotalEstudiantes() {
        return totalEstudiantes;
    }

    public static void reiniciarContador() {
        totalEstudiantes = 0;
    }

    public static int getProximoId() {  
        return totalEstudiantes + 1;
    }

    // ── Getters y Setters ──────────────────────────────────────────────────────

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public EstadoMatricula getEstado() {
        return estado;
    }

    public void setEstado(EstadoMatricula estado) {
        this.estado = estado;
    }

    public List<String> getCursosInscritos() {
        return cursosInscritos;
    }

    public void setCursosInscritos(List<String> cursosInscritos) {
        this.cursosInscritos = cursosInscritos;
    }
    
    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double p) {
        if (p >= PROMEDIO_MINIMO && p <= PROMEDIO_MAXIMO) {
            this.promedio = p;
        }
    }

    @Override
    public final String toString() {
        return "ID: " + id
             + " | Nombre: " + getNombre()
             + " | Apellido: " + getApellido()
             + " | Carrera: " + carrera
             + " | Promedio: " + String.format("%.2f", promedio);
    }
}