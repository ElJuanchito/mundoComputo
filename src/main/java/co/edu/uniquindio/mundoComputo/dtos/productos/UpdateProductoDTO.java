package co.edu.uniquindio.mundoComputo.dtos.productos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateProductoDTO(
    @NotNull Long id,
    @NotEmpty String nombre,
    @NotEmpty String descripcion,
    @NotNull @Min(0) Double precio,
    @NotNull @Min(0) Integer stockMinimo,
    @NotNull Long categoriaId
) {
}
