package com.kolayik.repository;


import com.kolayik.entity.UserRole;
import com.kolayik.utility.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {


    List<UserRole> findByRoleName(Role role);

    List<UserRole> findByPersonnelId(Long id);
}
