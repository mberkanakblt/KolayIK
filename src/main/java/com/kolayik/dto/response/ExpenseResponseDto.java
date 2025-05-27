package com.kolayik.dto.response;

import com.kolayik.utility.enums.Status;

import java.time.LocalDateTime;

public record ExpenseResponseDto(
        Long id,
        Double amount,
        String description,
        String fileUrl,
        LocalDateTime date,
        Status status,
        String userName
) {
}
