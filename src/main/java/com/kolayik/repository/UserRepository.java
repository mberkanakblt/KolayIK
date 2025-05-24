package com.kolayik.repository;

import com.kolayik.entity.User;
import com.kolayik.view.VwAllowManage;
import com.kolayik.view.VwManager;
import com.kolayik.view.VwPersonnel;
import com.kolayik.view.VwUser;
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

    @Query(value = "SELECT new com.kolayik.view.VwManager(u.id,u.name,u.surname, u.email, u.status) " +
            "FROM User u WHERE u.id IN " +
            "(SELECT ur.userId FROM UserRole ur WHERE ur.roleName = com.kolayik.utility.enums.Role.COMPANY_ADMIN)")
    List<VwManager> getAllManager();


    @Query("SELECT u FROM User u WHERE " +
            "CAST(u.id AS string) LIKE %:term% OR " +
            "LOWER(u.name) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(u.surname) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(u.address) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<User> searchByTerm(@Param("term") String term);

    @Query("SELECT new com.kolayik.view.VwPersonnel(u.id, u.name, u.surname, u.email, u.status, u.companyName) " +
            "FROM User u WHERE u.id IN (" +
            "SELECT ur.userId FROM UserRole ur WHERE ur.roleName = com.kolayik.utility.enums.Role.PERSONNEL)")
    List<VwPersonnel> getAllPersonnel();

    @Query("SELECT new com.kolayik.view.VwUser(a.id, a.name, a.surname) FROM User a")
    List<VwUser> getAllUser();
}
