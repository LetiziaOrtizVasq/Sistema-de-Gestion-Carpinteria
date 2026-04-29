package com.carpinteria.calidad;

import com.carpinteria.calidad.InspeccionCalidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InspeccionCalidadRepository extends JpaRepository<InspeccionCalidad, Long> {
    Optional<InspeccionCalidad> findByPedidoId(Long pedidoId);
    boolean existsByPedidoId(Long pedidoId);
}
