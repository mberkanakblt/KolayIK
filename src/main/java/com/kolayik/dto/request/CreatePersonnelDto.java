package com.kolayik.dto.request;

import com.kolayik.utility.enums.Role;
import com.kolayik.utility.enums.Status;

public record CreatePersonnelDto(

        String token,
        String name,
        String surname,
        String address,
        String phone,
        String email,
        Role roleName,
        String password,
        String avatar,
        Status status

) { }