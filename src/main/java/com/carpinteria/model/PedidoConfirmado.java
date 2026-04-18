package com.carpinteria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "pedido_confirmado")
public class PedidoConfirmado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La cotización es obligatoria")
    @OneToOne(optional = false)
    @JoinColumn(name = "cotizacion_id", nullable = false, unique = true)
    private Cotizacion cotizacion;

    @Column(nullable = false)
    private LocalDate fechaConfirmacion;

    @NotNull(message = "La fecha de entrega estimada es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaEntregaEstimada;

    @Column(length = 500)
    private String observaciones;

    // PENDIENTE_PAGO → EN_PRODUCCION → FINALIZADO → ENTREGADO
    @Column(nullable = false)
    private String estado;

    public PedidoConfirmado() {
        this.fechaConfirmacion = LocalDate.now();
        this.estado = "PENDIENTE_PAGO";
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cotizacion getCotizacion() { return cotizacion; }
    public void setCotizacion(Cotizacion cotizacion) { this.cotizacion = cotizacion; }

    public LocalDate getFechaConfirmacion() { return fechaConfirmacion; }
    public void setFechaConfirmacion(LocalDate fechaConfirmacion) { this.fechaConfirmacion = fechaConfirmacion; }

    public LocalDate getFechaEntregaEstimada() { return fechaEntregaEstimada; }
    public void setFechaEntregaEstimada(LocalDate fechaEntregaEstimada) { this.fechaEntregaEstimada = fechaEntregaEstimada; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
