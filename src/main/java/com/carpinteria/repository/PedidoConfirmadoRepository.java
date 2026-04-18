package com.carpinteria.repository;

import com.carpinteria.model.PedidoConfirmado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoConfirmadoRepository extends JpaRepository<PedidoConfirmado, Long> {

    Optional<PedidoConfirmado> findByCotizacionId(Long cotizacionId);

    boolean existsByCotizacionId(Long cotizacionId);
}
