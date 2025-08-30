package co.edu.uniquindio.mundoComputo.dtos.productos;

import lombok.Builder;

@Builder
public record ProductoInfoDTO(
    Long id,
    String nombre,
    String descripcion,
    Double precio,
    Integer stock,
    Integer stockMinimo,
    Long categoriaId
) {

}
