package com.kolayik.controller;

import com.kolayik.dto.response.BaseResponse;
import com.kolayik.service.CommentService;
import com.kolayik.view.VwComment;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kolayik.config.RestApis.*;




@RestController
@RequiredArgsConstructor
@RequestMapping(COMMENT)
@CrossOrigin("*")
@SecurityRequirement(name = "bearerAuth")
public class CommentController {
    private final CommentService commentService;

    @PostMapping(ADD_COMMENT)
    public ResponseEntity<BaseResponse<Boolean>> addComment(@RequestBody String description,Long userId){
        commentService.addComment(userId,description);
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
