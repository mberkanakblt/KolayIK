package com.kolayik.service;

import com.kolayik.dto.request.AllowRegisterRequestDto;
import com.kolayik.entity.Allow;
import com.kolayik.repository.AllowRepository;

import com.kolayik.view.VwAllow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AllowService {
    private final AllowRepository allowRepository;


    public void allowRegister(AllowRegisterRequestDto dto) {


        Allow allow = Allow.builder()

                .allowtype(dto.allowtype())
                .build();

        allowRepository.save(allow);


    }

//    public List<VwAllow> getAllAllows() {
//        return allowRepository.getAllAllow();
//    }

    public List<VwAllow> getAllAllowTypes() {
        return allowRepository.findAllAllowTypes();
    }

}




