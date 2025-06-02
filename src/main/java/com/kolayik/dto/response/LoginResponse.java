package com.kolayik.dto.response;

import com.kolayik.utility.enums.Role;

public record LoginResponse(
        String token,
        Role roleName
) {
}
