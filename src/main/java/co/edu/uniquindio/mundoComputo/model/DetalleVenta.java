package co.edu.uniquindio.mundoComputo.model;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "detalles_venta")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @NotNull
    @Column(name = "cantidad")
    private Integer cantidad;

    @NotNull
    @Column(name = "precio_unitario")
    private Double precioUnitario;

    @NotNull
    @Column(name = "subtotal")
    private Double subtotal;
}
