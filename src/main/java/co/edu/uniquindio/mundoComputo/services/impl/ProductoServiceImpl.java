package co.edu.uniquindio.mundoComputo.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import co.edu.uniquindio.mundoComputo.dtos.productos.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

/**
 * Implementación del servicio de gestión de productos.
 * Proporciona la lógica para crear, actualizar, eliminar productos, registrar movimientos de inventario
 * y enviar notificaciones a administradores en eventos relevantes.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaProductoRepository categoriaRepository;
    private final EmailService emailService;
    private final HistoricoInventarioRepository historicoInventarioRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * {@inheritDoc}
     * Crea un nuevo producto si no existe otro con el mismo nombre.
     */
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

    /**
     * {@inheritDoc}
     * Actualiza los datos de un producto y notifica a los administradores.
     */
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
    
    /**
     * {@inheritDoc}
     * Elimina un producto y notifica a los administradores.
     */
    @Override
    public void deleteProducto(Long id) throws Exception {
        Producto producto = getProductoById(id);
        productoRepository.delete(producto);
        sendAdminNotification("Producto eliminado", "El producto " + producto.getNombre() + " ha sido eliminado.");
    }

    /**
     * {@inheritDoc}
     * Obtiene la información detallada de un producto por su identificador.
     */
    @Override
    public ProductoInfoDTO getProductoInfoById(Long id) throws Exception {
        Producto producto = getProductoById(id);
        return mapToInfoDTO(producto);
    }

    /**
     * {@inheritDoc}
     * Obtiene la lista de todos los productos registrados.
     */
    @Override
    public List<ProductoInfoDTO> getAllProductos() throws Exception {
        List<Producto> productos = productoRepository.findAll();

        return productos.stream().map(this::mapToInfoDTO).toList();
    }

    /**
     * {@inheritDoc}
     * Registra una salida de inventario, actualiza el stock y notifica si el stock es bajo.
     * @param inventarioDTO DTO con los datos de la operación de salida
     */
    @Override
    public void recordOutput(InventarioDTO inventarioDTO) throws Exception {
        Producto producto = getProductoById(inventarioDTO.productoId());
        int nuevoStock = producto.getStock() - inventarioDTO.cantidad();
        if(nuevoStock < 0) {
            throw new Exception(String.format("No hay suficiente stock para el producto con id: %s", inventarioDTO.productoId()));
        }
        if(nuevoStock < producto.getStockMinimo()) {
            sendAdminNotification("Alerta de stock bajo", "El producto " + producto.getNombre() + " ha alcanzado un stock bajo.");
        }
        Usuario usuario = usuarioRepository.findById(inventarioDTO.usuarioId())
                .orElseThrow(() -> new Exception(String.format("Usuario con id: %s no encontrado", inventarioDTO.usuarioId())));
        HistoricoInventario historico = HistoricoInventario.builder()
                .producto(producto)
                .fecha(LocalDateTime.now())
                .cantidadAnterior(producto.getStock())
                .cantidadNueva(nuevoStock)
                .tipoMovimiento(TipoMovimientoInventario.SALIDA)
                .comentario(inventarioDTO.descripcion())
                .usuario(usuario)
                .build();
        historicoInventarioRepository.save(historico);
        producto.setStock(nuevoStock);
        productoRepository.save(producto);
        String message = String.format("Se ha registrado una salida del producto %s. Stock actual: %d", producto.getNombre(), nuevoStock);
        sendAdminNotification("Salida de producto", message);
    }

    /**
     * {@inheritDoc}
     * Registra una entrada de inventario y notifica a los administradores.
     * @param inventarioDTO DTO con los datos de la operación de entrada
     */
    @Override
    public void recordInput(InventarioDTO inventarioDTO) throws Exception {
        Producto producto = getProductoById(inventarioDTO.productoId());
        int nuevoStock = producto.getStock() + inventarioDTO.cantidad();
        Usuario usuario = usuarioRepository.findById(inventarioDTO.usuarioId())
                .orElseThrow(() -> new Exception(String.format("Usuario con id: %s no encontrado", inventarioDTO.usuarioId())));
        HistoricoInventario historico = HistoricoInventario.builder()
                .producto(producto)
                .fecha(LocalDateTime.now())
                .cantidadAnterior(producto.getStock())
                .cantidadNueva(nuevoStock)
                .tipoMovimiento(TipoMovimientoInventario.ENTRADA)
                .comentario(inventarioDTO.descripcion())
                .usuario(usuario)
                .build();
        historicoInventarioRepository.save(historico);
        producto.setStock(nuevoStock);
        productoRepository.save(producto);
        String message = String.format("Se ha registrado una entrada del producto %s. Stock actual: %d", producto.getNombre(), nuevoStock);
        sendAdminNotification("Entrada de producto", message);
    }

    /**
     * {@inheritDoc}
     * Registra un ajuste de inventario y notifica a los administradores.
     * @param inventarioDTO DTO con los datos de la operación de ajuste
     */
    @Override
    public void recordAdjust(InventarioDTO inventarioDTO) throws Exception {
        Producto producto = getProductoById(inventarioDTO.productoId());
        int nuevoStock = inventarioDTO.cantidad();
        Usuario usuario = usuarioRepository.findById(inventarioDTO.usuarioId())
                .orElseThrow(() -> new Exception(String.format("Usuario con id: %s no encontrado", inventarioDTO.usuarioId())));
        HistoricoInventario historico = HistoricoInventario.builder()
                .producto(producto)
                .fecha(LocalDateTime.now())
                .cantidadAnterior(producto.getStock())
                .cantidadNueva(nuevoStock)
                .tipoMovimiento(TipoMovimientoInventario.AJUSTE)
                .comentario(inventarioDTO.descripcion())
                .usuario(usuario)
                .build();
        historicoInventarioRepository.save(historico);
        producto.setStock(nuevoStock);
        productoRepository.save(producto);
        String message = String.format("Se ha registrado un ajuste del producto %s. Stock actual: %d", producto.getNombre(), nuevoStock);
        sendAdminNotification("Ajuste de producto", message);
    }

    /**
     * {@inheritDoc}
     * Obtiene los productos filtrados por categoría.
     */
    @Override
    public List<ProductoInfoDTO> getProductosByCategoria(Long categoriaId) throws Exception {
        CategoriaProducto categoria = getCategoriaById(categoriaId);
        List<Producto> productos = productoRepository.findByCategoria(categoria);
        return productos.stream().map(this::mapToInfoDTO).toList();
    }

    /**
     * {@inheritDoc}
     * Obtiene el histórico de movimientos de inventario de un producto.
     */
    @Override
    public List<HistoricoInventarioDTO> getHistorico(Long productoId) throws Exception {
        Producto producto = getProductoById(productoId);
        List<HistoricoInventario> historico = historicoInventarioRepository.findByProducto(producto);

        return historico.stream().map(h -> new HistoricoInventarioDTO(
                h.getId(),
                h.getProducto().getNombre(),
                h.getProducto().getId(),
                h.getFecha(),
                h.getCantidadAnterior(),
                h.getCantidadNueva(),
                h.getTipoMovimiento(),
                h.getUsuario().getNombre(),
                h.getUsuario().getId(),
                h.getComentario()
        )).toList();
    }

    /**
     * Obtiene la categoría por su identificador, lanzando excepción si no existe.
     * @param categoriaId Identificador de la categoría
     * @return Entidad CategoriaProducto
     * @throws Exception si la categoría no existe
     */
    private CategoriaProducto getCategoriaById(Long categoriaId) throws Exception {
        Optional<CategoriaProducto> categoriaOptional = categoriaRepository.findById(categoriaId);
        if (categoriaOptional.isEmpty()) {
            throw new Exception(String.format("La categoria con id: %s no existe", categoriaId));
        }
        return categoriaOptional.get();
    }

    /**
     * Obtiene el producto por su identificador, lanzando excepción si no existe.
     * @param id Identificador del producto
     * @return Entidad Producto
     * @throws Exception si el producto no existe
     */
    private Producto getProductoById(Long id) throws Exception {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if (productoOptional.isEmpty()) {
            throw new Exception(String.format("El producto con id: %s no existe", id));
        }
        return productoOptional.get();
    }

    /**
     * Convierte una entidad Producto en un DTO ProductoInfoDTO.
     * @param producto Entidad Producto
     * @return DTO con la información del producto
     */
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

    /**
     * Envía una notificación a todos los administradores del sistema.
     * @param subject Asunto del correo
     * @param message Mensaje del correo
     * @throws Exception si ocurre un error en el envío
     */
    private void sendAdminNotification(String subject, String message) throws Exception {
        List<Usuario> admins = usuarioRepository.findByRol(Rol.ADMIN);
        for (Usuario admin : admins) {
            emailService.sendNotification(admin.getEmail(), subject, message, TemplateEmailType.NOTIFICATION);
        }
    }

}
