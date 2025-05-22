package com.kolayik.repository;

import com.kolayik.entity.Company;
import com.kolayik.entity.User;
import com.kolayik.view.VwManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOptionalByEmailAndPassword(String email, String password);
    Optional<User> findByVerificationToken(String token);
    Optional<User> findByNameAndSurname(String name, String surname);
    Optional<User> findByEmail(String email);


    boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM vw_manager", nativeQuery = true)
    List<VwManager> getAllManager();


    @Query("SELECT u FROM User u WHERE " +
            "CAST(u.id AS string) LIKE %:term% OR " +
            "LOWER(u.name) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(u.surname) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(u.address) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<User> searchByTerm(@Param("term") String term);

}
