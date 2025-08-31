package co.edu.uniquindio.mundoComputo.repositories;

import co.edu.uniquindio.mundoComputo.model.HistoricoInventario;
import co.edu.uniquindio.mundoComputo.model.Producto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoInventarioRepository extends JpaRepository<HistoricoInventario, Long> {

    List<HistoricoInventario> findByProducto(Producto producto);
}

