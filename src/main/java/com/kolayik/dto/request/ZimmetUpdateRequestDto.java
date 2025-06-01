package com.kolayik.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Admin var olan bir zimmet satırını güncellemek istediğinde kullanır.
 * {
 *   "id": 12,
 *   "userId": 7,
 *   "itemName": "Samsung Kask Model Y",
 *   "note": "Yenileme istendi"
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZimmetUpdateRequestDto {

    @NotNull(message = "Güncellenecek zimmet ID zorunludur.")
    private Long id;

    @NotNull(message = "Personel ID zorunludur.")
    private Long userId;

    @NotBlank(message = "Eşya adı boş olamaz.")
    private String itemName;

    /**
     * Opsiyonel: Admin güncellerken not güncellemek isterse.
     */
    private String note;
}