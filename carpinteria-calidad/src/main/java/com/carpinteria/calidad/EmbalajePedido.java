package com.carpinteria.calidad;

import com.carpinteria.ventas.PedidoConfirmado;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/** CU-17: Registrar embalaje de pedido. */
@Entity
@Table(name = "embalaje_pedido")
public class EmbalajePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El pedido es obligatorio")
    @OneToOne(optional = false)
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    private PedidoConfirmado pedido;

    private String tipoEmbalaje;
    private String responsable;

    @Column(length = 400)
    private String observaciones;

    @Column(nullable = false)
    private LocalDate fechaEmbalaje;

    // PENDIENTE | EMBALADO | LISTO_ENTREGA
    @Column(nullable = false)
    private String estado;

    public EmbalajePedido() {
        this.fechaEmbalaje = LocalDate.now();
        this.estado = "EMBALADO";
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PedidoConfirmado getPedido() { return pedido; }
    public void setPedido(PedidoConfirmado pedido) { this.pedido = pedido; }

    public String getTipoEmbalaje() { return tipoEmbalaje; }
    public void setTipoEmbalaje(String tipoEmbalaje) { this.tipoEmbalaje = tipoEmbalaje; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public LocalDate getFechaEmbalaje() { return fechaEmbalaje; }
    public void setFechaEmbalaje(LocalDate fechaEmbalaje) { this.fechaEmbalaje = fechaEmbalaje; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
