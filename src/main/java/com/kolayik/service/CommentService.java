package com.kolayik.service;

import com.kolayik.entity.Comment;
import com.kolayik.entity.User;
import com.kolayik.repository.CommentRepository;
import com.kolayik.repository.UserRepository;
import com.kolayik.view.VwComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;



    public void addComment(Long userId,String description) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));


        Comment comment = Comment.builder()
                .description(description)
                .user(user)
                .commentDate(LocalDateTime.now())
                .build();
        commentRepository.save(comment);

    }

    public List<VwComment> getComment() {
        return commentRepository.getAllComment();

    }
}
