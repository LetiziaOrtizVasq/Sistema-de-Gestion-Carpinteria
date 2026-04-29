package com.carpinteria.clientes;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @NotNull
    @Column(nullable = false)
    private LocalDate fechaSolicitud;

    @Column(nullable = false)
    private String estado;

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ItemSolicitud> items = new ArrayList<>();

    public SolicitudCliente() {
        this.fechaSolicitud = LocalDate.now();
        this.estado = "PENDIENTE";
    }

    public String getResumenProductos() {
        if (items == null || items.isEmpty()) return "Sin artículos";
        return items.stream()
                .map(i -> (i.getCantidad() != null && i.getCantidad() > 1 ? i.getCantidad() + "x " : "") + i.getTipoProducto())
                .collect(Collectors.joining(", "));
    }

    public void agregarItem(ItemSolicitud item) {
        item.setSolicitud(this);
        items.add(item);
    }

    public void clearItems() {
        items.clear();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public LocalDate getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDate fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<ItemSolicitud> getItems() { return items; }
    public void setItems(List<ItemSolicitud> items) { this.items = items; }
}
