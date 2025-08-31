package co.edu.uniquindio.mundoComputo.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniquindio.mundoComputo.dtos.productos.CreateProductoDTO;
import co.edu.uniquindio.mundoComputo.dtos.productos.ProductoInfoDTO;
import co.edu.uniquindio.mundoComputo.dtos.productos.UpdateProductoDTO;
import co.edu.uniquindio.mundoComputo.model.CategoriaProducto;
import co.edu.uniquindio.mundoComputo.model.HistoricoInventario;
import co.edu.uniquindio.mundoComputo.model.Producto;
import co.edu.uniquindio.mundoComputo.model.Rol;
import co.edu.uniquindio.mundoComputo.model.TemplateEmailType;
import co.edu.uniquindio.mundoComputo.model.TipoMovimientoInventario;
import co.edu.uniquindio.mundoComputo.model.Usuario;
import co.edu.uniquindio.mundoComputo.repositories.ProductoRepository;
import co.edu.uniquindio.mundoComputo.repositories.UsuarioRepository;
import co.edu.uniquindio.mundoComputo.repositories.CategoriaProductoRepository;
import co.edu.uniquindio.mundoComputo.repositories.HistoricoInventarioRepository;
import co.edu.uniquindio.mundoComputo.services.EmailService;
import co.edu.uniquindio.mundoComputo.services.ProductoService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaProductoRepository categoriaRepository;
    private final EmailService emailService;
    private final HistoricoInventarioRepository historicoInventarioRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public void createProducto(CreateProductoDTO createProductoDTO) throws Exception {

        if (productoRepository.existsByNombreIgnoreCase(createProductoDTO.nombre())) {
            throw new Exception(String.format("El producto con nombre: %s ya existe", createProductoDTO.nombre()));
        }

        CategoriaProducto categoria = getCategoriaById(createProductoDTO.categoriaId());

        Producto producto = Producto.builder()
                .nombre(createProductoDTO.nombre())
                .descripcion(createProductoDTO.descripcion())
                .precio(createProductoDTO.precio())
                .stock(createProductoDTO.stock())
                .stockMinimo(createProductoDTO.stockMinimo())
                .categoria(categoria)
                .build();

        productoRepository.save(producto);
    }

    @Override
    public void updateProducto(UpdateProductoDTO updateProductoDTO) throws Exception {

        Producto producto = getProductoById(updateProductoDTO.id());
        CategoriaProducto categoria = getCategoriaById(updateProductoDTO.categoriaId());

        producto.setNombre(updateProductoDTO.nombre());
        producto.setDescripcion(updateProductoDTO.descripcion());
        producto.setPrecio(updateProductoDTO.precio());
        producto.setStockMinimo(updateProductoDTO.stockMinimo());
        producto.setCategoria(categoria);

        productoRepository.save(producto);

        sendAdminNotification("Producto actualizado", "El producto " + producto.getNombre() + " ha sido actualizado.");
    }
    
    @Override
    public void deleteProducto(Long id) throws Exception {
        Producto producto = getProductoById(id);
        productoRepository.delete(producto);
        sendAdminNotification("Producto eliminado", "El producto " + producto.getNombre() + " ha sido eliminado.");
    }

    @Override
    public ProductoInfoDTO getProductoInfoById(Long id) throws Exception {
        Producto producto = getProductoById(id);
        return mapToInfoDTO(producto);
    }

    @Override
    public List<ProductoInfoDTO> getAllProductos() throws Exception {
        List<Producto> productos = productoRepository.findAll();

        return productos.stream().map(this::mapToInfoDTO).toList();
    }

    @Override
    public void recordOutput(Long id, Long userId, int cantidad, String description) throws Exception {
        Producto producto = getProductoById(id);

        int nuevoStock = producto.getStock() - cantidad;

        if(nuevoStock < 0) {
            throw new Exception(String.format("No hay suficiente stock para el producto con id: %s", id));
        }
        
        if(nuevoStock < producto.getStockMinimo()) {
            sendAdminNotification("Alerta de stock bajo", "El producto " + producto.getNombre() + " ha alcanzado un stock bajo.");
        }

        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new Exception(String.format("Usuario con id: %s no encontrado", userId)));

        HistoricoInventario historico = HistoricoInventario.builder()
                .producto(producto)
                .fecha(LocalDateTime.now())
                .cantidadAnterior(producto.getStock())
                .cantidadNueva(nuevoStock)
                .tipoMovimiento(TipoMovimientoInventario.SALIDA)
                .comentario(description)
                .usuario(usuario)
                .build();

        historicoInventarioRepository.save(historico);

        producto.setStock(nuevoStock);
        productoRepository.save(producto);

        String message = String.format("Se ha registrado una salida del producto %s. Stock actual: %d", producto.getNombre(), nuevoStock);
        sendAdminNotification("Salida de producto", message);
    }

    @Override
    public void recordInput(Long id, Long userId, int cantidad, String description) throws Exception {
        Producto producto = getProductoById(id);

        int nuevoStock = producto.getStock() + cantidad;

        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new Exception(String.format("Usuario con id: %s no encontrado", userId)));

        HistoricoInventario historico = HistoricoInventario.builder()
                .producto(producto)
                .fecha(LocalDateTime.now())
                .cantidadAnterior(producto.getStock())
                .cantidadNueva(nuevoStock)
                .tipoMovimiento(TipoMovimientoInventario.ENTRADA)
                .comentario(description)
                .usuario(usuario)
                .build();

        historicoInventarioRepository.save(historico);

        producto.setStock(nuevoStock);
        productoRepository.save(producto);

        String message = String.format("Se ha registrado una entrada del producto %s. Stock actual: %d", producto.getNombre(), nuevoStock);
        sendAdminNotification("Entrada de producto", message);
    }

    @Override
    public void recordAdjust(Long id, Long userId, int cantidad, String description) throws Exception {
        Producto producto = getProductoById(id);

        int nuevoStock = cantidad;

        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new Exception(String.format("Usuario con id: %s no encontrado", userId)));

        HistoricoInventario historico = HistoricoInventario.builder()
                .producto(producto)
                .fecha(LocalDateTime.now())
                .cantidadAnterior(producto.getStock())
                .cantidadNueva(nuevoStock)
                .tipoMovimiento(TipoMovimientoInventario.AJUSTE)
                .comentario(description)
                .usuario(usuario)
                .build();

        historicoInventarioRepository.save(historico);

        producto.setStock(nuevoStock);
        productoRepository.save(producto);

        String message = String.format("Se ha registrado un ajuste del producto %s. Stock actual: %d", producto.getNombre(), nuevoStock);
        sendAdminNotification("Ajuste de producto", message);
    }

    private CategoriaProducto getCategoriaById(Long categoriaId) throws Exception {
        Optional<CategoriaProducto> categoriaOptional = categoriaRepository.findById(categoriaId);
        if (categoriaOptional.isEmpty()) {
            throw new Exception(String.format("La categoria con id: %s no existe", categoriaId));
        }
        return categoriaOptional.get();
    }

    private Producto getProductoById(Long id) throws Exception {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if (productoOptional.isEmpty()) {
            throw new Exception(String.format("El producto con id: %s no existe", id));
        }
        return productoOptional.get();
    }

    private ProductoInfoDTO mapToInfoDTO(Producto producto) {
        return ProductoInfoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .stockMinimo(producto.getStockMinimo())
                .categoriaId(producto.getCategoria().getId())
                .build();
    }

    @Override
    public List<ProductoInfoDTO> getProductosByCategoria(Long categoriaId) throws Exception {
        CategoriaProducto categoria = getCategoriaById(categoriaId);
        List<Producto> productos = productoRepository.findByCategoria(categoria);
        return productos.stream().map(this::mapToInfoDTO).toList();
    }

    @Override
    public List<HistoricoInventario> getHistorico(Long productoId) throws Exception {
        Producto producto = getProductoById(productoId);
        return historicoInventarioRepository.findByProducto(producto);
    }

    private void sendAdminNotification(String subject, String message) throws Exception {
        List<Usuario> admins = usuarioRepository.findByRol(Rol.ADMIN);
        for (Usuario admin : admins) {
            emailService.sendNotification(admin.getEmail(), subject, message, TemplateEmailType.NOTIFICATION);
        }
    }

}
