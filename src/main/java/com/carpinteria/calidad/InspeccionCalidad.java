package com.carpinteria.calidad;

import com.carpinteria.ventas.PedidoConfirmado;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/** CU-16: Registrar inspección de calidad. */
@Entity
@Table(name = "inspeccion_calidad")
public class InspeccionCalidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El pedido es obligatorio")
    @OneToOne(optional = false)
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    private PedidoConfirmado pedido;

    @NotBlank(message = "El inspector es obligatorio")
    @Column(nullable = false)
    private String inspector;

    // APROBADO | OBSERVADO | RECHAZADO
    @Column(nullable = false)
    private String resultado;

    @Column(length = 500)
    private String observaciones;

    // Acción tomada si el resultado es RETRABAJO/RECHAZADO
    @Column(length = 500)
    private String accionRetrabajo;

    @Column(nullable = false)
    private LocalDate fecha;

    public InspeccionCalidad() {
        this.fecha = LocalDate.now();
        this.resultado = "APROBADO";
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PedidoConfirmado getPedido() { return pedido; }
    public void setPedido(PedidoConfirmado pedido) { this.pedido = pedido; }

    public String getInspector() { return inspector; }
    public void setInspector(String inspector) { this.inspector = inspector; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getAccionRetrabajo() { return accionRetrabajo; }
    public void setAccionRetrabajo(String accionRetrabajo) { this.accionRetrabajo = accionRetrabajo; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}
