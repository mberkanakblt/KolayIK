package com.kolayik.dto.request;

import com.kolayik.utility.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DoRegisterRequestDto(
        @NotNull
        @NotEmpty
        @Size(min = 2, max = 80)
        String ad,
        String adres,
        @NotNull
        @NotEmpty
        @Size(min = 10, max = 20)
        String telefon,
        @NotNull
        @NotEmpty
        @Email
        String email,
        @NotNull
        @NotEmpty
        @Size(min = 8, max = 128)
        String password,
        @NotNull
        @NotEmpty
        @Size(min = 8, max = 128)
        String rePassword,
        @NotNull
        @NotEmpty
        String roleName,
        @NotNull
        @NotEmpty
        String companyName,
        Status status,
        String avatar
) {
}
