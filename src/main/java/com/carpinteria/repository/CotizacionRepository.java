package com.carpinteria.repository;

import com.carpinteria.model.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {

    // Buscar cotizaciones de una solicitud específica
    List<Cotizacion> findBySolicitudId(Long solicitudId);
}
