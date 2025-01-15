package com.technicaltest.mantenimientos.Services.Authentication;

import com.technicaltest.mantenimientos.Models.Authentication.CustomUserDetails;
import com.technicaltest.mantenimientos.Models.Authentication.Users;
import com.technicaltest.mantenimientos.Repositories.Authentication.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            Users usuarios = userOpt.get();
            return new CustomUserDetails(usuarios);
        }

        throw new UsernameNotFoundException("Username not found: " + username);
    }
}
