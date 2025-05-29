package com.kolayik.service;

import com.kolayik.dto.request.*;
import com.kolayik.dto.response.ZimmetDto;
import com.kolayik.entity.ZimmetAssignment;
import com.kolayik.entity.User;
import com.kolayik.utility.enums.Status;
import com.kolayik.exception.KolayIkException;
import com.kolayik.exception.ErrorType;
import com.kolayik.repository.ZimmetRepository;
import com.kolayik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZimmetService {
    private final ZimmetRepository repo;
    private final UserRepository userRepo;

    public List<ZimmetDto> getAllAssignmentsForCurrentUser() {
        return repo.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ZimmetDto createZimmet(ZimmetCreateRequestDto req) {
        User user = userRepo.findByEmail(req.getPersonnelEmail())
                .orElseThrow(() -> new KolayIkException(ErrorType.USER_NOT_FOUND));
        ZimmetAssignment e = ZimmetAssignment.builder()
                .user(user)
                .code(req.getCode())
                .name(req.getName())
                .model(req.getModel())
                .assignedAt(req.getAssignedAt())
                .status(Status.PASIF)
                .build();
        ZimmetAssignment saved = repo.save(e);
        return mapToDto(saved);
    }

    @Transactional
    public ZimmetDto updateZimmet(Long id, ZimmetUpdateRequestDto req) {
        ZimmetAssignment e = repo.findById(id)
                .orElseThrow(() -> new KolayIkException(ErrorType.ASSIGNMENT_NOT_FOUND));
        e.setCode(req.getCode());
        e.setName(req.getName());
        e.setModel(req.getModel());
        e.setAssignedAt(req.getAssignedAt());
        return mapToDto(repo.save(e));
    }

    @Transactional
    public void deleteZimmet(Long id) {
        ZimmetAssignment e = repo.findById(id)
                .orElseThrow(() -> new KolayIkException(ErrorType.ASSIGNMENT_NOT_FOUND));
        repo.delete(e);
    }

    @Transactional
    public ZimmetDto confirmAssignment(ZimmetConfirmRequestDto req) {
        ZimmetAssignment e = repo.findById(req.getId())
                .orElseThrow(() -> new KolayIkException(ErrorType.ASSIGNMENT_NOT_FOUND));
        e.setStatus(Status.AKTIF);
        e.setFeedback(null);
        return mapToDto(repo.save(e));
    }

    @Transactional
    public ZimmetDto rejectAssignment(ZimmetConfirmRequestDto req) {
        ZimmetAssignment e = repo.findById(req.getId())
                .orElseThrow(() -> new KolayIkException(ErrorType.ASSIGNMENT_NOT_FOUND));
        e.setStatus(Status.ASKIDA);
        e.setFeedback(req.getFeedback());
        return mapToDto(repo.save(e));
    }

    private ZimmetDto mapToDto(ZimmetAssignment e) {
        return ZimmetDto.builder()
                .id(e.getId())
                .personnelEmail(e.getUser().getEmail())
                .code(e.getCode())
                .name(e.getName())
                .model(e.getModel())
                .assignedAt(e.getAssignedAt())
                .status(e.getStatus())
                .feedback(e.getFeedback())
                .build();
    }
}