package com.kolayik.dto.request;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.kolayik.utility.enums.Description;

import java.time.LocalDateTime;

public record BreakUpdateDto(
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime startTime,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime endTime,
        Description description,
        Long userId


) {


}
