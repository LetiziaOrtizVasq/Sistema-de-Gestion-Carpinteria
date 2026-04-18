package com.carpinteria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * CU-13: Registrar avance de producción por etapa.
 * CU-14: Registrar finalización (avance al 100% en etapa TERMINADO).
 */
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

    // CORTE | ENSAMBLE | LIJADO | PINTURA | TERMINADO
    @NotBlank(message = "La etapa es obligatoria")
    @Column(nullable = false)
    private String etapa;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(nullable = false, length = 500)
    private String descripcion;

    @Min(0) @Max(100)
    @Column(nullable = false)
    private Integer porcentaje;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private Boolean completado;

    public AvanceProduccion() {
        this.fecha = LocalDate.now();
        this.completado = false;
        this.porcentaje = 0;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public AsignacionProduccion getAsignacion() { return asignacion; }
    public void setAsignacion(AsignacionProduccion asignacion) { this.asignacion = asignacion; }

    public String getEtapa() { return etapa; }
    public void setEtapa(String etapa) { this.etapa = etapa; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getPorcentaje() { return porcentaje; }
    public void setPorcentaje(Integer porcentaje) { this.porcentaje = porcentaje; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Boolean getCompletado() { return completado; }
    public void setCompletado(Boolean completado) { this.completado = completado; }
}
