package com.technicaltest.mantenimientos.Dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsersDto {
    private Integer idUser;
    private String name;
    private String lastName;
    private String email;
    private String username;
    private LocalDate connectionDate;
    private Boolean active;
}
