package co.edu.uniquindio.mundoComputo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reparaciones")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Reparacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 255)
    @Column(name = "descripcion_problema")
    private String descripcionProblema;

    @NotNull
    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @NotNull
    @Column(name = "fecha_estimada_entrega")
    private LocalDate fechaEstimadaEntrega;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoReparacion estado;

    @NotNull
    @Min(0)
    @Column(name = "costo")
    private Double costo;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "tecnico_id", nullable = false)
    private Usuario tecnico;

    @OneToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;
}
