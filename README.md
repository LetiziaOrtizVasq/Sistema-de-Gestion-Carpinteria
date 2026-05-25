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
| IA Generativa       | Google Gemini API (gemini-2.5-flash)            |
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
| IA — Cotización | —                | `/api/ai/cotizacion/sugerir?solicitudId={id}` |
| IA — Inventario | —                | `/api/ai/inventario/prediccion`               |
| IA — Reportes   | —                | `/api/ai/reporte/resumen?desde={date}&hasta={date}` |

---

## Funciones de Inteligencia Artificial

El sistema incluye dos funciones IA impulsadas por **Google Gemini API** (capa gratuita):

### Sugerencia de precios en cotizaciones
En el formulario de nueva cotización (`/cotizaciones/nueva`), al seleccionar una solicitud y hacer clic en **"✦ Sugerir precios con IA"**, Gemini analiza los artículos solicitados (tipo de mueble, material, dimensiones) y el historial de cotizaciones aceptadas para sugerir automáticamente el precio de materiales y mano de obra.

### Predicción de agotamiento de stock
En el listado de inventario (`/inventario`), el botón **"✦ Analizar stock"** envía el stock actual y el historial de consumos de los últimos 90 días a Gemini, que estima en cuántos días se agotará cada tipo de madera y asigna un nivel: **Urgente / Pronto / Estable / Sin datos**.

### Resumen ejecutivo en reportes
En `/reportes`, luego de aplicar un filtro de fechas, el botón **"✦ Resumen con IA"** genera un análisis ejecutivo en lenguaje natural con la facturación del período, tendencias de consumo de madera, clientes destacados y una recomendación de negocio.

### Configuración
Agregar la API key de Google AI Studio en `application.properties`:
```properties
gemini.api.key=TU_KEY_DE_AISTUDIO
```
Obtener key gratuita en: https://aistudio.google.com

---

## Repositorio

**https://github.com/LetiziaOrtizVasq/Sistema-de-Gestion-Carpinteria**
