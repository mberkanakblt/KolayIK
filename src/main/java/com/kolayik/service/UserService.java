package com.kolayik.service;

import com.kolayik.entity.User;
import com.kolayik.repository.UserRepository;
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
}
