package com.carpinteria.produccion;

import com.carpinteria.ventas.PedidoConfirmadoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ImagenPedidoService {

    @Value("${carpinteria.upload.dir:${user.home}/carpinteria-uploads}")
    private String uploadDir;

    private final ImagenPedidoRepository repository;
    private final PedidoConfirmadoRepository pedidoRepository;

    public ImagenPedidoService(ImagenPedidoRepository repository,
                               PedidoConfirmadoRepository pedidoRepository) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
    }

    public ImagenPedido guardar(MultipartFile file, Long pedidoId, String descripcion) throws IOException {
        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String nombreGuardado = UUID.randomUUID() + (ext != null ? "." + ext : "");

        Path dir = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(dir);
        Files.copy(file.getInputStream(), dir.resolve(nombreGuardado));

        ImagenPedido img = new ImagenPedido();
        img.setPedido(pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + pedidoId)));
        img.setNombreOriginal(file.getOriginalFilename());
        img.setNombreGuardado(nombreGuardado);
        img.setTipoContenido(file.getContentType() != null ? file.getContentType() : "application/octet-stream");
        img.setTamanio(file.getSize());
        img.setDescripcion(descripcion != null && !descripcion.isBlank() ? descripcion : null);
        return repository.save(img);
    }

    public List<ImagenPedido> listarPorPedido(Long pedidoId) {
        return repository.findByPedidoIdOrderByFechaSubidaDesc(pedidoId);
    }

    public List<ImagenPedido> listarTodas() {
        return repository.findAllByOrderByFechaSubidaDesc();
    }

    public List<com.carpinteria.ventas.PedidoConfirmado> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public void eliminar(Long id) {
        ImagenPedido img = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imagen no encontrada: " + id));
        try {
            Path f = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(img.getNombreGuardado());
            Files.deleteIfExists(f);
        } catch (IOException ignored) {}
        repository.deleteById(id);
    }
}
