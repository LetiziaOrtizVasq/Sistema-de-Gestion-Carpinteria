package com.carpinteria.produccion;

import com.carpinteria.produccion.InformacionTecnica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InformacionTecnicaRepository extends JpaRepository<InformacionTecnica, Long> {
    Optional<InformacionTecnica> findBySolicitudId(Long solicitudId);
    boolean existsBySolicitudId(Long solicitudId);
}
