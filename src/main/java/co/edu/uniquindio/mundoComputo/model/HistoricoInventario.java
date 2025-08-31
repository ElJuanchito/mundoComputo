package co.edu.uniquindio.mundoComputo.model;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico_inventario")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoricoInventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @NotNull
    @Column(name = "cantidad_anterior", nullable = false)
    private Integer cantidadAnterior;

    @NotNull
    @Column(name = "cantidad_nueva", nullable = false)
    private Integer cantidadNueva;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false)
    private TipoMovimientoInventario tipoMovimiento;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Size(max = 255)
    @Column(name = "comentario")
    private String comentario;
}

