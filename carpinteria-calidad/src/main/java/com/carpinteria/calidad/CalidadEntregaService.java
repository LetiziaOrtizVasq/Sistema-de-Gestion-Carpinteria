package com.carpinteria.calidad;

import com.carpinteria.ventas.PedidoConfirmado;
import com.carpinteria.ventas.PedidoConfirmadoRepository;
import com.carpinteria.pagos.PagoFinal;
import com.carpinteria.pagos.PagoFinalRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Agrupa CU-16, CU-17, CU-18 y CU-19.
 * Inspección de calidad, embalaje, entrega y pago final.
 */
@Service
public class CalidadEntregaService {

    private final InspeccionCalidadRepository inspeccionRepo;
    private final EmbalajePedidoRepository embalajeRepo;
    private final EntregaPedidoRepository entregaRepo;
    private final PagoFinalRepository pagoFinalRepo;
    private final PedidoConfirmadoRepository pedidoRepo;

    public CalidadEntregaService(InspeccionCalidadRepository inspeccionRepo,
                                 EmbalajePedidoRepository embalajeRepo,
                                 EntregaPedidoRepository entregaRepo,
                                 PagoFinalRepository pagoFinalRepo,
                                 PedidoConfirmadoRepository pedidoRepo) {
        this.inspeccionRepo = inspeccionRepo;
        this.embalajeRepo   = embalajeRepo;
        this.entregaRepo    = entregaRepo;
        this.pagoFinalRepo  = pagoFinalRepo;
        this.pedidoRepo     = pedidoRepo;
    }

    // --- Helpers comunes ---
    public List<PedidoConfirmado> listarTodosPedidos() { return pedidoRepo.findAll(); }

    public PedidoConfirmado buscarPedidoPorId(Long id) {
        return pedidoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + id));
    }

    // --- CU-16: Inspección de calidad ---
    public List<InspeccionCalidad> listarInspecciones() { return inspeccionRepo.findAll(); }

    public InspeccionCalidad guardarInspeccion(InspeccionCalidad i) {
        InspeccionCalidad saved = inspeccionRepo.save(i);
        PedidoConfirmado pedido = saved.getPedido();
        if ("APROBADO".equals(saved.getResultado())) {
            pedido.setEstado("LISTO_ENTREGA");
        } else if ("RETRABAJO".equals(saved.getResultado()) || "RECHAZADO".equals(saved.getResultado())) {
            pedido.setEstado("EN_PRODUCCION");
        }
        pedidoRepo.save(pedido);
        return saved;
    }

    public InspeccionCalidad buscarInspeccionPorId(Long id) {
        return inspeccionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Inspección no encontrada: " + id));
    }

    public void eliminarInspeccion(Long id) { inspeccionRepo.deleteById(id); }

    public List<PedidoConfirmado> pedidosSinInspeccion() {
        return pedidoRepo.findAll().stream()
                .filter(p -> !inspeccionRepo.existsByPedidoId(p.getId()))
                .toList();
    }

    // --- CU-17: Embalaje ---
    public List<EmbalajePedido> listarEmbalajes() { return embalajeRepo.findAll(); }

    public EmbalajePedido guardarEmbalaje(EmbalajePedido e) { return embalajeRepo.save(e); }

    public EmbalajePedido buscarEmbalajePorId(Long id) {
        return embalajeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Embalaje no encontrado: " + id));
    }

    public void eliminarEmbalaje(Long id) { embalajeRepo.deleteById(id); }

    public List<PedidoConfirmado> pedidosSinEmbalaje() {
        return pedidoRepo.findAll().stream()
                .filter(p -> !embalajeRepo.existsByPedidoId(p.getId()))
                .toList();
    }

    // --- CU-18: Entrega ---
    public List<EntregaPedido> listarEntregas() { return entregaRepo.findAll(); }

    public EntregaPedido guardarEntrega(EntregaPedido e) {
        EntregaPedido saved = entregaRepo.save(e);
        PedidoConfirmado pedido = saved.getPedido();
        pedido.setEstado("ENTREGADO");
        pedidoRepo.save(pedido);
        return saved;
    }

    public EntregaPedido buscarEntregaPorId(Long id) {
        return entregaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada: " + id));
    }

    public void eliminarEntrega(Long id) { entregaRepo.deleteById(id); }

    public List<PedidoConfirmado> pedidosSinEntrega() {
        return pedidoRepo.findAll().stream()
                .filter(p -> !entregaRepo.existsByPedidoId(p.getId()))
                .toList();
    }

    // --- CU-19: Pago final ---
    public List<PagoFinal> listarPagosFinal() { return pagoFinalRepo.findAll(); }

    public PagoFinal guardarPagoFinal(PagoFinal pf) {
        BigDecimal total = pf.getPedido().getCotizacion().getPrecioTotal();
        BigDecimal mitad = total.divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
        pf.setMontoRequerido(total.subtract(mitad)); // 50% restante
        PagoFinal saved = pagoFinalRepo.save(pf);
        PedidoConfirmado pedido = saved.getPedido();
        pedido.setEstado("CERRADO");
        pedidoRepo.save(pedido);
        return saved;
    }

    public PagoFinal buscarPagoFinalPorId(Long id) {
        return pagoFinalRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago final no encontrado: " + id));
    }

    public void eliminarPagoFinal(Long id) { pagoFinalRepo.deleteById(id); }

    public List<PedidoConfirmado> pedidosSinPagoFinal() {
        return pedidoRepo.findAll().stream()
                .filter(p -> !pagoFinalRepo.existsByPedidoId(p.getId()))
                .toList();
    }
}
