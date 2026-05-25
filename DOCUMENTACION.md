# Documentación Técnica — Sistema de Gestión para Carpintería

**Universidad Privada de Santa Cruz — UPSA**  
**Integrantes:** Letizia Camila Ortiz Vásquez · Mariana Toledo

---

## 1. Descripción General

Sistema web que automatiza la gestión integral de una carpintería artesanal. Cubre desde el primer contacto con el cliente hasta el cierre del proceso con el cobro final.

Procesos que gestiona el sistema:
- Registro de clientes y sus solicitudes de muebles
- Generación y aprobación de cotizaciones (materiales + mano de obra)
- Confirmación de pedidos y cobro de adelantos
- Planificación y seguimiento de la producción por etapas
- Control de stock de madera con alertas de mínimo
- Inspección de calidad, embalaje y entrega al cliente
- Registro de pago final y cierre del pedido
- Reportes de gestión por período de fechas

---

## 2. Tecnologías

| Capa               | Tecnología                              | Versión |
|--------------------|-----------------------------------------|---------|
| Lenguaje           | Java                                    | 21      |
| Framework backend  | Spring Boot                             | 3.2.5   |
| Motor de vistas    | Thymeleaf                               | 3.x     |
| ORM / Persistencia | Spring Data JPA + Hibernate             | 6.4     |
| Base de datos      | MySQL                                   | 8.4     |
| Validaciones       | Jakarta Bean Validation                 | 3.x     |
| Gestor de build    | Apache Maven (multi-módulo)             | 3.9.14  |
| Servidor web       | Apache Tomcat embebido                  | 10.1    |
| Estilos            | CSS custom properties (design system)   | —       |
| Tipografía         | Inter (Google Fonts)                    | —       |
| Iconografía        | Lucide Icons (SVG inline)               | —       |
| IA Generativa      | Google Gemini API (gemini-2.5-flash)    | —       |
| Control de versión | Git + GitHub                            | —       |

---

## 3. Arquitectura del Sistema

El sistema sigue la arquitectura **MVC (Model–View–Controller)** implementada con Spring Boot + Thymeleaf. El flujo de una petición HTTP es el siguiente:

```
Navegador
    │
    │  HTTP Request (GET /pedidos)
    ▼
Spring DispatcherServlet
    │
    ▼
Controller  ←── recibe la petición, llama al service
    │
    ▼
Service     ←── contiene la lógica de negocio
    │
    ▼
Repository  ←── accede a la base de datos mediante JPA
    │
    ▼
MySQL       ←── almacena y devuelve los datos
    │
    ▼ (datos devueltos como objetos Java)
Controller  ←── agrega datos al Model
    │
    ▼
Thymeleaf   ←── renderiza el HTML con los datos
    │
    │  HTTP Response (HTML generado)
    ▼
Navegador   ←── muestra la página al usuario
```

### Capas de cada módulo

Cada módulo del sistema sigue la misma estructura de capas:

```
com.carpinteria.MODULO/
├── Entidad.java             @Entity — representa una tabla de la BD
├── EntidadRepository.java   @Repository — métodos de acceso a datos (JPA)
├── EntidadService.java      @Service — lógica de negocio
└── EntidadController.java   @Controller — endpoints HTTP, conecta con Thymeleaf
```

---

## 4. Backend — Spring Boot

### ¿Qué es Spring Boot?

Spring Boot es un framework de Java que permite crear aplicaciones web sin configuración manual. Incluye un servidor Tomcat embebido (no hace falta instalar ningún servidor externo), gestión automática de dependencias y configuración mínima.

### Módulos del proyecto

El proyecto es un **proyecto Maven multi-módulo**. Cada módulo tiene su propio `pom.xml` con sus dependencias y es independiente del resto.

```
Sistema-de-Gestion-Carpinteria/
│
├── pom.xml (pom padre — declara todos los módulos)
│
├── carpinteria-common/
│   └── Recursos compartidos: CSS, nav.html, stepper.html
│       Todos los demás módulos dependen de este.
│
├── carpinteria-clientes/
│   └── Entidades: Cliente, SolicitudCliente, ItemSolicitud
│
├── carpinteria-ventas/
│   └── Entidades: Cotizacion, PedidoConfirmado
│
├── carpinteria-pagos/
│   └── Entidades: PagoInicial, PagoFinal
│
├── carpinteria-inventario/
│   └── Entidades: StockMadera, MovimientoMadera, Proveedor
│
├── carpinteria-produccion/
│   └── Entidades: InformacionTecnica, AsignacionProduccion, AvanceProduccion
│
├── carpinteria-calidad/
│   └── Entidades: InspeccionCalidad, EmbalajePedido, EntregaPedido
│
├── carpinteria-reportes/
│   └── Servicio de reportes (sin entidades propias, consulta a otras)
│
└── carpinteria-app/
    ├── CarpinteriaApplication.java  (main — punto de arranque)
    ├── application.properties       (configuración de BD, servidor y IA)
    └── ai/
        ├── ClaudeService.java           (cliente HTTP a la API de Gemini)
        ├── CotizacionAiController.java  (endpoint: sugerir precios)
        └── InventarioAiController.java  (endpoint: predicción de stock)
```

### Controladores (Controllers)

Los controllers reciben las peticiones HTTP y devuelven vistas Thymeleaf o redirecciones.

Ejemplo — `PedidoConfirmadoController`:

```java
@Controller
@RequestMapping("/pedidos")
public class PedidoConfirmadoController {

    @GetMapping               // GET /pedidos → lista todos los pedidos
    public String lista(Model model) {
        model.addAttribute("pedidos", service.listarTodos());
        return "pedido/lista";   // renderiza templates/pedido/lista.html
    }

    @PostMapping              // POST /pedidos → guarda un pedido nuevo
    public String guardar(@ModelAttribute PedidoConfirmado pedido) {
        service.guardar(pedido);
        return "redirect:/pedidos";
    }
}
```

### Servicios (Services)

Los services contienen la lógica de negocio: validaciones, cálculos, cambios de estado del pedido.

Ejemplo — cambio de estado automático al registrar un pago inicial:

```java
@Service
public class PagoInicialService {
    public void guardar(PagoInicial pago) {
        // Registra el pago
        pagoRepo.save(pago);
        // Actualiza el estado del pedido automáticamente
        PedidoConfirmado pedido = pago.getPedido();
        pedido.setEstado("PAGO_CONFIRMADO");
        pedidoRepo.save(pedido);
    }
}
```

### Repositorios (Repositories)

Los repositories son interfaces que heredan de `JpaRepository`. Spring genera automáticamente los métodos SQL sin que se escriba código SQL manual.

```java
public interface PedidoConfirmadoRepository
        extends JpaRepository<PedidoConfirmado, Long> {
    // Spring genera automáticamente: findAll(), findById(), save(), deleteById()

    // Método personalizado — Spring traduce el nombre a SQL:
    List<PedidoConfirmado> findByEstado(String estado);
}
```

---

## 5. Frontend — Thymeleaf

### ¿Qué es Thymeleaf?

Thymeleaf es un motor de plantillas para Java. Los archivos HTML se escriben normalmente pero con atributos especiales (`th:text`, `th:each`, `th:if`) que Thymeleaf reemplaza con datos reales del servidor antes de enviar el HTML al navegador.

El usuario **nunca** ve el código Thymeleaf — solo recibe HTML puro ya procesado.

### Atributos más usados

```html
<!-- Mostrar un valor -->
<span th:text="${pedido.estado}">PENDIENTE</span>

<!-- Iterar una lista -->
<tr th:each="p : ${pedidos}">
    <td th:text="${p.id}"></td>
    <td th:text="${p.cotizacion.solicitud.nombreCliente}"></td>
</tr>

<!-- Condición -->
<a th:if="${p.estado == 'PENDIENTE_PAGO'}" th:href="@{/pagos/nuevo}">
    Registrar pago
</a>

<!-- Incluir un fragmento (nav, stepper) -->
<div th:replace="~{layout/nav :: nav('ventas')}"></div>

<!-- URL dinámica con parámetro -->
<a th:href="@{/pedidos/editar/{id}(id=${p.id})}">Editar</a>
```

### Estructura de templates

```
src/main/resources/templates/
├── layout/
│   ├── nav.html          → sidebar (fragmento reutilizado en todos los módulos)
│   └── stepper.html      → barra de 8 pasos
├── dashboard.html         → página principal
├── cliente/
│   ├── lista.html
│   └── formulario.html
├── solicitud/
│   ├── lista.html
│   └── formulario.html
├── cotizacion/
│   ├── lista.html
│   └── formulario.html
├── pedido/
│   ├── lista.html
│   └── formulario.html
├── pago/
│   ├── lista.html
│   └── formulario.html
├── produccion/
│   ├── lista.html
│   ├── form.html
│   ├── avances.html
│   ├── tecnica-lista.html
│   └── tecnica-form.html
├── inventario/
│   ├── lista.html
│   ├── form.html
│   ├── movimientos.html
│   ├── mov-ingreso.html
│   ├── mov-consumo.html
│   ├── mov-reposicion.html
│   ├── proveedores.html
│   └── proveedor-form.html
├── calidad/
│   ├── lista.html
│   ├── form.html
│   ├── embalaje-lista.html
│   └── embalaje-form.html
├── entrega/
│   ├── lista.html
│   ├── form.html
│   ├── pago-final-lista.html
│   └── pago-final-form.html
└── reporte/
    └── index.html
```

### CSS — Design System

Todos los estilos están en un único archivo:

```
carpinteria-common/src/main/resources/static/css/estilo.css
```

El CSS usa **custom properties** (variables CSS) para toda la paleta de colores y espaciados. Cambiar una variable afecta a toda la interfaz.

#### Paleta de colores

| Variable               | Valor      | Uso                                  |
|------------------------|------------|--------------------------------------|
| `--sidebar-bg`         | `#0A0A0A`  | Fondo del sidebar (negro puro)       |
| `--bg`                 | `#F9FAFB`  | Fondo general (gris muy claro)       |
| `--card`               | `#FFFFFF`  | Fondo de tarjetas                    |
| `--border`             | `#E4E4E7`  | Bordes (zinc-200)                    |
| `--accent`             | `#6366F1`  | Acento índigo (activo, focus)        |
| `--cafe-800`           | `#18181B`  | Botones primarios (zinc-900)         |
| `--text`               | `#09090B`  | Texto principal (zinc-950)           |
| `--text-3`             | `#A1A1AA`  | Texto secundario (zinc-400)          |
| `--verde`              | `#16A34A`  | Estado exitoso / entregado           |
| `--rojo`               | `#DC2626`  | Estado peligro / cancelado           |
| `--azul`               | `#2563EB`  | Estado en proceso                    |
| `--amarillo`           | `#D97706`  | Estado pendiente                     |

#### Componentes CSS principales

- **Sidebar** — 256px fijo en desktop, colapsable en mobile con JavaScript
- **Stepper** — barra de 8 pasos con estados: pendiente / activo / completado
- **KPI cards** — grid de 4 columnas (2 en tablet, 1 en mobile)
- **Table card** — tabla con headers en fondo neutro y hover en filas
- **Badges de estado** — 7 variantes de color (pendiente, proceso, exitoso, peligro, etc.)
- **Botones pill** — `border-radius: 9999px`, variantes: primary, secondary, success, danger, warning
- **Empty states** — icono SVG + título + texto + botón de acción
- **Hamburger CSS-only** — el ícono de menú mobile se dibuja con `::before` y `box-shadow` sin usar texto ni emoji

#### Tipografía

**Inter** (Google Fonts) en cuatro pesos:
- `800` — números grandes de KPI
- `700` — títulos de página y sección
- `600` — labels, botones, cabeceras de tabla
- `400/500` — texto de cuerpo y formularios

---

## 6. Base de Datos — MySQL

### Conexión

| Parámetro | Valor                  |
|-----------|------------------------|
| Host      | `localhost`            |
| Puerto    | `3306`                 |
| Base      | `carpinteriadb`        |
| Usuario   | `root`                 |
| Contraseña| `Carpinteria2025!`     |
| Charset   | `utf8mb4_unicode_ci`   |

### Cómo se conecta Spring Boot a MySQL

La conexión está configurada en `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/carpinteriadb?useSSL=false&serverTimezone=America/La_Paz
spring.datasource.username=root
spring.datasource.password=Carpinteria2025!
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Cuando la aplicación arranca, Spring Boot:
1. Lee estas propiedades
2. Crea un **connection pool** (HikariCP) — un conjunto de conexiones reutilizables a MySQL
3. Hibernate escanea todas las clases `@Entity` y las sincroniza con las tablas de MySQL
4. Si una tabla no existe, la crea. Si le falta una columna, la agrega.

### Tablas del sistema

| Tabla                    | Módulo           | Descripción                                      |
|--------------------------|------------------|--------------------------------------------------|
| `cliente`                | clientes         | Datos de clientes (nombre, CI, teléfono)         |
| `solicitud_cliente`      | clientes         | Solicitudes de muebles vinculadas a un cliente   |
| `item_solicitud`         | clientes         | Artículos dentro de cada solicitud               |
| `cotizacion`             | ventas           | Cotizaciones (materiales + mano de obra = total) |
| `pedido_confirmado`      | ventas           | Pedidos aceptados con su estado actual           |
| `pago_inicial`           | pagos            | Pago del 50% al confirmar el pedido              |
| `informacion_tecnica`    | produccion       | Especificaciones: madera, acabado, color, dim.   |
| `asignacion_produccion`  | produccion       | Asignación de pedido a operario                  |
| `avance_produccion`      | produccion       | Registro de avances por etapa                    |
| `stock_madera`           | inventario       | Stock actual de cada tipo de madera              |
| `movimiento_madera`      | inventario       | Historial de ingresos, consumos y reposiciones   |
| `proveedor`              | inventario       | Proveedores de madera                            |
| `inspeccion_calidad`     | calidad          | Inspección del producto terminado                |
| `embalaje_pedido`        | calidad          | Registro de embalaje listo para entregar         |
| `entrega_pedido`         | calidad          | Confirmación de entrega al cliente               |
| `pago_final`             | calidad/pagos    | Pago del 50% restante al entregar               |

### Relaciones entre tablas

```
cliente
  └─< solicitud_cliente
        └─< item_solicitud
        └─── cotizacion
                └─── pedido_confirmado
                        ├─── pago_inicial
                        ├─── informacion_tecnica
                        ├─── asignacion_produccion
                        │         └─< avance_produccion
                        ├─── inspeccion_calidad
                        ├─── embalaje_pedido
                        ├─── entrega_pedido
                        └─── pago_final

stock_madera ──< movimiento_madera
proveedor    ──< movimiento_madera
```

### Ejemplo de entidad JPA

```java
@Entity
@Table(name = "pedido_confirmado")
public class PedidoConfirmado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne                          // Relación: muchos pedidos tienen una cotización
    @JoinColumn(name = "cotizacion_id")
    private Cotizacion cotizacion;

    private String estado;              // PENDIENTE_PAGO, PAGO_CONFIRMADO, etc.

    private LocalDate fechaEntregaEstimada;
}
```

Hibernate traduce esto a SQL automáticamente:
```sql
CREATE TABLE pedido_confirmado (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cotizacion_id BIGINT,
    estado VARCHAR(255),
    fecha_entrega_estimada DATE,
    FOREIGN KEY (cotizacion_id) REFERENCES cotizacion(id)
);
```

---

## 7. Configuración Completa — application.properties

```properties
# ── Base de datos MySQL ──────────────────────────────────────────
spring.datasource.url=jdbc:mysql://localhost:3306/carpinteriadb?useSSL=false&serverTimezone=America/La_Paz
spring.datasource.username=root
spring.datasource.password=Carpinteria2025!
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# ── Hibernate / JPA ──────────────────────────────────────────────
# update: crea tablas si no existen, agrega columnas si faltan
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=false

# ── Thymeleaf ────────────────────────────────────────────────────
# false: los cambios en HTML se ven sin reiniciar el servidor
spring.thymeleaf.cache=false

# ── MVC ──────────────────────────────────────────────────────────
# Permite enviar DELETE y PUT desde formularios HTML con _method
spring.mvc.hiddenmethod.filter.enabled=true

# ── Servidor ─────────────────────────────────────────────────────
server.port=8080

# ── IA — Google Gemini API ───────────────────────────────────────
# Obtener key gratuita en: https://aistudio.google.com
gemini.api.key=TU_KEY_AQUI
```

---

## 8. Flujo Completo del Sistema

El sistema guía al usuario en un proceso de 8 pasos. Cada formulario muestra un **stepper** en la parte superior indicando el paso actual y los completados.

```
1. Solicitud → 2. Cotización → 3. Pedido → 4. Pago 50% →
5. Producción → 6. Calidad → 7. Entrega → 8. Pago Final
```

**Paso 1 — Solicitud** (`/solicitudes/nueva`)  
El cliente describe los muebles que necesita (tipo, cantidad, dimensiones, material).

**Paso 2 — Cotización** (`/cotizaciones/nueva`)  
Se calcula el precio: materiales + mano de obra = total. El cliente puede aceptar o rechazar. Si acepta → pasa al Paso 3.

**Paso 3 — Pedido** (`/pedidos/nuevo`)  
Se confirma el pedido con fecha de entrega estimada. Estado: `PENDIENTE_PAGO`.

**Paso 4 — Pago Inicial** (`/pagos/nuevo`)  
El cliente paga el 50% del total. El sistema cambia el estado automáticamente a `PAGO_CONFIRMADO`.

**Paso 5 — Producción** (`/produccion/nuevo`)  
Se asigna el pedido a un operario. Se registran avances por etapa: Corte → Ensamble → Lijado → Pintura/Acabado → Terminado. Estado: `EN_PRODUCCION`.

**Paso 6 — Inspección de Calidad** (`/calidad/nueva`)  
Se inspecciona el producto terminado. Si Aprobado → estado `LISTO_ENTREGA`. Si Retrabajo → vuelve a producción.

**Paso 7 — Embalaje + Entrega**  
Embalaje (`/embalaje/nuevo`) → Entrega al cliente (`/entregas/nueva`). El sistema registra quién recibió el producto. Estado: `ENTREGADO`.

**Paso 8 — Pago Final** (`/pagos-final/nuevo`)  
El cliente paga el 50% restante. El sistema cierra el pedido: estado → `CERRADO`.

**Retomar el flujo:** desde `/pedidos`, cada fila muestra un botón de acción contextual según el estado actual del pedido.

---

## 9. API — Convenciones REST

| Acción             | Método HTTP | Ruta de ejemplo            |
|--------------------|-------------|----------------------------|
| Listar todos       | GET         | `/clientes`                |
| Ver formulario     | GET         | `/clientes/nuevo`          |
| Guardar (crear)    | POST        | `/clientes`                |
| Editar (formulario)| GET         | `/clientes/editar/{id}`    |
| Guardar (editar)   | POST        | `/clientes` (con id oculto)|
| Eliminar           | DELETE      | `/clientes/{id}`           |

Como los formularios HTML solo soportan GET y POST, el método DELETE se envía con un campo oculto:

```html
<form th:action="@{/clientes/{id}(id=${c.id})}" method="post">
    <input type="hidden" name="_method" value="DELETE">
    <button type="submit">Eliminar</button>
</form>
```

Spring Boot intercepta este campo y lo trata como un `DELETE` real gracias a `HiddenHttpMethodFilter` (habilitado con `spring.mvc.hiddenmethod.filter.enabled=true`).

---

## 10. Funcionalidades por Módulo

### Clientes — `/clientes`
- Registrar, editar y eliminar clientes (nombre, CI, teléfono, correo, dirección)
- Ver historial completo de solicitudes por cliente

### Solicitudes — `/solicitudes`
- Registrar solicitudes con múltiples artículos (tipo, cantidad, material, dimensiones)
- Estados: Pendiente / En Proceso / Cotizada / Cancelada
- Botón "Cotizar →" para ir directamente al formulario de cotización

### Cotizaciones — `/cotizaciones`
- Precio de materiales + mano de obra (total calculado automáticamente en la página)
- **Sugerencia IA:** botón "✦ Sugerir precios con IA" en el formulario — llama a `GET /api/ai/cotizacion/sugerir?solicitudId={id}`, que envía los artículos de la solicitud y el historial de cotizaciones aceptadas a Gemini y devuelve precios sugeridos con justificación
- Estados: Pendiente / Enviada / Aceptada / Rechazada
- Aceptar o rechazar directamente desde la lista

### Pedidos — `/pedidos`
- Confirmar pedido a partir de una cotización aceptada
- Cancelar pedidos activos
- Botón de acción contextual por estado (pagar, asignar, inspeccionar, entregar)

### Pagos Iniciales — `/pagos`
- Registrar el anticipo del 50%
- El sistema actualiza el estado del pedido automáticamente

### Información Técnica — `/tecnica`
- Especificaciones exactas: tipo de madera, acabado, dimensiones, color
- Vinculado a la solicitud del cliente

### Producción — `/produccion`
- Asignar pedido a un operario del taller
- Avances: Corte / Ensamble / Lijado / Pintura / Terminado
- Registrar porcentaje de avance y observaciones

### Inventario — `/inventario`
- Stock actual con alertas visuales de stock mínimo
- Registrar ingresos de madera (desde proveedor)
- Registrar consumos (vinculados a pedido)
- Registrar reposiciones
- Historial completo de movimientos
- **Predicción IA:** botón "✦ Analizar stock" — llama a `GET /api/ai/inventario/prediccion`, que envía el stock actual y los consumos de los últimos 90 días a Gemini y devuelve para cada tipo de madera los días estimados hasta agotarse y un nivel: **Urgente** (≤7 días), **Pronto** (8–30 días), **Estable** (>30 días) o **Sin datos**

### Proveedores — `/proveedores`
- Registrar y editar proveedores de madera

### Calidad — `/calidad`
- Resultado: Aprobado / Retrabajo / Rechazado
- Observaciones detalladas

### Embalaje — `/embalaje`
- Registrar embalaje del pedido listo para despachar

### Entregas — `/entregas`
- Confirmar entrega: nombre del receptor, CI, fecha, observaciones
- El pedido pasa a estado `ENTREGADO`

### Pago Final — `/pagos-final`
- Registrar el 50% restante
- El pedido pasa a estado `CERRADO`

### Reportes — `/reportes`
- Filtrar por período (fecha desde / hasta)
- Pedidos del período con estado y total
- **KPI facturación:** suma de `montoPagado` de los pagos finales del período (calculada en el controller)
- Consumo de madera agrupado por tipo
- Clientes con más solicitudes

---

## 11. Cómo Ver la Base de Datos

### MySQL Workbench

1. Abrir MySQL Workbench
2. Conectar a `localhost:3306` con usuario `root` y contraseña `Carpinteria2025!`
3. Seleccionar la base `carpinteriadb`
4. Las tablas aparecen en el panel izquierdo

### VS Code con Database Client

1. Instalar la extensión **Database Client** en VS Code
2. Ícono de cilindro en la barra lateral → **+** New Connection → MySQL
3. Completar:
   - Host: `localhost` · Port: `3306`
   - Username: `root` · Password: `Carpinteria2025!`
   - Database: `carpinteriadb`
4. Clic en **Connect** → ver tablas y ejecutar consultas

### Consultas SQL útiles

```sql
-- Ver todos los pedidos con su estado actual
SELECT p.id, s.nombre_cliente, p.estado
FROM pedido_confirmado p
JOIN cotizacion c ON p.cotizacion_id = c.id
JOIN solicitud_cliente s ON c.solicitud_id = s.id;

-- Ver stock de madera con alertas
SELECT tipo_madera, cantidad_disponible, stock_minimo,
       CASE WHEN cantidad_disponible <= stock_minimo THEN 'CRÍTICO' ELSE 'OK' END AS alerta
FROM stock_madera;

-- Pedidos del mes actual
SELECT p.id, s.nombre_cliente, p.estado, c.precio_total
FROM pedido_confirmado p
JOIN cotizacion c ON p.cotizacion_id = c.id
JOIN solicitud_cliente s ON c.solicitud_id = s.id
WHERE MONTH(p.fecha_pedido) = MONTH(NOW());
```

---

## 12. Repositorio en GitHub

**https://github.com/LetiziaOrtizVasq/Sistema-de-Gestion-Carpinteria**

---

## 13. Integración con Inteligencia Artificial

El sistema integra **Google Gemini API** (modelo `gemini-2.5-flash`) para dos funciones de asistencia. La integración está en el paquete `com.carpinteria.ai` dentro de `carpinteria-app`.

### Arquitectura de la integración

```
Navegador
    │  fetch('/api/ai/cotizacion/sugerir?solicitudId=X')
    ▼
CotizacionAiController  (o InventarioAiController)
    │  construye el prompt con datos de la BD
    ▼
ClaudeService.preguntar(prompt)
    │  POST https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent
    ▼
Google Gemini API
    │  responde con JSON
    ▼
Controller  →  devuelve JSON al navegador
    ▼
JavaScript en el template  →  rellena los campos del formulario
```

### ClaudeService

Cliente HTTP que llama a la API de Gemini usando `RestClient` (Spring 6). Configura un `SSLContext` permisivo para compatibilidad con el JDK local.

```java
@Service
public class ClaudeService {
    @Value("${gemini.api.key}")
    private String apiKey;

    public String preguntar(String prompt) {
        // POST a Gemini con el prompt
        // Devuelve el texto de la respuesta
    }
}
```

### Endpoint: sugerencia de cotización

`GET /api/ai/cotizacion/sugerir?solicitudId={id}`

El prompt incluye:
- Artículos de la solicitud (tipo, cantidad, material, dimensiones)
- Últimas 15 cotizaciones aceptadas como referencia de precios

Respuesta JSON:
```json
{
  "precioMateriales": 1250.00,
  "precioManoObra": 800.00,
  "justificacion": "Cedro es madera de alto valor..."
}
```

### Endpoint: predicción de stock

`GET /api/ai/inventario/prediccion`

El prompt incluye:
- Stock actual de cada tipo de madera (disponible, mínimo, unidad)
- Historial de consumos de los últimos 90 días

Respuesta JSON (array):
```json
[
  {
    "stockId": 1,
    "tipoMadera": "Cedro",
    "diasEstimados": 302,
    "nivel": "ESTABLE",
    "recomendacion": "Stock adecuado para consumo actual."
  }
]
```

Niveles posibles: `URGENTE` (≤7 días o ya crítico), `PRONTO` (8–30 días), `ESTABLE` (>30 días), `SIN_DATOS` (sin consumos registrados).

---

## 14. Notas Técnicas

- Las **fechas** se generan automáticamente en el servidor (no las ingresa el usuario).
- Los **datos persisten** en MySQL entre reinicios — no se pierden al apagar la PC.
- La **primera vez** que se inicia la app, Hibernate crea todas las tablas automáticamente.
- Con `spring.thymeleaf.cache=false` se pueden editar templates HTML y ver los cambios recargando el navegador sin reiniciar el servidor.
- Los **iconos SVG** están embebidos directamente en los archivos HTML — la interfaz funciona sin conexión a internet (excepto la fuente Inter que carga desde Google Fonts).
- El proyecto usa **HikariCP** como pool de conexiones a MySQL (incluido por defecto en Spring Boot).
- Cada módulo Maven tiene su propio `pom.xml` y puede integrarse de forma independiente a otro proyecto.
