package com.carpinteria.ventas;

import com.carpinteria.ventas.Cotizacion;
import com.carpinteria.ventas.CotizacionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cotizaciones")
public class CotizacionController {

    private final CotizacionService service;

    public CotizacionController(CotizacionService service) {
        this.service = service;
    }

    // CU-02: Listar cotizaciones
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cotizaciones", service.listarTodas());
        return "cotizacion/lista";
    }

    // CU-02: Mostrar formulario nueva cotización
    // Acepta parámetro opcional solicitudId para pre-cargar desde CU-01
    @GetMapping("/nueva")
    public String mostrarFormulario(@RequestParam(required = false) Long solicitudId, Model model) {
        Cotizacion cotizacion = new Cotizacion();
        if (solicitudId != null) {
            cotizacion.setSolicitud(service.buscarSolicitudPorId(solicitudId));
        }
        model.addAttribute("cotizacion", cotizacion);
        model.addAttribute("solicitudes", service.listarSolicitudes());
        model.addAttribute("titulo", "Nueva Cotización");
        return "cotizacion/formulario";
    }

    // CU-02: Guardar cotización
    @PostMapping
    public String guardar(@Valid @ModelAttribute("cotizacion") Cotizacion cotizacion,
                          BindingResult result,
                          @RequestParam("solicitudId") Long solicitudId,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("solicitudes", service.listarSolicitudes());
            model.addAttribute("titulo", cotizacion.getId() == null ? "Nueva Cotización" : "Editar Cotización");
            return "cotizacion/formulario";
        }
        cotizacion.setSolicitud(service.buscarSolicitudPorId(solicitudId));
        service.guardar(cotizacion);
        return "redirect:/cotizaciones";
    }

    // CU-02: Editar cotización
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("cotizacion", service.buscarPorId(id));
        model.addAttribute("solicitudes", service.listarSolicitudes());
        model.addAttribute("titulo", "Editar Cotización");
        return "cotizacion/formulario";
    }

    // CU-03: Registrar aceptación de cotización
    @GetMapping("/{id}/aceptar")
    public String aceptar(@PathVariable Long id) {
        Cotizacion cotizacion = service.buscarPorId(id);
        cotizacion.setEstado("ACEPTADA");
        service.guardar(cotizacion);
        return "redirect:/cotizaciones";
    }

    // CU-03: Registrar rechazo de cotización
    @GetMapping("/{id}/rechazar")
    public String rechazar(@PathVariable Long id) {
        Cotizacion cotizacion = service.buscarPorId(id);
        cotizacion.setEstado("RECHAZADA");
        service.guardar(cotizacion);
        return "redirect:/cotizaciones";
    }

    // CU-02: Eliminar cotización
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/cotizaciones";
    }
}
