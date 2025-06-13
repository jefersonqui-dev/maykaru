package com.jquiguantar.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jquiguantar.backend.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
