package bo.umss.fcyt.sis.ayudaya.modelo;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Clase que representa una solicitud de donación
 * Implementa RF-004: Solicitar donaciones específicas
 */
public class SolicitudDonacion {
    private String id;
    private String idInstitucion;
    private String idDonacion;
    private String motivo;
    private EstadoSolicitud estado;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaRespuesta;
    private String observaciones;
    
    public enum EstadoSolicitud {
        PENDIENTE, APROBADA, RECHAZADA, EN_PROCESO, COMPLETADA
    }
    
    public SolicitudDonacion() {
        this.fechaSolicitud = LocalDateTime.now();
        this.estado = EstadoSolicitud.PENDIENTE;
    }
    
    public SolicitudDonacion(String idInstitucion, String idDonacion, String motivo) {
        this();
        this.id = generarId();
        this.idInstitucion = idInstitucion;
        this.idDonacion = idDonacion;
        this.motivo = motivo;
    }
    
    private String generarId() {
        return "SOL" + System.currentTimeMillis();
    }
    
    /**
     * Método para aprobar la solicitud
     */
    public void aprobar() {
        if (this.estado != EstadoSolicitud.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden aprobar solicitudes pendientes");
        }
        this.estado = EstadoSolicitud.APROBADA;
        this.fechaRespuesta = LocalDateTime.now();
    }
    
    /**
     * Método para rechazar la solicitud
     */
    public void rechazar(String observaciones) {
        if (this.estado != EstadoSolicitud.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden rechazar solicitudes pendientes");
        }
        this.estado = EstadoSolicitud.RECHAZADA;
        this.fechaRespuesta = LocalDateTime.now();
        this.observaciones = observaciones;
    }
    
    /**
     * Método para marcar la solicitud como en proceso
     */
    public void marcarEnProceso() {
        if (this.estado != EstadoSolicitud.APROBADA) {
            throw new IllegalStateException("La solicitud debe estar aprobada para ser procesada");
        }
        this.estado = EstadoSolicitud.EN_PROCESO;
    }
    
    /**
     * Método para completar la solicitud
     */
    public void completar() {
        if (this.estado != EstadoSolicitud.EN_PROCESO) {
            throw new IllegalStateException("La solicitud debe estar en proceso para ser completada");
        }
        this.estado = EstadoSolicitud.COMPLETADA;
    }
    
    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getIdInstitucion() { return idInstitucion; }
    public void setIdInstitucion(String idInstitucion) { this.idInstitucion = idInstitucion; }
    
    public String getIdDonacion() { return idDonacion; }
    public void setIdDonacion(String idDonacion) { this.idDonacion = idDonacion; }
    
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    
    public EstadoSolicitud getEstado() { return estado; }
    public void setEstado(EstadoSolicitud estado) { this.estado = estado; }
    
    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }
    
    public LocalDateTime getFechaRespuesta() { return fechaRespuesta; }
    public void setFechaRespuesta(LocalDateTime fechaRespuesta) { this.fechaRespuesta = fechaRespuesta; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SolicitudDonacion that = (SolicitudDonacion) obj;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("SolicitudDonacion{id='%s', institucion='%s', donacion='%s', estado=%s, fecha=%s}", 
                           id, idInstitucion, idDonacion, estado, fechaSolicitud);
    }
}