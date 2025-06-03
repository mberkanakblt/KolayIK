package com.kolayik.service;

import com.kolayik.dto.request.CommentRequest;
import com.kolayik.dto.request.UpdateCommentDto;
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
        if (commentRepository.findByUserId(userId).isPresent()) {
            throw new IllegalStateException("User has already submitted a comment.");
        }

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

    public void editComment(UpdateCommentDto dto,Long userId) {
        Comment comment = commentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Comment not found "));

        comment.setDescription(dto.description());
        comment.setCommentDate(LocalDateTime.now());

        commentRepository.save(comment);



    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<Comment> getMyCommet(Long userId) {
        return commentRepository.findAllByUserId(userId);
    }
}
