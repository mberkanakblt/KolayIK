package com.kolayik.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZimmetConfirmRequestDto {
    private Long id;
    private String feedback;
}