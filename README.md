# Sistema de Gestión para Carpintería

Sistema web para la gestión integral de una carpintería artesanal: clientes, solicitudes, cotizaciones, pedidos, producción, inventario y entregas.

**Proyecto académico — Universidad Privada de Santa Cruz (UPSA)**  
**Integrantes:** Letizia Camila Ortiz Vásquez · Mariana Toledo

---

## Tecnologías utilizadas

| Componente       | Tecnología                        |
|------------------|-----------------------------------|
| Backend          | Java 21 + Spring Boot 3.2.5       |
| Vistas           | Thymeleaf (HTML server-side)      |
| Persistencia     | Spring Data JPA + Hibernate       |
| Base de datos    | MySQL 8.4                         |
| Validaciones     | Jakarta Validation                |
| Build            | Apache Maven 3.9 (multi-módulo)   |

---

## Requisitos previos

- Java 21 (ubicado en `C:\Users\ortiz\Java\jdk-21.0.10+7`)
- Maven 3.9 (ubicado en `C:\Users\ortiz\Maven\apache-maven-3.9.14`)
- MySQL 8.4 corriendo en el puerto 3306
  - Base de datos: `carpinteriadb`
  - Usuario: `root` · Contraseña: `Carpinteria2025!`

---

## Cómo ejecutar el proyecto

### Opción 1 — Script automático (recomendado, Windows)

Doble clic en **`ejecutar.bat`** en la raíz del proyecto.  
El script verifica si MySQL está activo, lo inicia si hace falta, y luego levanta la aplicación.

### Opción 2 — Manual desde terminal

```bash
# Desde la raíz del proyecto:
mvn -pl carpinteria-app spring-boot:run
```

Una vez iniciado, abrir en el navegador: **http://localhost:8080**

---

## Arquitectura modular (estilo SAP)

El proyecto está organizado como un **proyecto Maven multi-módulo**, donde cada módulo es completamente independiente y puede exportarse e integrarse a otro sistema sin modificar el resto.

```
Sistema-de-Gestion-Carpinteria/
│
├── carpinteria-common/       → Recursos compartidos (navegación, estilos CSS)
│                               Sin dependencias externas
│
├── carpinteria-clientes/     → Gestión de clientes y solicitudes
│                               Requiere: common
│
├── carpinteria-ventas/       → Cotizaciones y pedidos confirmados
│                               Requiere: common, clientes
│
├── carpinteria-pagos/        → Pagos iniciales y pagos finales
│                               Requiere: common, ventas
│
├── carpinteria-inventario/   → Stock de madera, proveedores y movimientos
│                               Requiere: common, ventas
│
├── carpinteria-produccion/   → Especificaciones técnicas, asignación y avances
│                               Requiere: common, clientes, ventas
│
├── carpinteria-calidad/      → Inspección de calidad, embalaje y entregas
│                               Requiere: common, ventas, pagos
│
├── carpinteria-reportes/     → Reportes de gestión del período
│                               Requiere: common, clientes, pagos, inventario
│
└── carpinteria-app/          → Módulo de arranque: integra todos los módulos
                                Contiene: CarpinteriaApplication + application.properties
```

> **¿Cómo exportar un módulo?**  
> Si otro proyecto necesita, por ejemplo, solo el módulo de inventario, basta con copiar la carpeta `carpinteria-inventario/` y agregar su dependencia en el `pom.xml` del proyecto destino junto con sus dependencias declaradas (`common` y `ventas`).

---

## Funcionalidades implementadas

### Clientes (`/clientes`)
- Registrar, editar y eliminar clientes
- Ver historial completo de solicitudes por cliente

### Solicitudes (`/solicitudes`)
- Registrar solicitudes con múltiples artículos por pedido (tipo, cantidad, material, dimensiones)
- Editar y eliminar solicitudes

### Cotizaciones (`/cotizaciones`)
- Crear cotizaciones vinculadas a una solicitud
- Calcular precio total (materiales + mano de obra)
- Aprobar o rechazar cotizaciones

### Pedidos (`/pedidos`)
- Confirmar pedidos a partir de cotizaciones aceptadas
- Duplicar, cancelar y cambiar estado de pedidos
- Máquina de estados: `PENDIENTE_PAGO → PAGO_CONFIRMADO → EN_PRODUCCION → LISTO_ENTREGA → ENTREGADO → CERRADO`

### Pagos Iniciales (`/pagos`)
- Registrar el pago inicial al confirmar el pedido

### Información Técnica (`/tecnica`)
- Registrar especificaciones exactas del producto: tipo de madera, dimensiones, acabado, color

### Producción (`/produccion`)
- Asignar pedidos a operarios del taller
- Registrar avances por etapa: Corte → Ensamble → Lijado → Pintura → Terminado

### Inventario (`/inventario`)
- Consultar stock de madera con alertas de mínimo
- Registrar ingresos, consumos y reposiciones
- Gestión de proveedores (`/proveedores`)

### Calidad y Entrega
- Inspección de calidad (`/calidad`)
- Embalaje del pedido (`/embalaje`)
- Confirmación de entrega al cliente (`/entregas`)
- Registro de pago final (`/pagos-final`)

### Reportes (`/reportes`)
- Pedidos del período seleccionado
- Consumo de madera por tipo
- Clientes más frecuentes

---

## Flujo principal del sistema

```
Solicitud del cliente
    → Cotización (aprobada)
        → Pedido Confirmado
            → Pago Inicial (50%)
                → Especificaciones Técnicas
                    → Asignación a Producción
                        → Avances por etapa
                            → Inspección de Calidad
                                → Embalaje
                                    → Entrega
                                        → Pago Final → CERRADO
```

---

## Notas de diseño

- Las **fechas se generan automáticamente** en el servidor; no se muestran en los formularios.
- Los **datos persisten** en MySQL entre reinicios (no es base de datos en memoria).
- Cada módulo Maven incluye su propio código Java **y** sus propias vistas Thymeleaf, lo que permite distribuirlos de forma independiente.
- Las APIs siguen convenciones REST: sin verbos en las URLs, con `DELETE /{id}` usando el filtro `HiddenHttpMethodFilter` de Spring.
