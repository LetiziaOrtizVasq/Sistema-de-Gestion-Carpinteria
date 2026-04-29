package com.carpinteria.clientes;

import com.carpinteria.clientes.ItemSolicitud;
import com.carpinteria.clientes.SolicitudCliente;
import com.carpinteria.clientes.SolicitudClienteService;
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

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("solicitudes", service.listarTodas());
        return "solicitud/lista";
    }

    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("solicitud", new SolicitudCliente());
        model.addAttribute("titulo", "Nueva Solicitud");
        return "solicitud/formulario";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("solicitud") SolicitudCliente solicitud,
                          BindingResult result, Model model,
                          @RequestParam(name = "iTipo",  required = false) String[] tipos,
                          @RequestParam(name = "iDesc",  required = false) String[] descs,
                          @RequestParam(name = "iCant",  required = false) Integer[] cants,
                          @RequestParam(name = "iDim",   required = false) String[] dims,
                          @RequestParam(name = "iMat",   required = false) String[] mats) {

        solicitud.clearItems();
        if (tipos != null) {
            for (int i = 0; i < tipos.length; i++) {
                if (tipos[i] != null && !tipos[i].isBlank()) {
                    ItemSolicitud item = new ItemSolicitud();
                    item.setTipoProducto(tipos[i]);
                    item.setDescripcion(descs != null && descs.length > i ? descs[i] : null);
                    item.setCantidad(cants != null && cants.length > i && cants[i] != null ? cants[i] : 1);
                    item.setDimensionesAproximadas(dims != null && dims.length > i ? dims[i] : null);
                    item.setMaterialPreferido(mats != null && mats.length > i ? mats[i] : null);
                    solicitud.agregarItem(item);
                }
            }
        }

        boolean sinArticulos = solicitud.getItems().isEmpty();
        if (result.hasErrors() || sinArticulos) {
            if (sinArticulos) model.addAttribute("itemError", "Debe agregar al menos un artículo");
            model.addAttribute("titulo", solicitud.getId() == null ? "Nueva Solicitud" : "Editar Solicitud");
            return "solicitud/formulario";
        }

        service.guardar(solicitud);
        return "redirect:/solicitudes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("solicitud", service.buscarPorId(id));
        model.addAttribute("titulo", "Editar Solicitud");
        return "solicitud/formulario";
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/solicitudes";
    }
}
