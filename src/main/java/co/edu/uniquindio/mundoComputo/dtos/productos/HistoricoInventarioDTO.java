package co.edu.uniquindio.mundoComputo.dtos.productos;

import co.edu.uniquindio.mundoComputo.model.Producto;
import co.edu.uniquindio.mundoComputo.model.TipoMovimientoInventario;
import co.edu.uniquindio.mundoComputo.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record HistoricoInventarioDTO(
        Long id,
        String nombreProducto,
        Long productoId,
        LocalDateTime fecha,
        int cantidadAnterior,
        int cantidadNueva,
        TipoMovimientoInventario tipoMovimiento,
        String nombreUsuario,
        Long usuarioId,
        String comentario
) {
}
