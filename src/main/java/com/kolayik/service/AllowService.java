package com.kolayik.service;

import com.kolayik.dto.request.AllowRegisterRequestDto;
import com.kolayik.entity.Allow;
import com.kolayik.entity.Shift;
import com.kolayik.repository.AllowRepository;

import com.kolayik.view.VwAllow;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AllowService {
    private final AllowRepository allowRepository;


    public void allowRegister(AllowRegisterRequestDto dto) {

        Allow allow = Allow.builder()
                .id(dto.id())
                .allowtype(dto.allowtype())
                .build();

        allowRepository.save(allow);


    }


    public List<VwAllow> getAllAllowTypes() {
        return allowRepository.findAllAllowTypes();
    }

    public void allowUpdate( AllowRegisterRequestDto dto, Long id) {

        Allow existingAllow = allowRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID " + id + " ile allow türü bulunamadı."));

         existingAllow.setId(dto.id());
         existingAllow.setAllowtype(dto.allowtype());

        allowRepository.save(existingAllow);
    }

    public void deleteAllow(Long id) {
        allowRepository.deleteById(id);
    }
}




