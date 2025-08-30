package co.edu.uniquindio.mundoComputo.services;

import java.util.List;

import co.edu.uniquindio.mundoComputo.dtos.categorias.CategoriaInfoDTO;
import co.edu.uniquindio.mundoComputo.dtos.categorias.CreateCategoriaDTO;
import co.edu.uniquindio.mundoComputo.dtos.categorias.UpdateCategoriaDTO;

public interface CategoriaService {
    void createCategoria(CreateCategoriaDTO createCategoriaDTO) throws Exception;
    void updateCategoria(UpdateCategoriaDTO updateCategoriaDTO) throws Exception;
    void deleteCategoria(Long id) throws Exception;
    CategoriaInfoDTO getCategoriaInfoById(Long id) throws Exception;
    List<CategoriaInfoDTO> getAllCategorias() throws Exception;
    List<CategoriaInfoDTO> getCategoriasByName(String nombre) throws Exception;
}
