package com.carpinteria.inventario;

import com.carpinteria.inventario.StockMadera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StockMaderaRepository extends JpaRepository<StockMadera, Long> {

    List<StockMadera> findByTipoMaderaContainingIgnoreCase(String tipo);

    // CU-24: Maderas con stock crítico
    @Query("SELECT s FROM StockMadera s WHERE s.cantidadDisponible <= s.stockMinimo")
    List<StockMadera> findStockCritico();
}
