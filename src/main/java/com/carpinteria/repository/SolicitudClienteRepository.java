package com.carpinteria.repository;

import com.carpinteria.model.SolicitudCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudClienteRepository extends JpaRepository<SolicitudCliente, Long> {
}
