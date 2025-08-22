package co.edu.uniquindio.mundoComputo.model;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "ventas")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha")
    private LocalDateTime fecha;

    @NotNull
    @Column(name = "total")
    private Double total;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private Set<DetalleVenta> detallesVenta;

    @OneToOne(mappedBy = "venta", cascade = CascadeType.ALL)
    private Factura factura;
}
