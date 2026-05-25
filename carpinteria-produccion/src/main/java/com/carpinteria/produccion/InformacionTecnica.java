package com.carpinteria.produccion;

import com.carpinteria.clientes.SolicitudCliente;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "informacion_tecnica")
public class InformacionTecnica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "solicitud_id", nullable = false, unique = true)
    private SolicitudCliente solicitud;

    @NotBlank(message = "Las especificaciones son obligatorias")
    @Column(nullable = false, length = 600)
    private String especificaciones;

    private String tiposMadera;

    private String acabado;

    private String dimensionesExactas;

    private String color;

    private String disenio;

    private String estiloMueble;

    private Boolean plegable;

    @Column(length = 600)
    private String requerimientosEspeciales;

    @Column(length = 400)
    private String notasAdicionales;

    @Column(nullable = false)
    private LocalDate fechaRegistro;

    public InformacionTecnica() {
        this.fechaRegistro = LocalDate.now();
        this.plegable = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public SolicitudCliente getSolicitud() { return solicitud; }
    public void setSolicitud(SolicitudCliente solicitud) { this.solicitud = solicitud; }

    public String getEspecificaciones() { return especificaciones; }
    public void setEspecificaciones(String especificaciones) { this.especificaciones = especificaciones; }

    public String getTiposMadera() { return tiposMadera; }
    public void setTiposMadera(String tiposMadera) { this.tiposMadera = tiposMadera; }

    public String getAcabado() { return acabado; }
    public void setAcabado(String acabado) { this.acabado = acabado; }

    public String getDimensionesExactas() { return dimensionesExactas; }
    public void setDimensionesExactas(String dimensionesExactas) { this.dimensionesExactas = dimensionesExactas; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getDisenio() { return disenio; }
    public void setDisenio(String disenio) { this.disenio = disenio; }

    public String getEstiloMueble() { return estiloMueble; }
    public void setEstiloMueble(String estiloMueble) { this.estiloMueble = estiloMueble; }

    public Boolean getPlegable() { return plegable; }
    public void setPlegable(Boolean plegable) { this.plegable = plegable; }

    public String getRequerimientosEspeciales() { return requerimientosEspeciales; }
    public void setRequerimientosEspeciales(String requerimientosEspeciales) { this.requerimientosEspeciales = requerimientosEspeciales; }

    public String getNotasAdicionales() { return notasAdicionales; }
    public void setNotasAdicionales(String notasAdicionales) { this.notasAdicionales = notasAdicionales; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
