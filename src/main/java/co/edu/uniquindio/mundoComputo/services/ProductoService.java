package co.edu.uniquindio.mundoComputo.services;

import java.util.List;

import co.edu.uniquindio.mundoComputo.dtos.productos.CreateProductoDTO;
import co.edu.uniquindio.mundoComputo.dtos.productos.ProductoInfoDTO;
import co.edu.uniquindio.mundoComputo.dtos.productos.UpdateProductoDTO;

public interface ProductoService {
    void createProducto(CreateProductoDTO createProductoDTO) throws Exception;
    void updateProducto(UpdateProductoDTO updateProductoDTO) throws Exception;
    void deleteProducto(Long id) throws Exception;
    ProductoInfoDTO getProductoInfoById(Long id) throws Exception;
    List<ProductoInfoDTO> getAllProductos() throws Exception;
    List<ProductoInfoDTO> getProductosByCategoria(Long categoriaId) throws Exception;
    void recordOutput(Long id, int cantidad, String description) throws Exception;
    void recordInput(Long id, int cantidad, String description) throws Exception;
    void recordAdjust(Long id, int cantidad, String description) throws Exception;
}
