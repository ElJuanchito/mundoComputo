package co.edu.uniquindio.mundoComputo.dtos.categorias;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateCategoriaDTO(
    @NotNull Long id,
    @NotEmpty String nombre,
    @NotEmpty String descripcion
) {

}
