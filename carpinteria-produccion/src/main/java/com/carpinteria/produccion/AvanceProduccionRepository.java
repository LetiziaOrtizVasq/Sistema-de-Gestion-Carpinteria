package com.carpinteria.produccion;

import com.carpinteria.produccion.AvanceProduccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AvanceProduccionRepository extends JpaRepository<AvanceProduccion, Long> {
    List<AvanceProduccion> findByAsignacionIdOrderByFechaAsc(Long asignacionId);
}
