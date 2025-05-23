package com.kolayik.service;

import com.kolayik.dto.request.*;
import com.kolayik.dto.response.ProfileResponseDto;
import com.kolayik.entity.PasswordResetToken;
import com.kolayik.entity.User;
import com.kolayik.entity.UserRole;
import com.kolayik.exception.ErrorType;
import com.kolayik.exception.KolayIkException;
import com.kolayik.repository.PasswordResetTokenRepository;
import com.kolayik.repository.UserRepository;
import com.kolayik.repository.UserRoleRepository;
import com.kolayik.utility.enums.Role;
import com.kolayik.utility.enums.Status;
import com.kolayik.view.VwManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRoleRepository userRoleRepository;



    public void doRegister(DoRegisterRequestDto dto) {
        String token = UUID.randomUUID().toString();

        User user = User.builder()
                .name(dto.name())
                .surname(dto.surname())
                .password(dto.password())
                .email(dto.email())
                .phone(dto.phone())
                .companyName(dto.companyName())
                .avatar(dto.avatar())
                .address(dto.address())
                .status(Status.ASKIDA)
                .emailVerified(false)
                .verificationToken(token)
                .build();
        System.out.println("=== TOKEN ===");
        System.out.println(token);
        System.out.println("=== USER ===");
        System.out.println(user);

        userRepository.save(user);
        UserRole userRole = UserRole.builder()
                .userId(user.getId())
                .roleName(Role.COMPANY_ADMIN)
                .build();
        userRoleRepository.save(userRole);

        emailService.sendVerificationEmail(user.getEmail(), token);

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
        Optional<User> optionalUser = findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new KolayIkException(ErrorType.EMAIL_NOT_FOUND);
        }

        User user = optionalUser.get();
        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expirationDate(LocalDateTime.now().plusMinutes(15))
                .build();

        passwordResetTokenRepository.save(resetToken);
        emailService.sendResetPasswordEmail(user.getEmail(), token);

    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public void resetPassword(String token, String newPassword) {
            Optional<PasswordResetToken> resetTokenOpt = passwordResetTokenRepository.findByToken(token);

            if (resetTokenOpt.isEmpty() || resetTokenOpt.get().getExpirationDate().isBefore(LocalDateTime.now())) {
                throw new KolayIkException(ErrorType.INVALID_TOKEN);
            }

            PasswordResetToken resetToken = resetTokenOpt.get();
            User user = resetToken.getUser();

            user.setPassword(newPassword);
            userRepository.save(user);

            passwordResetTokenRepository.delete(resetToken); // Token kullanıldıktan sonra silinir.

    }

    public List<VwManager> getVwManager() {

        return userRepository.getAllManager();
    }

    public void reject(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new KolayIkException(ErrorType.USER_NOT_FOUND));

        user.setStatus(Status.PASIF);
        userRepository.save(user);
    }

    public void approved(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new KolayIkException(ErrorType.USER_NOT_FOUND));
        user.setStatus(Status.AKTIF);
        userRepository.save(user);
    }


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
     * Kullanıcının şifresini günceller: önce mevcut şifre kontrol edilir, sonra yeni şifre kaydedilir.
     */
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new KolayIkException(ErrorType.USER_NOT_FOUND));
        // Mevcut şifre kontrolü
        if (!user.getPassword().equals(currentPassword)) {
            throw new KolayIkException(ErrorType.SIFREHATASI);
        }
        // Yeni şifreyi ata ve kaydet
        user.setPassword(newPassword);
        userRepository.save(user);
    }

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
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }


    public List<User> getAllPersonnel() {
        return userRepository.findAll();
    }
    public User createPersonnel(@Valid CreatePersonnelDto createPersonnelDto) {
        if (userRepository.existsByEmail(createPersonnelDto.email())) {
            throw new KolayIkException(ErrorType.EMAIL_SIFRE_HATASI);
        }

        User user = User.builder()
                .name(createPersonnelDto.name())
                .surname(createPersonnelDto.surname())
                .address(createPersonnelDto.address())
                .phone(createPersonnelDto.phone())
                .email(createPersonnelDto.email())
                .password(createPersonnelDto.password())
                .avatar(createPersonnelDto.avatar())
                .status(createPersonnelDto.status())
                .companyName(createPersonnelDto.companyName())
                .build();

        User savedUser = userRepository.save(user);

        UserRole userRole = UserRole.builder()
                .roleName(Role.PERSONNEL)
                .userId(savedUser.getId())
                .build();
        userRoleRepository.save(userRole);

        return savedUser;
    }
    public void deletePersonnelByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found for id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    public void changePersonnelStatus(Long userId, Status status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        user.setStatus(status);
        userRepository.save(user);
    }


    public void updatePersonnel(Long id, UpdatePersonnelDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.name() != null && !dto.name().isEmpty()) {
            user.setName(dto.name());
        }

        if (dto.surname() != null && !dto.surname().isEmpty()) {
            user.setSurname(dto.surname());
        }

        if (dto.address() != null && !dto.address().isEmpty()) {
            user.setAddress(dto.address());
        }

        if (dto.phone() != null && !dto.phone().isEmpty()) {
            user.setPhone(dto.phone());
        }

        if (dto.email() != null && !dto.email().isEmpty()) {
            user.setEmail(dto.email());
        }

        if (dto.password() != null && !dto.password().isEmpty()) {
            user.setPassword(dto.password());
        }

        if (dto.avatar() != null && !dto.avatar().isEmpty()) {
            user.setAvatar(dto.avatar());
        }

        if (dto.status() != null) {
            user.setStatus(dto.status());
        }

        if (dto.companyName() != null && !dto.companyName().isEmpty()) {
            user.setCompanyName(dto.companyName());
        }

        userRepository.save(user);
    }


    public List<User> searchPersonnel(String term) {
        String normalizedTerm = term.trim().toLowerCase();
        return userRepository.searchByTerm(normalizedTerm);
    }


}