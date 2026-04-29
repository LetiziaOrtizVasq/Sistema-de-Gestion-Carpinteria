package com.carpinteria.ventas;

import com.carpinteria.ventas.PedidoConfirmado;
import com.carpinteria.ventas.PedidoConfirmadoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pedidos")
public class PedidoConfirmadoController {

    private final PedidoConfirmadoService service;

    public PedidoConfirmadoController(PedidoConfirmadoService service) {
        this.service = service;
    }

    // CU-08: Listar pedidos confirmados
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pedidos", service.listarTodos());
        return "pedido/lista";
    }

    // CU-08: Formulario nuevo pedido
    @GetMapping("/nuevo")
    public String mostrarFormulario(@RequestParam(required = false) Long cotizacionId, Model model) {
        PedidoConfirmado pedido = new PedidoConfirmado();
        if (cotizacionId != null) {
            pedido.setCotizacion(service.buscarCotizacionPorId(cotizacionId));
        }
        model.addAttribute("pedido", pedido);
        model.addAttribute("cotizaciones", service.listarCotizacionesAceptadas());
        model.addAttribute("titulo", "Registrar Pedido Confirmado");
        return "pedido/formulario";
    }

    // CU-08: Guardar pedido
    @PostMapping
    public String guardar(@Valid @ModelAttribute("pedido") PedidoConfirmado pedido,
                          BindingResult result,
                          @RequestParam("cotizacionId") Long cotizacionId,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cotizaciones", service.listarCotizacionesAceptadas());
            model.addAttribute("titulo", pedido.getId() == null ? "Registrar Pedido Confirmado" : "Editar Pedido");
            return "pedido/formulario";
        }
        pedido.setCotizacion(service.buscarCotizacionPorId(cotizacionId));
        service.guardar(pedido);
        return "redirect:/pedidos";
    }

    // CU-08: Editar pedido
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("pedido", service.buscarPorId(id));
        model.addAttribute("cotizaciones", service.listarCotizacionesAceptadas());
        model.addAttribute("titulo", "Editar Pedido");
        return "pedido/formulario";
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/pedidos";
    }

    // Duplicar pedido
    @GetMapping("/duplicar/{id}")
    public String duplicar(@PathVariable Long id) {
        service.duplicar(id);
        return "redirect:/pedidos";
    }

    // Cancelar pedido
    @GetMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id) {
        service.cancelar(id);
        return "redirect:/pedidos";
    }
}
