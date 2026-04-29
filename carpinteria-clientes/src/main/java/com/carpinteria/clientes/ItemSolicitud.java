package com.carpinteria.clientes;

import jakarta.persistence.*;

@Entity
@Table(name = "item_solicitud")
public class ItemSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "solicitud_id")
    private SolicitudCliente solicitud;

    @Column(nullable = false)
    private String tipoProducto;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private Integer cantidad = 1;

    private String dimensionesAproximadas;
    private String materialPreferido;

    public ItemSolicitud() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public SolicitudCliente getSolicitud() { return solicitud; }
    public void setSolicitud(SolicitudCliente solicitud) { this.solicitud = solicitud; }

    public String getTipoProducto() { return tipoProducto; }
    public void setTipoProducto(String tipoProducto) { this.tipoProducto = tipoProducto; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getDimensionesAproximadas() { return dimensionesAproximadas; }
    public void setDimensionesAproximadas(String dimensionesAproximadas) { this.dimensionesAproximadas = dimensionesAproximadas; }

    public String getMaterialPreferido() { return materialPreferido; }
    public void setMaterialPreferido(String materialPreferido) { this.materialPreferido = materialPreferido; }
}
