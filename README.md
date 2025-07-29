📘 Sistema AyudaYa - Guía de Ejecución
📝 Descripción
AyudaYa es una aplicación de escritorio desarrollada en Java, que permite gestionar donaciones entre personas y instituciones benéficas.
 Su enfoque está en facilitar el registro de donantes, instituciones y donaciones, así como el seguimiento de su estado.

Este sistema fue desarrollado como parte de un trabajo académico para la carrera de Ingeniería de Sistemas.


📂 Estructura del Proyecto
El proyecto está organizado de forma modular, siguiendo principios de Programación Orientada a Objetos:

```
AyudaYa/
├── src/
│   └── ayudaya/
│       ├── modelos/
│       │   ├── Articulo.java
│       │   ├── CategoriaArticulo.java
│       │   ├── Donacion.java
│       │   ├── Donante.java
│       │   ├── EstadoArticulo.java
│       │   ├── EstadoDonacion.java
│       │   ├── Institucion.java
│       │   └── Usuario.java
│       ├── servicios/
│       │   └── SistemaAyudaYa.java
│       └── Principal.java
├── README.md
└── AyudaYa.zip

```


🧰 Requisitos
Para poder ejecutar el proyecto correctamente necesitás tener instalado:

 -Java JDK 8 o superior
 -NetBeans IDE 12 o superior (recomendado)
 -Sistema operativo: Windows, Linux o macOS



🚀 Cómo ejecutar el sistema en NetBeans
Paso 1: Importar el proyecto
1. Abrí NetBeans.
2. Andá a Archivo > Abrir Proyecto.
3. Seleccioná la carpeta del proyecto descomprimido  (AyudaYa)
4. NetBeans lo reconocerá automáticamente como proyecto Java. Hacé clic en `Abrir Proyecto.`

 Paso 2: Ejecutar el programa
1. En el panel izquierdo, buscá el archivo Principal.java.
2. Hacé clic derecho sobre él y seleccioná "Run File".
3. O simplemente presioná Shift + F6 para ejecutar directamente.



🎯 Funcionalidades implementadas
Casos de uso principales
- ✅ Registro de Donantes
- ✅ Registro de Instituciones
- ✅ Creación de Donaciones
- ✅ Gestión de estados de donaciones y artículos
- ✅ Sistema de puntos para premiar a los donantes
- ✅ Estadísticas y listado de usuarios/donaciones


Menú principal de consola
- Registrar Donante
- Registrar Institución
- Crear Donación
- Ver Donaciones
- Ver Estadísticas
- Ver Usuarios
- Salir



🔍 Datos de prueba incluidos
El sistema ya incluye algunos datos precargados para probar su funcionamiento:
- Donante de prueba: maria@email.com
- Institución de prueba: orfanato@email.com
- Tres artículos predefinidos disponibles para donación

🛠 Tecnologías y conceptos aplicados
- Lenguaje: Java 8+
- Entrada de datos: Scanner
- Fechas: LocalDateTime
- Colecciones: ArrayList
- Organización en paquetes y clases
- Principios POO:
   - Herencia: Usuario como clase base para Donante e Institucion
   - Polimorfismo: método mostrarInformacion() sobrescrito
   - Encapsulamiento: uso de atributos privados y getters/setters
   - Composición: Donacion contiene Donante, Institucion y Articulo
   - Enums: para definir estados y categorías


🚧 Posibles mejoras futuras
- Interfaz gráfica con JavaFX o Swing
- Almacenamiento con Base de Datos (JPA/Hibernate)
- Sistema de login y autenticación de usuarios
- Validaciones más estrictas de entrada de datos
- Conexión a servicios externos (por ejemplo, APIs de ubicación/mapas)
- Notificaciones o recordatorios automáticos

 👩‍💻 Autores
Trabajo realizado por el grupo de Ingeniería de Sistemas  SISTEMAS DE INFORMACION - UMSS:

- Calizaya Quispia Lisandra
- Caricampo Cucuta Jashel Jhoana
- Chalco Soliz Marcela
- Cordova Catunta Marizabel
- Flores Gutierrez Alison Danitza
- Suárez Ala Catherine Ainhoa 
