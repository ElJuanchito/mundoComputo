package co.edu.uniquindio.mundoComputo.services.impl;

import co.edu.uniquindio.mundoComputo.config.JwtUtil;
import co.edu.uniquindio.mundoComputo.dtos.auth.TokenDTO;
import co.edu.uniquindio.mundoComputo.dtos.usuarios.*;
import co.edu.uniquindio.mundoComputo.model.*;
import co.edu.uniquindio.mundoComputo.repositories.UsuarioRepository;
import co.edu.uniquindio.mundoComputo.services.EmailService;
import co.edu.uniquindio.mundoComputo.services.UsuarioService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil  jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    /**
     * {@inheritDoc}
     * Realiza la autenticación de un usuario y genera un token JWT si las credenciales son válidas.
     */
    @Override
    public TokenDTO login(LoginDTO loginDTO) throws Exception {
        if(usuarioRepository.findByEmail(loginDTO.email()).isEmpty()){
            throw new Exception("Usuario no encontrado");
        }
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.email()).get();

        if(usuario.getEstado().equals(EstadoUsuario.ELIMINADO) || usuario.getEstado().equals(EstadoUsuario.INACTIVO)){
            throw new Exception("El usuario esta:" + usuario.getEstado());
        }

        if(!passwordEncoder.matches(loginDTO.password(), usuario.getContrasena())) {
            throw new Exception("La contraseña es incorrecta");
        }

        CodigoVerificacion codigoVerificacion = usuario.getCodigoVerificacion();

        verificarCodigo(codigoVerificacion, loginDTO.code());

        usuario.setCodigoVerificacion(null);

        return new TokenDTO(jwtUtil.generateToken(usuario));
    }

    /**
     * {@inheritDoc}
     * Crea un nuevo usuario en el sistema si el correo no está registrado.
     */
    @Override
    public void createUser(CreateUsuarioDTO createUsuarioDTO) throws Exception {
        if(usuarioRepository.findByEmail(createUsuarioDTO.email()).isPresent()){
            throw new Exception("El usuario ya existe");
        }
        Usuario usuario = mapToUsuario(createUsuarioDTO);
        usuarioRepository.save(usuario);
    }

    /**
     * {@inheritDoc}
     * Actualiza los datos personales de un usuario existente.
     */
    @Override
    public void updateUsuario(UpdateUsuarioDTO updateUsuarioDTO) throws Exception {
        Usuario usuario = usuarioRepository.findById(updateUsuarioDTO.id())
                .orElseThrow(() -> new Exception(String.format("Usuario no encontrado id: %s", updateUsuarioDTO.id())));

        usuario.setNombre(updateUsuarioDTO.nombre());
        usuario.setApellido(updateUsuarioDTO.apellido());

        usuarioRepository.save(usuario);
    }

    /**
     * {@inheritDoc}
     * Marca el usuario como eliminado en el sistema.
     */
    @Override
    public void deleteUsuario(Long id) throws Exception {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Usuario con =id: %s, no encontrado", id)));

        usuario.setEstado(EstadoUsuario.ELIMINADO);
        usuarioRepository.save(usuario);
    }

    /**
     * {@inheritDoc}
     * Envía un código de verificación al correo electrónico del usuario.
     */
    @Override
    public void sendVerificationCode(String email) throws Exception {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new Exception(String.format("Usuario con email: %s, no encontrado", email)));

        if(usuario.getEstado().equals(EstadoUsuario.ELIMINADO)){
            throw new Exception("El usuario esta eliminado");
        }

        String codigo = generateRandomAlphanumeric(4);

        CodigoVerificacion codigoVerificacion = new CodigoVerificacion();
        codigoVerificacion.setCodigo(codigo);
        codigoVerificacion.setFechaCreacion(LocalDateTime.now());
        usuario.setCodigoVerificacion(codigoVerificacion);

        usuarioRepository.save(usuario);

        String asunto = "Codigo de verificacion";

        emailService.sendHtmlEmail(usuario.getEmail(), asunto, codigo, TemplateEmailType.VERIFICATION_CODE);
    }

    /**
     * {@inheritDoc}
     * Cambia la contraseña de un usuario tras validar el código de verificación.
     */
    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO) throws Exception {
        Usuario usuario = usuarioRepository.findByEmail(changePasswordDTO.email())
                .orElseThrow(() -> new Exception(String.format("Usuario con email: %s, no encontrado", changePasswordDTO.email())));

        if(usuario.getEstado().equals(EstadoUsuario.ELIMINADO)){
            throw new Exception("El usuario esta eliminado");
        }

        CodigoVerificacion codigoVerificacion = usuario.getCodigoVerificacion();

        verificarCodigo(codigoVerificacion, changePasswordDTO.code());

        usuario.setContrasena(passwordEncoder.encode(changePasswordDTO.newPassword()));
        usuario.setCodigoVerificacion(null);
        usuarioRepository.save(usuario);
    }

    /**
     * {@inheritDoc}
     * Obtiene la lista de todos los usuarios registrados en el sistema.
     */
    @Override
    public List<UsuarioInfoDTO> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(this::mapToInfoDTO).toList();
    }

    /**
     * {@inheritDoc}
     * Obtiene la información de un usuario por su identificador único.
     */
    @Override
    public UsuarioInfoDTO getUsuarioById(Long id) throws Exception {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Usuario con id: %s, no encontrado", id)));
        return mapToInfoDTO(usuario);
    }

    /**
     * {@inheritDoc}
     * Obtiene los usuarios filtrados por estado.
     */
    @Override
    public List<UsuarioInfoDTO> getUsuariosByEstado(EstadoUsuario estadoUsuario) {
        List<Usuario> usuarios = usuarioRepository.findByEstado(estadoUsuario);
        return usuarios.stream().map(this::mapToInfoDTO).toList();
    }

    /**
     * {@inheritDoc}
     * Obtiene los usuarios filtrados por rol.
     */
    @Override
    public List<UsuarioInfoDTO> getUsuariosByRol(Rol rol) {
        List<Usuario> usuarios = usuarioRepository.findByRol(rol);
        return usuarios.stream().map(this::mapToInfoDTO).toList();
    }

    /**
     * {@inheritDoc}
     * Obtiene los usuarios cuyo nombre contiene el parámetro proporcionado.
     */
    @Override
    public List<UsuarioInfoDTO> getUsuariosByNombre(String nombre) {
        List<Usuario> usuarios = usuarioRepository.findAll().stream()
                .filter(u -> u.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .toList();
        return usuarios.stream().map(this::mapToInfoDTO).toList();
    }

    /**
     * {@inheritDoc}
     * Obtiene la lista de roles disponibles en el sistema.
     */
    @Override
    public List<Rol> getRoles() {
        return List.of(Rol.values());
    }

    /**
     * Verifica la validez del código de verificación para operaciones sensibles.
     * @param codigoVerificacion Objeto con el código y fecha de creación
     * @param codigo Código ingresado por el usuario
     * @throws Exception si el código es inválido o ha expirado
     */
    private void verificarCodigo(CodigoVerificacion codigoVerificacion, String codigo) throws Exception {
        if(codigoVerificacion == null) throw new Exception("El codigo de verificacion no existe, por favor solicite uno nuevo");

        if(codigoVerificacion.getFechaCreacion().plusMinutes(15).isBefore(LocalDateTime.now())) {
            throw new Exception("El codigo de verificacion ha expirado, por favor solicite uno nuevo");
        }

        if(!codigoVerificacion.getCodigo().equalsIgnoreCase(codigo)) {
            throw new Exception("El codigo de verificacion es incorrecto");
        }
    }

    /**
     * Convierte un objeto Usuario en UsuarioInfoDTO para transferir información relevante.
     * @param usuario Entidad Usuario
     * @return UsuarioInfoDTO con los datos del usuario
     */
    private UsuarioInfoDTO mapToInfoDTO(Usuario usuario) {
        return new UsuarioInfoDTO(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getRol(),
                usuario.getEstado()
        );
    }

    private Usuario mapToUsuario(CreateUsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.nombre());
        usuario.setEmail(dto.email());
        usuario.setApellido(dto.apellido());
        usuario.setContrasena(passwordEncoder.encode(dto.contrasena()));
        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario.setRol(dto.rol());
        usuario.setImageUrl(dto.imageUrl());
        return usuario;
    }

    /**
     * Genera una cadena alfanumérica aleatoria de longitud especificada.
     * @param length Longitud de la cadena
     * @return Cadena alfanumérica aleatoria
     */
    private String generateRandomAlphanumeric(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int idx = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(idx));
        }
        return sb.toString();
    }

}
