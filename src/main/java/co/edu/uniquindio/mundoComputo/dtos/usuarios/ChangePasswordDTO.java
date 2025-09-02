package co.edu.uniquindio.mundoComputo.dtos.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ChangePasswordDTO(
        @NotBlank @Email String email,
        @NotBlank @Length(min = 8, max = 16) String newPassword,
        @NotBlank @Length(min = 4, max = 4) String code
) {
}
