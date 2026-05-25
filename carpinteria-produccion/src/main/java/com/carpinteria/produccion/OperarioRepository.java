package com.carpinteria.produccion;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OperarioRepository extends JpaRepository<Operario, Long> {
    List<Operario> findByActivoTrueOrderByNombreAsc();
}
