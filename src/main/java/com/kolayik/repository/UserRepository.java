package com.kolayik.repository;

import com.kolayik.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOptionalByEmailAndPassword(String email, String password);
    Optional<User> findByVerificationToken(String token);

    Optional<User> findByEmail(String email);

    Optional<User> findByNameAndSurname(String name, String surname);
}
