package co.edu.uniquindio.mundoComputo.model;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", unique = true, nullable = false)
    private String nombre; // ADMIN, VENDEDOR, TECNICO

    @OneToMany(mappedBy = "rol")
    private Set<Usuario> usuarios;
}
