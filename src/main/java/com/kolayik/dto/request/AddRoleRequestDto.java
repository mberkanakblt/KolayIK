package com.kolayik.dto.request;

import com.kolayik.utility.enums.Role;

public record AddRoleRequestDto(
        Role roleName,
        Long userId
) {
}
