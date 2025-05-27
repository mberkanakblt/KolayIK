package com.kolayik.controller;

import com.kolayik.config.JwtManager;
import com.kolayik.dto.request.CommentRequest;
import com.kolayik.dto.response.BaseResponse;
import com.kolayik.service.CommentService;
import com.kolayik.view.VwComment;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.kolayik.config.RestApis.*;




@RestController
@RequiredArgsConstructor
@RequestMapping(COMMENT)
@CrossOrigin("*")
@SecurityRequirement(name = "bearerAuth")
public class CommentController {
    private final CommentService commentService;
    private final JwtManager jwtManager;

    @PostMapping(ADD_COMMENT)
    public ResponseEntity<BaseResponse<Boolean>> addComment(@RequestBody CommentRequest dto){
        Optional<Long> optionalUserId = jwtManager.validateToken(dto.token());
        commentService.addComment(optionalUserId.get(), dto.description());
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                        .code(200)
                        .data(true)
                        .message("Comment added")
                .build());

    }
    @GetMapping(GET_COMMENT)
    public ResponseEntity<BaseResponse<List<VwComment>>> getAllComment(){
            return ResponseEntity.ok(BaseResponse.<List<VwComment>>builder()
                            .code(200)
                            .message("All comment found")
                            .data(commentService.getComment())
                    .build());
    }

}
