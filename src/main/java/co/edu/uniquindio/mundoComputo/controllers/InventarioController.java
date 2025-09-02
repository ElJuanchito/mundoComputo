package co.edu.uniquindio.mundoComputo.controllers;

import co.edu.uniquindio.mundoComputo.dtos.categorias.CategoriaInfoDTO;
import co.edu.uniquindio.mundoComputo.dtos.categorias.CreateCategoriaDTO;
import co.edu.uniquindio.mundoComputo.dtos.categorias.UpdateCategoriaDTO;
import co.edu.uniquindio.mundoComputo.dtos.productos.*;
import co.edu.uniquindio.mundoComputo.dtos.responses.MessageDTO;
import co.edu.uniquindio.mundoComputo.model.HistoricoInventario;
import co.edu.uniquindio.mundoComputo.services.CategoriaService;
import co.edu.uniquindio.mundoComputo.services.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
@Tag(name = "inventario")
@SecurityRequirement(name = "bearerAuth")
public class InventarioController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    // --- PRODUCTOS ---

    @Operation(summary = "Crear producto", description = "Crea un nuevo producto en el inventario")
    @PostMapping("/productos")
    public ResponseEntity<MessageDTO<String>> createProducto(@Valid @RequestBody CreateProductoDTO createProductoDTO) throws Exception {
        productoService.createProducto(createProductoDTO);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "Producto creado satisfactoriamente"));
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza la información de un producto existente")
    @PutMapping("/productos")
    public ResponseEntity<MessageDTO<String>> updateProducto(@Valid @RequestBody UpdateProductoDTO updateProductoDTO) throws Exception {
        productoService.updateProducto(updateProductoDTO);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "Producto actualizado satisfactoriamente"));
    }

    @Operation(summary = "Obtener producto por ID", description = "Obtiene la información de un producto por su identificador")
    @GetMapping("/productos/{id}")
    public ResponseEntity<MessageDTO<ProductoInfoDTO>> getProductoInfoById(@PathVariable("id") Long id) throws Exception {
        ProductoInfoDTO productoInfo = productoService.getProductoInfoById(id);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, productoInfo));
    }

    @Operation(summary = "Obtener todos los productos", description = "Obtiene una lista de todos los productos en el inventario")
    @GetMapping("/productos")
    public ResponseEntity<MessageDTO<List<ProductoInfoDTO>>> getAllProductos() throws Exception {
        List<ProductoInfoDTO> productos = productoService.getAllProductos();
        return ResponseEntity.status(200).body(new MessageDTO<>(false, productos));
    }

    @Operation(summary = "Obtener productos por categoría", description = "Obtiene productos filtrados por categoría")
    @GetMapping("/productos/byCategoria")
    public ResponseEntity<MessageDTO<List<ProductoInfoDTO>>> getProductosByCategoria(@RequestParam("categoriaId") Long categoriaId) throws Exception {
        List<ProductoInfoDTO> productos = productoService.getProductosByCategoria(categoriaId);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, productos));
    }

    @Operation(summary = "Registrar entrada de inventario", description = "Registra una entrada de inventario para un producto")
    @PostMapping("/productos/entrada")
    public ResponseEntity<MessageDTO<String>> recordInput(@Valid @RequestBody InventarioDTO dto) throws Exception {
        productoService.recordInput(dto);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "Entrada registrada satisfactoriamente"));
    }

    @Operation(summary = "Registrar salida de inventario", description = "Registra una salida de inventario para un producto")
    @PostMapping("/productos/salida")
    public ResponseEntity<MessageDTO<String>> recordOutput(@Valid @RequestBody InventarioDTO dto) throws Exception {
        productoService.recordOutput(dto);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "Salida registrada satisfactoriamente"));
    }

    @Operation(summary = "Registrar ajuste de inventario", description = "Registra un ajuste de inventario para un producto")
    @PostMapping("/productos/ajuste")
    public ResponseEntity<MessageDTO<String>> recordAdjust(@Valid @RequestBody InventarioDTO dto) throws Exception {
        productoService.recordAdjust(dto);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "Ajuste registrado satisfactoriamente"));
    }

    @Operation(summary = "Obtener histórico de inventario", description = "Obtiene el histórico de movimientos de inventario de un producto")
    @GetMapping("/productos/{id}/historico")
    public ResponseEntity<MessageDTO<List<HistoricoInventarioDTO>>> getHistorico(@PathVariable("id") Long id) throws Exception {
        List<HistoricoInventarioDTO> historico = productoService.getHistorico(id);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, historico));
    }

    // --- CATEGORIAS ---

    @Operation(summary = "Crear categoría", description = "Crea una nueva categoría de productos")
    @PostMapping("/categorias")
    public ResponseEntity<MessageDTO<String>> createCategoria(@Valid @RequestBody CreateCategoriaDTO createCategoriaDTO) throws Exception {
        categoriaService.createCategoria(createCategoriaDTO);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "Categoría creada satisfactoriamente"));
    }

    @Operation(summary = "Actualizar categoría", description = "Actualiza la información de una categoría existente")
    @PutMapping("/categorias")
    public ResponseEntity<MessageDTO<String>> updateCategoria(@Valid @RequestBody UpdateCategoriaDTO updateCategoriaDTO) throws Exception {
        categoriaService.updateCategoria(updateCategoriaDTO);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "Categoría actualizada satisfactoriamente"));
    }

    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría de productos")
    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<MessageDTO<String>> deleteCategoria(@PathVariable("id") Long id) throws Exception {
        categoriaService.deleteCategoria(id);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, "Categoría eliminada satisfactoriamente"));
    }

    @Operation(summary = "Obtener categoría por ID", description = "Obtiene la información de una categoría por su identificador")
    @GetMapping("/categorias/{id}")
    public ResponseEntity<MessageDTO<CategoriaInfoDTO>> getCategoriaInfoById(@PathVariable("id") Long id) throws Exception {
        CategoriaInfoDTO categoriaInfo = categoriaService.getCategoriaInfoById(id);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, categoriaInfo));
    }

    @Operation(summary = "Obtener todas las categorías", description = "Obtiene una lista de todas las categorías de productos")
    @GetMapping("/categorias")
    public ResponseEntity<MessageDTO<List<CategoriaInfoDTO>>> getAllCategorias() throws Exception {
        List<CategoriaInfoDTO> categorias = categoriaService.getAllCategorias();
        return ResponseEntity.status(200).body(new MessageDTO<>(false, categorias));
    }

    @Operation(summary = "Obtener categorías por nombre", description = "Obtiene categorías filtradas por nombre")
    @GetMapping("/categorias/byName")
    public ResponseEntity<MessageDTO<List<CategoriaInfoDTO>>> getCategoriasByName(@RequestParam("nombre") String nombre) throws Exception {
        List<CategoriaInfoDTO> categorias = categoriaService.getCategoriasByName(nombre);
        return ResponseEntity.status(200).body(new MessageDTO<>(false, categorias));
    }
}
