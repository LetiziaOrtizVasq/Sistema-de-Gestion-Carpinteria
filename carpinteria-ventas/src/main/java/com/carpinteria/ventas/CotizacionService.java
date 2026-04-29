package com.carpinteria.ventas;

import com.carpinteria.ventas.Cotizacion;
import com.carpinteria.clientes.SolicitudCliente;
import com.carpinteria.ventas.CotizacionRepository;
import com.carpinteria.clientes.SolicitudClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CotizacionService {

    private final CotizacionRepository cotizacionRepository;
    private final SolicitudClienteRepository solicitudRepository;

    public CotizacionService(CotizacionRepository cotizacionRepository,
                             SolicitudClienteRepository solicitudRepository) {
        this.cotizacionRepository = cotizacionRepository;
        this.solicitudRepository = solicitudRepository;
    }

    public List<Cotizacion> listarTodas() {
        return cotizacionRepository.findAll();
    }

    public Cotizacion guardar(Cotizacion cotizacion) {
        return cotizacionRepository.save(cotizacion);
    }

    public Cotizacion buscarPorId(Long id) {
        return cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada con id: " + id));
    }

    public void eliminar(Long id) {
        cotizacionRepository.deleteById(id);
    }

    public List<SolicitudCliente> listarSolicitudes() {
        return solicitudRepository.findAll();
    }

    public SolicitudCliente buscarSolicitudPorId(Long id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con id: " + id));
    }
}
