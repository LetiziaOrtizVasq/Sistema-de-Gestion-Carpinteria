# Sistema de Gestión para Carpintería

Sistema web para la gestión integral de una carpintería artesanal: clientes, solicitudes, cotizaciones, pedidos, producción, inventario, calidad y entregas.

**Proyecto académico — Universidad Privada de Santa Cruz (UPSA)**  
**Integrantes:** Letizia Camila Ortiz Vásquez · Mariana Toledo

---

## Tecnologías utilizadas

| Componente          | Tecnología                                      |
|---------------------|-------------------------------------------------|
| Backend             | Java 21 + Spring Boot 3.2.5                     |
| Vistas              | Thymeleaf (HTML renderizado en servidor)        |
| Persistencia        | Spring Data JPA + Hibernate 6                   |
| Base de datos       | MySQL 8.4                                       |
| Validaciones        | Jakarta Bean Validation                         |
| Build               | Apache Maven 3.9 (proyecto multi-módulo)        |
| Servidor embebido   | Apache Tomcat 10.1 (incluido en Spring Boot)    |
| Estilos             | CSS custom properties (design system propio)    |
| Tipografía          | Inter (Google Fonts)                            |
| Iconografía         | Lucide Icons (SVG inline — sin CDN)             |
| Control de versiones| Git + GitHub                                    |

---

## Requisitos previos

| Herramienta | Versión | Ubicación en esta PC                        |
|-------------|---------|---------------------------------------------|
| Java JDK    | 21      | `C:\Users\ortiz\Java\jdk-21.0.10+7`        |
| Maven       | 3.9.14  | `C:\Users\ortiz\Maven\apache-maven-3.9.14`  |
| MySQL       | 8.4     | `C:\Program Files\MySQL\MySQL Server 8.4`   |

### Configurar la base de datos (solo la primera vez)

Abrir MySQL Workbench o la terminal MySQL y ejecutar:

```sql
CREATE DATABASE carpinteriadb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Las tablas se crean **automáticamente** al iniciar la aplicación por primera vez (Hibernate `ddl-auto=update`). No hay scripts SQL adicionales que ejecutar.

---

## Cómo ejecutar el proyecto

### Opción 1 — Script automático (Windows)

Doble clic en **`ejecutar.bat`** en la raíz del proyecto.

### Opción 2 — Terminal

```bash
mvn -pl carpinteria-app -am spring-boot:run
```

### Opción 3 — VS Code con Extension Pack for Java

1. Abrir `carpinteria-app/src/main/java/com/carpinteria/CarpinteriaApplication.java`
2. Clic en **▶ Run** sobre el método `main`

Una vez iniciado → **http://localhost:8080**

---

## Conexión a la base de datos (VS Code)

Con la extensión **Database Client** instalada:

1. Ícono de base de datos en la barra lateral → **+** New Connection → MySQL
2. Completar:
   - Host: `localhost` · Port: `3306`
   - Username: `root` · Password: `Carpinteria2025!`
   - Database: `carpinteriadb`
3. Clic en **Connect**

---

## Arquitectura modular

```
Sistema-de-Gestion-Carpinteria/
├── carpinteria-common/       → CSS (estilo.css), nav.html, stepper.html
├── carpinteria-clientes/     → Clientes + Solicitudes
├── carpinteria-ventas/       → Cotizaciones + Pedidos
├── carpinteria-pagos/        → Pagos iniciales
├── carpinteria-inventario/   → Stock + Movimientos + Proveedores
├── carpinteria-produccion/   → Info técnica + Asignación + Avances
├── carpinteria-calidad/      → Calidad + Embalaje + Entregas + Pago final
├── carpinteria-reportes/     → Reportes del período
└── carpinteria-app/          → Módulo de arranque (main + application.properties)
```

---

## Flujo de trabajo

```
Solicitud → Cotización → Pedido → Pago 50% → Producción → Calidad → Entrega → Pago Final
```

Estados del pedido:
```
PENDIENTE_PAGO → PAGO_CONFIRMADO → EN_PRODUCCION → LISTO_ENTREGA → ENTREGADO → CERRADO
```

---

## Rutas principales

| Módulo          | Listado          | Nuevo / Formulario          |
|-----------------|------------------|-----------------------------|
| Dashboard       | `/`              | —                           |
| Clientes        | `/clientes`      | `/clientes/nuevo`           |
| Solicitudes     | `/solicitudes`   | `/solicitudes/nueva`        |
| Cotizaciones    | `/cotizaciones`  | `/cotizaciones/nueva`       |
| Pedidos         | `/pedidos`       | `/pedidos/nuevo`            |
| Pagos iniciales | `/pagos`         | `/pagos/nuevo`              |
| Info técnica    | `/tecnica`       | `/tecnica/nueva`            |
| Producción      | `/produccion`    | `/produccion/nuevo`         |
| Inventario      | `/inventario`    | `/inventario/ingreso`       |
| Proveedores     | `/proveedores`   | `/proveedores/nuevo`        |
| Calidad         | `/calidad`       | `/calidad/nueva`            |
| Embalaje        | `/embalaje`      | `/embalaje/nuevo`           |
| Entregas        | `/entregas`      | `/entregas/nueva`           |
| Pago final      | `/pagos-final`   | `/pagos-final/nuevo`        |
| Reportes        | `/reportes`      | —                           |

---

## Repositorio

**https://github.com/LetiziaOrtizVasq/Sistema-de-Gestion-Carpinteria**
