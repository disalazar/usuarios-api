# API de Registro de Usuarios

Este proyecto es un servicio REST desarrollado en **Java 17** usando **Spring Boot** y **Spring Data JPA**, que permite registrar usuarios junto con sus teléfonos, generando automáticamente un **token JWT** para autenticación.

---

## Tecnologías utilizadas
- **Java 17**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **Hibernate Validator** (Validaciones con anotaciones)
- **Base de Datos: H2**
- **Springdoc OpenAPI** (Swagger UI)
- **JWT (JSON Web Token)** para autenticación del Usuario

---

## Instalación y ejecución

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/disalazar/usuarios-api.git

2. **Configurar la base de datos**

   Si se desea, editar los valores de conexión en src/main/resources/application.properties:

   ```properties
   # Config DB:
   spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
   spring.datasource.driverClassName=org.h2.Driver
   spring.datasource.username=sa
   spring.datasource.password=

   spring.jpa.show-sql=true

   # Format SQL (for read):
   spring.jpa.properties.hibernate.format_sql=true

   # Tables creation strategy: create on init, delete on close
   spring.jpa.hibernate.ddl-auto=create-drop

3. **Levantar la aplicación**

   ```bash
   mvn spring-boot:run
   ```

   La API quedará disponible en:

   ```bash
   http://localhost:8080

4. **Acceder a la documentación de la API (Swagger)**

   ```bash
   http://localhost:8080/swagger-ui.html

---

## Endpoints expuestos
   **Registro de usuario:**
   
   POST /api/users

   **Request Body:**

   Debe cumplir el siguiente formato (todos los campos son obligatorios):
   ```json
    {
      "name": "Diego Salazar",
      "email": "diego.salazar@email.cl",
      "password": "huntEr$28",
      "phones": [
        {
          "number": "1234567",
          "cityCode": "2",
          "countryCode": "56"
        },
        {
          "number": "12345678",
          "cityCode": "2",
          "countryCode": "56"
        }
      ]
    }
   ```
---

## Validaciones implementadas
- name: no vacío (@NotBlank).
- email: formato válido (@Email), no vacío y no repetido.
- password: no vacía y debe cumplir el patrón configurado en las properties.
- phones: lista obligatoria, mínimo 1 teléfono.
- number, cityCode, countryCode: no vacíos (@NotBlank).
- El código retornado en casos de estos errores de validación es el Código HTTP: 400 Bad Request, con el mensaje en el formato solicitado.

---

## Seguridad y Token JWT
Cuando un usuario se registra exitosamente: 

1- Se almacena en la base de datos. 

2- Se genera automáticamente un token JWT. 

3- El token se devuelve en la respuesta. 

4- Para el uso de JWT (en vez de usar UUID en el token, utilizamos JWT para generar este token del usuario).
Tenemos en el application.properties lo siguiente:

app.jwt.secret=MiClaveSuperSecretaDe32Caracteres123456

app.jwt.expiration-ms=86400000 # 1 día

5- Estos valores, lógicamente, en producción deberán estar en una variable de entorno secreta, almacenada en el servicio cloud que se utilice, y no expuestas aquí.

En la presente entrega, el POST /users funciona sin la necesidad de contar con un token, pero devuelve y guarda un token JWT al User almacenado, y este token es un JWT válido.

Si a futuro quisiéramos proteger todos los endpoints con JWT (como el mismo POST), ya tenemos la base lista (validación en JwtTokenProvider).

Ejemplo de respuesta:
```json
    {
      "id": "33705296-1bc6-4f6d-b858-3eee53fd53dd",
      "name": "Diego Salazar",
      "email": "diego.salazar@email.cl",
      "created": "2025-08-13T09:48:32.0453866",
      "modified": "2025-08-13T09:48:32.0453866",
      "lastLogin": "2025-08-13T09:48:32.0453866",
      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaWVnby5zYWxhemFyQGVtYWlsLmNsIiwiaWF0IjoxNzU1MDg5MzEyLCJleHAiOjE3NTUxNzU3MTJ9.Xnq7TIB6t0-ARVoje85hb33nAYFqu6OzH4asFtCwcN4",
      "active": true
    }
   ```

## Diagrama de la solución

![Diagrama de la solución](src/docs/api_diagram.svg)

---

## Cómo probar la API

**1. Usando Swagger UI**
- Iniciar la app (mvn spring-boot:run).
- Abrir en el navegador:
  ```bash
   http://localhost:8080/swagger-ui.html
   ```
- Seleccionar POST /api/users.
- Pegar el JSON del User en el body y ejecutar.

**2. Usando cURL**
   ```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Juan Rodriguez",
    "email": "juan@rodriguez.org",
    "password": "Hunter2@123",
    "phones": [
      {
        "number": "1234567",
        "cityCode": "1",
        "countryCode": "57"
      }
    ]
  }'
   ```
**3. Usando Postman**
- Crear nueva request POST a http://localhost:8080/api/users
- En Body → raw → JSON pegar el JSON de ejemplo.
- Enviar y revisar respuesta.

---

**4. Script de creación de BD**

- Las tablas se generan automáticamente con Hibernate al iniciar la aplicación.
- El script de creación de tablas se encuentra en src/main/resources/schema.sql
- El archivo schema.sql contiene únicamente las sentencias create table necesarias para el funcionamiento de la API, sin incluir un create database, esto es intencional dado que no conocemos el motor real a utilizar, por esta razón eso se utiliza la BD indicada H2 en memoria, y en caso de querer migrar a otro motor de base de datos (PostgreSQL, MySQL, etc.), la creación de la base deberá hacerse manualmente antes de ejecutar el schema.sql.

---

## Notas
- Actualmente la API solo expone el endpoint de registro.
- El manejo de errores devuelve siempre un JSON con mensajes claros y código HTTP correcto.
- La validación de formato de correo y campos obligatorios se realiza antes de procesar la creación de usuario.
- Si bien se podria haber generado el token del User con UUID, se trata de cumplir con el requisito opcional de usar JWT para generar el token del usuario.
- Sobre el script de creación de la base de datos: Como indicamos antes, el archivo schema.sql sólo se remite a la creación de tablas. Los motivos de este punto son:
   * Uso de H2 Database:
   La API utiliza H2 como base de datos en memoria (o persistente en archivo, según configuración). En H2, la base de datos se crea automáticamente al iniciar la aplicación, a partir de la URL de conexión definida en application.properties.

   * Compatibilidad:
   Al no incluir un CREATE DATABASE, el script es portable y puede ejecutarse en cualquier motor SQL sin riesgo de errores por base de datos ya existente o permisos insuficientes.

   * Ejecución automática por Spring Boot:
   Spring Boot carga automáticamente el schema.sql en el arranque, por lo que las tablas se crean sin necesidad de intervención manual.

   * En caso de querer migrar a otro motor de base de datos (PostgreSQL, MySQL, etc.), la creación de la base deberá hacerse manualmente antes de ejecutar el schema.sql.
- El nombre de esta api es usuarios-api, quedó en español, pero quizas podría ser en inglés para unificarlo con el código, no lo hice por el tiempo que dispongo, igualmente se informa.