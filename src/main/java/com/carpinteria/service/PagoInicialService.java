package com.carpinteria.service;

import com.carpinteria.model.PagoInicial;
import com.carpinteria.model.PedidoConfirmado;
import com.carpinteria.repository.PagoInicialRepository;
import com.carpinteria.repository.PedidoConfirmadoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PagoInicialService {

    private final PagoInicialRepository pagoRepository;
    private final PedidoConfirmadoRepository pedidoRepository;

    public PagoInicialService(PagoInicialRepository pagoRepository,
                              PedidoConfirmadoRepository pedidoRepository) {
        this.pagoRepository = pagoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public List<PagoInicial> listarTodos() {
        return pagoRepository.findAll();
    }

    public PagoInicial buscarPorId(Long id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con id: " + id));
    }

    // CU-09: Confirmar pago inicial del 50%
    public PagoInicial guardar(PagoInicial pago) {
        // Calcular el 50% requerido desde la cotización vinculada
        BigDecimal total = pago.getPedido().getCotizacion().getPrecioTotal();
        BigDecimal mitad = total.divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
        pago.setMontoRequerido(mitad);
        PagoInicial saved = pagoRepository.save(pago);
        // Transición de estado: pedido pasa a PAGO_CONFIRMADO
        PedidoConfirmado pedido = saved.getPedido();
        pedido.setEstado("PAGO_CONFIRMADO");
        pedidoRepository.save(pedido);
        return saved;
    }

    public void eliminar(Long id) {
        pagoRepository.deleteById(id);
    }

    public List<PedidoConfirmado> listarPedidosSinPago() {
        return pedidoRepository.findAll().stream()
                .filter(p -> !pagoRepository.existsByPedidoId(p.getId()))
                .toList();
    }

    public PedidoConfirmado buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
    }
}
