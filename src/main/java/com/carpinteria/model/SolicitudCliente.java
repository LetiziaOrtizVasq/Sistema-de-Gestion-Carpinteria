package com.carpinteria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "solicitud_cliente")
public class SolicitudCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Column(nullable = false)
    private String nombreCliente;

    @NotBlank(message = "El teléfono es obligatorio")
    @Column(nullable = false)
    private String telefono;

    @NotBlank(message = "El tipo de producto es obligatorio")
    @Column(nullable = false)
    private String tipoProducto;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(nullable = false, length = 500)
    private String descripcion;

    private String dimensionesAproximadas;

    private String materialPreferido;

    @NotNull
    @Column(nullable = false)
    private LocalDate fechaSolicitud;

    @Column(nullable = false)
    private String estado;

    // Constructor vacío requerido por JPA
    public SolicitudCliente() {
        this.fechaSolicitud = LocalDate.now();
        this.estado = "PENDIENTE";
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getTipoProducto() { return tipoProducto; }
    public void setTipoProducto(String tipoProducto) { this.tipoProducto = tipoProducto; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getDimensionesAproximadas() { return dimensionesAproximadas; }
    public void setDimensionesAproximadas(String dimensionesAproximadas) { this.dimensionesAproximadas = dimensionesAproximadas; }

    public String getMaterialPreferido() { return materialPreferido; }
    public void setMaterialPreferido(String materialPreferido) { this.materialPreferido = materialPreferido; }

    public LocalDate getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDate fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
