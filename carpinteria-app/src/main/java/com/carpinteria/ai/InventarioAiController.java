package com.carpinteria.ai;

import com.carpinteria.inventario.MovimientoMadera;
import com.carpinteria.inventario.MovimientoMaderaRepository;
import com.carpinteria.inventario.StockMadera;
import com.carpinteria.inventario.StockMaderaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class InventarioAiController {

    private final ClaudeService claude;
    private final StockMaderaRepository stockRepo;
    private final MovimientoMaderaRepository movimientoRepo;
    private final ObjectMapper objectMapper;

    public InventarioAiController(ClaudeService claude,
                                   StockMaderaRepository stockRepo,
                                   MovimientoMaderaRepository movimientoRepo,
                                   ObjectMapper objectMapper) {
        this.claude = claude;
        this.stockRepo = stockRepo;
        this.movimientoRepo = movimientoRepo;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/inventario/prediccion")
    public ResponseEntity<?> predecirAgotamiento() {
        try {
            List<StockMadera> stocks = stockRepo.findAll();
            if (stocks.isEmpty()) return ResponseEntity.ok(List.of());

            List<MovimientoMadera> consumos = movimientoRepo
                    .findConsumosEnPeriodo(LocalDate.now().minusDays(90), LocalDate.now());

            String prompt = construirPromptInventario(stocks, consumos);
            String respuesta = claude.preguntar(prompt).trim();

            int inicio = respuesta.indexOf('[');
            int fin = respuesta.lastIndexOf(']') + 1;
            if (inicio < 0 || fin <= inicio) {
                return ResponseEntity.internalServerError()
                        .body(Map.of("error", "Respuesta inesperada del modelo"));
            }

            List<Map<String, Object>> resultado = objectMapper.readValue(
                    respuesta.substring(inicio, fin), List.class);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    private String construirPromptInventario(List<StockMadera> stocks, List<MovimientoMadera> consumos) {
        StringBuilder sb = new StringBuilder();
        sb.append("Eres un experto en gestion de inventario para carpinteria. ");
        sb.append("Predice en cuantos dias se agotara cada tipo de madera.\n\n");

        sb.append("STOCK ACTUAL:\n");
        stocks.forEach(s -> sb.append("- id:").append(s.getId())
                .append(", tipo:").append(s.getTipoMadera())
                .append(", disponible:").append(s.getCantidadDisponible())
                .append(", minimo:").append(s.getStockMinimo())
                .append(", unidad:").append(s.getUnidad()).append("\n"));

        sb.append("\nCONSUMOS ULTIMOS 90 DIAS:\n");
        if (consumos.isEmpty()) {
            sb.append("Sin consumos registrados.\n");
        } else {
            consumos.forEach(m -> sb.append("- ")
                    .append(m.getFecha()).append(": ")
                    .append(m.getStockMadera().getTipoMadera())
                    .append(" ").append(m.getCantidad())
                    .append(" ").append(m.getStockMadera().getUnidad()).append("\n"));
        }

        sb.append("\nPara cada stock calcula la tasa de consumo diaria y estima dias hasta agotarse. ");
        sb.append("Nivel: URGENTE (<=7 dias o ya critico), PRONTO (8-30 dias), ESTABLE (>30 dias), SIN_DATOS (sin consumos).\n\n");
        sb.append("Responde UNICAMENTE con JSON array, sin texto adicional:\n");
        sb.append("[{\"stockId\":<id>,\"tipoMadera\":\"<nombre>\",\"diasEstimados\":<numero o null>,");
        sb.append("\"nivel\":\"URGENTE|PRONTO|ESTABLE|SIN_DATOS\",\"recomendacion\":\"<max 70 chars>\"}]");
        return sb.toString();
    }
}
