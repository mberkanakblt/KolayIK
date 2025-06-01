package com.kolayik.dto.response;

import com.kolayik.utility.enums.Description;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BreakResponseDto {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Description description;
    private String userFullName;
}
