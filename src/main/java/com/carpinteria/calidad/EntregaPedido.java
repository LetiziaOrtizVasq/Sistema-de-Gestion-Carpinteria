package com.carpinteria.calidad;

import com.carpinteria.ventas.PedidoConfirmado;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/** CU-18: Confirmar entrega del pedido al cliente. */
@Entity
@Table(name = "entrega_pedido")
public class EntregaPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El pedido es obligatorio")
    @OneToOne(optional = false)
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    private PedidoConfirmado pedido;

    @Column(nullable = false)
    private LocalDate fechaEntrega;

    @NotBlank(message = "El nombre de quien recibe es obligatorio")
    @Column(nullable = false)
    private String recibidoPor;

    private String ci;

    @Column(length = 400)
    private String observaciones;

    // ENTREGADO | CON_OBSERVACIONES
    @Column(nullable = false)
    private String estado;

    public EntregaPedido() {
        this.fechaEntrega = LocalDate.now();
        this.estado = "ENTREGADO";
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PedidoConfirmado getPedido() { return pedido; }
    public void setPedido(PedidoConfirmado pedido) { this.pedido = pedido; }

    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public String getRecibidoPor() { return recibidoPor; }
    public void setRecibidoPor(String recibidoPor) { this.recibidoPor = recibidoPor; }

    public String getCi() { return ci; }
    public void setCi(String ci) { this.ci = ci; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
