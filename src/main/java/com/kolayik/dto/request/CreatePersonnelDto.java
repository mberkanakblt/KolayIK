package com.kolayik.dto.request;

import com.kolayik.utility.enums.Status;

public record CreatePersonnelDto(
        Long userid,
        Long id,
        String name,
        String surname,
        String address,
        String phone,
        String email,
        String password,
        String avatar,
        Status status,
        String companyName
) { }