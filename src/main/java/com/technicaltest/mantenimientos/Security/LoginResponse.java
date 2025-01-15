package com.technicaltest.mantenimientos.Security;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private Long expiresIn;

    public LoginResponse(String token, Long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }
}
