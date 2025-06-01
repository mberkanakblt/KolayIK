package com.kolayik.service;

import com.kolayik.dto.request.ZimmetConfirmRequestDto;
import com.kolayik.dto.request.ZimmetCreateRequestDto;
import com.kolayik.dto.request.ZimmetUpdateRequestDto;
import com.kolayik.dto.response.ZimmetDto;

import java.util.List;

public interface IZimmetService {

    /**
     * Admin tarafından yeni bir zimmet ataması yapılır.
     */
    ZimmetDto createZimmet(ZimmetCreateRequestDto createRequest);

    /**
     * Admin bir zimmet satırını güncellemek isterse.
     */
    ZimmetDto updateZimmet(ZimmetUpdateRequestDto updateRequest);

    /**
     * Admin uygulama içindeki tüm zimmet kayıtlarını listeler.
     */
    List<ZimmetDto> getAllZimmet();

    /**
     * İd ile tek bir zimmet kaydını getirir. Hem Admin, hem Personel ihtiyaç duyabilir.
     */
    ZimmetDto getZimmetById(Long id);

    /**
     * Personel kendi userId'sine ait tüm zimmetleri listeler.
     */
    List<ZimmetDto> getZimmetByUserId(Long userId);

    /**
     * Personel kendi zimmetini onaylama veya reddetme işlemi.
     * Burada, hem ID hem status hem de note geliyor.
     */
    ZimmetDto confirmZimmet(ZimmetConfirmRequestDto confirmRequest);

    /**
     * Admin tarafından bir zimmet kaydını silmek isterse.
     */
    void deleteZimmet(Long id);
}