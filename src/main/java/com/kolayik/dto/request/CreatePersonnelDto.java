package com.kolayik.dto.request;


import com.kolayik.utility.enums.Role;
import com.kolayik.utility.enums.Status;
import jakarta.validation.constraints.NotNull;


public record CreatePersonnelDto(
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

