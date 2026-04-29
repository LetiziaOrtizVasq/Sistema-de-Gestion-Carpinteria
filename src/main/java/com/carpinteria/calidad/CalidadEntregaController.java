package com.carpinteria.calidad;

import com.carpinteria.pagos.PagoFinal;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class CalidadEntregaController {

    private final CalidadEntregaService service;

    public CalidadEntregaController(CalidadEntregaService service) { this.service = service; }

    // ---- CU-16: Inspección de calidad ----
    @GetMapping("/calidad")
    public String listarInspecciones(Model model) {
        model.addAttribute("inspecciones", service.listarInspecciones());
        return "calidad/lista";
    }

    @GetMapping("/calidad/nueva")
    public String nuevaInspeccion(@RequestParam(required = false) Long pedidoId, Model model) {
        InspeccionCalidad i = new InspeccionCalidad();
        if (pedidoId != null) i.setPedido(service.buscarPedidoPorId(pedidoId));
        model.addAttribute("inspeccion", i);
        model.addAttribute("pedidos", service.pedidosSinInspeccion());
        model.addAttribute("titulo", "Registrar Inspección de Calidad");
        return "calidad/form";
    }

    @PostMapping("/calidad")
    public String guardarInspeccion(@Valid @ModelAttribute("inspeccion") InspeccionCalidad i,
                                    BindingResult r,
                                    @RequestParam("pedidoId") Long pedidoId, Model model) {
        if (r.hasErrors()) { model.addAttribute("pedidos", service.pedidosSinInspeccion()); model.addAttribute("titulo", "Registrar Inspección"); return "calidad/form"; }
        i.setPedido(service.buscarPedidoPorId(pedidoId));
        service.guardarInspeccion(i);
        return "redirect:/calidad";
    }

    @DeleteMapping("/calidad/{id}")
    public String eliminarInspeccion(@PathVariable Long id) { service.eliminarInspeccion(id); return "redirect:/calidad"; }

    // ---- CU-17: Embalaje ----
    @GetMapping("/embalaje")
    public String listarEmbalajes(Model model) {
        model.addAttribute("embalajes", service.listarEmbalajes());
        return "calidad/embalaje-lista";
    }

    @GetMapping("/embalaje/nuevo")
    public String nuevoEmbalaje(@RequestParam(required = false) Long pedidoId, Model model) {
        EmbalajePedido e = new EmbalajePedido();
        if (pedidoId != null) e.setPedido(service.buscarPedidoPorId(pedidoId));
        model.addAttribute("embalaje", e);
        model.addAttribute("pedidos", service.pedidosSinEmbalaje());
        model.addAttribute("titulo", "Registrar Embalaje");
        return "calidad/embalaje-form";
    }

    @PostMapping("/embalaje")
    public String guardarEmbalaje(@Valid @ModelAttribute("embalaje") EmbalajePedido e,
                                  BindingResult r,
                                  @RequestParam("pedidoId") Long pedidoId, Model model) {
        if (r.hasErrors()) { model.addAttribute("pedidos", service.pedidosSinEmbalaje()); model.addAttribute("titulo", "Registrar Embalaje"); return "calidad/embalaje-form"; }
        e.setPedido(service.buscarPedidoPorId(pedidoId));
        service.guardarEmbalaje(e);
        return "redirect:/embalaje";
    }

    @DeleteMapping("/embalaje/{id}")
    public String eliminarEmbalaje(@PathVariable Long id) { service.eliminarEmbalaje(id); return "redirect:/embalaje"; }

    // ---- CU-18: Entrega ----
    @GetMapping("/entregas")
    public String listarEntregas(Model model) {
        model.addAttribute("entregas", service.listarEntregas());
        return "entrega/lista";
    }

    @GetMapping("/entregas/nueva")
    public String nuevaEntrega(@RequestParam(required = false) Long pedidoId, Model model) {
        EntregaPedido e = new EntregaPedido();
        if (pedidoId != null) e.setPedido(service.buscarPedidoPorId(pedidoId));
        model.addAttribute("entrega", e);
        model.addAttribute("pedidos", service.pedidosSinEntrega());
        model.addAttribute("titulo", "Confirmar Entrega");
        return "entrega/form";
    }

    @PostMapping("/entregas")
    public String guardarEntrega(@Valid @ModelAttribute("entrega") EntregaPedido e,
                                 BindingResult r,
                                 @RequestParam("pedidoId") Long pedidoId, Model model) {
        if (r.hasErrors()) { model.addAttribute("pedidos", service.pedidosSinEntrega()); model.addAttribute("titulo", "Confirmar Entrega"); return "entrega/form"; }
        e.setPedido(service.buscarPedidoPorId(pedidoId));
        service.guardarEntrega(e);
        return "redirect:/entregas";
    }

    @DeleteMapping("/entregas/{id}")
    public String eliminarEntrega(@PathVariable Long id) { service.eliminarEntrega(id); return "redirect:/entregas"; }

    // ---- CU-19: Pago Final ----
    @GetMapping("/pagos-final")
    public String listarPagosFinal(Model model) {
        model.addAttribute("pagos", service.listarPagosFinal());
        return "entrega/pago-final-lista";
    }

    @GetMapping("/pagos-final/nuevo")
    public String nuevoPagoFinal(@RequestParam(required = false) Long pedidoId, Model model) {
        PagoFinal pf = new PagoFinal();
        if (pedidoId != null) pf.setPedido(service.buscarPedidoPorId(pedidoId));
        model.addAttribute("pagoFinal", pf);
        model.addAttribute("pedidos", service.pedidosSinPagoFinal());
        model.addAttribute("titulo", "Registrar Pago Final");
        return "entrega/pago-final-form";
    }

    @PostMapping("/pagos-final")
    public String guardarPagoFinal(@Valid @ModelAttribute("pagoFinal") PagoFinal pf,
                                   BindingResult r,
                                   @RequestParam("pedidoId") Long pedidoId, Model model) {
        if (r.hasErrors()) { model.addAttribute("pedidos", service.pedidosSinPagoFinal()); model.addAttribute("titulo", "Registrar Pago Final"); return "entrega/pago-final-form"; }
        pf.setPedido(service.buscarPedidoPorId(pedidoId));
        service.guardarPagoFinal(pf);
        return "redirect:/pagos-final";
    }

    @DeleteMapping("/pagos-final/{id}")
    public String eliminarPagoFinal(@PathVariable Long id) { service.eliminarPagoFinal(id); return "redirect:/pagos-final"; }
}
