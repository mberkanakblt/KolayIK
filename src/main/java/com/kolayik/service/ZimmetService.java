package com.kolayik.service;

import com.kolayik.dto.request.ZimmetConfirmRequestDto;
import com.kolayik.dto.request.ZimmetCreateRequestDto;
import com.kolayik.dto.request.ZimmetUpdateRequestDto;
import com.kolayik.dto.response.ZimmetDto;
import com.kolayik.entity.User;
import com.kolayik.entity.ZimmetAssignment;
import com.kolayik.exception.KolayIkException;
import com.kolayik.exception.ErrorType;
import com.kolayik.repository.UserRepository;
import com.kolayik.repository.ZimmetAssignmentRepository;
import com.kolayik.utility.enums.ZimmetStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZimmetService implements IZimmetService {

    private final ZimmetAssignmentRepository zimmetRepo;
    private final UserRepository userRepo;

    /**
     * Entity → DTO dönüşümü
     */
    private ZimmetDto mapEntityToDto(ZimmetAssignment entity) {
        return ZimmetDto.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .userFullName(entity.getUser().getName() + " " + entity.getUser().getSurname())
                .itemName(entity.getItemName())
                .assignedDate(entity.getAssignedDate())
                .status(entity.getStatus())
                .note(entity.getNote())
                .build();
    }

    @Override
    @Transactional
    public ZimmetDto createZimmet(ZimmetCreateRequestDto createRequest) {
        // 1) userEmail’e göre personeli bul
        User user = userRepo.findByEmail(createRequest.getUserEmail())
                .orElseThrow(() -> new KolayIkException(ErrorType.USER_NOT_FOUND));

        // 2) Zimmet entity’sini oluştur
        ZimmetAssignment zimmet = ZimmetAssignment.builder()
                .user(user)
                .itemName(createRequest.getItemName())
                .note(createRequest.getNote())
                // assignedDate ve status, @PrePersist ile otomatik set edilecek
                .build();

        // 3) Kaydet, DTO’ya dön
        ZimmetAssignment saved = zimmetRepo.save(zimmet);
        return mapEntityToDto(saved);
    }

    @Override
    @Transactional
    public ZimmetDto updateZimmet(ZimmetUpdateRequestDto updateRequest) {
        // 1) Kayıt var mı kontrol et
        ZimmetAssignment existing = zimmetRepo.findById(updateRequest.getId())
                .orElseThrow(() -> new KolayIkException(ErrorType.ASSIGNMENT_NOT_FOUND));

        // 2) Güncellenmek istenen yeni personel (ID üzerinden)
        User user = userRepo.findById(updateRequest.getUserId())
                .orElseThrow(() -> new KolayIkException(ErrorType.USER_NOT_FOUND));

        // 3) Alanları güncelle
        existing.setUser(user);
        existing.setItemName(updateRequest.getItemName());
        existing.setNote(updateRequest.getNote());
        // status yalnızca confirm/reject endpoint’inden değiştirilecek

        ZimmetAssignment updated = zimmetRepo.save(existing);
        return mapEntityToDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ZimmetDto> getAllZimmet() {
        return zimmetRepo.findAll()
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ZimmetDto getZimmetById(Long id) {
        ZimmetAssignment entity = zimmetRepo.findById(id)
                .orElseThrow(() -> new KolayIkException(ErrorType.ASSIGNMENT_NOT_FOUND));
        return mapEntityToDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ZimmetDto> getZimmetByUserId(Long userId) {
        // 1) Personel var mı?
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new KolayIkException(ErrorType.USER_NOT_FOUND));

        // 2) O kullanıcıya ait tüm zimmetleri getir
        return zimmetRepo.findByUser(user)
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ZimmetDto confirmZimmet(ZimmetConfirmRequestDto confirmRequest) {
        // 1) Kayıt bulundu mu?
        ZimmetAssignment entity = zimmetRepo.findById(confirmRequest.getId())
                .orElseThrow(() -> new KolayIkException(ErrorType.ASSIGNMENT_NOT_FOUND));

        // 2) Sadece PENDING durumdakileri değiştirelim
        if (entity.getStatus() != ZimmetStatus.PENDING) {
            throw new KolayIkException(ErrorType.BADREQUEST);
        }

        // 3) Yeni durumu ata ve not’u güncelle
        entity.setStatus(confirmRequest.getStatus());
        entity.setNote(confirmRequest.getNote());

        ZimmetAssignment updated = zimmetRepo.save(entity);
        return mapEntityToDto(updated);
    }

    @Override
    @Transactional
    public void deleteZimmet(Long id) {
        ZimmetAssignment existing = zimmetRepo.findById(id)
                .orElseThrow(() -> new KolayIkException(ErrorType.ASSIGNMENT_NOT_FOUND));
        zimmetRepo.delete(existing);
    }
}