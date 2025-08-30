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

    @Override
    public void deleteCategoria(Long id) throws Exception {
        if(!categoriaProductoRepository.existsById(id)) {
            throw new Exception("La categoría con ID " + id + " no existe");
        }
        categoriaProductoRepository.deleteById(id);
    }

    @Override
    public CategoriaInfoDTO getCategoriaInfoById(Long id) throws Exception {
        CategoriaProducto categoria = categoriaProductoRepository.findById(id)
            .orElseThrow(() -> new Exception("La categoría con ID " + id + " no existe"));
        return mapToCategoriaInfoDTO(categoria);
    }

    @Override
    public List<CategoriaInfoDTO> getAllCategorias() throws Exception {
        List<CategoriaProducto> categorias = categoriaProductoRepository.findAll();
        return categorias.stream()
            .map(this::mapToCategoriaInfoDTO)
            .toList();
    }

    @Override
    public List<CategoriaInfoDTO> getCategoriasByName(String nombre) throws Exception {
        List<CategoriaProducto> categorias = categoriaProductoRepository.findByNombre(nombre);
        return categorias.stream()
            .map(this::mapToCategoriaInfoDTO)
            .toList();
    }

    private CategoriaInfoDTO mapToCategoriaInfoDTO(CategoriaProducto categoria) {
        return new CategoriaInfoDTO(
            categoria.getId(),
            categoria.getNombre(),
            categoria.getDescripcion()
        );
    }
    
}
