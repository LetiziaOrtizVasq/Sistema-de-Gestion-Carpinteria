package com.carpinteria.repository;

import com.carpinteria.model.AsignacionProduccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AsignacionProduccionRepository extends JpaRepository<AsignacionProduccion, Long> {
    Optional<AsignacionProduccion> findByPedidoId(Long pedidoId);
    boolean existsByPedidoId(Long pedidoId);
}
