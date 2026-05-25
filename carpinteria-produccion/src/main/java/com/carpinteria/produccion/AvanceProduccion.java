package com.carpinteria.produccion;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "avance_produccion")
public class AvanceProduccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La asignación es obligatoria")
    @ManyToOne(optional = false)
    @JoinColumn(name = "asignacion_id", nullable = false)
    private AsignacionProduccion asignacion;

    // CORTE | GROSADO | CEPILLADO | LIJADO | ARMADO | BARNIZADO | EMBALAJE
    @NotBlank(message = "La etapa es obligatoria")
    @Column(nullable = false)
    private String etapa;

    @Column(length = 600)
    private String descripcion;

    @Column(length = 500)
    private String observaciones;

    @Column
    private String operario;

    @Column(length = 500)
    private String insumos;

    private String horaInicio;

    private String horaFin;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private Boolean completado;

    public AvanceProduccion() {
        this.fecha      = LocalDate.now();
        this.completado = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public AsignacionProduccion getAsignacion() { return asignacion; }
    public void setAsignacion(AsignacionProduccion asignacion) { this.asignacion = asignacion; }

    public String getEtapa() { return etapa; }
    public void setEtapa(String etapa) { this.etapa = etapa; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getOperario() { return operario; }
    public void setOperario(String operario) { this.operario = operario; }

    public String getInsumos() { return insumos; }
    public void setInsumos(String insumos) { this.insumos = insumos; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Boolean getCompletado() { return completado; }
    public void setCompletado(Boolean completado) { this.completado = completado; }
}
