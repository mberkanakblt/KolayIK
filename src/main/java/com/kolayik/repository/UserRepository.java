package com.kolayik.repository;

import com.kolayik.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOptionalByEmailAndSifre(String email, String password);
}
