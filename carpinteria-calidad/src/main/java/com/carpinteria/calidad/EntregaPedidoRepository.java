package com.carpinteria.calidad;

import com.carpinteria.calidad.EntregaPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EntregaPedidoRepository extends JpaRepository<EntregaPedido, Long> {
    Optional<EntregaPedido> findByPedidoId(Long pedidoId);
    boolean existsByPedidoId(Long pedidoId);
}
