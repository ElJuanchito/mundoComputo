package co.edu.uniquindio.mundoComputo.dtos.productos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProductoDTO(
    @NotBlank String nombre,
    @NotBlank String descripcion,
    @NotNull @Min(0) double precio,
    @NotNull @Min(0) int stock,
    @NotNull @Min(0) int stockMinimo,
    @NotNull Long categoriaId
) {
    
}
