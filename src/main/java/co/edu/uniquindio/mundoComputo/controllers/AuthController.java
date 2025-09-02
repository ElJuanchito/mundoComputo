package co.edu.uniquindio.mundoComputo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.mundoComputo.dtos.auth.TokenDTO;
import co.edu.uniquindio.mundoComputo.dtos.responses.MessageDTO;
import co.edu.uniquindio.mundoComputo.dtos.usuarios.ChangePasswordDTO;
import co.edu.uniquindio.mundoComputo.dtos.usuarios.LoginDTO;
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

    @Operation(summary = "Enviar código de verificación", description = "Envía un código de verificación al correo electrónico del usuario")
    @PostMapping("/send-verification-code")
    @ResponseStatus(code = HttpStatus.OK)
    public MessageDTO<String> sendVerificationCode(@RequestParam String email) throws Exception {
        usuarioService.sendVerificationCode(email);
        return new MessageDTO<>(false, "Código de verificación enviado correctamente");
    }

    @Operation(summary = "Cambiar contraseña", description = "Cambia la contraseña de un usuario usando el código de verificación")
    @PostMapping("/change-password")
    @ResponseStatus(code = HttpStatus.OK)
    public MessageDTO<String> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) throws Exception {
        usuarioService.changePassword(changePasswordDTO);
        return new MessageDTO<>(false, "Contraseña cambiada correctamente");
    }
}
