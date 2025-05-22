package com.kolayik.controller;

import com.kolayik.dto.request.ProfileUpdateRequestDto;
import com.kolayik.dto.response.ProfileResponseDto;
import com.kolayik.entity.User;

import com.kolayik.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping()
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    // Profil bilgilerini getirir
    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfile(
            @AuthenticationPrincipal User currentUser) {
        ProfileResponseDto dto = userService.getProfile(currentUser.getId());
        return ResponseEntity.ok(dto);
    }

    // Profil bilgilerini günceller


    // Hesabı pasifleştirir
    @PutMapping("/deactivate")
    public ResponseEntity<Void> deactivateAccount(
            @AuthenticationPrincipal User currentUser) {
        userService.deactivate(currentUser.getId());
        return ResponseEntity.noContent().build();
    }

    // Hesabı siler
    @DeleteMapping
    public ResponseEntity<Void> deleteAccount(
            @AuthenticationPrincipal User currentUser) {
        userService.deleteAccount(currentUser.getId());
        return ResponseEntity.noContent().build();
    }
}