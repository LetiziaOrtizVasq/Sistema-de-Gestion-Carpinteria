package com.carpinteria.clientes;

import com.carpinteria.clientes.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // CU-01: búsqueda rápida por nombre, apellido o CI/NIT
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) " +
           "OR LOWER(c.apellido) LIKE LOWER(CONCAT('%', :termino, '%')) " +
           "OR LOWER(c.ci) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Cliente> buscarPorNombreOCi(@Param("termino") String termino);
}
