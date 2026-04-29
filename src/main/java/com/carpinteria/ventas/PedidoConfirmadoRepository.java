package com.carpinteria.ventas;

import com.carpinteria.ventas.PedidoConfirmado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoConfirmadoRepository extends JpaRepository<PedidoConfirmado, Long> {

    Optional<PedidoConfirmado> findByCotizacionId(Long cotizacionId);

    boolean existsByCotizacionId(Long cotizacionId);
}
