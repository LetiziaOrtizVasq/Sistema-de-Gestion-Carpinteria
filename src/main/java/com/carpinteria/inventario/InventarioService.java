package com.carpinteria.inventario;

import com.carpinteria.ventas.PedidoConfirmado;
import com.carpinteria.ventas.PedidoConfirmadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class InventarioService {

    private final StockMaderaRepository stockRepo;
    private final MovimientoMaderaRepository movRepo;
    private final ProveedorRepository provRepo;
    private final PedidoConfirmadoRepository pedidoRepo;

    public InventarioService(StockMaderaRepository stockRepo,
                             MovimientoMaderaRepository movRepo,
                             ProveedorRepository provRepo,
                             PedidoConfirmadoRepository pedidoRepo) {
        this.stockRepo = stockRepo;
        this.movRepo   = movRepo;
        this.provRepo  = provRepo;
        this.pedidoRepo = pedidoRepo;
    }

    // --- Stock ---
    public List<StockMadera> listarStock() { return stockRepo.findAll(); }
    public List<StockMadera> stockCritico() { return stockRepo.findStockCritico(); }

    public StockMadera guardarStock(StockMadera s) { return stockRepo.save(s); }

    public StockMadera buscarStockPorId(Long id) {
        return stockRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock no encontrado: " + id));
    }

    public void eliminarStock(Long id) { stockRepo.deleteById(id); }

    // --- Movimientos ---
    public List<MovimientoMadera> listarMovimientos() { return movRepo.findAll(); }

    @Transactional
    public MovimientoMadera registrarIngreso(Long stockId, Long proveedorId,
                                             BigDecimal cantidad, String obs) {
        StockMadera stock = buscarStockPorId(stockId);
        stock.setCantidadDisponible(stock.getCantidadDisponible().add(cantidad));
        stockRepo.save(stock);

        MovimientoMadera mov = new MovimientoMadera();
        mov.setStockMadera(stock);
        mov.setTipoMovimiento("INGRESO");
        mov.setCantidad(cantidad);
        mov.setObservaciones(obs);
        if (proveedorId != null) mov.setProveedor(provRepo.findById(proveedorId).orElse(null));
        return movRepo.save(mov);
    }

    @Transactional
    public MovimientoMadera registrarConsumo(Long stockId, Long pedidoId,
                                              BigDecimal cantidad, String obs) {
        StockMadera stock = buscarStockPorId(stockId);
        stock.setCantidadDisponible(stock.getCantidadDisponible().subtract(cantidad));
        stockRepo.save(stock);

        MovimientoMadera mov = new MovimientoMadera();
        mov.setStockMadera(stock);
        mov.setTipoMovimiento("CONSUMO");
        mov.setCantidad(cantidad);
        mov.setObservaciones(obs);
        if (pedidoId != null) mov.setPedido(pedidoRepo.findById(pedidoId).orElse(null));
        return movRepo.save(mov);
    }

    @Transactional
    public MovimientoMadera registrarReposicion(Long stockId, BigDecimal cantidad, String obs) {
        StockMadera stock = buscarStockPorId(stockId);
        stock.setCantidadDisponible(stock.getCantidadDisponible().add(cantidad));
        stockRepo.save(stock);

        MovimientoMadera mov = new MovimientoMadera();
        mov.setStockMadera(stock);
        mov.setTipoMovimiento("REPOSICION");
        mov.setCantidad(cantidad);
        mov.setObservaciones(obs);
        return movRepo.save(mov);
    }

    public List<Proveedor> listarProveedoresActivos() { return provRepo.findByActivoTrue(); }
    public List<PedidoConfirmado> listarPedidos()     { return pedidoRepo.findAll(); }

    // CU-26
    public List<MovimientoMadera> consumosEnPeriodo(LocalDate desde, LocalDate hasta) {
        return movRepo.findConsumosEnPeriodo(desde, hasta);
    }
}
