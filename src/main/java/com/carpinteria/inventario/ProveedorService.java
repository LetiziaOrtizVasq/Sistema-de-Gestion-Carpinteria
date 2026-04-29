package com.carpinteria.inventario;

import com.carpinteria.inventario.Proveedor;
import com.carpinteria.inventario.ProveedorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProveedorService {

    private final ProveedorRepository repository;

    public ProveedorService(ProveedorRepository repository) {
        this.repository = repository;
    }

    public List<Proveedor> listarTodos() { return repository.findAll(); }
    public List<Proveedor> listarActivos() { return repository.findByActivoTrue(); }

    public Proveedor guardar(Proveedor proveedor) { return repository.save(proveedor); }

    public Proveedor buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado: " + id));
    }

    public void eliminar(Long id) { repository.deleteById(id); }
}
