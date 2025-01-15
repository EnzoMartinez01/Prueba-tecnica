package com.technicaltest.mantenimientos.Models.Authentication;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "tb_usuario")
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "apellido", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "fecha_conexion")
    private LocalDate connectionDate;

    @Column(name = "estado", nullable = false)
    private Boolean active = true;
}
