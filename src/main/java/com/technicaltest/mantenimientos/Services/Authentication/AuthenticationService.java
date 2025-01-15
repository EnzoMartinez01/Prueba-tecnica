package com.technicaltest.mantenimientos.Services.Authentication;

import com.technicaltest.mantenimientos.Dto.LoginDto;
import com.technicaltest.mantenimientos.Models.Authentication.Users;
import com.technicaltest.mantenimientos.Repositories.Authentication.ActiveTokenRepository;
import com.technicaltest.mantenimientos.Repositories.Authentication.UsersRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UsersRepository usersRepository;
    private final ActiveTokenRepository activeTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationService(UsersRepository usersRepository,
                                 ActiveTokenRepository activeTokenRepository,
                                 AuthenticationManager authenticationManager,
                                 BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.activeTokenRepository = activeTokenRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public Users registerUsers(Users users) {
        try {
            users.setPassword(passwordEncoder.encode(users.getPassword()));
            return usersRepository.save(users);
        } catch (Exception e) {
            logger.error("Error al crear el usuario", e);
            throw new RuntimeException("Error al crear el usuario", e);
        }
    }

    public Users authenticate(LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    )
            );
            Users users = usersRepository.findByUsername(loginDto.getUsername()).orElseThrow();
            users.setConnectionDate(LocalDate.now());
            return users;
        } catch (Exception e) {
            logger.error("Error al autenticar el usuario", e);
            throw new RuntimeException("Error al autenticar el usuario", e);
        }
    }

    @Transactional
    public void logout(String token) {
        try {
            activeTokenRepository.deleteByToken(token);
            System.out.println("Token eliminado correctamente.");
        } catch (Exception e) {
            logger.error("Error al eliminar el token de sesión", e);
            throw new RuntimeException("Error al eliminar el token de sesión", e);
        }
    }
}
