package com.kolayik.controller;

import com.kolayik.config.JwtManager;
import com.kolayik.dto.request.BreakRequestDto;
import com.kolayik.dto.request.BreakUpdateDto;
import com.kolayik.dto.response.BaseResponse;
import com.kolayik.dto.response.BreakResponseDto;
import com.kolayik.entity.Break;
import com.kolayik.service.BreakService;
import com.kolayik.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dev/v1/break")
@RequiredArgsConstructor
public class BreakController {

    private final BreakService breakService;
    private final UserService userService;
    private final JwtManager jwtManager;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<Break>> addBreak(
            @RequestBody BreakRequestDto dto,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        // token ile doğrulama yap

        Break savedBreak = breakService.addBreak(dto);
        return ResponseEntity.ok(BaseResponse.<Break>builder()
                .code(200)
                .data(savedBreak)
                .message("Mola başarıyla oluşturuldu.")
                .build());
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponse<Break>> updateBreak(
            @PathVariable Long id,
            @RequestBody BreakUpdateDto dto) {

        Break updatedBreak = breakService.updateBreak(id, dto);

        return ResponseEntity.ok(BaseResponse.<Break>builder()
                .code(200)
                .data(updatedBreak)
                .message("Mola başarıyla güncellendi.")
                .build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<Boolean>> deleteBreak(@PathVariable Long id) {
        breakService.deleteBreak(id);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .data(true)
                .message("Mola başarıyla silindi.")
                .build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<BaseResponse<List<BreakResponseDto>>> getBreaksByUserId(@PathVariable Long userId) {
        List<BreakResponseDto> response = breakService.getBreakDtoByUserId(userId);
        return ResponseEntity.ok(BaseResponse.<List<BreakResponseDto>>builder()
                .code(200)
                .message("Molalar başarıyla getirildi.")
                .data(response)
                .build());
    }
    @GetMapping("/my-breaks")public ResponseEntity<BaseResponse<List<BreakResponseDto>>> getMyBreaks(String token) {
        Optional<Long> optionaluserId=jwtManager.validateToken(token);
        List<BreakResponseDto> response = breakService.getBreakDtoByUserId(optionaluserId.get());
        return ResponseEntity.ok(BaseResponse.<List<BreakResponseDto>>builder()
                .code(200)
                .message("Molalarınız başarıyla getirildi.")
                .data(response)
                .build());
    }


}
