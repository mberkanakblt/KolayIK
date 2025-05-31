package com.kolayik.dto.request;

public record PersonnelSearchResponseDto(
        Long id,
        String name,
        String surname,
        String address,
        String phone,
        String email,
        String avatar,
        String status,
        String companyName

) {
}
