# Sistema de reservas de vuelos

## Problema

La gestión de reservas de vuelos puede ser un proceso complejo y desafiante tanto para los viajeros como para las compañías aéreas. Los usuarios a menudo se enfrentan a la dificultad de buscar, comparar y reservar vuelos de manera eficiente. Las compañías aéreas, por otro lado, necesitan una solución que simplifique la administración de vuelos y garantice la seguridad en las transacciones.

## Solución

El **Sistema de reservas de vuelos** es una solución integral diseñada para abordar los desafíos asociados con la gestión de reservas de vuelos. Con esta API, los usuarios pueden acceder a una plataforma eficiente y segura que facilita la búsqueda, visualización y reserva de vuelos. La implementación de características como la autenticación segura y la integración con tecnologías modernas garantiza una experiencia de reserva de vuelos sin complicaciones.

## Tecnologías Utilizadas

- **IntelliJ IDEA:** Entorno de desarrollo integrado (IDE) altamente potente y versátil desarrollado por JetBrains. Ofrece herramientas avanzadas para la creación, depuración y gestión de proyectos Java, facilitando el desarrollo de software con una interfaz intuitiva y funciones inteligentes.

- **Java 11:** Lenguaje de programación versátil y robusto que forma la base del desarrollo de la API.

- **Spring Boot 2.7.17:** Framework que simplifica el desarrollo de aplicaciones Java, proporcionando configuraciones predeterminadas y una estructura fácil de usar.

- **Gradle:** Herramienta de automatización de compilación y gestión de dependencias que facilita el desarrollo y la gestión del proyecto.

- **Spring Web y Spring Data JPA:** Módulos de Spring que permiten el desarrollo de aplicaciones web y el acceso a bases de datos relacionales de manera eficiente.

- **Spring Security y JWT:** Ofrecen capas de seguridad para proteger las transacciones y la información sensible, utilizando JSON Web Tokens para la autenticación.

- **JUnit:** Marco de pruebas unitarias para garantizar la calidad del código y la funcionalidad de la API.

- **MySQL:** Sistema de gestión de bases de datos relacional que almacena de manera segura la información de vuelos y usuarios.

- **Git y GitHub:** Herramientas de control de versiones y plataforma de alojamiento que facilitan la colaboración y el seguimiento del desarrollo del proyecto.

- **GitHub Actions:** Se utiliza para la automatización de flujos de trabajo, como pruebas continuas e implementación continua (CI/CD).

- **Swagger:** Herramienta de documentación que simplifica la comprensión y prueba de la API.

- **Postman:** Plataforma para el desarrollo y prueba de APIs, facilitando la validación de endpoints y la interacción con la API.

_¡A volar con tecnología de vanguardia!_ 🚀✈️

![Herramientas](https://skills.thijs.gg/icons?i=idea,java,spring,gradle,hibernate,mysql,git,github,githubactions,postman)

## Diagrama de Clases

El **Diagrama de Clases** representa las clases y las relaciones entre ellas en el sistema. Muestra la estructura estática del sistema, incluyendo las clases, sus atributos, métodos y cómo se relacionan. Este diagrama proporciona una visión general de la arquitectura y la organización de clases en el código fuente.

![Diagrama de Clases](/classDiagram/flightReservationSystem.png)

## Diagrama Relacional

El **Diagrama Relacional** ilustra la estructura de las tablas y las relaciones entre ellas en la base de datos. Muestra cómo las entidades están conectadas y cómo se relacionan a través de claves primarias y foráneas. Este diagrama es esencial para comprender la arquitectura de la base de datos y cómo se almacena la información de manera relacionada.

![Diagrama Relacional](/database/diagram/flightSystem.png)

## Uso de la Aplicación

### Obtención del Token de Seguridad

#### Registro de Nuevo Usuario

Para nuevos usuarios, el registro se realiza a través del siguiente endpoint:

**Endpoint:** `POST /api/registro`

**Descripción:** Registra un nuevo usuario en el sistema asignándole el rol de USER.

**Parámetros de Solicitud:**

```json
{
  "firstName": "Nombres del usuario."
  "lastName": "Apellidos del usuario."
  "username": "Nombre de usuario único."
  "password": "Contraseña del usuario."
}
```

**Respuesta Exitosa:**

- **Código:** 201 Created
- **Cuerpo de la Respuesta:**

  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzNTc4OTYyMCwiZXhwIjoxNjM1NzkzNDIwfQ.Lm4dUWt29vcuKQkwGfHIs06xXvWCXCMJipG3c2N6aKM"
  }
  ```

### Inicio de Sesión

Para usuarios ya registrados, el inicio de sesión se realiza a través del siguiente endpoint:

**Endpoint:** `POST /api/login`

**Descripción:** Inicia sesión y obtiene un token de seguridad.

**Parámetros de Solicitud:**

```json
 {
   "username": "Nombre de usuario único."
   "password": "Contraseña del usuario."
 }
```

**Respuesta Exitosa:**

- **Código:** 200 OK
- **Cuerpo de la Respuesta:**
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzNTc4OTYyMCwiZXhwIjoxNjM1NzkzNDIwfQ.Lm4dUWt29vcuKQkwGfHIs06xXvWCXCMJipG3c2N6aKM"
  }
  ```

**Notas:**

- El token tiene una duración de 1 hora.

### Acceso a los Endpoints

Una vez que se obtiene el token de seguridad, se debe incluir en la cabecera de las solicitudes a los endpoints que requieren autenticación. El token se incluye de la siguiente manera:

**Cabecera de Solicitud:**

- `Authorization: Bearer <token>`

Con el token adecuado, el usuario tendrá acceso a los endpoints según su rol y podrá utilizar las funcionalidades proporcionadas por la aplicación.
