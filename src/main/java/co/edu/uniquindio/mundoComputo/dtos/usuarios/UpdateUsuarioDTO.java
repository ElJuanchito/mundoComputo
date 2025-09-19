package co.edu.uniquindio.mundoComputo.dtos.usuarios;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUsuarioDTO(
        @NotNull Long id,
        @NotBlank String nombre,
        @NotBlank String apellido,
        @NotBlank String imageUrl
        ) {
}
