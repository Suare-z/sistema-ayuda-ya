package bo.umss.fcyt.sis.ayudaya;

import bo.umss.fcyt.sis.ayudaya.modelo.*;
import bo.umss.fcyt.sis.ayudaya.servicio.SistemaDonaciones;
import java.util.List;
import java.util.Scanner;

/**
 * Aplicación principal del sistema "Ayuda Ya"
 * Implementa interfaz de consola para demostrar funcionalidades
 * 
 * Proyecto: Sistemas de Información 1
 * Grupo: Tech Girls
 * Universidad: UMSS - Facultad de Ciencias y Tecnología
 */
public class AyudaYaApp {
    private static SistemaDonaciones sistema;
    private static Scanner scanner;
    private static Usuario usuarioActual;
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                    AYUDA YA                             ║");
        System.out.println("║          Plataforma Virtual de Donaciones               ║");
        System.out.println("║                                                          ║");
        System.out.println("║  Universidad Mayor de San Simón                         ║");
        System.out.println("║  Facultad de Ciencias y Tecnología                      ║");
        System.out.println("║  Carrera: Ingeniería de Sistemas                        ║");
        System.out.println("║  Materia: Sistemas de Información 1                     ║");
        System.out.println("║  Grupo: Tech Girls                                       ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.println();
        
        sistema = new SistemaDonaciones();
        scanner = new Scanner(System.in);
        
        // Datos de prueba
        cargarDatosPrueba();
        
        mostrarMenuPrincipal();
    }
    
    private static void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Registrarse como Donante");
            System.out.println("2. Registrarse como Institución");
            System.out.println("3. Iniciar Sesión");
            System.out.println("4. Ver Donaciones Disponibles");
            System.out.println("5. Buscar por Categoría");
            System.out.println("6. Ver Estadísticas");
            System.out.println("7. Datos de Prueba");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerEntero();
            
            switch (opcion) {
                case 1: registrarDonante(); break;
                case 2: registrarInstitucion(); break;
                case 3: iniciarSesion(); break;
                case 4: verDonacionesDisponibles(); break;
                case 5: buscarPorCategoria(); break;
                case 6: sistema.mostrarEstadisticas(); break;
                case 7: mostrarDatosPrueba(); break;
                case 0: 
                    System.out.println("¡Gracias por usar Ayuda Ya!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("❌ Opción inválida");
            }
        }
    }
    
    private static void registrarDonante() {
        System.out.println("\n=== REGISTRO DE DONANTE ===");
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = scanner.nextLine();
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        
        try {
            String id = sistema.registrarDonante(nombre, email, telefono, contraseña, direccion);
            System.out.println("✓ Registro exitoso. ID asignado: " + id);
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    private static void registrarInstitucion() {
        System.out.println("\n=== REGISTRO DE INSTITUCIÓN ===");
        System.out.print("Nombre de la institución: ");
        String nombre = scanner.nextLine();
        System.out.print("Email institucional: ");
        String email = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = scanner.nextLine();
        System.out.print("Número de registro legal: ");
        String registro = scanner.nextLine();
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Representante legal: ");
        String representante = scanner.nextLine();
        
        System.out.println("\nTipos de institución:");
        Institucion.TipoInstitucion[] tipos = Institucion.TipoInstitucion.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.printf("%d. %s\n", i + 1, tipos[i]);
        }
        System.out.print("Seleccione tipo (1-" + tipos.length + "): ");
        int tipoIndex = leerEntero() - 1;
        
        if (tipoIndex < 0 || tipoIndex >= tipos.length) {
            System.out.println("❌ Tipo inválido");
            return;
        }
        
        try {
            String id = sistema.registrarInstitucion(nombre, email, telefono, contraseña,
                                                   registro, direccion, tipos[tipoIndex], representante);
            System.out.println("✓ Registro exitoso. ID asignado: " + id);
            System.out.println("⚠️  Nota: Los documentos deben ser validados antes de solicitar donaciones");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    private static void iniciarSesion() {
        System.out.println("\n=== INICIAR SESIÓN ===");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = scanner.nextLine();
        
        Usuario usuario = sistema.autenticar(email, contraseña);
        if (usuario != null) {
            usuarioActual = usuario;
            System.out.println("✓ Bienvenido, " + usuario.getNombre());
            
            if (usuario instanceof Donante) {
                menuDonante();
            } else if (usuario instanceof Institucion) {
                menuInstitucion();
            }
        } else {
            System.out.println("❌ Credenciales incorrectas");
        }
    }
    
    private static void menuDonante() {
        while (usuarioActual != null) {
            System.out.println("\n=== MENÚ DONANTE ===");
            System.out.println("1. Publicar Donación");
            System.out.println("2. Ver mis Donaciones");
            System.out.println("3. Ver mis Puntos");
            System.out.println("4. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerEntero();
            
            switch (opcion) {
                case 1: publicarDonacion(); break;
                case 2: verMisDonaciones(); break;
                case 3: verMisPuntos(); break;
                case 4: 
                    usuarioActual = null;
                    System.out.println("✓ Sesión cerrada");
                    return;
                default:
                    System.out.println("❌ Opción inválida");
            }
        }
    }
    
    private static void menuInstitucion() {
        while (usuarioActual != null) {
            System.out.println("\n=== MENÚ INSTITUCIÓN ===");
            System.out.println("1. Buscar Donaciones");
            System.out.println("2. Solicitar Donación");
            System.out.println("3. Ver mis Solicitudes");
            System.out.println("4. Validar Documentos");
            System.out.println("5. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerEntero();
            
            switch (opcion) {
                case 1: verDonacionesDisponibles(); break;
                case 2: solicitarDonacion(); break;
                case 3: verMisSolicitudes(); break;
                case 4: validarDocumentos(); break;
                case 5: 
                    usuarioActual = null;
                    System.out.println("✓ Sesión cerrada");
                    return;
                default:
                    System.out.println("❌ Opción inválida");
            }
        }
    }
    
    private static void publicarDonacion() {
        System.out.println("\n=== PUBLICAR DONACIÓN ===");
        System.out.print("Nombre del artículo: ");
        String nombre = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Estado del artículo: ");
        String estado = scanner.nextLine();
        System.out.print("Ruta de fotografía: ");
        String foto = scanner.nextLine();
        
        System.out.println("\nCategorías disponibles:");
        List<String> categorias = sistema.getCategoriasPermitidas();
        for (int i = 0; i < categorias.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, categorias.get(i));
        }
        System.out.print("Seleccione categoría (1-" + categorias.size() + "): ");
        int catIndex = leerEntero() - 1;
        
        if (catIndex < 0 || catIndex >= categorias.size()) {
            System.out.println("❌ Categoría inválida");
            return;
        }
        
        try {
            String id = sistema.publicarDonacion(usuarioActual.getId(), nombre, descripcion,
                                               categorias.get(catIndex), estado, foto);
            System.out.println("✓ Donación publicada exitosamente: " + id);
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    private static void verMisDonaciones() {
        System.out.println("\n=== MIS DONACIONES ===");
        List<Donacion> donaciones = sistema.verHistorialDonante(usuarioActual.getId());
        
        if (donaciones.isEmpty()) {
            System.out.println("No tienes donaciones registradas");
        } else {
            for (Donacion d : donaciones) {
                System.out.printf("ID: %s | %s | %s | Estado: %s\n", 
                               d.getId(), d.getNombre(), d.getCategoria(), d.getEstadoDonacion());
            }
        }
    }
    
    private static void verMisPuntos() {
        Donante donante = (Donante) usuarioActual;
        System.out.println("\n=== MIS PUNTOS DE RECOMPENSA ===");
        System.out.printf("Puntos acumulados: %d\n", donante.getPuntosRecompensa());
        System.out.printf("Donaciones realizadas: %d\n", donante.getDonacionesRealizadas().size());
    }
    
    private static void verDonacionesDisponibles() {
        System.out.println("\n=== DONACIONES DISPONIBLES ===");
        List<Donacion> donaciones = sistema.buscarDonacionesDisponibles();
        
        if (donaciones.isEmpty()) {
            System.out.println("No hay donaciones disponibles en este momento");
        } else {
            for (Donacion d : donaciones) {
                System.out.printf("ID: %s | %s | %s | %s\n", 
                               d.getId(), d.getNombre(), d.getCategoria(), d.getDescripcion());
            }
        }
    }
    
    private static void buscarPorCategoria() {
        System.out.println("\n=== BUSCAR POR CATEGORÍA ===");
        List<String> categorias = sistema.getCategoriasPermitidas();
        for (int i = 0; i < categorias.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, categorias.get(i));
        }
        System.out.print("Seleccione categoría (1-" + categorias.size() + "): ");
        int catIndex = leerEntero() - 1;
        
        if (catIndex < 0 || catIndex >= categorias.size()) {
            System.out.println("❌ Categoría inválida");
            return;
        }
        
        List<Donacion> donaciones = sistema.buscarDonacionesPorCategoria(categorias.get(catIndex));
        
        if (donaciones.isEmpty()) {
            System.out.printf("No hay donaciones disponibles en la categoría: %s\n", categorias.get(catIndex));
        } else {
            System.out.printf("\n=== DONACIONES EN %s ===\n", categorias.get(catIndex).toUpperCase());
            for (Donacion d : donaciones) {
                System.out.printf("ID: %s | %s | %s\n", d.getId(), d.getNombre(), d.getDescripcion());
            }
        }
    }
    
    private static void solicitarDonacion() {
        System.out.println("\n=== SOLICITAR DONACIÓN ===");
        
        Institucion inst = (Institucion) usuarioActual;
        if (!inst.isDocumentosValidados()) {
            System.out.println("❌ Debe validar sus documentos antes de solicitar donaciones");
            return;
        }
        
        verDonacionesDisponibles();
        System.out.print("\nIngrese ID de la donación que desea solicitar: ");
        String idDonacion = scanner.nextLine();
        System.out.print("Motivo de la solicitud: ");
        String motivo = scanner.nextLine();
        
        try {
            String id = sistema.solicitarDonacion(usuarioActual.getId(), idDonacion, motivo);
            System.out.println("✓ Solicitud enviada exitosamente: " + id);
            
            // Simular proceso logístico
            sistema.enviarDonacion(idDonacion);
            Thread.sleep(1000); // Simular tiempo de transporte
            sistema.confirmarEntrega(idDonacion);
            System.out.println("✓ Proceso completado - Donación entregada");
            
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    private static void verMisSolicitudes() {
        System.out.println("\n=== MIS SOLICITUDES ===");
        List<SolicitudDonacion> solicitudes = sistema.verHistorialInstitucion(usuarioActual.getId());
        
        if (solicitudes.isEmpty()) {
            System.out.println("No tienes solicitudes registradas");
        } else {
            for (SolicitudDonacion s : solicitudes) {
                System.out.printf("ID: %s | Donación: %s | Estado: %s | Fecha: %s\n", 
                               s.getId(), s.getIdDonacion(), s.getEstado(), s.getFechaSolicitud());
            }
        }
    }
    
    private static void validarDocumentos() {
        System.out.println("\n=== VALIDAR DOCUMENTOS ===");
        try {
            sistema.validarDocumentosInstitucion(usuarioActual.getId());
            System.out.println("✓ Documentos validados exitosamente");
            System.out.println("Ahora puede solicitar donaciones");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    private static void cargarDatosPrueba() {
        // Registrar donantes de prueba
        String donante1 = sistema.registrarDonante("María García", "maria@email.com", "77123456", "123456", "Av. América 123");
        String donante2 = sistema.registrarDonante("Carlos López", "carlos@email.com", "77234567", "123456", "Calle Jordán 456");
        
        // Registrar instituciones de prueba
        String inst1 = sistema.registrarInstitucion("Hogar San José", "hogar@email.com", "44321987", "123456",
                                                   "REG001", "Zona Norte", Institucion.TipoInstitucion.ORFANATO, "Hermana María");
        String inst2 = sistema.registrarInstitucion("Hospital del Niño", "hospital@email.com", "44456789", "123456",
                                                   "REG002", "Zona Sud", Institucion.TipoInstitucion.HOSPITAL, "Dr. Pérez");
        
        // Validar documentos de instituciones
        sistema.validarDocumentosInstitucion(inst1);
        sistema.validarDocumentosInstitucion(inst2);
        
        // Publicar donaciones de prueba
        sistema.publicarDonacion(donante1, "Ropa de bebé", "Conjunto de ropa para bebés, excelente estado", "Ropa", "Como nuevo", "/fotos/ropa1.jpg");
        sistema.publicarDonacion(donante1, "Libros infantiles", "Colección de 20 libros para niños", "Libros", "Muy bueno", "/fotos/libros1.jpg");
        sistema.publicarDonacion(donante2, "Juguetes educativos", "Set de juguetes didácticos", "Juguetes", "Bueno", "/fotos/juguetes1.jpg");
        sistema.publicarDonacion(donante2, "Microondas", "Microondas Samsung funcionando perfectamente", "Electrodomésticos", "Muy bueno", "/fotos/micro1.jpg");
    }
    
    private static void mostrarDatosPrueba() {
        System.out.println("\n=== DATOS DE PRUEBA CARGADOS ===");
        System.out.println("DONANTES:");
        System.out.println("- maria@email.com / 123456");
        System.out.println("- carlos@email.com / 123456");
        System.out.println("\nINSTITUCIONES:");
        System.out.println("- hogar@email.com / 123456 (Documentos validados)");
        System.out.println("- hospital@email.com / 123456 (Documentos validados)");
        System.out.println("\nDONACIONES DISPONIBLES:");
        verDonacionesDisponibles();
    }
    
    private static int leerEntero() {
        try {
            String input = scanner.nextLine();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}