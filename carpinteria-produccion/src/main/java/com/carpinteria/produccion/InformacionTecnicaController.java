package com.carpinteria.produccion;

import com.carpinteria.produccion.InformacionTecnica;
import com.carpinteria.produccion.InformacionTecnicaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tecnica")
public class InformacionTecnicaController {

    private final InformacionTecnicaService service;

    public InformacionTecnicaController(InformacionTecnicaService service) { this.service = service; }

    // CU-06: Listar informaciones técnicas
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tecnicos", service.listarTodas());
        return "produccion/tecnica-lista";
    }

    @GetMapping("/nueva")
    public String nuevo(@RequestParam(required = false) Long solicitudId, Model model) {
        InformacionTecnica info = new InformacionTecnica();
        if (solicitudId != null) info.setSolicitud(service.buscarSolicitudPorId(solicitudId));
        model.addAttribute("info", info);
        model.addAttribute("solicitudes", service.solicitudesSinInfoTecnica());
        model.addAttribute("titulo", "Registrar Info Técnica");
        return "produccion/tecnica-form";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("info") InformacionTecnica info,
                          BindingResult r,
                          @RequestParam("solicitudId") Long solicitudId,
                          Model model) {
        if (r.hasErrors()) {
            model.addAttribute("solicitudes", service.solicitudesSinInfoTecnica());
            model.addAttribute("titulo", info.getId() == null ? "Registrar Info Técnica" : "Editar Info Técnica");
            return "produccion/tecnica-form";
        }
        info.setSolicitud(service.buscarSolicitudPorId(solicitudId));
        service.guardar(info);
        return "redirect:/tecnica";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        InformacionTecnica info = service.buscarPorId(id);
        model.addAttribute("info", info);
        model.addAttribute("solicitudes", service.solicitudesSinInfoTecnica());
        model.addAttribute("titulo", "Editar Info Técnica");
        return "produccion/tecnica-form";
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/tecnica";
    }
}
