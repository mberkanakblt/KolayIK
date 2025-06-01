package com.kolayik.repository;

import com.kolayik.entity.ZimmetAssignment;
import com.kolayik.entity.User;
import com.kolayik.utility.enums.ZimmetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZimmetAssignmentRepository extends JpaRepository<ZimmetAssignment, Long> {

    /**
     * Belirli bir kullanıcıya (personel) ait tüm zimmetleri getirir.
     */
    List<ZimmetAssignment> findByUser(User user);

    /**
     * Belirli bir kullanıcı için, yalnızca PENDING (bekleyen) zimmetleri getirir.
     * İhtiyaca göre kullanılabilir.
     */
    List<ZimmetAssignment> findByUserAndStatus(User user, ZimmetStatus status);

    /**
     * İhtiyaç halinde tüm PENDING zimmetleri çekmek için:
     */
    List<ZimmetAssignment> findByStatus(ZimmetStatus status);
}