package com.carpinteria.controller;

import com.carpinteria.model.PagoInicial;
import com.carpinteria.service.PagoInicialService;
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
        model.addAttribute("titulo", "Confirmar Pago Inicial");
        return "pago/formulario";
    }

    // CU-09: Guardar pago
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("pago") PagoInicial pago,
                          BindingResult result,
                          @RequestParam("pedidoId") Long pedidoId,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pedidos", service.listarPedidosSinPago());
            model.addAttribute("titulo", "Confirmar Pago Inicial");
            return "pago/formulario";
        }
        pago.setPedido(service.buscarPedidoPorId(pedidoId));
        service.guardar(pago);
        return "redirect:/pagos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/pagos";
    }
}
