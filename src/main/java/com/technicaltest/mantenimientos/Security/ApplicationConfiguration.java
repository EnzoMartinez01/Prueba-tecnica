package com.technicaltest.mantenimientos.Security;

import com.technicaltest.mantenimientos.Models.Authentication.CustomUserDetails;
import com.technicaltest.mantenimientos.Models.Authentication.Users;
import com.technicaltest.mantenimientos.Repositories.Authentication.UsersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Configuration
public class ApplicationConfiguration {
    private final UsersRepository userRepository;

    public ApplicationConfiguration(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<com.technicaltest.mantenimientos.Models.Authentication.Users> sellerSupervisorOpt = userRepository.findByUsername(username);
            if (sellerSupervisorOpt.isPresent()) {
                Users usuarios = sellerSupervisorOpt.get();
                return new CustomUserDetails(
                        usuarios
                );
            }

            throw new UsernameNotFoundException("Username not found");
        };
    }


    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
