package com.kolayik.dto.response;

import com.kolayik.utility.enums.Status;

public record CompanyResponseDto(
        Long id,
        String name,
        String address,
        String phone,
        Status status,
        String sector,
        Long userId,
        String userName
) {
}
