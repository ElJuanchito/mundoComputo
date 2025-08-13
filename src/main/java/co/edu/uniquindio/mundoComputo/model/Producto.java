package co.edu.uniquindio.mundoComputo.model;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import java.util.Set;

@Entity
@Table(name = "productos")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "nombre")
    private String nombre;

    @NotNull
    @Size(min = 5, max = 255)
    @Column(name = "descripcion")
    private String descripcion;

    @NotNull
    @Min(0)
    @Column(name = "precio")
    private Double precio;

    @NotNull
    @Min(0)
    @Column(name = "stock")
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaProducto categoria;

    @OneToMany(mappedBy = "producto")
    private Set<DetalleVenta> detallesVenta;
}
