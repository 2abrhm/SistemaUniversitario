package com.miapp.controlador;

import com.miapp.modelo.Estudiante;
import com.miapp.servicios.IBuscador;
import com.miapp.vista.EstudianteView;
import com.miapp.utilidades.EstadoMatricula;

import java.util.ArrayList;
import java.util.List;

public class EstudianteController implements IBuscador {

    // ── Constantes finales ────────────────────────────────────────────────────
    private static final int CANTIDAD_ESTUDIANTES_INICIALES = 12;
    private static final String MENSAJE_BUSQUEDA_VACIA = "Por favor ingrese un nombre para buscar.";
    private static final String MENSAJE_BUSQUEDA_CARRERA_VACIA = "Por favor seleccione una carrera para buscar.";

    // ── Vista ─────────────────────────────────────────────────────────────────
    private EstudianteView vista;

    // ── Array de estudiantes (fuente de datos) ────────────────────────────────
    
    private Estudiante[] estudiantes;

    // ── Constructor ───────────────────────────────────────────────────────────
    public EstudianteController(EstudianteView vista) {
        this.vista = vista;
        cargarDatos();
        this.vista.setControlador(this);
    }

    // ── Implementación de la interfaz IBuscador ───────────────────────────────
    @Override
    public void cargarDatos() {
        inicializarEstudiantes();
    }

    @Override
    public void buscarEstudiante(String criterio) {
        buscarPorCriterio(criterio);
    }

    @Override
    public void buscarEstudiantePorCarrera(String carrera) {
        buscarPorCarrera(carrera);
    }

    @Override
    public void buscarEstudiantesPorCurso(String codigoCurso) {
        buscarEstudiantesPorCursoLogica(codigoCurso);
    }

    @Override
    public void buscarEstudiantePorEstado(String estadoMatricula) {
        try {
            EstadoMatricula estado = EstadoMatricula.valueOf(estadoMatricula);
            buscarEstudiantesPorEstado(estado);
        } catch (Exception e) {
            vista.mostrarError("Estado no válido.");
        }
    }

    // ── Carga de datos iniciales ──────────────────────────────────────────────
    private void inicializarEstudiantes() {
        estudiantes = new Estudiante[CANTIDAD_ESTUDIANTES_INICIALES];
        Estudiante.reiniciarContador();

        estudiantes[0]  = new Estudiante("Ana ", 1, "García", "Ingeniería de Sistemas", 4.5);
        estudiantes[1]  = new Estudiante("Carlos", 2, " López", "Ingeniería Civil", 3.8);
        estudiantes[2]  = new Estudiante("María", 3, "Rodríguez", "Medicina", 4.9);
        estudiantes[3]  = new Estudiante("José ", 4, "Martínez", "Derecho", 3.5);
        estudiantes[4]  = new Estudiante("Laura ", 5, "Sánchez", "Administración", 4.1);
        estudiantes[5]  = new Estudiante("Andrés ", 6, "Torres", "Ingeniería de Sistemas", 3.9);
        estudiantes[6]  = new Estudiante("Valentina ", 7, "Gómez", "Psicología", 4.3);
        estudiantes[7]  = new Estudiante("Luis ", 8, "Herrera", "Economía", 3.7);
        estudiantes[8]  = new Estudiante("Sofía ", 9, "Díaz", "Ingeniería Civil", 4.6);
        estudiantes[9]  = new Estudiante("Juliana ", 10, "Morales", "Medicina", 4.8);
        estudiantes[10] = new Estudiante("Ana Milena ", 11, "Ruiz", "Derecho", 4.0);
        estudiantes[11] = new Estudiante("Carlos Andrés ", 12, "Paz", "Administración", 3.6);

        System.out.println("Total de estudiantes cargados: " + Estudiante.getTotalEstudiantes());
    }

    // ── Lógica de búsqueda ────────────────────────────────────────────────────
    private void buscarPorCriterio(String criterio) {
        if (criterio == null || criterio.isEmpty()) {
            vista.mostrarError(MENSAJE_BUSQUEDA_VACIA);
            return;
        }

        List<Estudiante> resultados = new ArrayList<>();
        String criterioBajo = criterio.toLowerCase();

        for (Estudiante e : estudiantes) {
            if (e != null && (e.getNombre().toLowerCase().contains(criterioBajo) ||
                e.getApellido().toLowerCase().contains(criterioBajo))) {
                resultados.add(e);
            }
        }

        if (resultados.isEmpty()) {
            vista.mostrarEstudiantes(new ArrayList<>()); 
        } else if (resultados.size() == 1) {
            vista.mostrarEstudiante(convertirAFila(resultados.get(0)));
        } else {
            vista.mostrarEstudiantes(convertirAFilas(resultados));
        }
    }

    private void buscarPorCarrera(String carrera) {
        if (carrera == null || carrera.isEmpty() || carrera.equals("Seleccionar...")) {
            vista.mostrarError(MENSAJE_BUSQUEDA_CARRERA_VACIA);
            return;
        }

        List<Estudiante> resultados = new ArrayList<>();
        for (Estudiante e : estudiantes) {
            if (e != null && e.getCarrera().equalsIgnoreCase(carrera)) {
                resultados.add(e);
            }
        }

        vista.mostrarEstudiantes(convertirAFilas(resultados));
    }

    public void buscarEstudiantesPorEstado(EstadoMatricula estado) {
        List<Estudiante> resultados = new ArrayList<>();
        for (Estudiante e : estudiantes) {
            if (e != null && e.getEstado() == estado) {
                resultados.add(e);
            }
        }
        vista.mostrarEstudiantes(convertirAFilas(resultados));
    }

    public void buscarEstudiantesPorCursoLogica(String curso) {
     List<Estudiante> resultados = new ArrayList<>();

    for (Estudiante e : estudiantes) {
        if (e != null && e.estaInscritoEn(curso)) {
            resultados.add(e);
        }
    }

    if (resultados.isEmpty()) {
        vista.mostrarError("No se encontraron estudiantes matriculados en: " + curso);
    }

 
    vista.mostrarEstudiantes(convertirAFilas(resultados));
    }

    // ── Métodos Auxiliares ────────────────────────────────────────────────────
    private Object[] convertirAFila(Estudiante e) {
        return new Object[]{
            e.getId(),
            e.getNombre(),
            e.getApellido(),
            e.getCarrera(),
            String.format("%.2f", e.getPromedio()),
            e.getEstado()
        };
    }

    private List<Object[]> convertirAFilas(List<Estudiante> lista) {
        List<Object[]> filas = new ArrayList<>();
        for (Estudiante e : lista) {
            if (e != null) {
                filas.add(convertirAFila(e));
            }
        }
        return filas;
    }

    public Estudiante obtenerEstudiantePorId(int id) {
        for (Estudiante e : estudiantes) {
            if (e != null && e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public String[] obtenerCarrerasUnicas() {
        List<String> carreras = new ArrayList<>();
        for (Estudiante e : estudiantes) {
            if (e != null) {
                String carrera = e.getCarrera();
                if (!carreras.contains(carrera)) {
                    carreras.add(carrera);
                }
            }
        }
        return carreras.toArray(new String[0]);
    }

    public final int obtenerTotalEstudiantes() {
        return Estudiante.getTotalEstudiantes();
    }

    public boolean agregarEstudiante(String nombre, String apellido, String carrera, double promedio) {
        if (nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty() ||
            carrera == null || carrera.isEmpty()) {
            vista.mostrarError("Todos los campos son obligatorios.");
            return false;
        }

        if (estudiantes.length == Estudiante.getTotalEstudiantes()) {
            Estudiante[] nuevoArray = new Estudiante[estudiantes.length + 5];
            System.arraycopy(estudiantes, 0, nuevoArray, 0, estudiantes.length);
            estudiantes = nuevoArray;
        }

        int indiceNuevoEstudiante = Estudiante.getTotalEstudiantes();
        int proximoId = Estudiante.getProximoId();
        
        Estudiante nuevoEstudiante = new Estudiante(nombre, proximoId, apellido, carrera, promedio);
        estudiantes[indiceNuevoEstudiante] = nuevoEstudiante;

        vista.mostrarMensaje("Estudiante agregado correctamente.\nTotal de estudiantes: " +
                            Estudiante.getTotalEstudiantes());

        return true;
    }
    
    public void inscribirEstudianteEnCurso(int id, String curso) {
    Estudiante e = obtenerEstudiantePorId(id);
    if (e != null) {
        vista.mostrarMensaje("Estudiante " + e.getNombre() + " " + e.getApellido() + " inscrito exitosamente en el curso: " + curso);
    } else {
        vista.mostrarError("No se encontró el estudiante seleccionado.");
    }
}
    
    
    public void cambiarEstadoEstudiante(int id, EstadoMatricula nuevoEstado) {
    Estudiante e = obtenerEstudiantePorId(id);
    if (e != null) {
        e.setEstado(nuevoEstado);
        vista.mostrarMensaje("El estado de " + e.getNombre() + " " + e.getApellido() + " se actualizó a: " + nuevoEstado);
    } else {
        vista.mostrarError("No se encontró el estudiante.");
    }
}
    
    
}