package co.edu.uniquindio.mundoComputo.dtos.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link co.edu.uniquindio.mundoComputo.model.Cliente}
 */
public record UpdateClienteDTO(@Size(min = 2, max = 50) @NotEmpty @NotBlank String nombre,
                               @Size(min = 2, max = 50) @NotEmpty @NotBlank String apellido,
                               @Size(min = 10, max = 10) @NotBlank String telefono,
                               @NotEmpty @NotBlank Long id) {
}