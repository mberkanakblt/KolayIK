package com.kolayik.repository;

import com.kolayik.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOptionalByEmailAndPassword(String email, String password);
    Optional<User> findByVerificationToken(String token);

    Optional<User> findByEmail(String email);

    Optional<User> findPersonnelById(Long userId);

    boolean existsByEmail(String email);
}
