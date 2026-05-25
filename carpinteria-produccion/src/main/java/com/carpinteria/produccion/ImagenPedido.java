package com.carpinteria.produccion;

import com.carpinteria.ventas.PedidoConfirmado;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "imagen_pedido")
public class ImagenPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private PedidoConfirmado pedido;

    @Column(nullable = false)
    private String nombreOriginal;

    @Column(nullable = false)
    private String nombreGuardado;

    @Column(nullable = false)
    private String tipoContenido;

    private Long tamanio;

    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fechaSubida;

    public ImagenPedido() {
        this.fechaSubida = LocalDateTime.now();
    }

    public boolean esImagen() {
        return tipoContenido != null && tipoContenido.startsWith("image/");
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PedidoConfirmado getPedido() { return pedido; }
    public void setPedido(PedidoConfirmado pedido) { this.pedido = pedido; }

    public String getNombreOriginal() { return nombreOriginal; }
    public void setNombreOriginal(String nombreOriginal) { this.nombreOriginal = nombreOriginal; }

    public String getNombreGuardado() { return nombreGuardado; }
    public void setNombreGuardado(String nombreGuardado) { this.nombreGuardado = nombreGuardado; }

    public String getTipoContenido() { return tipoContenido; }
    public void setTipoContenido(String tipoContenido) { this.tipoContenido = tipoContenido; }

    public Long getTamanio() { return tamanio; }
    public void setTamanio(Long tamanio) { this.tamanio = tamanio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getFechaSubida() { return fechaSubida; }
    public void setFechaSubida(LocalDateTime fechaSubida) { this.fechaSubida = fechaSubida; }
}
