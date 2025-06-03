package com.kolayik.dto.request;

public record AddAdminRequestDto(
        String name,
        String surname,
        String email,
        String password,
        String rePassword
) {
}
