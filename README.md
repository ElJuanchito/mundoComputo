# mundoComputo

## Descripción del Proyecto
mundoComputo es una API para la gestión de inventario y clientes desarrollada con Spring Boot. Proporciona endpoints para administrar productos, usuarios, clientes y operaciones de inventario, con soporte para autenticación y control de acceso por roles.

## Funcionalidades
- Gestión de inventario (entrada, salida, ajuste)
- Autenticación y autorización de usuarios (JWT)
- Acceso por roles (ADMIN, INVENTARIO, VENDEDOR)
- Gestión de clientes
- Notificaciones por correo electrónico (verificación, restablecimiento de contraseña)
- Soporte para Docker

## Tecnologías Utilizadas
- Java 21 (JDK 21)
- Spring Boot
- Gradle
- Docker
- Jakarta Mail

## Prerrequisitos
- JDK 21
- Docker (opcional, para contenerización)
- Archivo .env configurado con datos sensibles (ver abajo)

## Instrucciones de Instalación
1. **Clona el repositorio:**
   ```bash
   git clone <repo-url>
   cd mundoComputo
   ```
2. **Configura las variables de entorno:**
   Crea un archivo `.env` en el directorio raíz y agrega todos los datos sensibles (credenciales de BD, secreto JWT, credenciales de correo, etc.).
   Ejemplo:
   ```env
   DB_URL=jdbc:mysql://localhost:3306/mundo_computo
   DB_USER=tu_usuario_bd
   DB_PASSWORD=tu_contraseña_bd
   JWT_SECRET=tu_secreto_jwt
   MAIL_USERNAME=tu_email@gmail.com
   MAIL_PASSWORD=tu_contraseña_email
   MAIL_HOST=smtp.gmail.com
   MAIL_PORT=587
   ```
3. **Compila el proyecto:**
   ```bash
   ./gradlew build
   ```
4. **Ejecuta con Docker:**
   ```bash
   docker build -t mundo-computo-api .
   docker run --env-file .env -p 8080:8080 mundo-computo-api
   ```
5. **Ejecuta localmente:**
   ```bash
   ./gradlew bootRun
   ```

## Endpoints de la API
- Consulta la [Documentación de la API](docs/API.md) para ver la lista completa de endpoints.
- Ejemplos de endpoints:
  - `/api/admin/**` (solo ADMIN)
  - `/api/inventario/**` (ADMIN, INVENTARIO)
  - `/api/clientes/**` (ADMIN, VENDEDOR)

## Solución de Problemas
- **Problemas de CORS:**
  - Asegúrate de habilitar CORS en la configuración de Spring Boot para los dominios del frontend.
- **Error de autenticación de correo:**
  - Verifica las credenciales en tu archivo `.env`. Si usas Gmail y tienes 2FA, utiliza una contraseña de aplicación.
- **Hibernate TransientObjectException:**
  - Guarda las entidades relacionadas antes de persistir los objetos principales.
- **Errores de JWT/Beans:**
  - Verifica que todas las variables de entorno requeridas estén presentes en `.env` y se carguen correctamente.

## Licencia
MIT

## Autor
- [Tu Nombre]
- [tu.email@ejemplo.com]
