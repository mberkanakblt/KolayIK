package com.kolayik.dto.request;

public record ResetPasswordRequest(
        String token,
        String newPassword
) {
}
