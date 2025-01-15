package com.technicaltest.mantenimientos.Dto;

import lombok.Data;

@Data
public class EntidadDto {
    private Integer idEntidad;
    private Integer documentTypeId;
    private String documentTypeName;
    private String documentNumber;
    private String socialReason;
    private String nameCommercial;
    private Integer contributorTypeId;
    private String contributorTypeName;
    private String address;
    private String phone;
    private Boolean active;
}
