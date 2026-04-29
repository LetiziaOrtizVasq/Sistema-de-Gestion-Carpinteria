# Sistema de Gestión para Carpintería

Sistema web para la gestión integral de una carpintería: clientes, solicitudes, cotizaciones, pedidos, producción, inventario y entrega.

**Proyecto académico — UPSA**  
Integrantes: Camila Ortiz, Mariana Toledo

---

## Tecnologías

| Componente       | Tecnología                       |
|------------------|----------------------------------|
| Backend          | Java 21 + Spring Boot 3.2.5      |
| Vistas           | Thymeleaf                        |
| Persistencia     | Spring Data JPA + Hibernate      |
| Base de datos    | H2 (en memoria)                  |
| Validaciones     | Jakarta Validation               |
| Build            | Apache Maven 3.9                 |

---

## Cómo ejecutar

### Requisito previo
Tener Java 21 instalado (o usar el JDK incluido en la carpeta del proyecto).

### Opción 1 — Script automático (Windows)
Doble clic en `ejecutar.bat`

### Opción 2 — Manual
```bash
# Desde la raíz del proyecto:
mvn spring-boot:run
```

Una vez iniciado, abrir: **http://localhost:8080**

### Consola H2 (base de datos)
Disponible en: http://localhost:8080/h2-console  
JDBC URL: `jdbc:h2:mem:carpinteriadb` | Usuario: `sa` | Contraseña: _(vacía)_

> La base de datos es en memoria: los datos se pierden al reiniciar la aplicación.

---

## Estructura de módulos (paquetes)

El proyecto sigue una arquitectura **vertical por módulo** (estilo SAP), donde cada paquete contiene todas las capas (entidad, repositorio, servicio, controlador) de un dominio de negocio específico.

```
com.carpinteria
├── clientes/       — Clientes y solicitudes (CU-01, CU-04, CU-05, CU-07)
├── ventas/         — Cotizaciones y pedidos confirmados (CU-02, CU-03, CU-08)
├── pagos/          — Pago inicial (CU-09) y pago final (CU-19)
├── produccion/     — Info técnica, asignación y avances (CU-06, CU-12–CU-15)
├── inventario/     — Stock de madera, proveedores y movimientos (CU-10, CU-11, CU-20–CU-23)
├── calidad/        — Inspección, embalaje y entrega (CU-16, CU-17, CU-18)
└── reportes/       — Reportes de período (CU-25, CU-26, CU-27)
```

Ventaja: cualquier módulo puede desprenderse de forma independiente sin afectar los demás.

---

## Módulos y funcionalidades

### Clientes (`/clientes`)
- Registrar, editar y buscar clientes
- Historial de solicitudes por cliente

### Solicitudes (`/solicitudes`)
- Registrar solicitudes con múltiples artículos por pedido
- Editar y eliminar solicitudes

### Cotizaciones (`/cotizaciones`)
- Crear cotizaciones vinculadas a solicitudes
- Calcular precio total automáticamente (materiales + mano de obra)
- Aprobar o rechazar cotizaciones

### Pedidos (`/pedidos`)
- Confirmar pedidos desde cotizaciones aceptadas
- Duplicar pedidos, cambiar estado, cancelar

### Pagos Iniciales (`/pagos`)
- Registrar el pago inicial (50%) al confirmar el pedido

### Información Técnica (`/tecnica`)
- Registrar especificaciones técnicas del pedido (maderas, acabado, dimensiones)

### Producción (`/produccion`)
- Asignar pedidos a operarios
- Registrar avances de producción

### Inventario (`/inventario`)
- Consultar stock de madera con alertas de stock mínimo
- Registrar ingresos, consumos y reposiciones de madera
- Gestión de proveedores (`/proveedores`)

### Calidad y Entrega
- Inspección de calidad (`/calidad`)
- Embalaje del pedido (`/embalaje`)
- Confirmación de entrega (`/entregas`)
- Pago final (`/pagos-final`)

### Reportes (`/reportes`)
- Pedidos del período
- Consumo de madera por tipo
- Clientes más frecuentes

---

## Flujo principal del sistema

```
Cliente → Solicitud → Cotización → Pedido Confirmado → Pago Inicial
       → Info Técnica → Producción → Inspección → Embalaje → Entrega → Pago Final
```

---

## Notas de diseño

- Las **fechas se generan automáticamente** en el servidor (no se ingresan manualmente).
- Una solicitud puede contener **múltiples artículos** distintos.
- La arquitectura sigue el patrón **paquete por funcionalidad** para facilitar el mantenimiento y la separación modular.
