package com.technicaltest.mantenimientos.Controllers.Authentication;

import com.technicaltest.mantenimientos.Dto.LoginDto;
import com.technicaltest.mantenimientos.Models.Authentication.CustomUserDetails;
import com.technicaltest.mantenimientos.Models.Authentication.Users;
import com.technicaltest.mantenimientos.Security.JWTService;
import com.technicaltest.mantenimientos.Security.LoginResponse;
import com.technicaltest.mantenimientos.Services.Authentication.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JWTService jwtService;

    public AuthenticationController(AuthenticationService authenticationService,
                                    JWTService jwtService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;

    }

    @PostMapping("/register/user")
    public ResponseEntity<Map<String, String>> registerPersonal(@RequestBody Users users) {
        try {
            Users registeredUser = authenticationService.registerUsers(users);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuario registrado exitosamente.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        try {
            Users authenticatedPersonal = authenticationService.authenticate(loginDto);

            if (authenticatedPersonal.getActive()) {

                String jwtToken = jwtService.generateToken(new CustomUserDetails(authenticatedPersonal));

                LoginResponse loginResponse = new LoginResponse(jwtToken, null);
                return ResponseEntity.ok(loginResponse);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (RuntimeException e) {
            System.out.println("No se encontró el personal tampoco.");
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(Map.of("message", "Token no proporcionado o inválido."));
        }

        String token = authHeader.substring(7);

        authenticationService.logout(token);

        return ResponseEntity.ok(Map.of("message", "Logged out successfully."));
    }
}
