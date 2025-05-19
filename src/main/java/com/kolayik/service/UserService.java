package com.kolayik.service;

import com.kolayik.dto.request.DoLoginRequestDto;
import com.kolayik.dto.request.DoRegisterRequestDto;
import com.kolayik.dto.request.ProfileUpdateRequestDto;
import com.kolayik.dto.response.ProfileResponseDto;
import com.kolayik.entity.PasswordResetToken;
import com.kolayik.entity.User;
import com.kolayik.exception.ErrorType;
import com.kolayik.exception.KolayIkException;
import com.kolayik.repository.PasswordResetTokenRepository;
import com.kolayik.repository.UserRepository;
import com.kolayik.utility.enums.Role;
import com.kolayik.utility.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public void doRegister(DoRegisterRequestDto dto) {
        User user = User.builder()
                .name(dto.name())
                .password(dto.password())
                .email(dto.email())
                .role(Role.PERSONNEL)
                .status(Status.AKTIF)
                .build();
        userRepository.save(user);
    }

    public Optional<User> findByUserId(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findByEmailPassword(DoLoginRequestDto dto) {
        return userRepository.findOptionalByEmailAndPassword(dto.email(), dto.password());
    }

    public Optional<User> findByToken(String token) {
        return userRepository.findByVerificationToken(token);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void forgotPassword(String email) {
    }

    private Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void resetPassword(String token, String newPassword) {
    }

    // AŞAĞIDA PROFİL İŞLEMLERİ EKLENDİ

    /**
     * Kullanıcının profil bilgilerini döner.
     */
    public ProfileResponseDto getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new KolayIkException(ErrorType.USER_NOT_FOUND));
        return new ProfileResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getCompanyName(),
                user.getAddress(),
                user.getAvatar()
        );
    }

    /**
     * Kullanıcının profil bilgilerini günceller.
     */
    public void updateProfile(Long userId, ProfileUpdateRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new KolayIkException(ErrorType.USER_NOT_FOUND));

        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setCompanyName(dto.getCompanyName());
        user.setAddress(dto.getAddress());
        user.setAvatar(dto.getAvatar());


        userRepository.save(user);
    }

    /**
     * Kullanıcı hesabını pasifleştirir.
     */
    public void deactivate(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new KolayIkException(ErrorType.USER_NOT_FOUND));
        user.setStatus(Status.PASIF);
        userRepository.save(user);
    }

    /**
     * Kullanıcı hesabını kalıcı olarak siler.
     */
    public void deleteAccount(Long userId) {
        userRepository.deleteById(userId);
    }
}