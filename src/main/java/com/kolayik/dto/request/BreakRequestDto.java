package com.kolayik.dto.request;

import com.kolayik.utility.enums.Description;

import java.time.LocalDateTime;

public record BreakRequestDto(
        LocalDateTime startTime,
        LocalDateTime endTime,
        Description  description,
        Long userId

) {
}
