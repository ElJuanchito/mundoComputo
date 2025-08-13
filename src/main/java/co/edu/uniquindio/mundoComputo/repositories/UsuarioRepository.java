package co.edu.uniquindio.mundoComputo.repositories;

import co.edu.uniquindio.mundoComputo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}

