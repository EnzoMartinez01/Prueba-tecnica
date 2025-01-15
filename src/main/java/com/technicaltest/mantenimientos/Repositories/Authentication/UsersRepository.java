package com.technicaltest.mantenimientos.Repositories.Authentication;

import com.technicaltest.mantenimientos.Models.Authentication.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);
}
