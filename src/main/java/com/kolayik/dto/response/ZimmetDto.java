package com.kolayik.dto.response;

import com.kolayik.utility.enums.ZimmetStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Zimmet listesini veya tek bir zimmet satırını geri dönerken kullanılır.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZimmetDto {

    private Long id;
    private Long userId;
    private String userFullName;  // Örn. "Ahmet Yılmaz"
    private String itemName;
    private LocalDateTime assignedDate;
    private ZimmetStatus status;
    private String note;
}