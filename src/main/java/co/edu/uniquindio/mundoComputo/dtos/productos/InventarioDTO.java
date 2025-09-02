package co.edu.uniquindio.mundoComputo.dtos.productos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para registrar movimientos de inventario (entrada, salida, ajuste) de productos.
 * Encapsula los datos necesarios para la operación.
 */
public record InventarioDTO(
    @NotNull Long productoId,
    @NotNull Long usuarioId,
    @NotNull @Min(0) int cantidad,
    @NotBlank String descripcion
) {}

