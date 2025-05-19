package com.kolayik.repository;

import com.kolayik.entity.AllowManage;

import com.kolayik.entity.User;
import com.kolayik.utility.enums.AllowState;
import com.kolayik.view.VwAllowManage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AllowManageRepository extends JpaRepository<AllowManage, Long> {


    Optional<User> findOptionalByNameAndSurname(String name, String surname);

    List<AllowManage> findAll();


    @Query("SELECT new com.kolayik.view.VwAllowManage(" +
            "a. id, a.allow_id, a.user_id, a.name, a.surname, a.allowstate, " +
            "a.allowstartdate, a.allowfinishdate, a.approveddate, a.rejecteddate, a.allowtype) " +
            "FROM AllowManage a")
    List<VwAllowManage> getAllAllowManage();

    Optional<AllowManage> findById(Long id);


//    Optional<Allow> findTopByUserIdOrderByAllowstartdateDesc(Long userId);



    @Query("SELECT new com.kolayik.view.VwAllowManage(" +
            "a. id, a.allow_id, a.user_id, a.name, a.surname, a.allowstate, " +
            "a.allowstartdate, a.allowfinishdate, a.approveddate, a.rejecteddate, a.allowtype) " +
            "FROM AllowManage a WHERE a.allowstate = com.kolayik.utility.enums.AllowState.BEKLEMEDE")
    List<VwAllowManage> getAllPendingAllows();


//    @Transactional
//    @Modifying
//    @Query("UPDATE AllowManage a SET a.allowstate = :state WHERE a.allow_id = :id")
//    int updateAllowState(@Param("id") Long id, @Param("state") AllowState state);


//
//    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Allow a " +
//            "WHERE a.userId = :userId " +
//            "AND (:startDate BETWEEN a.allowStartDate AND a.allowFinishDate " +
//            "OR :finishDate BETWEEN a.allowStartDate AND a.allowFinishDate " +
//            "OR a.allowStartDate BETWEEN :startDate AND :finishDate)")
//    boolean existsByUserIdAndDateRange(@Param("userId") Long userId,
//                                       @Param("startDate") Date startDate,
//                                       @Param("finishDate") Date finishDate);
}

