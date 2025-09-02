package co.edu.uniquindio.mundoComputo.services;

import co.edu.uniquindio.mundoComputo.dtos.auth.TokenDTO;
import co.edu.uniquindio.mundoComputo.dtos.usuarios.*;
import co.edu.uniquindio.mundoComputo.model.EstadoUsuario;
import co.edu.uniquindio.mundoComputo.model.Rol;

import java.util.List;

/**
 * Servicio para la gestión de usuarios en el sistema MundoComputo.
 * Provee métodos para autenticación, creación, actualización, eliminación,
 * recuperación de usuarios y gestión de roles.
 */
public interface UsuarioService {

    /**
     * Inicia sesión con las credenciales proporcionadas.
     * @param loginDTO DTO con los datos de inicio de sesión
     * @return TokenDTO con el token de autenticación
     * @throws Exception si las credenciales son inválidas o ocurre un error
     */
    TokenDTO login(LoginDTO loginDTO) throws Exception;

    /**
     * Crea un nuevo usuario en el sistema.
     * @param createUserDTO DTO con los datos del usuario a crear
     * @throws Exception si ocurre un error en la creación
     */
    void createUser(CreateUsuarioDTO createUserDTO) throws Exception;

    /**
     * Actualiza la información de un usuario existente.
     * @param updateUsuarioDTO DTO con los datos actualizados del usuario
     * @throws Exception si ocurre un error en la actualización
     */
    void updateUsuario(UpdateUsuarioDTO updateUsuarioDTO) throws Exception;

    /**
     * Elimina un usuario por su identificador.
     * @param id Identificador del usuario a eliminar
     * @throws Exception si ocurre un error en la eliminación
     */
    void deleteUsuario(Long id) throws Exception;

    /**
     * Envía un código de verificación al correo electrónico proporcionado.
     * @param email Correo electrónico del usuario
     * @throws Exception si ocurre un error en el envío
     */
    void sendVerificationCode(String email) throws Exception;

    /**
     * Cambia la contraseña de un usuario.
     * @param changePasswordDTO DTO con los datos para el cambio de contraseña
     * @throws Exception si ocurre un error en el proceso
     */
    void changePassword(ChangePasswordDTO changePasswordDTO) throws Exception;

    /**
     * Obtiene la lista de todos los usuarios registrados.
     * @return Lista de UsuarioInfoDTO con la información de los usuarios
     */
    List<UsuarioInfoDTO> getAllUsuarios();

    /**
     * Obtiene la información de un usuario por su identificador.
     * @param id Identificador del usuario
     * @return UsuarioInfoDTO con la información del usuario
     * @throws Exception si el usuario no existe o ocurre un error
     */
    UsuarioInfoDTO getUsuarioById(Long id) throws Exception;

    /**
     * Obtiene los usuarios filtrados por estado.
     * @param estadoUsuario Estado por el cual filtrar
     * @return Lista de UsuarioInfoDTO con los usuarios filtrados
     * @throws Exception si ocurre un error en la consulta
     */
    List<UsuarioInfoDTO> getUsuariosByEstado(EstadoUsuario estadoUsuario) throws Exception;

    /**
     * Obtiene los usuarios filtrados por rol.
     * @param rol Rol por el cual filtrar
     * @return Lista de UsuarioInfoDTO con los usuarios filtrados
     * @throws Exception si ocurre un error en la consulta
     */
    List<UsuarioInfoDTO> getUsuariosByRol(Rol rol) throws Exception;

    /**
     * Obtiene los usuarios cuyo nombre coincide con el parámetro proporcionado.
     * @param nombre Nombre por el cual filtrar
     * @return Lista de UsuarioInfoDTO con los usuarios filtrados
     * @throws Exception si ocurre un error en la consulta
     */
    List<UsuarioInfoDTO> getUsuariosByNombre(String nombre) throws Exception;

    /**
     * Obtiene la lista de roles disponibles en el sistema.
     * @return Lista de roles
     */
    List<Rol> getRoles();
}
