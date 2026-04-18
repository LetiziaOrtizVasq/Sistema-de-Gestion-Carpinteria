package com.carpinteria.service;

import com.carpinteria.model.AsignacionProduccion;
import com.carpinteria.model.AvanceProduccion;
import com.carpinteria.model.PedidoConfirmado;
import com.carpinteria.repository.AsignacionProduccionRepository;
import com.carpinteria.repository.AvanceProduccionRepository;
import com.carpinteria.repository.PedidoConfirmadoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProduccionService {

    private final AsignacionProduccionRepository asignRepo;
    private final AvanceProduccionRepository avanceRepo;
    private final PedidoConfirmadoRepository pedidoRepo;

    public ProduccionService(AsignacionProduccionRepository asignRepo,
                             AvanceProduccionRepository avanceRepo,
                             PedidoConfirmadoRepository pedidoRepo) {
        this.asignRepo  = asignRepo;
        this.avanceRepo = avanceRepo;
        this.pedidoRepo = pedidoRepo;
    }

    // --- Asignaciones ---
    public List<AsignacionProduccion> listarAsignaciones() { return asignRepo.findAll(); }

    public AsignacionProduccion guardarAsignacion(AsignacionProduccion a) {
        AsignacionProduccion saved = asignRepo.save(a);
        // Transición de estado: pedido pasa a EN_PRODUCCION
        PedidoConfirmado pedido = saved.getPedido();
        pedido.setEstado("EN_PRODUCCION");
        pedidoRepo.save(pedido);
        return saved;
    }

    public AsignacionProduccion buscarAsignacionPorId(Long id) {
        return asignRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada: " + id));
    }

    public void eliminarAsignacion(Long id) { asignRepo.deleteById(id); }

    public List<PedidoConfirmado> pedidosSinAsignacion() {
        return pedidoRepo.findAll().stream()
                .filter(p -> "PAGO_CONFIRMADO".equals(p.getEstado()))
                .filter(p -> !asignRepo.existsByPedidoId(p.getId()))
                .toList();
    }

    public PedidoConfirmado buscarPedidoPorId(Long id) {
        return pedidoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + id));
    }

    // --- Avances ---
    public List<AvanceProduccion> listarAvancesDe(Long asignacionId) {
        return avanceRepo.findByAsignacionIdOrderByFechaAsc(asignacionId);
    }

    public AvanceProduccion guardarAvance(AvanceProduccion av) { return avanceRepo.save(av); }

    public AvanceProduccion buscarAvancePorId(Long id) {
        return avanceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Avance no encontrado: " + id));
    }

    public void eliminarAvance(Long id) { avanceRepo.deleteById(id); }
}
