package co.edu.uniquindio.mundoComputo.services;

import java.util.List;

import co.edu.uniquindio.mundoComputo.dtos.productos.CreateProductoDTO;
import co.edu.uniquindio.mundoComputo.dtos.productos.ProductoInfoDTO;
import co.edu.uniquindio.mundoComputo.dtos.productos.UpdateProductoDTO;
import co.edu.uniquindio.mundoComputo.model.HistoricoInventario;

public interface ProductoService {
    void createProducto(CreateProductoDTO createProductoDTO) throws Exception;
    void updateProducto(UpdateProductoDTO updateProductoDTO) throws Exception;
    void deleteProducto(Long id) throws Exception;
    ProductoInfoDTO getProductoInfoById(Long id) throws Exception;
    List<ProductoInfoDTO> getAllProductos() throws Exception;
    List<ProductoInfoDTO> getProductosByCategoria(Long categoriaId) throws Exception;
    void recordOutput(Long id, Long userId, int cantidad, String description) throws Exception;
    void recordInput(Long id, Long userId, int cantidad, String description) throws Exception;
    void recordAdjust(Long id, Long userId, int cantidad, String description) throws Exception;
    List<HistoricoInventario> getHistorico(Long productoId) throws Exception;
}
