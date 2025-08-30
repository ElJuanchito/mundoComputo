package co.edu.uniquindio.mundoComputo.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniquindio.mundoComputo.dtos.productos.CreateProductoDTO;
import co.edu.uniquindio.mundoComputo.dtos.productos.ProductoInfoDTO;
import co.edu.uniquindio.mundoComputo.dtos.productos.UpdateProductoDTO;
import co.edu.uniquindio.mundoComputo.model.CategoriaProducto;
import co.edu.uniquindio.mundoComputo.model.Producto;
import co.edu.uniquindio.mundoComputo.repositories.ProductoRepository;
import co.edu.uniquindio.mundoComputo.repositories.CategoriaProductoRepository;
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
    }
    
    @Override
    public void deleteProducto(Long id) throws Exception {
        Producto producto = getProductoById(id);
        productoRepository.delete(producto);
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
    public void recordOutput(Long id, int cantidad, String description) throws Exception {
        Producto producto = getProductoById(id);

        int nuevoStock = producto.getStock() - cantidad;

        if(nuevoStock < 0) {
            throw new Exception(String.format("No hay suficiente stock para el producto con id: %s", id));
        }
        
        if(nuevoStock < producto.getStockMinimo()) {
            //emailService.sendHtmlEmail();
            //TODO requiere de el correo del admin, es decir tener un admin registrado
        }

        producto.setStock(nuevoStock);
    }

    @Override
    public void recordInput(Long id, int cantidad, String description) throws Exception {
        Producto producto = getProductoById(id);

        int nuevoStock = producto.getStock() + cantidad;
        producto.setStock(nuevoStock);

        // Implementar la lógica para registrar una entrada
        // TODO emailService.sendHtmlEmail();
    }

    @Override
    public void recordAdjust(Long id, int cantidad, String description) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recordAdjust'");
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

}
