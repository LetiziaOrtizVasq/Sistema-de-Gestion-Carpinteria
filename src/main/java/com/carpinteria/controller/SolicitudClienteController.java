package com.carpinteria.controller;

import com.carpinteria.model.SolicitudCliente;
import com.carpinteria.service.SolicitudClienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudClienteController {

    private final SolicitudClienteService service;

    public SolicitudClienteController(SolicitudClienteService service) {
        this.service = service;
    }

    // Listar todas las solicitudes
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("solicitudes", service.listarTodas());
        return "solicitud/lista";
    }

    // Mostrar formulario para nueva solicitud
    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("solicitud", new SolicitudCliente());
        model.addAttribute("titulo", "Nueva Solicitud");
        return "solicitud/formulario";
    }

    // Guardar nueva solicitud
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("solicitud") SolicitudCliente solicitud,
                          BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Nueva Solicitud");
            return "solicitud/formulario";
        }
        service.guardar(solicitud);
        return "redirect:/solicitudes";
    }

    // Mostrar formulario para editar
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("solicitud", service.buscarPorId(id));
        model.addAttribute("titulo", "Editar Solicitud");
        return "solicitud/formulario";
    }

    // Eliminar solicitud
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/solicitudes";
    }
}
