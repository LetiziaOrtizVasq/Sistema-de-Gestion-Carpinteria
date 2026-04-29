package com.carpinteria.inventario;

import com.carpinteria.inventario.MovimientoMadera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimientoMaderaRepository extends JpaRepository<MovimientoMadera, Long> {

    List<MovimientoMadera> findByTipoMovimiento(String tipo);

    List<MovimientoMadera> findByStockMaderaId(Long stockId);

    // CU-26: Consumo por tipo de madera en un período
    @Query("SELECT m FROM MovimientoMadera m WHERE m.tipoMovimiento = 'CONSUMO' " +
           "AND m.fecha BETWEEN :desde AND :hasta")
    List<MovimientoMadera> findConsumosEnPeriodo(@Param("desde") LocalDate desde,
                                                  @Param("hasta") LocalDate hasta);
}
