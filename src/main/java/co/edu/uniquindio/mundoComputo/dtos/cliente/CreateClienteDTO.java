package co.edu.uniquindio.mundoComputo.dtos.cliente;

import jakarta.validation.constraints.*;

import java.io.Serializable;

/**
 * DTO for {@link co.edu.uniquindio.mundoComputo.model.Cliente}
 */
public record CreateClienteDTO(@Size(min = 2, max = 50) @NotEmpty @NotBlank String nombre,
                               @Size(min = 2, max = 50) @NotEmpty @NotBlank String apellido,
                               @Email @NotEmpty @NotBlank String email,
                               @Size(min = 10, max = 10) @NotBlank String telefono) {
}