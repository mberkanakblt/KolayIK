package com.kolayik.repository;

import com.kolayik.entity.Company;
import com.kolayik.view.VwCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT new com.kolayik.view.VwCompany(c.name, c.status, u.name, u.email) " +
            "FROM Company c JOIN User u ON c.userId = u.id")
    List<VwCompany> getAllCompany();

    @Query("SELECT c FROM Company c WHERE c.userId = :userId")
    List<Company> getStatusCompanyId(@Param("userId") Long userId);

    boolean existsByUserId(Long userId);
}
