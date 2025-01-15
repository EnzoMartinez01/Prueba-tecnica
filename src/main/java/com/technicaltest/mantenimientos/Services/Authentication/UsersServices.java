package com.technicaltest.mantenimientos.Services.Authentication;

import com.technicaltest.mantenimientos.Dto.UsersDto;
import com.technicaltest.mantenimientos.Models.Authentication.Users;
import com.technicaltest.mantenimientos.Repositories.Authentication.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsersServices {
    private static final Logger logger = LoggerFactory.getLogger(UsersServices.class);

    private final UsersRepository usersRepository;

    public UsersServices(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Page<UsersDto> findAll(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return usersRepository.findAll(pageable).map(this::mapToDto);
        } catch (Exception e) {
            logger.error("Error al obtener los usuarios", e);
            throw new RuntimeException("Error al obtener los usuarios", e);
        }
    }

    public UsersDto findByUsername(String username) {
        try {
            return usersRepository.findByUsername(username).map(this::mapToDto).orElse(null);
        } catch (Exception e) {
            logger.error("Error al obtener el usuario con username {}", username);
            throw new RuntimeException("Error al obtener el usuario con username " + username, e);
        }
    }

    public UsersDto mapToDto(Users users) {
        UsersDto dto = new UsersDto();
        dto.setIdUser(users.getId());
        dto.setName(users.getName());
        dto.setLastName(users.getLastName());
        dto.setEmail(users.getEmail());
        dto.setUsername(users.getUsername());
        dto.setConnectionDate(users.getConnectionDate());
        dto.setActive(users.getActive());
        return dto;
    }

    public Users updateUser(Integer id, Users users) {
        try {
            return usersRepository.findById(id).map(existingUser -> {
                if (users.getName() != null && !users.getName().isEmpty()) {
                    existingUser.setName(users.getName());
                }
                if (users.getLastName() != null && !users.getLastName().isEmpty()) {
                    existingUser.setLastName(users.getLastName());
                }
                if (users.getEmail() != null && !users.getEmail().isEmpty()) {
                    existingUser.setEmail(users.getEmail());
                }
                if (users.getUsername() != null && !users.getUsername().isEmpty()) {
                    existingUser.setUsername(users.getUsername());
                }
                if (users.getActive() != null) {
                    existingUser.setActive(users.getActive());
                }
                return usersRepository.save(existingUser);
            }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        } catch (Exception e) {
            logger.error("Error al actualizar el usuario", e);
            throw new RuntimeException("Error al actualizar el usuario", e);
        }
    }

    public void deleteUser(Integer id) {
        try {
            usersRepository.findById(id).ifPresent(usersRepository::delete);
        } catch (Exception e) {
            logger.error("Error al eliminar el usuario", e);
            throw new RuntimeException("Error al eliminar el usuario", e);
        }
    }
}
