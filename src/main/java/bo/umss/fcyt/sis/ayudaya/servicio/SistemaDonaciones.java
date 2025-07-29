package bo.umss.fcyt.sis.ayudaya.servicio;

import bo.umss.fcyt.sis.ayudaya.modelo.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Clase principal que gestiona el sistema de donaciones "Ayuda Ya"
 * Implementa todos los requisitos funcionales principales
 */
public class SistemaDonaciones {
    private Map<String, Usuario> usuarios;
    private Map<String, Donacion> donaciones;
    private Map<String, SolicitudDonacion> solicitudes;
    private List<String> categoriasPermitidas;
    
    public SistemaDonaciones() {
        this.usuarios = new HashMap<>();
        this.donaciones = new HashMap<>();
        this.solicitudes = new HashMap<>();
        this.categoriasPermitidas = Arrays.asList(
            "Ropa", "Muebles", "Electrodomésticos", "Alimentos", "Libros", "Juguetes", "Otros"
        );
    }
    
    /**
     * RF-001: Registrar donante
     */
    public String registrarDonante(String nombre, String email, String telefono, 
                                  String contraseña, String direccion) {
        validarDatosBasicos(nombre, email, telefono, contraseña);
        
        if (existeEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado en el sistema");
        }
        
        Donante donante = new Donante(nombre, email, telefono, contraseña, direccion);
        donante.setVerificado(true); // Auto-verificación para simplificar
        usuarios.put(donante.getId(), donante);
        
        System.out.println("✓ Donante registrado exitosamente: " + donante.getId());
        return donante.getId();
    }
    
    /**
     * RF-001: Registrar institución
     */
    public String registrarInstitucion(String nombre, String email, String telefono, String contraseña,
                                     String numeroRegistro, String direccion, 
                                     Institucion.TipoInstitucion tipo, String representante) {
        validarDatosBasicos(nombre, email, telefono, contraseña);
        
        if (existeEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado en el sistema");
        }
        
        Institucion institucion = new Institucion(nombre, email, telefono, contraseña,
                                                 numeroRegistro, direccion, tipo, representante);
        usuarios.put(institucion.getId(), institucion);
        
        System.out.println("✓ Institución registrada exitosamente: " + institucion.getId());
        return institucion.getId();
    }
    
    /**
     * RF-002: Publicar donación
     */
    public String publicarDonacion(String idDonante, String nombre, String descripcion, 
                                  String categoria, String estado, String rutaFoto) {
        Usuario usuario = usuarios.get(idDonante);
        if (usuario == null || !(usuario instanceof Donante)) {
            throw new IllegalArgumentException("Donante no encontrado");
        }
        
        if (!categoriasPermitidas.contains(categoria)) {
            throw new IllegalArgumentException("Categoría no válida. Permitidas: " + categoriasPermitidas);
        }
        
        Donante donante = (Donante) usuario;
        Donacion donacion = donante.publicarDonacion(nombre, descripcion, categoria, estado, rutaFoto);
        donaciones.put(donacion.getId(), donacion);
        
        // Agregar puntos de recompensa
        donante.agregarPuntos(10);
        
        System.out.println("✓ Donación publicada exitosamente: " + donacion.getId());
        return donacion.getId();
    }
    
    /**
     * RF-003: Buscar donaciones disponibles
     */
    public List<Donacion> buscarDonacionesDisponibles() {
        return donaciones.values().stream()
                .filter(Donacion::estaDisponible)
                .collect(Collectors.toList());
    }
    
    /**
     * RF-003: Buscar donaciones por categoría
     */
    public List<Donacion> buscarDonacionesPorCategoria(String categoria) {
        return donaciones.values().stream()
                .filter(d -> d.estaDisponible() && d.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }
    
    /**
     * RF-004: Solicitar donación específica
     */
    public String solicitarDonacion(String idInstitucion, String idDonacion, String motivo) {
        Usuario usuario = usuarios.get(idInstitucion);
        if (usuario == null || !(usuario instanceof Institucion)) {
            throw new IllegalArgumentException("Institución no encontrada");
        }
        
        Institucion institucion = (Institucion) usuario;
        if (!institucion.isDocumentosValidados()) {
            throw new IllegalStateException("La institución debe validar sus documentos primero");
        }
        
        Donacion donacion = donaciones.get(idDonacion);
        if (donacion == null) {
            throw new IllegalArgumentException("Donación no encontrada");
        }
        
        if (!donacion.estaDisponible()) {
            throw new IllegalStateException("La donación no está disponible");
        }
        
        SolicitudDonacion solicitud = institucion.solicitarDonacion(donacion, motivo);
        solicitudes.put(solicitud.getId(), solicitud);
        
        // Marcar donación como solicitada
        donacion.marcarComoSolicitada(idInstitucion);
        
        // Auto-aprobar para simplificar el flujo
        solicitud.aprobar();
        solicitud.marcarEnProceso();
        
        System.out.println("✓ Donación solicitada exitosamente: " + solicitud.getId());
        return solicitud.getId();
    }
    
    /**
     * RF-005: Enviar donación (simular logística)
     */
    public void enviarDonacion(String idDonacion) {
        Donacion donacion = donaciones.get(idDonacion);
        if (donacion == null) {
            throw new IllegalArgumentException("Donación no encontrada");
        }
        
        donacion.marcarEnTransito();
        System.out.println("✓ Donación enviada - En tránsito: " + idDonacion);
    }
    
    /**
     * RF-006: Confirmar entrega
     */
    public void confirmarEntrega(String idDonacion) {
        Donacion donacion = donaciones.get(idDonacion);
        if (donacion == null) {
            throw new IllegalArgumentException("Donación no encontrada");
        }
        
        donacion.confirmarEntrega();
        
        // Completar la solicitud correspondiente
        solicitudes.values().stream()
                .filter(s -> s.getIdDonacion().equals(idDonacion))
                .findFirst()
                .ifPresent(SolicitudDonacion::completar);
        
        System.out.println("✓ Entrega confirmada exitosamente: " + idDonacion);
    }
    
    /**
     * RF-007: Ver historial de donaciones de un donante
     */
    public List<Donacion> verHistorialDonante(String idDonante) {
        Usuario usuario = usuarios.get(idDonante);
        if (usuario == null || !(usuario instanceof Donante)) {
            throw new IllegalArgumentException("Donante no encontrado");
        }
        
        Donante donante = (Donante) usuario;
        return donante.getHistorialDonaciones();
    }
    
    /**
     * RF-007: Ver historial de solicitudes de una institución
     */
    public List<SolicitudDonacion> verHistorialInstitucion(String idInstitucion) {
        Usuario usuario = usuarios.get(idInstitucion);
        if (usuario == null || !(usuario instanceof Institucion)) {
            throw new IllegalArgumentException("Institución no encontrada");
        }
        
        Institucion institucion = (Institucion) usuario;
        return institucion.getHistorialSolicitudes();
    }
    
    /**
     * Método para validar documentos de institución
     */
    public void validarDocumentosInstitucion(String idInstitucion) {
        Usuario usuario = usuarios.get(idInstitucion);
        if (usuario == null || !(usuario instanceof Institucion)) {
            throw new IllegalArgumentException("Institución no encontrada");
        }
        
        Institucion institucion = (Institucion) usuario;
        institucion.validarDocumentos();
        System.out.println("✓ Documentos validados para institución: " + idInstitucion);
    }
    
    /**
     * Autenticación simple
     */
    public Usuario autenticar(String email, String contraseña) {
        return usuarios.values().stream()
                .filter(u -> u.getEmail().equals(email) && u.getContraseña().equals(contraseña))
                .findFirst()
                .orElse(null);
    }
    
    // Métodos auxiliares
    private void validarDatosBasicos(String nombre, String email, String telefono, String contraseña) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es obligatorio");
        }
        if (contraseña == null || contraseña.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }
    }
    
    private boolean existeEmail(String email) {
        return usuarios.values().stream()
                .anyMatch(u -> u.getEmail().equals(email));
    }
    
    // Getters para consultas
    public Map<String, Usuario> getUsuarios() { return new HashMap<>(usuarios); }
    public Map<String, Donacion> getDonaciones() { return new HashMap<>(donaciones); }
    public Map<String, SolicitudDonacion> getSolicitudes() { return new HashMap<>(solicitudes); }
    public List<String> getCategoriasPermitidas() { return new ArrayList<>(categoriasPermitidas); }
    
    /**
     * Método para obtener estadísticas del sistema
     */
    public void mostrarEstadisticas() {
        System.out.println("\n=== ESTADÍSTICAS DEL SISTEMA ===");
        System.out.printf("Total usuarios: %d\n", usuarios.size());
        System.out.printf("Total donantes: %d\n", usuarios.values().stream()
                .mapToInt(u -> u instanceof Donante ? 1 : 0).sum());
        System.out.printf("Total instituciones: %d\n", usuarios.values().stream()
                .mapToInt(u -> u instanceof Institucion ? 1 : 0).sum());
        System.out.printf("Total donaciones: %d\n", donaciones.size());
        System.out.printf("Donaciones disponibles: %d\n", 
                (int) donaciones.values().stream().filter(Donacion::estaDisponible).count());
        System.out.printf("Total solicitudes: %d\n", solicitudes.size());
        System.out.println("================================\n");
    }
}