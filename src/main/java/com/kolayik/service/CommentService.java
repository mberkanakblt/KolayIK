package com.kolayik.service;

import com.kolayik.dto.request.CommentRequest;
import com.kolayik.dto.request.UpdateCommentDto;
import com.kolayik.entity.Comment;
import com.kolayik.entity.Company;
import com.kolayik.entity.User;
import com.kolayik.repository.CommentRepository;
import com.kolayik.repository.CompanyRepository;
import com.kolayik.repository.UserRepository;
import com.kolayik.utility.enums.Status;
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
    private final CompanyRepository companyRepository;



    public void addComment(Long userId,String description) {
        // Kullanıcının zaten yorum yapıp yapmadığını kontrol et
        if (commentRepository.findByUserId(userId).isPresent()) {
            throw new IllegalStateException("User has already submitted a comment.");
        }

        // Kullanıcıyı getir
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Kullanıcının şirket ID'si var mı kontrol et
        if (user.getCompanyId() == null) {
            throw new IllegalStateException("User is not associated with any company.");
        }

        // Şirketi getir
        Company company = companyRepository.findById(user.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + user.getCompanyId()));

        // Şirket aktif mi kontrol et
        if (company.getStatus() != Status.AKTIF) {
            throw new IllegalStateException("User's company is not active. Cannot add comment.");
        }

        // Yorum oluştur
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
