package bo.umss.fcyt.sis.ayudaya.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa a un donante en el sistema
 * Implementa RF-001: Registro de usuarios donantes
 */
public class Donante extends Usuario {
    private String direccion;
    private List<Donacion> donacionesRealizadas;
    private int puntosRecompensa;
    
    public Donante() {
        super();
        this.tipo = TipoUsuario.DONANTE;
        this.donacionesRealizadas = new ArrayList<>();
        this.puntosRecompensa = 0;
    }
    
    public Donante(String nombre, String email, String telefono, String contraseña, String direccion) {
        super(nombre, email, telefono, contraseña, TipoUsuario.DONANTE);
        this.direccion = direccion;
        this.donacionesRealizadas = new ArrayList<>();
        this.puntosRecompensa = 0;
    }
    
    /**
     * Método para publicar una nueva donación
     * Implementa RF-002: Publicar artículos para donación
     */
    public Donacion publicarDonacion(String nombre, String descripcion, String categoria, String estado, String rutaFoto) {
        Donacion donacion = new Donacion(nombre, descripcion, categoria, estado, rutaFoto, this.id);
        donacionesRealizadas.add(donacion);
        return donacion;
    }
    
    /**
     * Método para obtener el historial de donaciones
     * Implementa RF-007: Ver historial de donaciones
     */
    public List<Donacion> getHistorialDonaciones() {
        return new ArrayList<>(donacionesRealizadas);
    }
    
    /**
     * Método para agregar puntos de recompensa
     */
    public void agregarPuntos(int puntos) {
        this.puntosRecompensa += puntos;
    }
    
    // Getters y Setters
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public List<Donacion> getDonacionesRealizadas() { return donacionesRealizadas; }
    public void setDonacionesRealizadas(List<Donacion> donacionesRealizadas) { 
        this.donacionesRealizadas = donacionesRealizadas; 
    }
    
    public int getPuntosRecompensa() { return puntosRecompensa; }
    public void setPuntosRecompensa(int puntosRecompensa) { this.puntosRecompensa = puntosRecompensa; }
    
    @Override
    public String toString() {
        return String.format("Donante{id='%s', nombre='%s', email='%s', direccion='%s', donaciones=%d, puntos=%d}", 
                           id, nombre, email, direccion, donacionesRealizadas.size(), puntosRecompensa);
    }
}