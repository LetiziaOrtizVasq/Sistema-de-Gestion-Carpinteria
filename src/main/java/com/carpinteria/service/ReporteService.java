package com.carpinteria.service;

import com.carpinteria.model.*;
import com.carpinteria.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * CU-25: Reporte de pedidos en un período.
 * CU-26: Reporte de consumo de madera por tipo.
 * CU-27: Clientes frecuentes.
 */
@Service
public class ReporteService {

    private final PagoFinalRepository pagoFinalRepo;
    private final MovimientoMaderaRepository movRepo;
    private final SolicitudClienteRepository solicitudRepo;

    public ReporteService(PagoFinalRepository pagoFinalRepo,
                          MovimientoMaderaRepository movRepo,
                          SolicitudClienteRepository solicitudRepo) {
        this.pagoFinalRepo = pagoFinalRepo;
        this.movRepo       = movRepo;
        this.solicitudRepo = solicitudRepo;
    }

    // CU-25: Pedidos facturados en un período
    public List<PagoFinal> pedidosEnPeriodo(LocalDate desde, LocalDate hasta) {
        return pagoFinalRepo.findPagosEnPeriodo(desde, hasta);
    }

    // CU-26: Consumo de madera por tipo en un período
    public Map<String, java.math.BigDecimal> consumoPorTipo(LocalDate desde, LocalDate hasta) {
        return movRepo.findConsumosEnPeriodo(desde, hasta).stream()
                .collect(Collectors.groupingBy(
                        m -> m.getStockMadera().getTipoMadera(),
                        Collectors.reducing(java.math.BigDecimal.ZERO,
                                MovimientoMadera::getCantidad,
                                java.math.BigDecimal::add)
                ));
    }

    // CU-27: Clientes frecuentes (por cantidad de solicitudes)
    public List<Map.Entry<String, Long>> clientesFrecuentes() {
        return solicitudRepo.findAll().stream()
                .collect(Collectors.groupingBy(SolicitudCliente::getNombreCliente, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .toList();
    }
}
