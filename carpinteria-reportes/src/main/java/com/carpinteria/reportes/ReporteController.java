package com.carpinteria.reportes;

import com.carpinteria.pagos.PagoFinal;
import com.carpinteria.reportes.ReporteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    private final ReporteService service;

    public ReporteController(ReporteService service) { this.service = service; }

    @GetMapping
    public String index(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            Model model) {

        LocalDate d = desde != null ? desde : LocalDate.now().withDayOfMonth(1);
        LocalDate h = hasta != null ? hasta : LocalDate.now();

        List<PagoFinal> pedidos = service.pedidosEnPeriodo(d, h);
        BigDecimal facturacion = pedidos.stream()
                .map(PagoFinal::getMontoPagado)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("desde", d);
        model.addAttribute("hasta", h);
        model.addAttribute("pedidosPeriodo", pedidos);
        model.addAttribute("facturacionPeriodo", facturacion);
        model.addAttribute("consumoPorTipo", service.consumoPorTipo(d, h));
        model.addAttribute("clientesFrecuentes", service.clientesFrecuentes());
        return "reporte/index";
    }
}
