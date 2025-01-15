package com.technicaltest.mantenimientos.Models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.technicaltest.mantenimientos.Deserializer.ContributorTypeDeserializer;
import com.technicaltest.mantenimientos.Deserializer.DocumentTypeDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "tb_entidad")
@Data
public class Entidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_entidad")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_documento", nullable = false)
    @JsonDeserialize(using = DocumentTypeDeserializer.class)
    private DocumentType documentType;

    @Column(name = "nro_documento", nullable = false, unique = true, length = 25)
    @Size(max = 25)
    private String documentNumber;

    @Column(name = "razon_social", nullable = false, length = 100)
    @Size(max = 100)
    private String socialReason;

    @Column(name = "nombre_comercial", length = 100)
    @Size(max = 100)
    private String nameCommercial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_contribuyente")
    @JsonDeserialize(using = ContributorTypeDeserializer.class)
    private ContributorType contributorType;

    @Column(name = "direccion", length = 250)
    @Size(max = 250)
    private String address;

    @Column(name = "telefono", length = 50)
    @Size(max = 50)
    private String phone;

    @Column(name = "estado", nullable = false)
    private Boolean active = true;
}
