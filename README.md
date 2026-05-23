# Sistema de Gestión para Carpintería — MaderaCraft

Sistema web para la gestión integral de una carpintería artesanal: clientes, solicitudes, cotizaciones, pedidos, producción, inventario y entregas.

**Proyecto académico — Universidad Privada de Santa Cruz (UPSA)**  
**Integrantes:** Letizia Camila Ortiz Vásquez · Mariana Toledo

---

## Tecnologías utilizadas

| Componente          | Tecnología                          |
|---------------------|-------------------------------------|
| Backend             | Java 21 + Spring Boot 3.2.5         |
| Vistas              | Thymeleaf (HTML server-side)        |
| Persistencia        | Spring Data JPA + Hibernate         |
| Base de datos       | MySQL 8.4                           |
| Validaciones        | Jakarta Validation                  |
| Build               | Apache Maven 3.9 (multi-módulo)     |
| Estilos             | CSS custom properties (design system propio) |
| Tipografía          | Inter (Google Fonts)                |
| Iconografía         | Lucide Icons (SVG inline)           |

---

## Requisitos previos

- Java 21 (ubicado en `C:\Users\ortiz\Java\jdk-21.0.10+7`)
- Maven 3.9 (ubicado en `C:\Users\ortiz\Maven\apache-maven-3.9.14`)
- MySQL 8.4 corriendo en el puerto 3306
  - Usuario: `root` · Contraseña: `Carpinteria2025!`
  - Crear la base de datos antes de correr el proyecto (solo la primera vez):
    ```sql
    CREATE DATABASE carpinteriadb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    ```
  - Las tablas se crean automáticamente al iniciar la aplicación.

---

## Cómo ejecutar el proyecto

### Opción 1 — Script automático (recomendado, Windows)

Doble clic en **`ejecutar.bat`** en la raíz del proyecto.  
El script verifica si MySQL está activo, lo inicia si hace falta, y luego levanta la aplicación.

### Opción 2 — Terminal / VS Code

```bash
mvn -pl carpinteria-app -am spring-boot:run
```

Una vez iniciado, abrir en el navegador: **http://localhost:8080**

### Opción 3 — VS Code con Extension Pack for Java

1. Abrir `carpinteria-app/src/main/java/com/carpinteria/CarpinteriaApplication.java`
2. Clic en **▶ Run** sobre el método `main`
3. Abrir: **http://localhost:8080**

---

## Interfaz — Walnut Workshop Design System

La interfaz está diseñada con el sistema visual propio **Walnut Workshop**, inspirado en herramientas como Linear, Notion y Stripe Dashboard.

- **Tipografía:** Inter (Google Fonts), jerarquía limpia en 4 tamaños
- **Paleta:** Walnut oscuro `#0D0A09` (sidebar) · Parchment `#F7F4F0` (fondo) · Oak `#C4956A` (acento activo)
- **Iconografía:** Iconos Lucide SVG inline — sin dependencia de CDN, sin emojis
- **Componentes:** Sidebar colapsable con grupos, stepper de 8 pasos, badges de estado, empty states, tabla con headers neutros
- **Responsive:** Sidebar mobile con hamburger CSS-only (sin JavaScript para el ícono)
- **Sin emojis** en ninguna parte de la interfaz

---

## Arquitectura modular

```
Sistema-de-Gestion-Carpinteria/
│
├── carpinteria-common/       → CSS (estilo.css), nav.html, stepper.html
│
├── carpinteria-clientes/     → Clientes + Solicitudes
├── carpinteria-ventas/       → Cotizaciones + Pedidos
├── carpinteria-pagos/        → Pagos iniciales
├── carpinteria-inventario/   → Stock + Movimientos + Proveedores
├── carpinteria-produccion/   → Info técnica + Asignación + Avances
├── carpinteria-calidad/      → Calidad + Embalaje + Entregas + Pago final
├── carpinteria-reportes/     → Reportes del período
│
└── carpinteria-app/          → Módulo de arranque (main + application.properties)
```

---

## Flujo principal

```
Solicitud → Cotización → Pedido → Pago 50% → Producción → Calidad → Entrega → Pago Final
```

Estados del pedido: `PENDIENTE_PAGO → PAGO_CONFIRMADO → EN_PRODUCCION → LISTO_ENTREGA → ENTREGADO → CERRADO`

---

## Funcionalidades

| Módulo              | Rutas principales                                    |
|---------------------|------------------------------------------------------|
| Dashboard           | `/`                                                  |
| Clientes            | `/clientes`, `/clientes/nuevo`                       |
| Solicitudes         | `/solicitudes`, `/solicitudes/nueva`                 |
| Cotizaciones        | `/cotizaciones`, `/cotizaciones/nueva`               |
| Pedidos             | `/pedidos`, `/pedidos/nuevo`                         |
| Pagos iniciales     | `/pagos`, `/pagos/nuevo`                             |
| Info técnica        | `/tecnica`, `/tecnica/nueva`                         |
| Producción          | `/produccion`, `/produccion/nuevo`                   |
| Inventario          | `/inventario`, `/inventario/ingreso`                 |
| Movimientos         | `/inventario/movimientos`                            |
| Proveedores         | `/proveedores`                                       |
| Calidad             | `/calidad`, `/calidad/nueva`                         |
| Embalaje            | `/embalaje`, `/embalaje/nuevo`                       |
| Entregas            | `/entregas`, `/entregas/nueva`                       |
| Pago final          | `/pagos-final`, `/pagos-final/nuevo`                 |
| Reportes            | `/reportes`                                          |

---

## Ver la base de datos desde VS Code

Con la extensión **Database Client** instalada en VS Code:

1. Clic en el ícono de base de datos (cilindro) en la barra lateral
2. Clic en **+** (New Connection) → tipo **MySQL**
3. Completar:
   - Host: `localhost` · Port: `3306`
   - Username: `root` · Password: `Carpinteria2025!`
   - Database: `carpinteriadb`
4. Clic en **Connect**

---

## Repositorio

**https://github.com/LetiziaOrtizVasq/Sistema-de-Gestion-Carpinteria**

---

## Notas

- Las fechas se generan automáticamente en el servidor.
- Los datos persisten en MySQL entre reinicios.
- Hibernate crea las tablas automáticamente la primera vez (`ddl-auto=update`).
- APIs siguen convenciones REST; `DELETE` usa `HiddenHttpMethodFilter` de Spring.
- `spring.thymeleaf.cache=false` permite ver cambios en templates sin reiniciar.
