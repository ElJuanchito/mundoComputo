package co.edu.uniquindio.mundoComputo.repositories;

import co.edu.uniquindio.mundoComputo.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
}

