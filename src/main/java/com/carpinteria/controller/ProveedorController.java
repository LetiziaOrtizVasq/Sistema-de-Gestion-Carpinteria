package com.carpinteria.controller;

import com.carpinteria.model.Proveedor;
import com.carpinteria.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {

    private final ProveedorService service;

    public ProveedorController(ProveedorService service) { this.service = service; }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("proveedores", service.listarTodos());
        return "inventario/proveedores";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        model.addAttribute("titulo", "Nuevo Proveedor");
        return "inventario/proveedor-form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("proveedor") Proveedor p,
                          BindingResult r, Model model) {
        if (r.hasErrors()) { model.addAttribute("titulo", p.getId() == null ? "Nuevo Proveedor" : "Editar Proveedor"); return "inventario/proveedor-form"; }
        service.guardar(p);
        return "redirect:/proveedores";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("proveedor", service.buscarPorId(id));
        model.addAttribute("titulo", "Editar Proveedor");
        return "inventario/proveedor-form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/proveedores";
    }
}
