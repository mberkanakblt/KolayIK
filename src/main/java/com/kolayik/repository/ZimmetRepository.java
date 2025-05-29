package com.kolayik.repository;

import com.kolayik.entity.ZimmetAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZimmetRepository extends JpaRepository<ZimmetAssignment, Long> {}
