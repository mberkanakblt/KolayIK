package com.kolayik.repository;

import com.kolayik.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUserId(Long userId);

    List<Expense> findAllByCompanyId(Long companyId);

    List<Expense> findAllByUserIdIn(List<Long> userIds);
}
