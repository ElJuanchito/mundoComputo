package co.edu.uniquindio.mundoComputo.dtos.productos;

import co.edu.uniquindio.mundoComputo.model.TipoMovimientoInventario;

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
