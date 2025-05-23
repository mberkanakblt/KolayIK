package com.kolayik.dto.request;

public record ChangePasswordRequestDto(
        String newPassword,
        String currentPassword
) {
}
