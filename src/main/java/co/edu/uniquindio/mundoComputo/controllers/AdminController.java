package co.edu.uniquindio.mundoComputo.controllers;

import co.edu.uniquindio.mundoComputo.dtos.responses.MessageDTO;
import co.edu.uniquindio.mundoComputo.dtos.usuarios.CreateUsuarioDTO;
import co.edu.uniquindio.mundoComputo.dtos.usuarios.UpdateUsuarioDTO;
import co.edu.uniquindio.mundoComputo.dtos.usuarios.UsuarioInfoDTO;
import co.edu.uniquindio.mundoComputo.model.EstadoUsuario;
import co.edu.uniquindio.mundoComputo.model.Rol;
import co.edu.uniquindio.mundoComputo.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "admin")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Obtener roles", description = "Obtiene la lista de roles disponibles")
    @GetMapping("/roles")
    @ResponseStatus(code = HttpStatus.OK)
    public MessageDTO<List<Rol>> getRoles() throws Exception {
        List<Rol> roles = usuarioService.getRoles();
        return new MessageDTO<>(false, roles);
    }

    @Operation(summary = "Crear usuario", description = "Crea un nuevo usuario en el sistema")
    @PostMapping("/usuarios")
    @ResponseStatus(code = HttpStatus.OK)
    public MessageDTO<String> createUser(@Valid @RequestBody CreateUsuarioDTO createUsuarioDTO) throws Exception {
        usuarioService.createUser(createUsuarioDTO);
        return new MessageDTO<>(false, "Usuario creado correctamente");
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza la información de un usuario existente")
    @PutMapping("/usuarios")
    @ResponseStatus(code = HttpStatus.OK)
    public MessageDTO<String> updateUsuario(@Valid @RequestBody UpdateUsuarioDTO updateUsuarioDTO) throws Exception {
        usuarioService.updateUsuario(updateUsuarioDTO);
        return new MessageDTO<>(false, "Usuario actualizado correctamente");
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su identificador")
    @DeleteMapping("/usuarios/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public MessageDTO<String> deleteUsuario(@PathVariable("id") Long id) throws Exception {
        usuarioService.deleteUsuario(id);
        return new MessageDTO<>(false, "Usuario eliminado correctamente");
    }

    @Operation(summary = "Obtener todos los usuarios", description = "Obtiene la lista de todos los usuarios registrados")
    @GetMapping("/usuarios")
    @ResponseStatus(code = HttpStatus.OK)
    public MessageDTO<List<UsuarioInfoDTO>> getAllUsuarios() {
        List<UsuarioInfoDTO> usuarios = usuarioService.getAllUsuarios();
        return new MessageDTO<>(false, usuarios);
    }

    @Operation(summary = "Obtener usuario por ID", description = "Obtiene la información de un usuario por su identificador")
    @GetMapping("/usuarios/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public MessageDTO<UsuarioInfoDTO> getUsuarioById(@PathVariable("id") Long id) throws Exception {
        UsuarioInfoDTO usuario = usuarioService.getUsuarioById(id);
        return new MessageDTO<>(false, usuario);
    }

    @Operation(summary = "Obtener usuarios por estado", description = "Obtiene los usuarios filtrados por estado")
    @GetMapping("/usuarios/byEstado")
    @ResponseStatus(code = HttpStatus.OK)
    public MessageDTO<List<UsuarioInfoDTO>> getUsuariosByEstado(@RequestParam EstadoUsuario estado) throws Exception {
        List<UsuarioInfoDTO> usuarios = usuarioService.getUsuariosByEstado(estado);
        return new MessageDTO<>(false, usuarios);
    }

    @Operation(summary = "Obtener usuarios por rol", description = "Obtiene los usuarios filtrados por rol")
    @GetMapping("/usuarios/byRol")
    @ResponseStatus(code = HttpStatus.OK)
    public MessageDTO<List<UsuarioInfoDTO>> getUsuariosByRol(@RequestParam Rol rol) throws Exception {
        List<UsuarioInfoDTO> usuarios = usuarioService.getUsuariosByRol(rol);
        return new MessageDTO<>(false, usuarios);
    }

    @Operation(summary = "Obtener usuarios por nombre", description = "Obtiene los usuarios cuyo nombre contiene el parámetro proporcionado")
    @GetMapping("/usuarios/byNombre")
    @ResponseStatus(code = HttpStatus.OK)
    public MessageDTO<List<UsuarioInfoDTO>> getUsuariosByNombre(@RequestParam String nombre) throws Exception {
        List<UsuarioInfoDTO> usuarios = usuarioService.getUsuariosByNombre(nombre);
        return new MessageDTO<>(false, usuarios);
    }
}
