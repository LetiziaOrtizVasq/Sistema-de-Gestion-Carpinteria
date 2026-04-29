package com.carpinteria.inventario;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
@Table(name = "stock_madera")
public class StockMadera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El tipo de madera es obligatorio")
    @Column(nullable = false)
    private String tipoMadera;

    private String dimension;

    private String unidad;

    @NotNull(message = "La cantidad disponible es obligatoria")
    @PositiveOrZero
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidadDisponible;

    @NotNull(message = "El stock mínimo es obligatorio")
    @PositiveOrZero
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal stockMinimo;

    @Column(precision = 10, scale = 2)
    private BigDecimal precioPorUnidad;

    private String descripcion;

    // Indica si el stock está en nivel crítico
    public boolean isStockCritico() {
        if (cantidadDisponible == null || stockMinimo == null) return false;
        return cantidadDisponible.compareTo(stockMinimo) <= 0;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipoMadera() { return tipoMadera; }
    public void setTipoMadera(String tipoMadera) { this.tipoMadera = tipoMadera; }

    public String getDimension() { return dimension; }
    public void setDimension(String dimension) { this.dimension = dimension; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }

    public BigDecimal getCantidadDisponible() { return cantidadDisponible; }
    public void setCantidadDisponible(BigDecimal cantidadDisponible) { this.cantidadDisponible = cantidadDisponible; }

    public BigDecimal getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(BigDecimal stockMinimo) { this.stockMinimo = stockMinimo; }

    public BigDecimal getPrecioPorUnidad() { return precioPorUnidad; }
    public void setPrecioPorUnidad(BigDecimal precioPorUnidad) { this.precioPorUnidad = precioPorUnidad; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
