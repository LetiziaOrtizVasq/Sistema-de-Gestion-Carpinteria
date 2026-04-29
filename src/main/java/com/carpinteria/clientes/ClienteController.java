package com.carpinteria.clientes;

import com.carpinteria.clientes.Cliente;
import com.carpinteria.clientes.ClienteService;
import com.carpinteria.clientes.SolicitudClienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final SolicitudClienteService solicitudService;

    public ClienteController(ClienteService clienteService, SolicitudClienteService solicitudService) {
        this.clienteService = clienteService;
        this.solicitudService = solicitudService;
    }

    // CU-04/05: Listar clientes con búsqueda por nombre/CI
    @GetMapping
    public String listar(@RequestParam(required = false) String buscar, Model model) {
        model.addAttribute("clientes", clienteService.buscar(buscar));
        model.addAttribute("buscar", buscar != null ? buscar : "");
        return "cliente/lista";
    }

    // CU-04: Mostrar formulario nuevo cliente
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("titulo", "Registrar Cliente");
        return "cliente/formulario";
    }

    // CU-04: Guardar nuevo cliente
    @PostMapping
    public String guardar(@Valid @ModelAttribute("cliente") Cliente cliente,
                          BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", cliente.getId() == null ? "Registrar Cliente" : "Editar Cliente");
            return "cliente/formulario";
        }
        clienteService.guardar(cliente);
        return "redirect:/clientes";
    }

    // CU-05: Mostrar formulario editar cliente
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", clienteService.buscarPorId(id));
        model.addAttribute("titulo", "Editar Cliente");
        return "cliente/formulario";
    }

    // CU-07: Ver historial de solicitudes del cliente
    @GetMapping("/{id}/historial")
    public String historial(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.buscarPorId(id);
        model.addAttribute("cliente", cliente);
        model.addAttribute("solicitudes", solicitudService.listarPorNombreCliente(cliente.getNombreCompleto()));
        return "cliente/historial";
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return "redirect:/clientes";
    }
}
