package com.kolayik.service;

import com.kolayik.dto.request.DoLoginRequestDto;
import com.kolayik.dto.request.DoRegisterRequestDto;
import com.kolayik.entity.User;
import com.kolayik.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public Optional<User> findByUserId(Long userId) {
        return userRepository.findById(userId);
    }


    public void doRegister(DoRegisterRequestDto dto) {
        userRepository.save(User.builder()
                .ad(dto.ad())
                .telefon(dto.telefon())
                .email(dto.email())
                .sifre(dto.password())
                .build());
    }

    public Optional<User> findByEmailPassword(DoLoginRequestDto dto) {
        return userRepository.findOptionalByEmailAndSifre(dto.email(), dto.password());
    }


}

