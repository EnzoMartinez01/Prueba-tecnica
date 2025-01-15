package com.technicaltest.mantenimientos.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "tb_tipo_contribuyente")
@Data
public class ContributorType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_tipo_contribuyente")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 50)
    @Size(max = 50)
    private String name;

    @Column(name = "estado", nullable = false)
    private Boolean active;
}
