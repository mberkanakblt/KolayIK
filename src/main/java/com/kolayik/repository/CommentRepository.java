package com.kolayik.repository;

import com.kolayik.entity.Comment;
import com.kolayik.view.VwComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT new com.kolayik.view.VwComment(c.description, c.commentDate, u.companyName, u.name,u.surname) " +
            "FROM Comment c JOIN c.user u")
    List<VwComment> getAllComment();
    Optional<Comment> findByUserId(Long userId);
}
