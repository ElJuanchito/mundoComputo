package co.edu.uniquindio.mundoComputo.model;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 6)
    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @NotNull
    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "nombre")
    private String nombre;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "apellido")
    private String apellido;

    @NotNull
    @Column(name = "rol")
    private Rol rol;

    @NotNull
    @Column(name = "estado")
    private EstadoUsuario estado;

    @ToString.Exclude
    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "codigo_verificacion_id")
    private CodigoVerificacion codigoVerificacion;

    @Column(length = 1024)
    private String imageUrl;
}
