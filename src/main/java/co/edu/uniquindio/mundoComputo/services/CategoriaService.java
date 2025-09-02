package co.edu.uniquindio.mundoComputo.services;

import java.util.List;

import co.edu.uniquindio.mundoComputo.dtos.categorias.CategoriaInfoDTO;
import co.edu.uniquindio.mundoComputo.dtos.categorias.CreateCategoriaDTO;
import co.edu.uniquindio.mundoComputo.dtos.categorias.UpdateCategoriaDTO;

/**
 * Servicio para la gestión de categorías de productos.
 * Proporciona métodos para crear, actualizar, eliminar y consultar categorías.
 */
public interface CategoriaService {
    /**
     * Crea una nueva categoría de producto.
     * @param createCategoriaDTO DTO con los datos de la categoría a crear
     * @throws Exception si la categoría ya existe o ocurre un error en la creación
     */
    void createCategoria(CreateCategoriaDTO createCategoriaDTO) throws Exception;

    /**
     * Actualiza los datos de una categoría existente.
     * @param updateCategoriaDTO DTO con los datos actualizados de la categoría
     * @throws Exception si la categoría no existe o ocurre un error en la actualización
     */
    void updateCategoria(UpdateCategoriaDTO updateCategoriaDTO) throws Exception;

    /**
     * Elimina una categoría por su identificador.
     * @param id Identificador de la categoría a eliminar
     * @throws Exception si la categoría no existe o ocurre un error en la eliminación
     */
    void deleteCategoria(Long id) throws Exception;

    /**
     * Obtiene la información de una categoría por su identificador.
     * @param id Identificador de la categoría
     * @return DTO con la información de la categoría
     * @throws Exception si la categoría no existe o ocurre un error en la consulta
     */
    CategoriaInfoDTO getCategoriaInfoById(Long id) throws Exception;

    /**
     * Obtiene la lista de todas las categorías registradas.
     * @return Lista de DTOs con la información de las categorías
     * @throws Exception si ocurre un error en la consulta
     */
    List<CategoriaInfoDTO> getAllCategorias() throws Exception;

    /**
     * Obtiene las categorías cuyo nombre coincide con el parámetro proporcionado.
     * @param nombre Nombre por el cual filtrar
     * @return Lista de DTOs con la información de las categorías filtradas
     * @throws Exception si ocurre un error en la consulta
     */
    List<CategoriaInfoDTO> getCategoriasByName(String nombre) throws Exception;
}
