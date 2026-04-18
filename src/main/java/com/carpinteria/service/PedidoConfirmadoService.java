package com.carpinteria.service;

import com.carpinteria.model.Cotizacion;
import com.carpinteria.model.PedidoConfirmado;
import com.carpinteria.model.SolicitudCliente;
import com.carpinteria.repository.CotizacionRepository;
import com.carpinteria.repository.PedidoConfirmadoRepository;
import com.carpinteria.repository.SolicitudClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoConfirmadoService {

    private final PedidoConfirmadoRepository pedidoRepository;
    private final CotizacionRepository cotizacionRepository;
    private final SolicitudClienteRepository solicitudRepository;

    public PedidoConfirmadoService(PedidoConfirmadoRepository pedidoRepository,
                                   CotizacionRepository cotizacionRepository,
                                   SolicitudClienteRepository solicitudRepository) {
        this.pedidoRepository = pedidoRepository;
        this.cotizacionRepository = cotizacionRepository;
        this.solicitudRepository = solicitudRepository;
    }

    public List<PedidoConfirmado> listarTodos() {
        return pedidoRepository.findAll();
    }

    public PedidoConfirmado buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
    }

    // CU-08: Registrar pedido confirmado
    public PedidoConfirmado guardar(PedidoConfirmado pedido) {
        return pedidoRepository.save(pedido);
    }

    public void eliminar(Long id) {
        pedidoRepository.deleteById(id);
    }

    public List<Cotizacion> listarCotizacionesAceptadas() {
        return cotizacionRepository.findAll().stream()
                .filter(c -> "ACEPTADA".equals(c.getEstado()))
                .filter(c -> !pedidoRepository.existsByCotizacionId(c.getId()))
                .toList();
    }

    public Cotizacion buscarCotizacionPorId(Long id) {
        return cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada con id: " + id));
    }

    // Duplicar pedido: crea nueva solicitud, cotización y pedido con los mismos datos
    public PedidoConfirmado duplicar(Long id) {
        PedidoConfirmado original = buscarPorId(id);
        Cotizacion cotOrig = original.getCotizacion();
        SolicitudCliente solOrig = cotOrig.getSolicitud();

        SolicitudCliente nuevaSol = new SolicitudCliente();
        nuevaSol.setNombreCliente(solOrig.getNombreCliente());
        nuevaSol.setTelefono(solOrig.getTelefono());
        nuevaSol.setTipoProducto(solOrig.getTipoProducto());
        nuevaSol.setDescripcion(solOrig.getDescripcion());
        nuevaSol.setDimensionesAproximadas(solOrig.getDimensionesAproximadas());
        nuevaSol.setMaterialPreferido(solOrig.getMaterialPreferido());
        solicitudRepository.save(nuevaSol);

        Cotizacion nuevaCot = new Cotizacion();
        nuevaCot.setSolicitud(nuevaSol);
        nuevaCot.setPrecioMateriales(cotOrig.getPrecioMateriales());
        nuevaCot.setPrecioManoObra(cotOrig.getPrecioManoObra());
        nuevaCot.setObservaciones(cotOrig.getObservaciones());
        nuevaCot.setEstado("ACEPTADA");
        cotizacionRepository.save(nuevaCot);

        PedidoConfirmado nuevo = new PedidoConfirmado();
        nuevo.setCotizacion(nuevaCot);
        nuevo.setFechaEntregaEstimada(original.getFechaEntregaEstimada());
        nuevo.setObservaciones("Duplicado del pedido #" + original.getId());
        return pedidoRepository.save(nuevo);
    }

    // Cancelar pedido
    public void cancelar(Long id) {
        PedidoConfirmado pedido = buscarPorId(id);
        pedido.setEstado("CANCELADO");
        pedidoRepository.save(pedido);
    }
}
