package com.kolayik.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Admin yeni bir zimmet ataması yaparken göndereceği payload:
 * {
 *   "userEmail": "personel@ornek.com",
 *   "itemName": "Dell XPS 13 Laptop",
 *   "note": "Acil ihtiyaç"
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZimmetCreateRequestDto {

    @NotNull(message = "Personel e-posta zorunludur.")
    @NotBlank(message = "Personel e-posta boş olamaz.")
    @Email(message = "Geçerli bir e-posta girin.")
    private String userEmail;

    @NotBlank(message = "Eşya adı boş olamaz.")
    private String itemName;

    /**
     * Opsiyonel. Admin eklerken bir not bırakmak isterse.
     */
    private String note;
}