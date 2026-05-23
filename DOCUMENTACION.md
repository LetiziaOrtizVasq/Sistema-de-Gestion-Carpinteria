# Documentación del Sistema — MaderaCraft · Gestión para Carpintería

**Universidad Privada de Santa Cruz — UPSA**  
**Materia:** Sistemas de Información / Programación  
**Integrantes:** Letizia Camila Ortiz Vásquez · Mariana Toledo

---

## 1. Descripción del Sistema

MaderaCraft es una aplicación web que permite administrar de forma integral los procesos de una carpintería artesanal. Cubre desde el primer contacto con el cliente hasta la entrega final del producto y el cobro completo.

El sistema automatiza los siguientes procesos:
- Registro de clientes y sus solicitudes de muebles
- Generación y aprobación de cotizaciones
- Confirmación de pedidos y cobro de adelantos
- Planificación y seguimiento de la producción
- Control de stock de madera e insumos
- Inspección de calidad, embalaje y entrega
- Registro de pago final y cierre del proceso
- Reportes de gestión por período

---

## 2. Tecnologías Utilizadas

| Componente           | Tecnología                           | Versión   |
|----------------------|--------------------------------------|-----------|
| Lenguaje             | Java                                 | 21        |
| Framework backend    | Spring Boot                          | 3.2.5     |
| Vistas (frontend)    | Thymeleaf (HTML dinámico)            | 3.x       |
| Persistencia         | Spring Data JPA + Hibernate          | 6.4       |
| Base de datos        | MySQL                                | 8.4       |
| Validaciones         | Jakarta Validation (Bean Validation) | 3.x       |
| Gestor de build      | Apache Maven (multi-módulo)          | 3.9.14    |
| Servidor web         | Apache Tomcat (embebido)             | 10.1      |
| Estilos              | CSS custom properties (design system propio) | — |
| Tipografía           | Inter (Google Fonts)                 | Variable  |
| Iconografía          | Lucide Icons (SVG inline)            | —         |
| Control de versiones | Git + GitHub                         | —         |

---

## 3. Requisitos para Correr el Sistema

### En la computadora deben estar instalados:

| Herramienta | Ubicación en esta PC                       |
|-------------|--------------------------------------------|
| Java 21 JDK | `C:\Users\ortiz\Java\jdk-21.0.10+7`       |
| Maven 3.9   | `C:\Users\ortiz\Maven\apache-maven-3.9.14` |
| MySQL 8.4   | `C:\Program Files\MySQL\MySQL Server 8.4`  |

### Base de datos:

- **Servidor:** localhost, puerto 3306
- **Base de datos:** `carpinteriadb`
- **Usuario:** `root`
- **Contraseña:** `Carpinteria2025!`

La primera vez, crear la base de datos con:
```sql
CREATE DATABASE carpinteriadb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Las tablas se crean automáticamente al iniciar el sistema (Hibernate `ddl-auto=update`). No hay que ejecutar scripts SQL adicionales.

---

## 4. Cómo Correr el Sistema

### Opción A — Script automático (recomendado)

1. Abrir la carpeta del proyecto en el Explorador de Windows
2. Hacer doble clic en **`ejecutar.bat`**
3. El script:
   - Verifica si MySQL está activo
   - Lo inicia automáticamente si no está corriendo
   - Compila y levanta la aplicación
4. Cuando aparezca el mensaje `Started CarpinteriaApplication`, abrir el navegador en:

   **http://localhost:8080**

---

### Opción B — Desde la terminal de VS Code

1. Abrir el proyecto en VS Code
2. Abrir la terminal integrada: `` Ctrl + ` ``
3. Asegurarse de que MySQL esté corriendo
4. Ejecutar:

```bash
mvn -pl carpinteria-app -am spring-boot:run
```

5. Esperar el mensaje `Started CarpinteriaApplication`
6. Abrir en el navegador: **http://localhost:8080**

---

### Opción C — Desde VS Code con extensión Java

Si tenés instalado el **Extension Pack for Java** en VS Code:

1. Abrir el archivo `carpinteria-app/src/main/java/com/carpinteria/CarpinteriaApplication.java`
2. Hacer clic en el botón **▶ Run** que aparece arriba del método `main`
3. Abrir en el navegador: **http://localhost:8080**

---

## 5. Ver la Base de Datos desde VS Code

Con la extensión **Database Client** instalada en VS Code:

1. Hacer clic en el ícono de base de datos (cilindro) en la barra lateral izquierda
2. Hacer clic en **+** (New Connection)
3. Seleccionar tipo: **MySQL**
4. Completar los campos:
   - **Host:** `localhost`
   - **Port:** `3306`
   - **Username:** `root`
   - **Password:** `Carpinteria2025!`
   - **Database:** `carpinteriadb`
5. Hacer clic en **Connect**
6. Se mostrarán todas las tablas con sus datos

### Tablas principales del sistema

| Tabla                  | Descripción                                 |
|------------------------|---------------------------------------------|
| `cliente`              | Clientes registrados                        |
| `solicitud`            | Solicitudes de muebles                      |
| `item_solicitud`       | Artículos dentro de cada solicitud          |
| `cotizacion`           | Cotizaciones vinculadas a solicitudes       |
| `pedido_confirmado`    | Pedidos confirmados con estado              |
| `pago_inicial`         | Pagos del 50% inicial                       |
| `info_tecnica`         | Especificaciones técnicas del producto      |
| `produccion`           | Asignaciones de producción                  |
| `avance_produccion`    | Etapas de avance por pedido                 |
| `inventario_madera`    | Stock actual de madera                      |
| `movimiento_inventario`| Ingresos, consumos y reposiciones de stock  |
| `proveedor`            | Proveedores de madera                       |
| `inspeccion_calidad`   | Inspecciones de calidad                     |
| `embalaje`             | Registros de embalaje                       |
| `entrega`              | Entregas confirmadas al cliente             |
| `pago_final`           | Pagos del 50% restante                      |

---

## 6. Diseño de Interfaz — Walnut Workshop Design System

La interfaz fue diseñada con un sistema visual propio llamado **Walnut Workshop**, inspirado en herramientas profesionales como Linear, Notion, Stripe Dashboard y Vercel. El objetivo es una estética minimalista, elegante y sobria.

### Principios de diseño

- **Menos es más:** sin gradientes excesivos, sin sombras exageradas, sin decoraciones superfluas
- **Jerarquía clara:** tamaños tipográficos, pesos y colores bien definidos
- **Consistencia total:** todos los módulos usan los mismos componentes y tokens de diseño
- **Sin emojis:** toda la iconografía usa SVG de Lucide Icons para mayor profesionalismo

### Paleta de colores

| Token                  | Valor      | Uso                                  |
|------------------------|------------|--------------------------------------|
| `--sidebar-bg`         | `#0D0A09`  | Fondo del sidebar (casi negro cálido)|
| `--bg`                 | `#F7F4F0`  | Fondo general (parchment cálido)     |
| `--card`               | `#FFFFFF`  | Fondo de tarjetas                    |
| `--border`             | `#E3D9D0`  | Bordes sutiles                       |
| `--cafe-700`           | `#4A2C17`  | Walnut oscuro (botones primarios)    |
| `--cafe-300`           | `#C4956A`  | Oak claro (acento sidebar activo)    |
| `--text`               | `#1C1410`  | Texto principal                      |
| `--text-3`             | `#9C8374`  | Texto secundario / placeholders      |

### Tipografía

**Inter** (Google Fonts) en cuatro pesos:
- `800` — KPI values, números grandes
- `700` — Títulos de página, sección
- `600` — Labels, botones, cabeceras de tabla
- `400/500` — Texto de cuerpo, formularios

### Iconografía

Todos los iconos son **Lucide Icons** embebidos como SVG inline en los templates HTML. No se usa ningún CDN de iconos — los SVGs están directamente en los archivos para máxima fiabilidad (funciona sin conexión a internet, ideal para demos).

| Sección              | Ícono Lucide         |
|----------------------|----------------------|
| Dashboard            | `home`               |
| Clientes             | `users`              |
| Ventas               | `briefcase`          |
| Producción           | `settings` (cog)     |
| Inventario           | `layers`             |
| Calidad y Entrega    | `truck`              |
| Reportes             | `bar-chart-2`        |

### Componentes principales (`estilo.css`)

- **Sidebar** — 256px fijo, colapsable en mobile, grupos con toggle JS
- **Stepper** — Barra de 8 pasos con estados: pendiente / activo / completado
- **KPI cards** — 4 columnas en desktop, 2 en tablet, 1 en mobile
- **Table card** — Headers neutros en fondo claro (no gradiente oscuro)
- **Card con header** — Borde izquierdo de acento walnut, sin gradiente
- **Badges** — 7 variantes de estado (pendiente, proceso, exitoso, peligro, etc.)
- **Botones pill** — `border-radius: 9999px`, 5 variantes (primary, secondary, success, danger, warning)
- **Empty states** — Ícono SVG centrado + título + texto descriptivo + CTA
- **Hamburger mobile** — CSS-only: `font-size: 0` + `::before` con `box-shadow` (sin JS para el ícono)

---

## 7. Arquitectura del Sistema (Estilo SAP Modular)

El proyecto está organizado como un **proyecto Maven multi-módulo**. Cada módulo es independiente: tiene su propio código Java, sus vistas HTML y su archivo `pom.xml`. Si se necesita integrar solo una parte del sistema a otro proyecto, basta con copiar el módulo correspondiente.

```
Sistema-de-Gestion-Carpinteria/
│
├── carpinteria-common/         Recursos compartidos
│   ├── estilo.css              Design system completo (variables, componentes)
│   ├── nav.html                Sidebar con fragmento Thymeleaf reutilizable
│   └── stepper.html            Barra de progreso de 8 pasos
│
├── carpinteria-clientes/       Clientes y Solicitudes
├── carpinteria-ventas/         Cotizaciones y Pedidos
├── carpinteria-pagos/          Pagos iniciales
├── carpinteria-inventario/     Stock, Movimientos y Proveedores
├── carpinteria-produccion/     Info técnica, Asignación y Avances
├── carpinteria-calidad/        Calidad, Embalaje, Entregas y Pago final
├── carpinteria-reportes/       Reportes del período
│
└── carpinteria-app/            Módulo de arranque
    ├── CarpinteriaApplication.java
    └── application.properties
```

### Dependencias entre módulos

```
common ← clientes ← ventas ← pagos
                  ↑          ↑
              produccion    calidad ← reportes
              inventario
```

### Estructura interna de cada módulo (patrón MVC)

```
com.carpinteria.MODULO/
├── Entidad.java              ← tabla en BD (@Entity)
├── EntidadRepository.java    ← acceso a datos (JPA)
├── EntidadService.java       ← lógica de negocio
└── EntidadController.java    ← endpoints HTTP (@Controller)

resources/templates/MODULO/
├── lista.html                ← vista de listado con tabla
└── formulario.html           ← vista de crear / editar
```

---

## 8. Funcionalidades del Sistema (Casos de Uso)

### Clientes — `/clientes`
- Registrar nuevo cliente (nombre, CI, teléfono, dirección)
- Editar y eliminar clientes
- Ver historial completo de solicitudes por cliente

### Solicitudes — `/solicitudes`
- Registrar solicitud con múltiples artículos (tipo de mueble, cantidad, material, dimensiones)
- Estados: Pendiente / En Proceso / Cotizada / Cancelada
- Editar y eliminar solicitudes

### Cotizaciones — `/cotizaciones`
- Crear cotización vinculada a una solicitud
- Ingresar precio de materiales y mano de obra (total calculado automáticamente)
- Aprobar o rechazar cotización

### Pedidos — `/pedidos`
- Confirmar pedido desde una cotización aprobada
- Duplicar y cancelar pedidos
- Seguimiento de estados:

```
PENDIENTE_PAGO → PAGO_CONFIRMADO → EN_PRODUCCION → LISTO_ENTREGA → ENTREGADO → CERRADO
```

### Pagos Iniciales — `/pagos`
- Registrar el pago del 50% al confirmar el pedido
- El sistema actualiza automáticamente el estado del pedido a `PAGO_CONFIRMADO`

### Información Técnica — `/tecnica`
- Registrar especificaciones exactas: tipo de madera, acabado, dimensiones, color
- Vinculado a la solicitud del cliente

### Producción — `/produccion`
- Asignar pedido a un operario del taller
- Registrar avances por etapa: Corte → Ensamble → Lijado → Pintura/Acabado → Terminado
- Indicar porcentaje de avance y si la etapa está completada

### Inventario — `/inventario`
- Consultar stock actual de madera con alertas de stock mínimo
- Registrar ingresos, consumos y reposiciones
- Movimientos con historial completo (`/inventario/movimientos`)

### Proveedores — `/proveedores`
- Registrar y gestionar proveedores de madera

### Calidad — `/calidad`
- Registrar inspección de calidad del producto terminado
- Resultado: Aprobado / Retrabajo / Rechazado

### Embalaje — `/embalaje`
- Registrar el embalaje del pedido listo para entregar

### Entregas — `/entregas`
- Confirmar la entrega al cliente (recibido por, CI del receptor, observaciones)
- El sistema cambia el estado del pedido a `ENTREGADO`

### Pago Final — `/pagos-final`
- Registrar el 50% restante del pago
- El sistema cierra el pedido: estado → `CERRADO`

### Reportes — `/reportes`
- Pedidos del período (filtro por fecha desde / hasta)
- Consumo de madera por tipo
- Clientes con más solicitudes

---

## 9. Flujo Completo del Sistema — Paso a Paso

El sistema guía al usuario a través de un flujo de 8 pasos. Cada formulario muestra un stepper en la parte superior indicando el paso actual. El flujo es **no obligatorio**: se puede abandonar en cualquier momento y retomar desde la lista de pedidos.

```
┌─────────────────────────────────────────────────────────────────────────┐
│  1          2           3        4         5          6     7      8    │
│  Solicitud→ Cotización→ Pedido→ Pago 50%→ Producción→ Calidad→ Entrega→ Pago Final │
└─────────────────────────────────────────────────────────────────────────┘
```

**Paso 1 — Solicitud** (`/solicitudes/nueva`)
El cliente describe los muebles que necesita. Al guardar, el sistema redirige al Paso 2.

**Paso 2 — Cotización** (`/cotizaciones/nueva`)
Se calculan materiales + mano de obra. Al **Aceptar** → pasa al Paso 3. Si se rechaza → proceso termina.

**Paso 3 — Pedido** (`/pedidos/nuevo`)
Se confirma el pedido con fecha de entrega estimada. Al guardar → pasa al Paso 4.

**Paso 4 — Pago Inicial** (`/pagos/nuevo`)
El cliente paga el 50% del total. El sistema actualiza el estado a `PAGO_CONFIRMADO` → pasa al Paso 5.

**Paso 5 — Producción** (`/produccion/nuevo`)
Se asigna el pedido a un operario. Se registran avances por etapa hasta `Terminado`.

**Paso 6 — Inspección de Calidad** (`/calidad/nueva`)
Se inspecciona el producto. Si **Aprobado** → pasa al Paso 7. Si **Retrabajo** → vuelve a producción.

**Paso 7 — Embalaje + Entrega**
Embalaje (`/embalaje/nuevo`) → Entrega (`/entregas/nueva`). El sistema registra quién recibió el producto.

**Paso 8 — Pago Final** (`/pagos-final/nuevo`)
El cliente paga el 50% restante. El sistema cierra el pedido: estado → `CERRADO`.

**Retomar el flujo:** desde `/pedidos`, cada fila muestra un botón de acción contextual según el estado actual.

---

## 10. Convenciones de la API (REST)

El sistema sigue convenciones REST:

| Acción         | Método HTTP | Ejemplo                  |
|----------------|-------------|--------------------------|
| Ver listado    | GET         | `/clientes`              |
| Ver formulario | GET         | `/clientes/nuevo`        |
| Guardar        | POST        | `/clientes`              |
| Editar         | GET         | `/clientes/editar/{id}`  |
| Eliminar       | DELETE      | `/clientes/{id}`         |

Como los formularios HTML solo soportan GET y POST, el método DELETE se envía con `<input name="_method" value="DELETE">` y el filtro `HiddenHttpMethodFilter` de Spring lo convierte automáticamente.

---

## 11. Configuración de la Aplicación

Archivo: `carpinteria-app/src/main/resources/application.properties`

```properties
# Base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/carpinteriadb?useSSL=false&serverTimezone=America/La_Paz
spring.datasource.username=root
spring.datasource.password=Carpinteria2025!

# JPA — tablas automáticas, sin log de SQL
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Soporte para DELETE/PUT desde formularios HTML
spring.mvc.hiddenmethod.filter.enabled=true

# Puerto del servidor
server.port=8080

# Sin caché de templates — los cambios en HTML se ven sin reiniciar
spring.thymeleaf.cache=false
```

---

## 12. Repositorio en GitHub

**https://github.com/LetiziaOrtizVasq/Sistema-de-Gestion-Carpinteria**

---

## 13. Notas Finales

- Las **fechas** se generan automáticamente en el servidor. No las ingresa el usuario.
- Los **datos persisten** en MySQL entre reinicios (no se pierden al apagar la PC).
- La primera vez que se inicia, Hibernate crea todas las tablas automáticamente.
- `spring.thymeleaf.cache=false` permite editar templates HTML y ver los cambios recargando el navegador sin reiniciar el servidor.
- Cada módulo Maven tiene su propio `pom.xml` con sus dependencias declaradas, lo que permite exportarlo e integrarlo a otro proyecto de forma independiente.
- La interfaz funciona sin conexión a internet: Inter se carga desde Google Fonts (requiere internet), pero los íconos SVG están embebidos directamente en el HTML.
