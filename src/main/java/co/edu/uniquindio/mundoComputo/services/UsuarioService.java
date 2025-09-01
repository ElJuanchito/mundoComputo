package co.edu.uniquindio.mundoComputo.services;

import co.edu.uniquindio.mundoComputo.dtos.auth.TokenDTO;
import co.edu.uniquindio.mundoComputo.dtos.usuarios.CreateUsuarioDTO;
import co.edu.uniquindio.mundoComputo.model.Rol;

import java.util.List;

public interface UsuarioService {

    TokenDTO login(String email, String password) throws Exception;
    void createUser(CreateUsuarioDTO createUserDTO) throws Exception;
    List<Rol> getRoles();
}
