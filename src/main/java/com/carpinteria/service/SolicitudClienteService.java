package com.carpinteria.service;

import com.carpinteria.model.SolicitudCliente;
import com.carpinteria.repository.SolicitudClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudClienteService {

    private final SolicitudClienteRepository repository;

    public SolicitudClienteService(SolicitudClienteRepository repository) {
        this.repository = repository;
    }

    public List<SolicitudCliente> listarTodas() {
        return repository.findAll();
    }

    public SolicitudCliente guardar(SolicitudCliente solicitud) {
        return repository.save(solicitud);
    }

    public SolicitudCliente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con id: " + id));
    }

    // CU-07: historial de solicitudes por nombre de cliente
    public List<SolicitudCliente> listarPorNombreCliente(String nombreCompleto) {
        return repository.findAll().stream()
                .filter(s -> s.getNombreCliente() != null &&
                             s.getNombreCliente().equalsIgnoreCase(nombreCompleto))
                .toList();
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
