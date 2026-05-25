package com.carpinteria.produccion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/imagenes")
public class ImagenPedidoController {

    private final ImagenPedidoService service;

    public ImagenPedidoController(ImagenPedidoService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) Long pedidoId, Model model) {
        if (pedidoId != null) {
            model.addAttribute("imagenes", service.listarPorPedido(pedidoId));
            model.addAttribute("pedidoFiltro", pedidoId);
        } else {
            model.addAttribute("imagenes", service.listarTodas());
        }
        model.addAttribute("pedidos", service.listarPedidos());
        return "imagenes/lista";
    }

    @PostMapping("/subir")
    public String subir(@RequestParam Long pedidoId,
                        @RequestParam("archivos") MultipartFile[] archivos,
                        @RequestParam(required = false) String descripcion,
                        RedirectAttributes ra) {
        int subidas = 0;
        int errores = 0;
        for (MultipartFile file : archivos) {
            if (!file.isEmpty()) {
                try {
                    service.guardar(file, pedidoId, descripcion);
                    subidas++;
                } catch (IOException e) {
                    errores++;
                }
            }
        }
        if (subidas > 0) ra.addFlashAttribute("msgOk", subidas + " archivo(s) subido(s) correctamente.");
        if (errores > 0) ra.addFlashAttribute("msgErr", errores + " archivo(s) no se pudieron subir.");
        return "redirect:/imagenes?pedidoId=" + pedidoId;
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id,
                           @RequestParam(required = false) Long pedidoId) {
        service.eliminar(id);
        if (pedidoId != null) return "redirect:/imagenes?pedidoId=" + pedidoId;
        return "redirect:/imagenes";
    }
}
