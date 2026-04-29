package com.carpinteria.inventario;

import com.carpinteria.inventario.StockMadera;
import com.carpinteria.inventario.InventarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService service;

    public InventarioController(InventarioService service) { this.service = service; }

    // CU-10 / CU-11: Consultar disponibilidad de madera + alertas de stock mínimo
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("stocks", service.listarStock());
        model.addAttribute("alertas", service.stockCritico());
        return "inventario/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("stock", new StockMadera());
        model.addAttribute("titulo", "Registrar Madera");
        return "inventario/form";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("stock") StockMadera s,
                          BindingResult r, Model model) {
        if (r.hasErrors()) { model.addAttribute("titulo", s.getId() == null ? "Registrar Madera" : "Editar Madera"); return "inventario/form"; }
        service.guardarStock(s);
        return "redirect:/inventario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("stock", service.buscarStockPorId(id));
        model.addAttribute("titulo", "Editar Madera");
        return "inventario/form";
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminarStock(id);
        return "redirect:/inventario";
    }

    // CU-21: Ingreso de madera desde proveedor
    @GetMapping("/ingreso")
    public String formIngreso(Model model) {
        model.addAttribute("stocks", service.listarStock());
        model.addAttribute("proveedores", service.listarProveedoresActivos());
        return "inventario/mov-ingreso";
    }

    @PostMapping("/ingreso")
    public String registrarIngreso(@RequestParam Long stockId,
                                   @RequestParam(required = false) Long proveedorId,
                                   @RequestParam BigDecimal cantidad,
                                   @RequestParam(required = false) String observaciones) {
        service.registrarIngreso(stockId, proveedorId, cantidad, observaciones);
        return "redirect:/inventario";
    }

    // CU-23: Consumo de madera por pedido
    @GetMapping("/consumo")
    public String formConsumo(Model model) {
        model.addAttribute("stocks", service.listarStock());
        model.addAttribute("pedidos", service.listarPedidos());
        return "inventario/mov-consumo";
    }

    @PostMapping("/consumo")
    public String registrarConsumo(@RequestParam Long stockId,
                                   @RequestParam(required = false) Long pedidoId,
                                   @RequestParam BigDecimal cantidad,
                                   @RequestParam(required = false) String observaciones) {
        service.registrarConsumo(stockId, pedidoId, cantidad, observaciones);
        return "redirect:/inventario";
    }

    // CU-20: Reposición de madera
    @GetMapping("/reposicion")
    public String formReposicion(Model model) {
        model.addAttribute("stocks", service.listarStock());
        return "inventario/mov-reposicion";
    }

    @PostMapping("/reposicion")
    public String registrarReposicion(@RequestParam Long stockId,
                                      @RequestParam BigDecimal cantidad,
                                      @RequestParam(required = false) String observaciones) {
        service.registrarReposicion(stockId, cantidad, observaciones);
        return "redirect:/inventario";
    }

    // Historial de movimientos
    @GetMapping("/movimientos")
    public String movimientos(Model model) {
        model.addAttribute("movimientos", service.listarMovimientos());
        return "inventario/movimientos";
    }
}
