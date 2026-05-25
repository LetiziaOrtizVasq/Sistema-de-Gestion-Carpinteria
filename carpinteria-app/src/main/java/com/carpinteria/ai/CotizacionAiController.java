package com.carpinteria.ai;

import com.carpinteria.clientes.SolicitudCliente;
import com.carpinteria.clientes.SolicitudClienteRepository;
import com.carpinteria.ventas.Cotizacion;
import com.carpinteria.ventas.CotizacionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ai")
public class CotizacionAiController {

    private final ClaudeService claude;
    private final SolicitudClienteRepository solicitudRepo;
    private final CotizacionRepository cotizacionRepo;
    private final ObjectMapper objectMapper;

    public CotizacionAiController(ClaudeService claude,
                                   SolicitudClienteRepository solicitudRepo,
                                   CotizacionRepository cotizacionRepo,
                                   ObjectMapper objectMapper) {
        this.claude = claude;
        this.solicitudRepo = solicitudRepo;
        this.cotizacionRepo = cotizacionRepo;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/cotizacion/sugerir")
    public ResponseEntity<?> sugerirPrecio(@RequestParam Long solicitudId) {
        try {
            SolicitudCliente solicitud = solicitudRepo.findById(solicitudId)
                    .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

            List<Cotizacion> historial = cotizacionRepo.findAll().stream()
                    .filter(c -> "ACEPTADA".equals(c.getEstado()))
                    .limit(15)
                    .collect(Collectors.toList());

            String prompt = construirPromptCotizacion(solicitud, historial);
            String respuesta = claude.preguntar(prompt).trim();

            int inicio = respuesta.indexOf('{');
            int fin = respuesta.lastIndexOf('}') + 1;
            if (inicio < 0 || fin <= inicio) {
                return ResponseEntity.internalServerError()
                        .body(Map.of("error", "Respuesta inesperada del modelo"));
            }

            Map<String, Object> resultado = objectMapper.readValue(respuesta.substring(inicio, fin), Map.class);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    private String construirPromptCotizacion(SolicitudCliente solicitud, List<Cotizacion> historial) {
        StringBuilder sb = new StringBuilder();
        sb.append("Eres un experto en costos de carpintería en Bolivia (precios en bolivianos). ");
        sb.append("Sugiere precio de materiales y mano de obra para esta solicitud.\n\n");

        sb.append("SOLICITUD:\n");
        sb.append("- Cliente: ").append(solicitud.getNombreCliente()).append("\n");
        solicitud.getItems().forEach(item -> {
            sb.append("- Producto: ").append(item.getTipoProducto()).append(", cantidad: ").append(item.getCantidad()).append("\n");
            if (item.getDescripcion() != null && !item.getDescripcion().isBlank())
                sb.append("  Descripcion: ").append(item.getDescripcion()).append("\n");
            if (item.getDimensionesAproximadas() != null && !item.getDimensionesAproximadas().isBlank())
                sb.append("  Dimensiones: ").append(item.getDimensionesAproximadas()).append("\n");
            if (item.getMaterialPreferido() != null && !item.getMaterialPreferido().isBlank())
                sb.append("  Material preferido: ").append(item.getMaterialPreferido()).append("\n");
        });

        if (!historial.isEmpty()) {
            sb.append("\nREFERENCIA (cotizaciones aceptadas anteriores):\n");
            historial.forEach(c -> sb.append("- ")
                    .append(c.getSolicitud().getResumenProductos())
                    .append(": materiales Bs.").append(c.getPrecioMateriales())
                    .append(", mano de obra Bs.").append(c.getPrecioManoObra())
                    .append(", total Bs.").append(c.getPrecioTotal()).append("\n"));
        }

        sb.append("\nResponde UNICAMENTE con JSON, sin texto adicional:\n");
        sb.append("{\"precioMateriales\": <numero>, \"precioManoObra\": <numero>, \"justificacion\": \"<max 80 chars>\"}");
        return sb.toString();
    }
}
