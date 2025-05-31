package com.kolayik.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kolayik.utility.enums.Day;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tblshift")

public class Shift {

    /**
     * Bu sayfa Vardiya sayfasıdır
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  Long userId;
    private   LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private  LocalTime endTime;
    private  Double totalWorkTime;
    private  Boolean isRepeat;
    private List<Integer> days;


}