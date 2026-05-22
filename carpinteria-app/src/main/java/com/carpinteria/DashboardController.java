package com.carpinteria;

import com.carpinteria.clientes.ClienteRepository;
import com.carpinteria.clientes.SolicitudClienteRepository;
import com.carpinteria.inventario.StockMaderaRepository;
import com.carpinteria.ventas.PedidoConfirmadoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Controller
public class DashboardController {

    private final ClienteRepository clienteRepo;
    private final SolicitudClienteRepository solicitudRepo;
    private final PedidoConfirmadoRepository pedidoRepo;
    private final StockMaderaRepository stockRepo;

    public DashboardController(ClienteRepository clienteRepo,
                               SolicitudClienteRepository solicitudRepo,
                               PedidoConfirmadoRepository pedidoRepo,
                               StockMaderaRepository stockRepo) {
        this.clienteRepo  = clienteRepo;
        this.solicitudRepo = solicitudRepo;
        this.pedidoRepo   = pedidoRepo;
        this.stockRepo    = stockRepo;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        long totalClientes  = clienteRepo.count();
        long totalSolicitudes = solicitudRepo.count();

        var allPedidos = pedidoRepo.findAll();
        List<String> estadosActivos = Arrays.asList("PENDIENTE_PAGO", "PAGO_CONFIRMADO", "EN_PRODUCCION", "LISTO_ENTREGA", "ENTREGADO");

        long pedidosActivos = allPedidos.stream()
            .filter(p -> estadosActivos.contains(p.getEstado())).count();
        long pendientesPago = allPedidos.stream()
            .filter(p -> "PENDIENTE_PAGO".equals(p.getEstado())).count();
        long enProduccion = allPedidos.stream()
            .filter(p -> "EN_PRODUCCION".equals(p.getEstado())).count();
        long listoEntrega = allPedidos.stream()
            .filter(p -> "LISTO_ENTREGA".equals(p.getEstado())).count();

        long alertasStock = stockRepo.findStockCritico().size();

        var ultimosPedidos = allPedidos.stream()
            .sorted((a, b) -> b.getFechaConfirmacion().compareTo(a.getFechaConfirmacion()))
            .limit(6)
            .toList();

        var stockCritico = stockRepo.findStockCritico();

        String fechaHoy = LocalDate.now()
            .format(DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM yyyy",
                new java.util.Locale("es", "ES")));

        model.addAttribute("totalClientes",   totalClientes);
        model.addAttribute("totalSolicitudes", totalSolicitudes);
        model.addAttribute("pedidosActivos",  pedidosActivos);
        model.addAttribute("alertasStock",    alertasStock);
        model.addAttribute("pendientesPago",  pendientesPago);
        model.addAttribute("enProduccion",    enProduccion);
        model.addAttribute("listoEntrega",    listoEntrega);
        model.addAttribute("ultimosPedidos",  ultimosPedidos);
        model.addAttribute("stockCritico",    stockCritico);
        model.addAttribute("fechaHoy",        fechaHoy);

        return "dashboard";
    }
}
