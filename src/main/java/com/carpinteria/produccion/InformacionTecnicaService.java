package com.carpinteria.produccion;

import com.carpinteria.produccion.InformacionTecnica;
import com.carpinteria.clientes.SolicitudCliente;
import com.carpinteria.produccion.InformacionTecnicaRepository;
import com.carpinteria.clientes.SolicitudClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InformacionTecnicaService {

    private final InformacionTecnicaRepository repo;
    private final SolicitudClienteRepository solicitudRepo;

    public InformacionTecnicaService(InformacionTecnicaRepository repo,
                                     SolicitudClienteRepository solicitudRepo) {
        this.repo = repo;
        this.solicitudRepo = solicitudRepo;
    }

    public List<InformacionTecnica> listarTodas() { return repo.findAll(); }

    public InformacionTecnica guardar(InformacionTecnica info) { return repo.save(info); }

    public InformacionTecnica buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Información técnica no encontrada: " + id));
    }

    public void eliminar(Long id) { repo.deleteById(id); }

    public List<SolicitudCliente> solicitudesSinInfoTecnica() {
        return solicitudRepo.findAll().stream()
                .filter(s -> !repo.existsBySolicitudId(s.getId()))
                .toList();
    }

    public SolicitudCliente buscarSolicitudPorId(Long id) {
        return solicitudRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada: " + id));
    }
}
