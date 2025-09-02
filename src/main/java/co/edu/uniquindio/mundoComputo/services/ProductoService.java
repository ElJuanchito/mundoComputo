package co.edu.uniquindio.mundoComputo.services;

import java.util.List;

import co.edu.uniquindio.mundoComputo.dtos.productos.*;

/**
 * Servicio para la gestión de productos en el sistema MundoComputo.
 * Proporciona métodos para crear, actualizar, eliminar, consultar productos,
 * registrar movimientos de inventario y obtener el histórico de inventario.
 */
public interface ProductoService {
    /**
     * Crea un nuevo producto en el sistema.
     * @param createProductoDTO DTO con los datos del producto a crear
     * @throws Exception si el producto ya existe o ocurre un error en la creación
     */
    void createProducto(CreateProductoDTO createProductoDTO) throws Exception;

    /**
     * Actualiza los datos de un producto existente.
     * @param updateProductoDTO DTO con los datos actualizados del producto
     * @throws Exception si el producto no existe o ocurre un error en la actualización
     */
    void updateProducto(UpdateProductoDTO updateProductoDTO) throws Exception;

    /**
     * Elimina un producto por su identificador.
     * @param id Identificador del producto a eliminar
     * @throws Exception si el producto no existe o ocurre un error en la eliminación
     */
    void deleteProducto(Long id) throws Exception;

    /**
     * Obtiene la información de un producto por su identificador.
     * @param id Identificador del producto
     * @return DTO con la información del producto
     * @throws Exception si el producto no existe o ocurre un error en la consulta
     */
    ProductoInfoDTO getProductoInfoById(Long id) throws Exception;

    /**
     * Obtiene la lista de todos los productos registrados.
     * @return Lista de DTOs con la información de los productos
     * @throws Exception si ocurre un error en la consulta
     */
    List<ProductoInfoDTO> getAllProductos() throws Exception;

    /**
     * Obtiene los productos filtrados por categoría.
     * @param categoriaId Identificador de la categoría
     * @return Lista de DTOs con los productos de la categoría
     * @throws Exception si la categoría no existe o ocurre un error en la consulta
     */
    List<ProductoInfoDTO> getProductosByCategoria(Long categoriaId) throws Exception;

    /**
     * Registra una salida de inventario para un producto.
     * @param inventarioDTO DTO con los datos de la operación de salida
     * @throws Exception si el producto no existe, no hay suficiente stock o ocurre un error
     */
    void recordOutput(InventarioDTO inventarioDTO) throws Exception;

    /**
     * Registra una entrada de inventario para un producto.
     * @param inventarioDTO DTO con los datos de la operación de entrada
     * @throws Exception si el producto no existe o ocurre un error
     */
    void recordInput(InventarioDTO inventarioDTO) throws Exception;

    /**
     * Registra un ajuste de inventario para un producto.
     * @param inventarioDTO DTO con los datos de la operación de ajuste
     * @throws Exception si el producto no existe o ocurre un error
     */
    void recordAdjust(InventarioDTO inventarioDTO) throws Exception;

    /**
     * Obtiene el histórico de movimientos de inventario de un producto.
     * @param productoId Identificador del producto
     * @return Lista de movimientos históricos de inventario
     * @throws Exception si el producto no existe o ocurre un error en la consulta
     */
    List<HistoricoInventarioDTO> getHistorico(Long productoId) throws Exception;
}
