package co.edu.uniquindio.mundoComputo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "nombre")
    private String nombre;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "apellido")
    private String apellido;

    @NotNull
    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotNull
    @Size(min = 10, max = 10)
    @Column(name = "telefono")
    private String telefono;

    @OneToMany(mappedBy = "cliente")
    private Set<Venta> ventas;

    @OneToMany(mappedBy = "cliente")
    private Set<Reparacion> reparaciones;

    @Column(name = "estado")
    private EstadoCliente estado;

}
