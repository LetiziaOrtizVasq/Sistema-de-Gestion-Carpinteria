package com.carpinteria.repository;

import com.carpinteria.model.EmbalajePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmbalajePedidoRepository extends JpaRepository<EmbalajePedido, Long> {
    Optional<EmbalajePedido> findByPedidoId(Long pedidoId);
    boolean existsByPedidoId(Long pedidoId);
}
