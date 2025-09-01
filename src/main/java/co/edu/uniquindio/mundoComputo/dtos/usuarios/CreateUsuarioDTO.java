package co.edu.uniquindio.mundoComputo.dtos.usuarios;

import co.edu.uniquindio.mundoComputo.model.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CreateUsuarioDTO(
        @NotBlank String nombre,
        @NotBlank String apellido,
        @NotBlank @Email String email,
        @Length(min = 8, max = 16) String contrasena,
        @NotNull Rol rol
) {
}
