package com.carpinteria.produccion;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OperarioService {

    private final OperarioRepository repo;

    public OperarioService(OperarioRepository repo) { this.repo = repo; }

    public List<Operario> listarActivos() { return repo.findByActivoTrueOrderByNombreAsc(); }
    public List<Operario> listarTodos()   { return repo.findAll(); }

    public Operario buscarPorId(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Operario no encontrado: " + id));
    }

    public Operario guardar(Operario o) { return repo.save(o); }

    public void eliminar(Long id) { repo.deleteById(id); }
}
