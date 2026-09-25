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
   
    private String carrera;
    private double promedio;
    private EstadoMatricula estado;
    private List<Curso> cursosInscritos;

    // ── Constructor ───────────────────────────────────────────────────────────

    public Estudiante(String nombre, int id, String apellido, String carrera, double promedio) {
        super(nombre, id, apellido);
        this.carrera = carrera;
        this.estado = EstadoMatricula.ACTIVO; // Se inicializa por defecto
        this.cursosInscritos = new ArrayList<>(); // Se inicializa vacía
   
        if (promedio >= PROMEDIO_MINIMO && promedio <= PROMEDIO_MAXIMO) {
            this.promedio = promedio;
        } else {
            this.promedio = 0.0;  // Por defecto si está fuera de rango
        }
        
        totalEstudiantes++;
    }
        
    @Override
    public boolean inscribir(Curso curso) {
        if (cursosInscritos.size() < MAX_MATERIAS) {
            cursosInscritos.add(curso);
            return true;
        }
        return false;   
    } // ¡AQUÍ FALTABA ESTA LLAVE!
        
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

    public List<Curso> getCursosInscritos() {
        return cursosInscritos;
    }

    public void setCursosInscritos(List<Curso> cursosInscritos) {
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