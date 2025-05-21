package com.kolayik.repository;

import com.kolayik.entity.Company;
import com.kolayik.view.VwCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT new com.kolayik.view.VwCompany(c.name,c.status,u.name,u.email) "+"FROM Company c JOIN c.user u" )
    List<VwCompany> getAllCompany();

}
