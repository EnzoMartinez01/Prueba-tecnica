package com.technicaltest.mantenimientos.Models.Authentication;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ActiveToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 6555)
    private String token;
}
