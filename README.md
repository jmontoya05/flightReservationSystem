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

![Herramientas](https://skills.thijs.gg/icons?i=idea,java,spring,gradle,hibernate,mysql,git,github,githubactions,postman)

## Diagrama de Clases

El **Diagrama de Clases** representa las clases y las relaciones entre ellas en el sistema. Muestra la estructura estática del sistema, incluyendo las clases, sus atributos, métodos y cómo se relacionan. Este diagrama proporciona una visión general de la arquitectura y la organización de clases en el código fuente.

![Diagrama de Clases](/src/main/resources/static/images/flightReservationSystem.png)

## Diagrama Relacional

El **Diagrama Relacional** ilustra la estructura de las tablas y las relaciones entre ellas en la base de datos. Muestra cómo las entidades están conectadas y cómo se relacionan a través de claves primarias y foráneas. Este diagrama es esencial para comprender la arquitectura de la base de datos y cómo se almacena la información de manera relacionada.

![Diagrama Relacional](/src/main/resources/static/images/flightSystem.png)

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

## Endpoints de la Aplicación

### Auth Controller

El Auth Controller gestiona las operaciones de autenticación y autorización. En particular, las funciones proporcionadas permiten a los usuarios registrarse en el sistema y realizar el inicio de sesión.

| Endpoint        | Tipo de Petición | Función                                  | Roles Permitidos       |
| --------------- | ---------------- | ---------------------------------------- | ---------------------- |
| `/api/login`    | POST             | Permite loguear un usuario al sistema.   | Todos (sin autenticar) |
| `/api/register` | POST             | Permite registrar un usuario al sistema. | Todos (sin autenticar) |

#### Acceso a los Endpoints

Una vez que se obtiene el token de seguridad, se debe incluir en la cabecera de las solicitudes a los endpoints que requieren autenticación. El token se incluye de la siguiente manera:

**Cabecera de Solicitud:**

- `Authorization: Bearer <token>`

Con el token adecuado, el usuario tendrá acceso a los endpoints según su rol y podrá utilizar las funcionalidades proporcionadas por la aplicación.

### Flight Controller

El Flight Controller en tu aplicación proporciona una interfaz para la gestión de vuelos, permitiendo a las aerolíneas y usuarios acceder y manipular la información relacionada con los vuelos. Los endpoints ofrecen funcionalidades clave para la creación, visualización, actualización y eliminación de vuelos. A través de estos endpoints, los operadores de aerolínea pueden agregar nuevos vuelos, actualizar información existente, y los usuarios registrados pueden acceder a detalles específicos de vuelos, así como buscar y explorar la lista de vuelos disponibles

| Endpoint                    | Tipo de Petición | Función                                                 | Roles Permitidos     |
| --------------------------- | ---------------- | ------------------------------------------------------- | -------------------- |
| `/api/flights`              | POST             | Crea un nuevo vuelo.                                    | AIRLINE, ADMIN       |
| `/api/flights/{flightCode}` | GET              | Obtiene los detalles de un vuelo específico.            | AIRLINE, USER, ADMIN |
| `/api/flights`              | GET              | Obtiene la lista de vuelos según criterios específicos. | AIRLINE, USER, ADMIN |
| `/api/flights/{flightCode}` | PUT              | Actualiza la información de un vuelo existente.         | AIRLINE, ADMIN       |
| `/api/flights/{flightCode}` | DELETE           | Elimina un vuelo específico.                            | ADMIN                |

### Reservation Controller

Estos endpoints permiten la gestión de reservas, incluyendo la creación, obtención de detalles, obtención de listas, obtención de reservas asociadas a un pasajero y eliminación de reservas. Los roles determinan quién tiene acceso a cada funcionalidad, asegurando un control adecuado del acceso en el sistema

| Endpoint                                    | Tipo de Petición | Función                                         | Roles Permitidos     |
| ------------------------------------------- | ---------------- | ----------------------------------------------- | -------------------- |
| `/api/reservations`                         | POST             | Crea una nueva reserva.                         | AIRLINE, USER, ADMIN |
| `/api/reservations/{reservationCode}`       | GET              | Obtiene los detalles de una reserva específica. | AIRLINE, USER, ADMIN |
| `/api/reservations`                         | GET              | Obtiene la lista de todas las reservas.         | AIRLINE, ADMIN       |
| `/api/reservations/passenger/{passengerId}` | GET              | Obtiene las reservas asociadas a un pasajero.   | AIRLINE, USER, ADMIN |
| `/api/reservations/{reservationCode}`       | DELETE           | Elimina una reserva específica.                 | ADMIN                |

### Passenger Controller

El Passenger Controller gestiona las operaciones relacionadas con la entidad de pasajero. Proporciona endpoints para la creación, obtención, actualización y eliminación de información de pasajeros. Los usuarios con roles AIRLINE y ADMIN tienen acceso a estas funcionalidades, permitiéndoles administrar y mantener los registros de pasajeros en el sistema. Estos endpoints permiten a las aerolíneas (AIRLINE) gestionar la información de los pasajeros asociados a sus vuelos, y a los administradores (ADMIN) realizar operaciones de mantenimiento en la base de datos de pasajeros

| Endpoint                        | Tipo de Petición | Función                                            | Roles Permitidos |
| ------------------------------- | ---------------- | -------------------------------------------------- | ---------------- |
| `/api/passengers`               | POST             | Crea un nuevo pasajero.                            | AIRLINE, ADMIN   |
| `/api/passengers/{passengerId}` | GET              | Obtiene los detalles de un pasajero específico.    | AIRLINE, ADMIN   |
| `/api/passengers`               | GET              | Obtiene la lista de todos los pasajeros.           | AIRLINE, ADMIN   |
| `/api/passengers/{passengerId}` | PUT              | Actualiza la información de un pasajero existente. | AIRLINE, ADMIN   |
| `/api/passengers/{passengerId}` | DELETE           | Elimina un pasajero específico.                    | ADMIN            |

### FlightType Controller

El FlightType Controller facilita la gestión de los diferentes tipos de vuelos disponibles. Proporciona endpoints para la creación, obtención, actualización y eliminación de información relacionada con los tipos de vuelo. Los usuarios con roles AIRLINE y ADMIN tienen acceso a estas funcionalidades, permitiéndoles administrar y mantener la diversidad de tipos de vuelo en el sistema. Estos endpoints son cruciales para la personalización y adaptabilidad de los tipos de vuelo, ya que permiten a las aerolíneas (AIRLINE) y administradores (ADMIN) realizar operaciones que afectan directamente a la clasificación y características de los vuelos disponibles

| Endpoint                           | Tipo de Petición | Función                                                 | Roles Permitidos |
| ---------------------------------- | ---------------- | ------------------------------------------------------- | ---------------- |
| `/api/flight-types`                | POST             | Crea un nuevo tipo de vuelo.                            | AIRLINE, ADMIN   |
| `/api/flight-types/{flightTypeId}` | GET              | Obtiene los detalles de un tipo de vuelo específico.    | AIRLINE, ADMIN   |
| `/api/flight-types`                | GET              | Obtiene la lista de todos los tipos de vuelo.           | AIRLINE, ADMIN   |
| `/api/flight-types/{flightTypeId}` | PUT              | Actualiza la información de un tipo de vuelo existente. | AIRLINE, ADMIN   |
| `/api/flight-types/{flightTypeId}` | DELETE           | Elimina un tipo de vuelo específico.                    | ADMIN            |

### Airline Controller

El Airline Controller maneja las operaciones relacionadas con las aerolíneas. Proporciona endpoints para la creación, obtención, actualización y eliminación de información de las aerolíneas. Los usuarios con roles AIRLINE y ADMIN tienen acceso a estas funcionalidades, permitiéndoles administrar y mantener registros de las aerolíneas en el sistema. Estos endpoints son fundamentales para gestionar la información clave de las aerolíneas, como sus detalles y configuraciones

| Endpoint                    | Tipo de Petición | Función                                              | Roles Permitidos |
| --------------------------- | ---------------- | ---------------------------------------------------- | ---------------- |
| `/api/airlines`             | POST             | Crea una nueva aerolínea.                            | AIRLINE, ADMIN   |
| `/api/airlines/{airlineId}` | GET              | Obtiene los detalles de una aerolínea específica.    | AIRLINE, ADMIN   |
| `/api/airlines`             | GET              | Obtiene la lista de todas las aerolíneas.            | AIRLINE, ADMIN   |
| `/api/airlines/{airlineId}` | PUT              | Actualiza la información de una aerolínea existente. | AIRLINE, ADMIN   |
| `/api/airlines/{airlineId}` | DELETE           | Elimina una aerolínea específica.                    | ADMIN            |

### Airport Controller

El Airport Controller maneja las operaciones relacionadas con los aeropuertos. Proporciona endpoints para la creación, obtención, actualización y eliminación de información de los aeropuertos. Los usuarios con roles AIRLINE y ADMIN tienen acceso a estas funcionalidades, permitiéndoles administrar y mantener registros de los aeropuertos en el sistema. Estos endpoints son fundamentales para gestionar la información clave de los aeropuertos, como sus ubicaciones y detalles operativos

| Endpoint                    | Tipo de Petición | Función                                              | Roles Permitidos |
| --------------------------- | ---------------- | ---------------------------------------------------- | ---------------- |
| `/api/airports`             | POST             | Crea un nuevo aeropuerto.                            | AIRLINE, ADMIN   |
| `/api/airports/{airportId}` | GET              | Obtiene los detalles de un aeropuerto específico.    | AIRLINE, ADMIN   |
| `/api/airports`             | GET              | Obtiene la lista de todos los aeropuertos.           | AIRLINE, ADMIN   |
| `/api/airports/{airportId}` | PUT              | Actualiza la información de un aeropuerto existente. | AIRLINE, ADMIN   |
| `/api/airports/{airportId}` | DELETE           | Elimina un aeropuerto específico.                    | ADMIN            |

### City Controller

El City Controller maneja las operaciones relacionadas con las ciudades. Proporciona endpoints para la creación, obtención, actualización y eliminación de información de ciudades. Los usuarios con roles AIRLINE y ADMIN tienen acceso a estas funcionalidades, permitiéndoles administrar y mantener registros de las ciudades en el sistema. Estos endpoints son fundamentales para gestionar información clave sobre las ubicaciones geográficas de interés, como sus nombres y detalles asociados

| Endpoint               | Tipo de Petición | Función                                           | Roles Permitidos |
| ---------------------- | ---------------- | ------------------------------------------------- | ---------------- |
| `/api/cities`          | POST             | Crea una nueva ciudad.                            | AIRLINE, ADMIN   |
| `/api/cities/{cityId}` | GET              | Obtiene los detalles de una ciudad específica.    | AIRLINE, ADMIN   |
| `/api/cities`          | GET              | Obtiene la lista de todas las ciudades.           | AIRLINE, ADMIN   |
| `/api/cities/{cityId}` | PUT              | Actualiza la información de una ciudad existente. | AIRLINE, ADMIN   |
| `/api/cities/{cityId}` | DELETE           | Elimina una ciudad específica.                    | ADMIN            |

### Country Controller

El Country Controller maneja las operaciones relacionadas con los países. Proporciona endpoints para la creación, obtención, actualización y eliminación de información de países. Los usuarios con roles AIRLINE y ADMIN tienen acceso a estas funcionalidades, permitiéndoles administrar y mantener registros de los países en el sistema. Estos endpoints son fundamentales para gestionar información clave sobre los países, como sus nombres y detalles asociados

| Endpoint                     | Tipo de Petición | Función                                        | Roles Permitidos |
| ---------------------------- | ---------------- | ---------------------------------------------- | ---------------- |
| `/api/countries`             | POST             | Crea un nuevo país.                            | AIRLINE, ADMIN   |
| `/api/countries/{countryId}` | GET              | Obtiene los detalles de un país específico.    | AIRLINE, ADMIN   |
| `/api/countries`             | GET              | Obtiene la lista de todos los países.          | AIRLINE, ADMIN   |
| `/api/countries/{countryId}` | PUT              | Actualiza la información de un país existente. | AIRLINE, ADMIN   |
| `/api/countries/{countryId}` | DELETE           | Elimina un país específico.                    | ADMIN            |

### User Controller

El UserController gestiona operaciones específicas relacionadas con los usuarios y roles. Proporciona endpoints para obtener la lista de todos los usuarios y asignar roles a usuarios específicos. Estos endpoints están diseñados para ser accedidos únicamente por usuarios con el rol ADMIN, lo que garantiza que solo los administradores tengan la capacidad de realizar estas acciones. El endpoint /api/users permite obtener una lista completa de usuarios, y el endpoint /api/users/{userId}/{roleId} permite asignar un rol específico a un usuario mediante la asignación de IDs de usuario y rol. La limitación de acceso a roles administrativos es crucial para mantener la integridad y la seguridad del sistema, asegurando que solo los usuarios autorizados realicen operaciones que afectan a otros usuarios y sus roles

| Endpoint                       | Tipo de Petición | Función                                 | Roles Permitidos |
| ------------------------------ | ---------------- | --------------------------------------- | ---------------- |
| `/api/users`                   | GET              | Obtiene la lista de todos los usuarios. | ADMIN            |
| `/api/users/{userId}/{roleId}` | PUT              | Asigna un rol a un usuario específico.  | ADMIN            |

### Role Controller

El Role Controller gestiona las operaciones relacionadas con los roles de usuario. Proporciona endpoints para la creación de nuevos roles y la obtención de la lista de todos los roles disponibles. Estos endpoints están diseñados para ser accedidos exclusivamente por usuarios con el rol ADMIN, garantizando que solo los administradores tengan la capacidad de crear nuevos roles y visualizar la lista completa de roles en el sistema. La capacidad de definir y gestionar roles es esencial para establecer y mantener un sistema de control de acceso efectivo en tu aplicación. Limitar el acceso a estos endpoints a roles administrativos asegura la integridad y seguridad del sistema al prevenir cambios no autorizados en la estructura de roles.

| Endpoint     | Tipo de Petición | Función                              | Roles Permitidos |
| ------------ | ---------------- | ------------------------------------ | ---------------- |
| `/api/roles` | POST             | Crea un nuevo rol.                   | ADMIN            |
| `/api/roles` | GET              | Obtiene la lista de todos los roles. | ADMIN            |

## Pruebas Unitarias

Para garantizar la robustez y la confiabilidad de la aplicación, se han implementado pruebas unitarias utilizando el framework JUnit. Las pruebas unitarias se centran en evaluar cada componente individual de la aplicación de manera aislada, verificando su comportamiento y asegurando que cumpla con los requisitos establecidos.

### Ejecución de Pruebas Unitarias

1. **Entorno de Desarrollo:**

   - Asegúrate de tener configurado y activo un entorno de desarrollo con las dependencias adecuadas.
   - Las pruebas unitarias se ejecutan en un entorno controlado para simular diferentes escenarios y asegurar la coherencia del código.

2. **Herramienta de Construcción:**

   - Utiliza la herramienta de construcción (por ejemplo, Gradle) para compilar y ejecutar las pruebas unitarias.
     ```bash
     gradle test
     ```

3. **Resultados de las Pruebas:**
   - Revisa la salida de las pruebas para identificar posibles fallos o errores.
   - Asegúrate de que todas las pruebas unitarias se ejecuten con éxito.

## Pruebas de Integración

Además de las pruebas unitarias, la aplicación también se somete a pruebas de integración. Estas pruebas evalúan la interoperabilidad y el comportamiento conjunto de varios componentes del sistema. Las pruebas de integración son esenciales para garantizar que los diferentes módulos de la aplicación funcionen correctamente cuando se combinan.

### Ejecución de Pruebas de Integración

1. **Configuración del Entorno:**

   - Asegúrate de tener un entorno de prueba con las configuraciones necesarias, como una base de datos de prueba.
   - Configura las propiedades de la aplicación para apuntar al entorno de prueba.

2. **Herramienta de Construcción:**

   - Utiliza la herramienta de construcción para ejecutar las pruebas de integración.
     ```bash
     gradle integrationTest
     ```

3. **Resultados de las Pruebas:**
   - Analiza la salida y los informes de las pruebas de integración para verificar que todos los componentes interactúan correctamente.

## Cobertura de Código

Se ha realizado un análisis de cobertura de código para evaluar la extensión en la que las pruebas abarcan el código fuente. La cobertura de código proporciona información sobre las porciones de código que han sido ejecutadas durante las pruebas.

### Análisis de Cobertura de Código

1. **Herramienta de Cobertura:**

   - Utiliza una herramienta de cobertura de código (por ejemplo, JaCoCo) para analizar la cobertura.
     ```bash
     gradle jacocoTestReport
     ```

2. **Informe de Cobertura:**
   - Revisa el informe de cobertura generado para identificar áreas del código que pueden necesitar pruebas adicionales.

_¡A volar con tecnología de vanguardia!_ 🚀✈️
