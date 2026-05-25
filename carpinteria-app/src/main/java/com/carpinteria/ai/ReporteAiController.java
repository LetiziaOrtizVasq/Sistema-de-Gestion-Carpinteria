package com.carpinteria.ai;

import com.carpinteria.pagos.PagoFinal;
import com.carpinteria.reportes.ReporteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class ReporteAiController {

    private final ClaudeService claude;
    private final ReporteService reporteService;
    private final ObjectMapper objectMapper;

    public ReporteAiController(ClaudeService claude, ReporteService reporteService, ObjectMapper objectMapper) {
        this.claude = claude;
        this.reporteService = reporteService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/reporte/resumen")
    public ResponseEntity<?> generarResumen(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        try {
            List<PagoFinal> pedidos = reporteService.pedidosEnPeriodo(desde, hasta);
            BigDecimal facturacion = pedidos.stream()
                    .map(PagoFinal::getMontoPagado)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Map<String, BigDecimal> consumo = reporteService.consumoPorTipo(desde, hasta);
            List<Map.Entry<String, Long>> clientes = reporteService.clientesFrecuentes();

            String prompt = construirPrompt(desde, hasta, pedidos.size(), facturacion, consumo, clientes);
            String respuesta = claude.preguntar(prompt).trim()
                    .replaceAll("```json\\s*", "").replaceAll("```\\s*", "").trim();

            int inicio = respuesta.indexOf('{');
            int fin = respuesta.lastIndexOf('}') + 1;
            if (inicio < 0 || fin <= inicio) {
                return ResponseEntity.ok(Map.of("resumen", respuesta));
            }

            Map<String, Object> resultado = objectMapper.readValue(respuesta.substring(inicio, fin), Map.class);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    private String construirPrompt(LocalDate desde, LocalDate hasta, int numPedidos,
                                    BigDecimal facturacion, Map<String, BigDecimal> consumo,
                                    List<Map.Entry<String, Long>> clientes) {
        StringBuilder sb = new StringBuilder();
        sb.append("Eres un analista de negocios para una carpintería artesanal boliviana. ");
        sb.append("Genera un resumen ejecutivo breve y útil en español.\n\n");

        sb.append("DATOS DEL PERÍODO ").append(desde).append(" al ").append(hasta).append(":\n");
        sb.append("- Pedidos completados y cobrados: ").append(numPedidos).append("\n");
        sb.append("- Facturación total: Bs. ").append(facturacion).append("\n");

        if (!consumo.isEmpty()) {
            sb.append("- Consumo de madera por tipo:\n");
            consumo.forEach((tipo, cant) ->
                    sb.append("  · ").append(tipo).append(": ").append(cant).append(" unidades\n"));
        } else {
            sb.append("- Sin consumo de madera registrado en el período.\n");
        }

        if (!clientes.isEmpty()) {
            sb.append("- Clientes más frecuentes: ");
            clientes.stream().limit(3)
                    .forEach(e -> sb.append(e.getKey()).append(" (").append(e.getValue()).append(" pedidos), "));
            sb.append("\n");
        }

        sb.append("\nResponde UNICAMENTE con este JSON, sin texto extra:\n");
        sb.append("{\"resumen\": \"<2 a 3 párrafos: análisis del período, fortalezas, áreas de atención y una recomendación concreta>\"}");
        return sb.toString();
    }
}
