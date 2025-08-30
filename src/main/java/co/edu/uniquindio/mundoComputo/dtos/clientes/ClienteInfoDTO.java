package co.edu.uniquindio.mundoComputo.dtos.clientes;

import co.edu.uniquindio.mundoComputo.model.EstadoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link co.edu.uniquindio.mundoComputo.model.Cliente}
 */
public record ClienteInfoDTO(Long id, @NotNull @Size(min = 2, max = 50) String nombre,
                             @NotNull @Size(min = 2, max = 50) String apellido, @NotNull @Email String email,
                             @NotNull @Size(min = 10, max = 10) String telefono,
                             EstadoUsuario estado) implements Serializable {
}