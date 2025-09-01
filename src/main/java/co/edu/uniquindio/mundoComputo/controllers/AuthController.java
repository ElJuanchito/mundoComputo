package co.edu.uniquindio.mundoComputo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.mundoComputo.dtos.auth.TokenDTO;
import co.edu.uniquindio.mundoComputo.dtos.responses.MessageDTO;
import co.edu.uniquindio.mundoComputo.dtos.usuarios.CreateUsuarioDTO;
import co.edu.uniquindio.mundoComputo.dtos.usuarios.LoginDTO;
import co.edu.uniquindio.mundoComputo.model.Rol;
import co.edu.uniquindio.mundoComputo.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "auth")
public class AuthController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Iniciar sesión", description = "Inicia sesión en la plataforma")
    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.OK)
    public MessageDTO<TokenDTO> login(@Valid @RequestBody LoginDTO loginDTO) throws Exception {
        TokenDTO token = usuarioService.login(loginDTO);
        return new MessageDTO<>(false, token);
    }

    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario en la plataforma")
    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.OK)
    public MessageDTO<String> createUsuario(@Valid @RequestBody CreateUsuarioDTO createUsuarioDTO) throws Exception {
        usuarioService.createUser(createUsuarioDTO);
        return new MessageDTO<>(false, "Usuario registrado exitosamente");
    }

    @Operation(summary = "Obtener roles", description = "Obtiene la lista de roles disponibles")
    @GetMapping("/roles")
    @ResponseStatus(code = HttpStatus.OK)
    public MessageDTO<List<Rol>> getRoles() throws Exception {
        List<Rol> roles = usuarioService.getRoles();
        return new MessageDTO<>(false, roles);
    }
}
