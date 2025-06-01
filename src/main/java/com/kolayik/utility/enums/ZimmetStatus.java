package com.kolayik.utility.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Zimmet atamasının durumunu tutar:
 * - PENDING: Admin tarafından atandı, personel henüz onaylamadı
 * - CONFIRMED: Personel tarafından onaylandı (teslim alındı)
 * - REJECTED: Personel tarafından reddedildi
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum ZimmetStatus {
    PENDING,
    CONFIRMED,
    REJECTED
}