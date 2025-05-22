package com.kolayik.dto.request;

import lombok.Data;

/**
 * Şifre değiştirme ekranından gelen mevcut ve yeni parolayı taşır.
 */
@Data
public class ChangePasswordRequestDto {
    private String currentPassword;
    private String newPassword;
}