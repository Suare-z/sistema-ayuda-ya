package bo.umss.fcyt.sis.ayudaya.modelo;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Clase que representa una donación en el sistema
 * Implementa RF-002: Publicar artículos para donación
 */
public class Donacion {
    private String id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String estado;
    private String rutaFotografia;
    private String idDonante;
    private EstadoDonacion estadoDonacion;
    private LocalDateTime fechaPublicacion;
    private LocalDateTime fechaEntrega;
    private String idInstitucionSolicitante;
    
    public enum EstadoDonacion {
        DISPONIBLE, SOLICITADA, EN_TRANSITO, ENTREGADA, CANCELADA
    }
    
    public Donacion() {
        this.fechaPublicacion = LocalDateTime.now();
        this.estadoDonacion = EstadoDonacion.DISPONIBLE;
    }
    
    public Donacion(String nombre, String descripcion, String categoria, String estado, 
                   String rutaFotografia, String idDonante) {
        this();
        this.id = generarId();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.estado = estado;
        this.rutaFotografia = rutaFotografia;
        this.idDonante = idDonante;
    }
    
    private String generarId() {
        return "DON" + System.currentTimeMillis();
    }
    
    /**
     * Método para marcar la donación como solicitada
     * Implementa RF-004: Solicitar donaciones
     */
    public void marcarComoSolicitada(String idInstitucion) {
        if (this.estadoDonacion != EstadoDonacion.DISPONIBLE) {
            throw new IllegalStateException("La donación no está disponible para ser solicitada");
        }
        this.estadoDonacion = EstadoDonacion.SOLICITADA;
        this.idInstitucionSolicitante = idInstitucion;
    }
    
    /**
     * Método para marcar la donación como en tránsito
     * Implementa RF-005: Envío de donación
     */
    public void marcarEnTransito() {
        if (this.estadoDonacion != EstadoDonacion.SOLICITADA) {
            throw new IllegalStateException("La donación debe estar solicitada para ser enviada");
        }
        this.estadoDonacion = EstadoDonacion.EN_TRANSITO;
    }
    
    /**
     * Método para confirmar la entrega
     * Implementa RF-006: Confirmación de entrega
     */
    public void confirmarEntrega() {
        if (this.estadoDonacion != EstadoDonacion.EN_TRANSITO) {
            throw new IllegalStateException("La donación debe estar en tránsito para ser entregada");
        }
        this.estadoDonacion = EstadoDonacion.ENTREGADA;
        this.fechaEntrega = LocalDateTime.now();
    }
    
    /**
     * Método para verificar si la donación está disponible
     * Implementa RF-003: Buscar donaciones disponibles
     */
    public boolean estaDisponible() {
        return this.estadoDonacion == EstadoDonacion.DISPONIBLE;
    }
    
    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getRutaFotografia() { return rutaFotografia; }
    public void setRutaFotografia(String rutaFotografia) { this.rutaFotografia = rutaFotografia; }
    
    public String getIdDonante() { return idDonante; }
    public void setIdDonante(String idDonante) { this.idDonante = idDonante; }
    
    public EstadoDonacion getEstadoDonacion() { return estadoDonacion; }
    public void setEstadoDonacion(EstadoDonacion estadoDonacion) { this.estadoDonacion = estadoDonacion; }
    
    public LocalDateTime getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDateTime fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
    
    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    
    public String getIdInstitucionSolicitante() { return idInstitucionSolicitante; }
    public void setIdInstitucionSolicitante(String idInstitucionSolicitante) { 
        this.idInstitucionSolicitante = idInstitucionSolicitante; 
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Donacion donacion = (Donacion) obj;
        return Objects.equals(id, donacion.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Donacion{id='%s', nombre='%s', categoria='%s', estado=%s, fechaPublicacion=%s}", 
                           id, nombre, categoria, estadoDonacion, fechaPublicacion);
    }
}