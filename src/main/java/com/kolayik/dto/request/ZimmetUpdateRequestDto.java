package com.kolayik.dto.request;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZimmetUpdateRequestDto {
    private String code;
    private String name;
    private String model;
    private LocalDateTime assignedAt;
}