package com.carpinteria.pagos;

import com.carpinteria.pagos.PagoInicial;
import com.carpinteria.pagos.PagoInicialService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pagos")
public class PagoInicialController {

    private final PagoInicialService service;

    public PagoInicialController(PagoInicialService service) {
        this.service = service;
    }

    // CU-09: Listar pagos
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pagos", service.listarTodos());
        return "pago/lista";
    }

    // CU-09: Formulario nuevo pago
    @GetMapping("/nuevo")
    public String mostrarFormulario(@RequestParam(required = false) Long pedidoId, Model model) {
        PagoInicial pago = new PagoInicial();
        if (pedidoId != null) {
            pago.setPedido(service.buscarPedidoPorId(pedidoId));
        }
        model.addAttribute("pago", pago);
        model.addAttribute("pedidos", service.listarPedidosSinPago());
        model.addAttribute("titulo", "Confirmar Pago Inicial (50%)");
        model.addAttribute("pasoActual", 4);
        return "pago/formulario";
    }

    // CU-09: Guardar pago
    @PostMapping
    public String guardar(@Valid @ModelAttribute("pago") PagoInicial pago,
                          BindingResult result,
                          @RequestParam(value = "pedidoId", required = false) Long pedidoId,
                          Model model) {
        if (pedidoId == null) {
            result.rejectValue("pedido", "required", "El pedido es obligatorio");
        }
        if (result.hasErrors()) {
            model.addAttribute("pedidos", service.listarPedidosSinPago());
            model.addAttribute("titulo", "Confirmar Pago Inicial");
            return "pago/formulario";
        }
        pago.setPedido(service.buscarPedidoPorId(pedidoId));
        service.guardar(pago);
        return "redirect:/produccion/nuevo?pedidoId=" + pedidoId;
    }

    @GetMapping("/{id}/recibo")
    public String verRecibo(@PathVariable Long id, Model model) {
        model.addAttribute("pago", service.buscarPorId(id));
        return "pago/recibo";
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/pagos";
    }
}
