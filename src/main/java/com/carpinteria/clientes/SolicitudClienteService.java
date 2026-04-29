package com.carpinteria.clientes;

import com.carpinteria.clientes.ItemSolicitud;
import com.carpinteria.clientes.SolicitudCliente;
import com.carpinteria.clientes.SolicitudClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public SolicitudCliente guardar(SolicitudCliente solicitud) {
        if (solicitud.getId() != null) {
            SolicitudCliente existente = buscarPorId(solicitud.getId());
            existente.setNombreCliente(solicitud.getNombreCliente());
            existente.setTelefono(solicitud.getTelefono());
            existente.setEstado(solicitud.getEstado());
            existente.getItems().clear();
            for (ItemSolicitud item : solicitud.getItems()) {
                existente.agregarItem(item);
            }
            return repository.save(existente);
        }
        solicitud.getItems().forEach(item -> item.setSolicitud(solicitud));
        return repository.save(solicitud);
    }

    public SolicitudCliente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con id: " + id));
    }

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
