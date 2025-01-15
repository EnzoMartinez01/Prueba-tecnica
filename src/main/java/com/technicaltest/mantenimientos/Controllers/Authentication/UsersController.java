package com.technicaltest.mantenimientos.Controllers.Authentication;

import com.technicaltest.mantenimientos.Dto.UsersDto;
import com.technicaltest.mantenimientos.Models.Authentication.Users;
import com.technicaltest.mantenimientos.Services.Authentication.UsersServices;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    private final UsersServices usersServices;

    public UsersController(UsersServices usersServices) {
        this.usersServices = usersServices;
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<Page<UsersDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<UsersDto> users = usersServices.findAll(page, size);
            return ResponseEntity.ok(users);
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UsersDto> getCurrentUser(Principal principal) {
        String username = principal.getName();
        UsersDto user = usersServices.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<Map<String, String>> updateUser(@PathVariable Integer id, @RequestBody Users users) {
        try {
            Users updatedUser = usersServices.updateUser(id, users);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuario " + updatedUser.getUsername() + " actualizado satisfactoriamente.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Integer id) {
        try {
            usersServices.deleteUser(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuario " + id + " eliminado satisfactoriamente.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
