package com.carpinteria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cotizacion")
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con la solicitud de origen
    @ManyToOne(optional = false)
    @JoinColumn(name = "solicitud_id", nullable = false)
    private SolicitudCliente solicitud;

    @NotNull(message = "El precio de materiales es obligatorio")
    @Positive(message = "Debe ser un valor positivo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioMateriales;

    @NotNull(message = "El precio de mano de obra es obligatorio")
    @Positive(message = "Debe ser un valor positivo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioManoObra;

    // Se calcula automáticamente: materiales + mano de obra
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioTotal;

    @Column(length = 500)
    private String observaciones;

    @NotNull
    @Column(nullable = false)
    private LocalDate fechaCotizacion;

    @NotNull(message = "La fecha de validez es obligatoria")
    @Column(nullable = false)
    private LocalDate validaHasta;

    @Column(nullable = false)
    private String estado;

    public Cotizacion() {
        this.fechaCotizacion = LocalDate.now();
        this.validaHasta = LocalDate.now().plusDays(15);
        this.estado = "PENDIENTE";
    }

    // Calcula el total automáticamente antes de guardar
    @PrePersist
    @PreUpdate
    public void calcularTotal() {
        if (precioMateriales != null && precioManoObra != null) {
            this.precioTotal = precioMateriales.add(precioManoObra);
        }
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public SolicitudCliente getSolicitud() { return solicitud; }
    public void setSolicitud(SolicitudCliente solicitud) { this.solicitud = solicitud; }

    public BigDecimal getPrecioMateriales() { return precioMateriales; }
    public void setPrecioMateriales(BigDecimal precioMateriales) { this.precioMateriales = precioMateriales; }

    public BigDecimal getPrecioManoObra() { return precioManoObra; }
    public void setPrecioManoObra(BigDecimal precioManoObra) { this.precioManoObra = precioManoObra; }

    public BigDecimal getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(BigDecimal precioTotal) { this.precioTotal = precioTotal; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public LocalDate getFechaCotizacion() { return fechaCotizacion; }
    public void setFechaCotizacion(LocalDate fechaCotizacion) { this.fechaCotizacion = fechaCotizacion; }

    public LocalDate getValidaHasta() { return validaHasta; }
    public void setValidaHasta(LocalDate validaHasta) { this.validaHasta = validaHasta; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
