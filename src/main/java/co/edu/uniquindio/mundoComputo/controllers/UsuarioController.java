package co.edu.uniquindio.mundoComputo.controllers;

import co.edu.uniquindio.mundoComputo.dtos.responses.MessageDTO;
import co.edu.uniquindio.mundoComputo.dtos.usuarios.UpdateUsuarioDTO;
import co.edu.uniquindio.mundoComputo.dtos.usuarios.UsuarioInfoDTO;
import co.edu.uniquindio.mundoComputo.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Actualizar usuario", description = "Actualiza la información de un usuario existente")
    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public MessageDTO<String> updateUsuario(@Valid @RequestBody UpdateUsuarioDTO updateUsuarioDTO) throws Exception {
        usuarioService.updateUsuario(updateUsuarioDTO);
        return new MessageDTO<>(false, "Usuario actualizado correctamente");
    }

    @Operation(summary = "Obtener usuario por ID", description = "Obtiene la información de un usuario por su identificador")
    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public MessageDTO<UsuarioInfoDTO> getUsuarioById(@PathVariable("id") Long id) throws Exception {
        UsuarioInfoDTO usuario = usuarioService.getUsuarioById(id);
        return new MessageDTO<>(false, usuario);
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su identificador")
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public MessageDTO<String> deleteUsuario(@PathVariable("id") Long id) throws Exception {
        usuarioService.deleteUsuario(id);
        return new MessageDTO<>(false, "Usuario eliminado correctamente");
    }
}
