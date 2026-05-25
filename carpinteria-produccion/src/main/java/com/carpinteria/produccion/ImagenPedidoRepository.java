package com.carpinteria.produccion;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImagenPedidoRepository extends JpaRepository<ImagenPedido, Long> {
    List<ImagenPedido> findByPedidoIdOrderByFechaSubidaDesc(Long pedidoId);
    List<ImagenPedido> findAllByOrderByFechaSubidaDesc();
    long countByPedidoId(Long pedidoId);
}
