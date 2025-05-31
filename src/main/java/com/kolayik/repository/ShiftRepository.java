package com.kolayik.repository;

import com.kolayik.entity.Shift;
import com.kolayik.view.VwAllowManage;
import com.kolayik.view.VwShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
    void deleteByUserId(Long userId);
    boolean existsByUserId(Long userId);


    Optional<Shift> findById(Long id);

    void deleteById(Long id);

    @Query("SELECT new com.kolayik.view.VwShift(" +
            "a.id, a.userId, b.name, b.surname, a.startDate, a.startTime, a.endTime, a.totalWorkTime, a.isRepeat, a.days) " +
            "FROM Shift a JOIN User b ON a.userId = b.id")
    List<VwShift> getAllShifts();


}
