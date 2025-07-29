package bo.umss.fcyt.sis.ayudaya.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa a una institución benéfica en el sistema
 * Implementa RF-001: Registro de instituciones con validación
 */
public class Institucion extends Usuario {
    private String numeroRegistroLegal;
    private String direccion;
    private TipoInstitucion tipoInstitucion;
    private String representanteLegal;
    private boolean documentosValidados;
    private List<SolicitudDonacion> solicitudesRealizadas;
    
    public enum TipoInstitucion {
        HOSPITAL, ESCUELA, ORFANATO, CASA_HOGAR, FUNDACION, ASOCIACION_BENEFICA
    }
    
    public Institucion() {
        super();
        this.tipo = TipoUsuario.INSTITUCION;
        this.documentosValidados = false;
        this.solicitudesRealizadas = new ArrayList<>();
    }
    
    public Institucion(String nombre, String email, String telefono, String contraseña, 
                      String numeroRegistroLegal, String direccion, TipoInstitucion tipoInstitucion, 
                      String representanteLegal) {
        super(nombre, email, telefono, contraseña, TipoUsuario.INSTITUCION);
        this.numeroRegistroLegal = numeroRegistroLegal;
        this.direccion = direccion;
        this.tipoInstitucion = tipoInstitucion;
        this.representanteLegal = representanteLegal;
        this.documentosValidados = false;
        this.solicitudesRealizadas = new ArrayList<>();
    }
    
    /**
     * Método para solicitar una donación específica
     * Implementa RF-004: Solicitar donaciones mediante publicaciones
     */
    public SolicitudDonacion solicitarDonacion(Donacion donacion, String motivo) {
        if (!this.documentosValidados) {
            throw new IllegalStateException("La institución debe tener documentos validados para solicitar donaciones");
        }
        
        SolicitudDonacion solicitud = new SolicitudDonacion(this.id, donacion.getId(), motivo);
        solicitudesRealizadas.add(solicitud);
        return solicitud;
    }
    
    /**
     * Método para obtener el historial de solicitudes
     * Implementa RF-007: Ver historial de donaciones
     */
    public List<SolicitudDonacion> getHistorialSolicitudes() {
        return new ArrayList<>(solicitudesRealizadas);
    }
    
    /**
     * Método para validar documentos institucionales
     */
    public void validarDocumentos() {
        this.documentosValidados = true;
        this.setVerificado(true);
    }
    
    // Getters y Setters
    public String getNumeroRegistroLegal() { return numeroRegistroLegal; }
    public void setNumeroRegistroLegal(String numeroRegistroLegal) { 
        this.numeroRegistroLegal = numeroRegistroLegal; 
    }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public TipoInstitucion getTipoInstitucion() { return tipoInstitucion; }
    public void setTipoInstitucion(TipoInstitucion tipoInstitucion) { 
        this.tipoInstitucion = tipoInstitucion; 
    }
    
    public String getRepresentanteLegal() { return representanteLegal; }
    public void setRepresentanteLegal(String representanteLegal) { 
        this.representanteLegal = representanteLegal; 
    }
    
    public boolean isDocumentosValidados() { return documentosValidados; }
    public void setDocumentosValidados(boolean documentosValidados) { 
        this.documentosValidados = documentosValidados; 
    }
    
    public List<SolicitudDonacion> getSolicitudesRealizadas() { return solicitudesRealizadas; }
    public void setSolicitudesRealizadas(List<SolicitudDonacion> solicitudesRealizadas) { 
        this.solicitudesRealizadas = solicitudesRealizadas; 
    }
    
    @Override
    public String toString() {
        return String.format("Institucion{id='%s', nombre='%s', tipo=%s, representante='%s', validada=%s}", 
                           id, nombre, tipoInstitucion, representanteLegal, documentosValidados);
    }
}