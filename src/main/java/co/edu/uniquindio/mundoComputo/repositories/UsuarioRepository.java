package co.edu.uniquindio.mundoComputo.repositories;

import co.edu.uniquindio.mundoComputo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import co.edu.uniquindio.mundoComputo.model.Rol;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByRol(Rol rol);
}

