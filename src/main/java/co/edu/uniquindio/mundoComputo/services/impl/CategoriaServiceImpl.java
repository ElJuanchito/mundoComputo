package co.edu.uniquindio.mundoComputo.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniquindio.mundoComputo.dtos.categorias.CategoriaInfoDTO;
import co.edu.uniquindio.mundoComputo.dtos.categorias.CreateCategoriaDTO;
import co.edu.uniquindio.mundoComputo.dtos.categorias.UpdateCategoriaDTO;
import co.edu.uniquindio.mundoComputo.model.CategoriaProducto;
import co.edu.uniquindio.mundoComputo.repositories.CategoriaProductoRepository;
import co.edu.uniquindio.mundoComputo.services.CategoriaService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaProductoRepository categoriaProductoRepository;

    /**
     * {@inheritDoc}
     * Crea una nueva categoría si el nombre no existe previamente.
     */
    @Override
    public void createCategoria(CreateCategoriaDTO createCategoriaDTO) throws Exception {
        if(categoriaProductoRepository.existsByNombre(createCategoriaDTO.nombre())) {
            throw new Exception("La categoría de nombre " + createCategoriaDTO.nombre() + " ya existe");
        }

        CategoriaProducto categoria = new CategoriaProducto();
        categoria.setNombre(createCategoriaDTO.nombre());
        categoria.setDescripcion(createCategoriaDTO.descripcion());
        categoriaProductoRepository.save(categoria);
    }

    /**
     * {@inheritDoc}
     * Actualiza los datos de una categoría existente por su ID.
     */
    @Override
    public void updateCategoria(UpdateCategoriaDTO updateCategoriaDTO) throws Exception {
        if(!categoriaProductoRepository.existsById(updateCategoriaDTO.id())) {
            throw new Exception("La categoría con ID " + updateCategoriaDTO.id() + " no existe");
        }

        CategoriaProducto categoria = new CategoriaProducto();
        categoria.setNombre(updateCategoriaDTO.nombre());
        categoria.setDescripcion(updateCategoriaDTO.descripcion());
        categoriaProductoRepository.save(categoria);
    }

    /**
     * {@inheritDoc}
     * Elimina una categoría por su identificador si existe.
     */
    @Override
    public void deleteCategoria(Long id) throws Exception {
        if(!categoriaProductoRepository.existsById(id)) {
            throw new Exception("La categoría con ID " + id + " no existe");
        }
        categoriaProductoRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     * Obtiene la información de una categoría por su identificador.
     */
    @Override
    public CategoriaInfoDTO getCategoriaInfoById(Long id) throws Exception {
        CategoriaProducto categoria = categoriaProductoRepository.findById(id)
            .orElseThrow(() -> new Exception("La categoría con ID " + id + " no existe"));
        return mapToCategoriaInfoDTO(categoria);
    }

    /**
     * {@inheritDoc}
     * Obtiene la lista de todas las categorías registradas.
     */
    @Override
    public List<CategoriaInfoDTO> getAllCategorias() throws Exception {
        List<CategoriaProducto> categorias = categoriaProductoRepository.findAll();
        return categorias.stream()
            .map(this::mapToCategoriaInfoDTO)
            .toList();
    }

    /**
     * {@inheritDoc}
     * Obtiene las categorías cuyo nombre coincide con el parámetro proporcionado.
     */
    @Override
    public List<CategoriaInfoDTO> getCategoriasByName(String nombre) throws Exception {
        List<CategoriaProducto> categorias = categoriaProductoRepository.findByNombre(nombre);
        return categorias.stream()
            .map(this::mapToCategoriaInfoDTO)
            .toList();
    }

    /**
     * Convierte una entidad CategoriaProducto en un DTO CategoriaInfoDTO.
     * @param categoria Entidad de categoría
     * @return DTO con la información de la categoría
     */
    private CategoriaInfoDTO mapToCategoriaInfoDTO(CategoriaProducto categoria) {
        return new CategoriaInfoDTO(
            categoria.getId(),
            categoria.getNombre(),
            categoria.getDescripcion()
        );
    }
    
}
