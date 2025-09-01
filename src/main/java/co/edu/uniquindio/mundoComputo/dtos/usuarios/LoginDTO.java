package co.edu.uniquindio.mundoComputo.dtos.usuarios;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
    @NotBlank @Email String email,
    @Length(min = 8, max = 16) @NotBlank String password
) {
    
}
