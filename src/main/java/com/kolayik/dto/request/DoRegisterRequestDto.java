package com.kolayik.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DoRegisterRequestDto(
        @NotNull
        @NotEmpty
        @Size(min = 2, max = 80)
        String name,
        @NotNull
        @NotEmpty
        @Size(min = 10, max = 20)
                String surname,
        String phone,
        @NotNull
        @NotEmpty
        @Email
        String email,
        String address,
        String avatar,
        @NotNull
        @NotEmpty
        @Size(min = 8, max = 128)
        String password,
        @NotNull
        @NotEmpty
        String companyName,
        @NotNull
        @NotEmpty
        @Size(min = 8, max = 128)
        String rePassword
) {
}
