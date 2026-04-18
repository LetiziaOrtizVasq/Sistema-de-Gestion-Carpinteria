package com.carpinteria.repository;

import com.carpinteria.model.PagoInicial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagoInicialRepository extends JpaRepository<PagoInicial, Long> {

    Optional<PagoInicial> findByPedidoId(Long pedidoId);

    boolean existsByPedidoId(Long pedidoId);
}
