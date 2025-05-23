package com.kolayik.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProfileUpdateRequestDto(
        String name,
        String surname,
        String email,
        String phone,
        String address,
        String companyName,
        String birthdate,
        String gender,
        String avatar
) {}