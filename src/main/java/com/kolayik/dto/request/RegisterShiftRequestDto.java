package com.kolayik.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kolayik.utility.enums.Day;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public record RegisterShiftRequestDto(
        Long userId,

        LocalDate startDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        LocalTime startTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        LocalTime endTime,
        Double totalWorkTime,
        Boolean isRepeat,
         List<Integer> days

) {

}
