package com.kolayik.repository;

import com.kolayik.entity.Allow;
import com.kolayik.entity.Shift;
import com.kolayik.view.VwAllow;
import com.kolayik.view.VwAllowManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AllowRepository extends JpaRepository<Allow, Long> {


    Optional<Allow> findByAllowtype(String allowtype);
    List<Allow> findAll();


    Optional<Allow> findById(Long id);

    void deleteById(Long id);



//    @Query("SELECT new com.kolayik.view.VwAllow(" +
//            "a.id" +
//            "a.allowtype) " +
//            "FROM Allow a")
//    List<VwAllow> getAllAllow();

//    @Query("SELECT a.id, a.allowtype FROM Allow a")
//    List<String> findAllAllowTypes();

    @Query("SELECT new com.kolayik.view.VwAllow(a.id, a.allowtype) FROM Allow a")
    List<VwAllow> findAllAllowTypes();




}



