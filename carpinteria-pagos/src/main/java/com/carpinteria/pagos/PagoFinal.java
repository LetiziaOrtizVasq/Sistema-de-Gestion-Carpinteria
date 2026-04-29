package com.carpinteria.pagos;

import com.carpinteria.ventas.PedidoConfirmado;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

/** CU-19: Registrar pago final del producto entregado. */
@Entity
@Table(name = "pago_final")
public class PagoFinal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El pedido es obligatorio")
    @OneToOne(optional = false)
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    private PedidoConfirmado pedido;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montoPagado;

    @Column(precision = 10, scale = 2)
    private BigDecimal montoRequerido;

    @NotBlank(message = "El método de pago es obligatorio")
    @Column(nullable = false)
    private String metodoPago;

    private String comprobante;

    @Column(nullable = false)
    private LocalDate fechaPago;

    // PENDIENTE | CONFIRMADO
    @Column(nullable = false)
    private String estado;

    public PagoFinal() {
        this.fechaPago = LocalDate.now();
        this.estado = "CONFIRMADO";
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PedidoConfirmado getPedido() { return pedido; }
    public void setPedido(PedidoConfirmado pedido) { this.pedido = pedido; }

    public BigDecimal getMontoPagado() { return montoPagado; }
    public void setMontoPagado(BigDecimal montoPagado) { this.montoPagado = montoPagado; }

    public BigDecimal getMontoRequerido() { return montoRequerido; }
    public void setMontoRequerido(BigDecimal montoRequerido) { this.montoRequerido = montoRequerido; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getComprobante() { return comprobante; }
    public void setComprobante(String comprobante) { this.comprobante = comprobante; }

    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
