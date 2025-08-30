package co.edu.uniquindio.mundoComputo.dtos.categorias;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoriaDTO(
    @NotBlank String nombre,
    @NotBlank String descripcion
) {

}
