package com.kolayik.dto.request;


import com.kolayik.utility.enums.Status;

public record PersonnelUpdateRequestDto(
        String name,
        String email,
        String surname,
        String address,
        String phone,
        String avatar,
        Status  status,
        String companyName

) { }
