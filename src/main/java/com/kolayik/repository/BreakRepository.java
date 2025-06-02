package com.kolayik.repository;

import com.kolayik.entity.Break;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BreakRepository extends JpaRepository<Break, Long> {
    @Query("SELECT b FROM Break b WHERE b.user.id = :userId")
    List<Break> findAllByUserId(@Param("userId") Long userId);



}
