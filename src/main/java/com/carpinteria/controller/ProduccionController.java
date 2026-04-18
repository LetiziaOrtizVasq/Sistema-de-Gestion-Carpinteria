package com.carpinteria.controller;

import com.carpinteria.model.AsignacionProduccion;
import com.carpinteria.model.AvanceProduccion;
import com.carpinteria.service.ProduccionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/produccion")
public class ProduccionController {

    private final ProduccionService service;

    public ProduccionController(ProduccionService service) { this.service = service; }

    // CU-12: Listar asignaciones a producción
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("asignaciones", service.listarAsignaciones());
        return "produccion/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(@RequestParam(required = false) Long pedidoId, Model model) {
        AsignacionProduccion a = new AsignacionProduccion();
        if (pedidoId != null) a.setPedido(service.buscarPedidoPorId(pedidoId));
        model.addAttribute("asignacion", a);
        model.addAttribute("pedidos", service.pedidosSinAsignacion());
        model.addAttribute("titulo", "Asignar Pedido a Producción");
        return "produccion/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("asignacion") AsignacionProduccion a,
                          BindingResult r,
                          @RequestParam("pedidoId") Long pedidoId,
                          Model model) {
        if (r.hasErrors()) {
            model.addAttribute("pedidos", service.pedidosSinAsignacion());
            model.addAttribute("titulo", "Asignar Pedido a Producción");
            return "produccion/form";
        }
        a.setPedido(service.buscarPedidoPorId(pedidoId));
        service.guardarAsignacion(a);
        return "redirect:/produccion";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("asignacion", service.buscarAsignacionPorId(id));
        model.addAttribute("pedidos", service.pedidosSinAsignacion());
        model.addAttribute("titulo", "Editar Asignación");
        return "produccion/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminarAsignacion(id);
        return "redirect:/produccion";
    }

    // CU-13/14/15: Avances de una asignación
    @GetMapping("/{id}/avances")
    public String avances(@PathVariable Long id, Model model) {
        model.addAttribute("asignacion", service.buscarAsignacionPorId(id));
        model.addAttribute("avances", service.listarAvancesDe(id));
        model.addAttribute("avance", new AvanceProduccion());
        return "produccion/avances";
    }

    @PostMapping("/{asignacionId}/avances/guardar")
    public String guardarAvance(@PathVariable Long asignacionId,
                                @Valid @ModelAttribute("avance") AvanceProduccion av,
                                BindingResult r, Model model) {
        if (r.hasErrors()) {
            model.addAttribute("asignacion", service.buscarAsignacionPorId(asignacionId));
            model.addAttribute("avances", service.listarAvancesDe(asignacionId));
            return "produccion/avances";
        }
        av.setAsignacion(service.buscarAsignacionPorId(asignacionId));
        service.guardarAvance(av);
        return "redirect:/produccion/" + asignacionId + "/avances";
    }

    @GetMapping("/avances/eliminar/{id}")
    public String eliminarAvance(@PathVariable Long id) {
        AvanceProduccion av = service.buscarAvancePorId(id);
        Long asignacionId = av.getAsignacion().getId();
        service.eliminarAvance(id);
        return "redirect:/produccion/" + asignacionId + "/avances";
    }
}
