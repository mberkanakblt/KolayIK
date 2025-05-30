package com.kolayik.dto.response;

import com.kolayik.utility.enums.Role;

public record LoginResponseDto(
        String token,
        Role roleName
) {
}
