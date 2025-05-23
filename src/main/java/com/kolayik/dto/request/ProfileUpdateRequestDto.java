package com.kolayik.dto.request;


public record ProfileUpdateRequestDto(
        String token,
        String name,
        String surname,
        String email,
        String phone,
        String address,
        String companyName,
        String avatar
) {}