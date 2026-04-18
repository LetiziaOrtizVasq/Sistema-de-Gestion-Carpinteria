package com.carpinteria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Cubre CU-21 (ingreso de proveedor), CU-23 (consumo por pedido) y CU-20 (reposición).
 * El tipo de movimiento distingue el caso de uso: INGRESO, CONSUMO, REPOSICION.
 */
@Entity
@Table(name = "movimiento_madera")
public class MovimientoMadera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El stock es obligatorio")
    @ManyToOne(optional = false)
    @JoinColumn(name = "stock_madera_id", nullable = false)
    private StockMadera stockMadera;

    // INGRESO | CONSUMO | REPOSICION
    @Column(nullable = false)
    private String tipoMovimiento;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser positiva")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;

    @Column(nullable = false)
    private LocalDate fecha;

    // Solo para INGRESO: proveedor de origen
    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    // Solo para CONSUMO: pedido que consume la madera
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoConfirmado pedido;

    @Column(length = 400)
    private String observaciones;

    public MovimientoMadera() {
        this.fecha = LocalDate.now();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public StockMadera getStockMadera() { return stockMadera; }
    public void setStockMadera(StockMadera stockMadera) { this.stockMadera = stockMadera; }

    public String getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(String tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }

    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public PedidoConfirmado getPedido() { return pedido; }
    public void setPedido(PedidoConfirmado pedido) { this.pedido = pedido; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
