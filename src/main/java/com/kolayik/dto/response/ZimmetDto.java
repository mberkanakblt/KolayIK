package com.kolayik.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import com.kolayik.utility.enums.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZimmetDto {
    private Long id;
    private String personnelEmail;
    private String code;
    private String name;
    private String model;
    private LocalDateTime assignedAt;
    private Status status;
    private String feedback;
}
