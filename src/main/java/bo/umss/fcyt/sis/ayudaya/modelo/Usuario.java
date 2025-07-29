package bo.umss.fcyt.sis.ayudaya.modelo;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Clase base para todos los usuarios del sistema Ayuda Ya
 * Implementa RF-001: Registro de usuarios
 */
public abstract class Usuario {
    protected String id;
    protected String nombre;
    protected String email;
    protected String telefono;
    protected String contraseña;
    protected boolean verificado;
    protected LocalDateTime fechaRegistro;
    protected TipoUsuario tipo;
    
    public enum TipoUsuario {
        DONANTE, INSTITUCION
    }
    
    public Usuario() {
        this.fechaRegistro = LocalDateTime.now();
        this.verificado = false;
    }
    
    public Usuario(String nombre, String email, String telefono, String contraseña, TipoUsuario tipo) {
        this();
        this.id = generarId();
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.tipo = tipo;
    }
    
    private String generarId() {
        return "USR" + System.currentTimeMillis();
    }
    
    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    
    public boolean isVerificado() { return verificado; }
    public void setVerificado(boolean verificado) { this.verificado = verificado; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public TipoUsuario getTipo() { return tipo; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return Objects.equals(id, usuario.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Usuario{id='%s', nombre='%s', email='%s', tipo=%s, verificado=%s}", 
                           id, nombre, email, tipo, verificado);
    }
}