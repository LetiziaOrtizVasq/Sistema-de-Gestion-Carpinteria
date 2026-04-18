package com.carpinteria.service;

import com.carpinteria.model.Cliente;
import com.carpinteria.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    // CU-04: Registrar información personal del cliente
    public Cliente guardar(Cliente cliente) {
        return repository.save(cliente);
    }

    // CU-05: Actualizar información del cliente
    public Cliente actualizar(Cliente cliente) {
        buscarPorId(cliente.getId()); // verifica que existe
        return repository.save(cliente);
    }

    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
    }

    // CU-01: búsqueda rápida por nombre o CI/NIT
    public List<Cliente> buscar(String termino) {
        if (termino == null || termino.isBlank()) return repository.findAll();
        return repository.buscarPorNombreOCi(termino);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
