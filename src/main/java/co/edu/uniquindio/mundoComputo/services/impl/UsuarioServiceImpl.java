package co.edu.uniquindio.mundoComputo.services.impl;

import co.edu.uniquindio.mundoComputo.config.JwtUtil;
import co.edu.uniquindio.mundoComputo.dtos.auth.TokenDTO;
import co.edu.uniquindio.mundoComputo.dtos.usuarios.CreateUsuarioDTO;
import co.edu.uniquindio.mundoComputo.model.EstadoUsuario;
import co.edu.uniquindio.mundoComputo.model.Rol;
import co.edu.uniquindio.mundoComputo.model.Usuario;
import co.edu.uniquindio.mundoComputo.repositories.UsuarioRepository;
import co.edu.uniquindio.mundoComputo.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private JwtUtil  jwtUtil;

    @Override
    public TokenDTO login(String email, String password) throws Exception {
        if(usuarioRepository.findByEmail(email).isEmpty()){
            throw new Exception("Usuario no encontrado");
        }
        Usuario usuario = usuarioRepository.findByEmail(email).get();

        if(usuario.getEstado().equals(EstadoUsuario.ELIMINADO) || usuario.getEstado().equals(EstadoUsuario.INACTIVO)){
            throw new Exception("El usuario esta:" + usuario.getEstado());
        }

        return new TokenDTO(jwtUtil.generateToken(usuario));
    }

    @Override
    public void createUser(CreateUsuarioDTO token) throws Exception {

    }

    @Override
    public List<Rol> getRoles() {
        return List.of();
    }

    private Usuario mapToUsuario(CreateUsuarioDTO dto) throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.nombre());
        usuario.setEmail(dto.email());
        usuario.setContrasena(dto.contrasena());
        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario.setRol(dto.rol());
        return usuario;
    }
}
