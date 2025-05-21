package com.kolayik.repository;

import com.kolayik.entity.Company;
import com.kolayik.entity.User;
import com.kolayik.view.VwManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOptionalByEmailAndPassword(String email, String password);
    Optional<User> findByVerificationToken(String token);
    Optional<User> findByNameAndSurname(String name, String surname);
    Optional<User> findByEmail(String email);
    @Query(value = "SELECT new com.kolayik.view.VwManager(u.id,u.name,u.surname, u.email, u.status) " +
            "FROM User u WHERE u.id IN " +
            "(SELECT ur.userId FROM UserRole ur WHERE ur.roleName = com.kolayik.utility.enums.Role.COMPANY_ADMIN)")
    List<VwManager> getAllManager();

}
