package com.carpinteria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * CU-12: Asignar pedido a producción.
 * CU-15: El estado actual del pedido se consulta desde esta entidad y sus avances.
 */
@Entity
@Table(name = "asignacion_produccion")
public class AsignacionProduccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El pedido es obligatorio")
    @OneToOne(optional = false)
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    private PedidoConfirmado pedido;

    @NotBlank(message = "El operario es obligatorio")
    @Column(nullable = false)
    private String operario;

    @Column(nullable = false)
    private LocalDate fechaAsignacion;

    private LocalDate fechaInicioEstimada;

    @Column(length = 400)
    private String observaciones;

    // ASIGNADO | EN_PROCESO | COMPLETADO
    @Column(nullable = false)
    private String estado;

    public AsignacionProduccion() {
        this.fechaAsignacion = LocalDate.now();
        this.estado = "ASIGNADO";
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PedidoConfirmado getPedido() { return pedido; }
    public void setPedido(PedidoConfirmado pedido) { this.pedido = pedido; }

    public String getOperario() { return operario; }
    public void setOperario(String operario) { this.operario = operario; }

    public LocalDate getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDate fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }

    public LocalDate getFechaInicioEstimada() { return fechaInicioEstimada; }
    public void setFechaInicioEstimada(LocalDate fechaInicioEstimada) { this.fechaInicioEstimada = fechaInicioEstimada; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
