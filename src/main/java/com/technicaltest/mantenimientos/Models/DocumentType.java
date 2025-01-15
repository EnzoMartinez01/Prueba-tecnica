package com.technicaltest.mantenimientos.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "tb_tipo_documento")
@Data
public class DocumentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_tipo_documento")
    private Integer id;

    @Column(name = "codigo", nullable = false, length = 20)
    @Size(max = 20)
    private String code;

    @Column(name = "nombre", nullable = false, length = 100)
    @Size(max = 100)
    private String name;

    @Column(name = "descripcion", length = 200)
    @Size(max = 200)
    private String description;

    @Column(name = "estado", nullable = false)
    private Boolean active = true;

}
