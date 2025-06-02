package com.kolayik.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DoLoginRequestDto(
        @Email
        @NotEmpty
        @NotNull
        String email,
        @NotEmpty
        @NotNull
        String password
) {
}
