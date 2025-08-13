package co.edu.uniquindio.mundoComputo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "facturas")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero_factura", unique = true, nullable = false)
    private String numeroFactura;

    @NotNull
    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @NotNull
    @Column(name = "total")
    private Double total;

    @OneToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;
}
