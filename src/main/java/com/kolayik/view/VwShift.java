package com.kolayik.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Getter
@AllArgsConstructor
public class VwShift {
    private  Long id;
    private Long userId;
    private String name;
    private String surname;
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private  LocalTime endTime;
    private  Double totalWorkTime;
    private  Boolean isRepeat;
    private List<Integer> days;

}
