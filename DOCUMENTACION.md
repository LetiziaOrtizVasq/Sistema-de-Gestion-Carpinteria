# Documentación del Sistema — Gestión para Carpintería

**Universidad Privada de Santa Cruz — UPSA**  
**Materia:** Sistemas de Información / Programación  
**Integrantes:** Letizia Camila Ortiz Vásquez · Mariana Toledo  

---

## 1. Descripción del Sistema

El Sistema de Gestión para Carpintería es una aplicación web que permite administrar de forma integral los procesos de una carpintería artesanal. Cubre desde el primer contacto con el cliente hasta la entrega final del producto y el cobro.

El sistema automatiza los siguientes procesos:
- Registro de clientes y sus solicitudes de muebles
- Generación y aprobación de cotizaciones
- Confirmación de pedidos y cobro de adelantos
- Planificación y seguimiento de la producción
- Control de stock de madera e insumos
- Inspección de calidad, embalaje y entrega
- Reportes de gestión

---

## 2. Tecnologías Utilizadas

| Componente        | Tecnología                          | Versión   |
|-------------------|-------------------------------------|-----------|
| Lenguaje          | Java                                | 21        |
| Framework backend | Spring Boot                         | 3.2.5     |
| Vistas (frontend) | Thymeleaf (HTML dinámico)           | 3.x       |
| Persistencia      | Spring Data JPA + Hibernate         | 6.4       |
| Base de datos     | MySQL                               | 8.4       |
| Validaciones      | Jakarta Validation (Bean Validation)| 3.x       |
| Gestor de build   | Apache Maven (multi-módulo)         | 3.9.14    |
| Servidor web      | Apache Tomcat (embebido)            | 10.1      |
| Control de versiones | Git + GitHub                     | —         |

---

## 3. Requisitos para Correr el Sistema

### En la computadora deben estar instalados:

| Herramienta | Ubicación en esta PC               |
|-------------|------------------------------------|
| Java 21 JDK | `C:\Users\ortiz\Java\jdk-21.0.10+7`|
| Maven 3.9   | `C:\Users\ortiz\Maven\apache-maven-3.9.14` |
| MySQL 8.4   | `C:\Program Files\MySQL\MySQL Server 8.4` |

### Base de datos:
- **Servidor:** localhost, puerto 3306
- **Base de datos:** `carpinteriadb`
- **Usuario:** `root`
- **Contraseña:** `Carpinteria2025!`

> La base de datos se crea automáticamente la primera vez que se inicia el sistema. No hay que ejecutar ningún script SQL.

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
2. Abrir la terminal integrada: `Ctrl + `` ` ``
3. Asegurarse de que MySQL esté corriendo
4. Ejecutar:

```bash
mvn -pl carpinteria-app -am spring-boot:run
```

5. Esperar el mensaje `Started CarpinteriaApplication`
6. Abrir en el navegador: **http://localhost:8080**

---

### Opción C — Desde VS Code con extensión Java

Si tenés instalado el Extension Pack for Java en VS Code:

1. Abrir el archivo `carpinteria-app/src/main/java/com/carpinteria/CarpinteriaApplication.java`
2. Hacer clic en el botón **▶ Run** que aparece arriba del método `main`
3. Abrir en el navegador: **http://localhost:8080**

---

## 5. Ver la Base de Datos desde VS Code

Usando la extensión **Database Client** (ya instalada en VS Code):

1. Hacer clic en el ícono de base de datos (cilindro) en la barra lateral izquierda de VS Code
2. Hacer clic en **`+`** (New Connection)
3. Seleccionar tipo: **MySQL**
4. Completar los campos:
   - **Host:** `localhost`
   - **Port:** `3306`
   - **Username:** `root`
   - **Password:** `Carpinteria2025!`
   - **Database:** `carpinteriadb`
5. Hacer clic en **Connect**
6. Se mostrarán todas las tablas con sus datos

---

## 6. Arquitectura del Sistema (Estilo SAP Modular)

El proyecto está organizado como un **proyecto Maven multi-módulo**. Cada módulo es independiente: tiene su propio código Java, sus vistas HTML y su archivo de configuración (`pom.xml`). Si se necesita integrar solo una parte del sistema a otro proyecto, basta con copiar el módulo correspondiente.

```
Sistema-de-Gestion-Carpinteria/
│
├── carpinteria-common/         Recursos compartidos
│   └── nav.html, estilo.css
│
├── carpinteria-clientes/       Módulo de clientes y solicitudes
│   └── Java + vistas HTML
│
├── carpinteria-ventas/         Módulo de cotizaciones y pedidos
│   └── Java + vistas HTML
│
├── carpinteria-pagos/          Módulo de pagos (inicial y final)
│   └── Java + vistas HTML
│
├── carpinteria-inventario/     Módulo de stock y proveedores
│   └── Java + vistas HTML
│
├── carpinteria-produccion/     Módulo de producción y avances
│   └── Java + vistas HTML
│
├── carpinteria-calidad/        Módulo de calidad y entregas
│   └── Java + vistas HTML
│
├── carpinteria-reportes/       Módulo de reportes
│   └── Java + vistas HTML
│
└── carpinteria-app/            Módulo de arranque
    └── CarpinteriaApplication.java + application.properties
```

### Dependencias entre módulos

```
common  ←  clientes  ←  ventas  ←  pagos
                    ↑             ↑
                    produccion    calidad
                    inventario
                    ↓
                    reportes
```

> `←` significa "requiere". Por ejemplo: `ventas` requiere `clientes`.

### Estructura interna de cada módulo

Cada módulo sigue el patrón **MVC por capas**:

```
com.carpinteria.MODULO/
├── Entidad.java              ← tabla en la base de datos (@Entity)
├── EntidadRepository.java    ← acceso a datos (JPA)
├── EntidadService.java       ← lógica de negocio
└── EntidadController.java    ← endpoints HTTP (@Controller)

resources/templates/MODULO/
├── lista.html                ← vista de listado
└── formulario.html           ← vista de crear/editar
```

---

## 7. Funcionalidades del Sistema (Casos de Uso)

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
- Ingresar precio de materiales y mano de obra (el total se calcula automáticamente)
- Aprobar o rechazar cotización

### Pedidos — `/pedidos`
- Confirmar pedido desde una cotización aprobada
- Duplicar pedidos existentes
- Cancelar pedidos
- Seguimiento de estados del pedido:

```
PENDIENTE_PAGO → PAGO_CONFIRMADO → EN_PRODUCCION → LISTO_ENTREGA → ENTREGADO → CERRADO
```

### Pagos Iniciales — `/pagos`
- Registrar el pago del 50% al confirmar el pedido
- El sistema cambia automáticamente el estado del pedido a `PAGO_CONFIRMADO`

### Información Técnica — `/tecnica`
- Registrar especificaciones exactas: tipo de madera, acabado, dimensiones, color
- Vinculado a la solicitud del cliente

### Producción — `/produccion`
- Asignar pedido a un operario del taller
- Registrar avances por etapa:
  - Corte → Ensamble → Lijado → Pintura / Acabado → Terminado
- Indicar porcentaje de avance y si la etapa está completada

### Inventario — `/inventario`
- Consultar stock actual de madera con alertas de stock mínimo
- Registrar ingresos de madera
- Registrar consumos de madera (descontando del stock)
- Registrar reposiciones

### Proveedores — `/proveedores`
- Registrar y gestionar proveedores de madera

### Calidad — `/calidad`
- Registrar inspección de calidad del producto terminado
- Resultado: aprobado o con observaciones

### Embalaje — `/embalaje`
- Registrar el embalaje del pedido listo para entregar

### Entregas — `/entregas`
- Confirmar la entrega al cliente
- El sistema cambia el estado del pedido a `ENTREGADO`

### Pago Final — `/pagos-final`
- Registrar el pago restante (50%)
- El sistema cambia el estado del pedido a `CERRADO`

### Reportes — `/reportes`
- Pedidos del período (por fechas)
- Consumo de madera por tipo
- Clientes más frecuentes

---

## 8. Flujo Completo del Sistema — Paso a Paso

El sistema guía al usuario a través de un flujo de 8 pasos. Cada formulario muestra una barra de progreso en la parte superior indicando en qué paso se encuentra. El flujo es **no obligatorio**: se puede abandonar en cualquier momento y retomar después desde la lista de pedidos usando el botón de acción correspondiente al estado actual.

```
┌─────────────────────────────────────────────────────────────────────────────┐
│  Paso 1      Paso 2      Paso 3      Paso 4      Paso 5      Paso 6-7-8    │
│  Solicitud → Cotización → Pedido → Pago 50% → Producción → Calidad/Entrega │
└─────────────────────────────────────────────────────────────────────────────┘
```

**Paso 1 — Solicitud** (`/solicitudes/nueva`)
- El cliente describe los muebles que quiere
- Al guardar → pasa automáticamente al Paso 2

**Paso 2 — Cotización** (`/cotizaciones/nueva`)
- Se calculan materiales + mano de obra
- Al **Aceptar** la cotización → pasa automáticamente al Paso 3
- Si se rechaza → el proceso termina aquí (el cliente puede volver cuando quiera)

**Paso 3 — Pedido** (`/pedidos/nuevo`)
- Se confirma el pedido con fecha de entrega estimada
- Al guardar → pasa automáticamente al Paso 4

**Paso 4 — Pago Inicial** (`/pagos/nuevo`)
- El cliente paga el 50% del total
- El sistema actualiza el estado del pedido a `PAGO_CONFIRMADO`
- Al guardar → pasa automáticamente al Paso 5

**Paso 5 — Producción** (`/produccion/nuevo`)
- Se asigna el pedido a un operario del taller
- Al guardar → abre directamente los avances de producción
- El operario registra avances por etapa: Corte → Ensamble → Lijado → Pintura → Terminado

**Paso 6 — Inspección de Calidad** (`/calidad/nueva`)
- Se inspecciona el producto terminado
- Si **Aprobado** → pasa automáticamente al Paso 7 (Embalaje)
- Si **Retrabajo/Rechazado** → vuelve a producción (el ciclo se repite)

**Paso 7 — Embalaje + Entrega**
- Embalaje (`/embalaje/nuevo`): se prepara el producto para envío → pasa a Entrega
- Entrega (`/entregas/nueva`): se confirma que el cliente recibió el producto → pasa al Paso 8

**Paso 8 — Pago Final** (`/pagos-final/nuevo`)
- El cliente paga el 50% restante
- El sistema cierra el pedido: estado → `CERRADO`

**Estados del pedido a lo largo del flujo:**
```
PENDIENTE_PAGO → PAGO_CONFIRMADO → EN_PRODUCCION → LISTO_ENTREGA → ENTREGADO → CERRADO
     Paso 3           Paso 4           Paso 5           Paso 6          Paso 7      Paso 8
```

**Retomar el flujo en cualquier momento:**  
Desde `/pedidos`, cada fila muestra un botón de acción contextual según el estado actual del pedido, permitiendo continuar exactamente donde se dejó.

---

## 9. Convenciones de la API (REST)

El sistema sigue convenciones REST:

- Las URLs **no contienen verbos** (sin `/guardar`, sin `/eliminar`)
- Se usan los métodos HTTP correctos:

| Acción         | Método HTTP | Ejemplo de URL         |
|----------------|-------------|------------------------|
| Ver listado    | GET         | `/clientes`            |
| Ver formulario | GET         | `/clientes/nuevo`      |
| Guardar        | POST        | `/clientes`            |
| Editar         | GET         | `/clientes/editar/{id}`|
| Eliminar       | DELETE      | `/clientes/{id}`       |

> Como los formularios HTML solo soportan GET y POST, el método DELETE se envía con un campo oculto `<input name="_method" value="DELETE">` y el filtro `HiddenHttpMethodFilter` de Spring lo convierte automáticamente.

---

## 10. Repositorio en GitHub

El código fuente está disponible en:

**https://github.com/LetiziaOrtizVasq/Sistema-de-Gestion-Carpinteria**

---

## 11. Notas Finales

- Las **fechas** se generan automáticamente en el servidor. No se muestran en los formularios y no las ingresa el usuario.
- Los **datos persisten** en MySQL entre reinicios (no se pierden al apagar la computadora).
- La primera vez que se inicia el sistema, Hibernate crea todas las tablas automáticamente (`ddl-auto=update`).
- Cada módulo Maven tiene en su `pom.xml` la lista de sus dependencias, lo que permite exportarlo e integrarlo a otro proyecto de forma independiente.
