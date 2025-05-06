package com.kolayik.dto.request;

public record AddRoleRequestDto(
        String roleName,
        Long userId
) {
}
