package com.kolayik.dto.request;

import com.kolayik.utility.enums.ZimmetStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Personel kendi zimmetini onaylama (CONFRIM) veya reddetme (REJECT) işlemi için kullanır.
 * {
 *   "id": 12,
 *   "status": "CONFIRMED",
 *   "note": "Kabul ediyorum"
 * }
 * veya
 * {
 *   "id": 12,
 *   "status": "REJECTED",
 *   "note": "Hasarlı, değiştirilmesi gerekiyor"
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZimmetConfirmRequestDto {

    @NotNull(message = "Onaylanacak zimmet ID zorunludur.")
    private Long id;

    @NotNull(message = "Durum (status) zorunludur.")
    private ZimmetStatus status;

    /**
     * Personel onaylarken veya reddederken açıklama ekleyebilir.
     */
    private String note;
}