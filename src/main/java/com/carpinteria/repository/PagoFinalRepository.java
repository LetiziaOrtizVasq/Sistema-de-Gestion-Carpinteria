package com.carpinteria.repository;

import com.carpinteria.model.PagoFinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagoFinalRepository extends JpaRepository<PagoFinal, Long> {
    Optional<PagoFinal> findByPedidoId(Long pedidoId);
    boolean existsByPedidoId(Long pedidoId);

    // CU-25: pedidos facturados en un período
    @Query("SELECT p FROM PagoFinal p WHERE p.fechaPago BETWEEN :desde AND :hasta")
    List<PagoFinal> findPagosEnPeriodo(@Param("desde") LocalDate desde,
                                        @Param("hasta") LocalDate hasta);
}
