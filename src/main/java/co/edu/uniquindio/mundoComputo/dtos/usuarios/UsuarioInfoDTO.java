package co.edu.uniquindio.mundoComputo.dtos.usuarios;

import co.edu.uniquindio.mundoComputo.model.EstadoUsuario;
import co.edu.uniquindio.mundoComputo.model.Rol;

public record UsuarioInfoDTO(
        Long id,
        String email,
        String nombre,
        String apellido,
        Rol rol,
        EstadoUsuario estado
) {
}
