package com.carpinteria.produccion;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/operarios")
public class OperarioController {

    private final OperarioService service;

    public OperarioController(OperarioService service) { this.service = service; }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("operarios", service.listarTodos());
        return "produccion/operario-lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("operario", new Operario());
        model.addAttribute("titulo", "Registrar Operario");
        return "produccion/operario-form";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("operario") Operario o,
                          BindingResult r, Model model) {
        if (r.hasErrors()) {
            model.addAttribute("titulo", "Registrar Operario");
            return "produccion/operario-form";
        }
        service.guardar(o);
        return "redirect:/operarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("operario", service.buscarPorId(id));
        model.addAttribute("titulo", "Editar Operario");
        return "produccion/operario-form";
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/operarios";
    }
}
