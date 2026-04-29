package com.carpinteria.clientes;

import com.carpinteria.clientes.SolicitudCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudClienteRepository extends JpaRepository<SolicitudCliente, Long> {
}
