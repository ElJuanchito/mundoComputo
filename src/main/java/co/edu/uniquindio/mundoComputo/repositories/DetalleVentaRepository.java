package co.edu.uniquindio.mundoComputo.repositories;

import co.edu.uniquindio.mundoComputo.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
}

