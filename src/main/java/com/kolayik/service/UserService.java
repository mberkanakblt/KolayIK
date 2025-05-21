package com.kolayik.service;

import com.kolayik.dto.request.CreatePersonnelDto;
import com.kolayik.dto.request.DoLoginRequestDto;
import com.kolayik.dto.request.DoRegisterRequestDto;
import com.kolayik.dto.request.UpdatePersonnelDto;
import com.kolayik.entity.PasswordResetToken;
import com.kolayik.entity.User;
import com.kolayik.entity.UserRole;
import com.kolayik.exception.ErrorType;
import com.kolayik.exception.KolayIkException;
import com.kolayik.repository.PasswordResetTokenRepository;
import com.kolayik.repository.UserRepository;
import com.kolayik.repository.UserRoleRepository;
import com.kolayik.repository.UserRoleRepository;
import com.kolayik.utility.enums.Role;
import com.kolayik.utility.enums.Status;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import com.kolayik.view.VwManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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


    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public Optional<User> findByEmail(String username) {
        return userRepository.findByEmail(username);
    }
    private final UserRoleRepository userRoleRepository;


    public Optional<User> findByUserId(Long userId) {
        return userRepository.findById(userId);
    }

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
                .status(Status.PASIF)
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

    public Optional<User> findByEmailPassword(DoLoginRequestDto dto) {
        Optional<User> optionalUser = userRepository.findByEmail(dto.email());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (dto.password().equals(user.getPassword())) { // encoder.matches yerine equals
                return Optional.of(user);
            }
        }
        return Optional.empty();
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


    public Optional<User> findByToken(String token) {

        return userRepository.findByVerificationToken(token);
    }


    public void save(User user) {
        userRepository.save(user);

    }


    public User createPersonnel(@Valid CreatePersonnelDto createPersonnelDto) {
        if (userRepository.existsByEmail(createPersonnelDto.email())) {
            throw new KolayIkException(ErrorType.EMAIL_ALREADY_EXISTS);
        }

        User user = User.builder()
                .name(createPersonnelDto.name())
                .surname(createPersonnelDto.surname())
                .address(createPersonnelDto.address())
                .phone(createPersonnelDto.phone())
                .email(createPersonnelDto.email())
                .password(createPersonnelDto.password()) // .encode kaldırıldı
                .avatar(createPersonnelDto.avatar())
                .status(createPersonnelDto.status())
                .companyName(createPersonnelDto.companyName())
                .build();
        User savedUser = userRepository.save(user);

        UserRole userRole = new UserRole();
        userRole.setRoleName(createPersonnelDto.role());
        userRole.setPersonnel(savedUser);
        userRoleRepository.save(userRole);

        return savedUser;
    }


    public List<User> getAllPersonnel() {
        return userRepository.findAll();

    }


    public void deletePersonnelByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found for id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    public void updatePersonnel(Long userId, @Valid UpdatePersonnelDto updatePersonnelDto) {
        User user = userRepository.findPersonnelById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        if (updatePersonnelDto.email() != null && !updatePersonnelDto.email().equals(user.getEmail())) {
            boolean emailExists = userRepository.existsByEmail(updatePersonnelDto.email());
            if (emailExists) {
                throw new KolayIkException(ErrorType.EMAIL_ALREADY_EXISTS);

            }
            user.setEmail(updatePersonnelDto.email());
        }



        if (updatePersonnelDto.name() != null) user.setName(updatePersonnelDto.name());
        if (updatePersonnelDto.surname() != null) user.setSurname(updatePersonnelDto.surname());
        if (updatePersonnelDto.phone() != null) user.setPhone(updatePersonnelDto.phone());
        if (updatePersonnelDto.status() != null) user.setStatus(updatePersonnelDto.status());
        if (updatePersonnelDto.avatar() != null) user.setAvatar(updatePersonnelDto.avatar());
        if (updatePersonnelDto.companyName() != null) user.setCompanyName(updatePersonnelDto.companyName());

        userRepository.save(user);

        // Rol güncelleme
        if (updatePersonnelDto.role() != null) {
            List<UserRole> userRoles = userRoleRepository.findByPersonnelId(userId);

            if (!userRoles.isEmpty()) {
                UserRole existingRole = userRoles.get(0);
                existingRole.setRoleName(updatePersonnelDto.role());
                userRoleRepository.save(existingRole);
            } else {
                UserRole newRole = UserRole.builder()
                        .personnel(user)
                        .roleName(updatePersonnelDto.role())
                        .build();
                userRoleRepository.save(newRole);
            }
        }
    }


    public void changePersonnelStatus(Long userId, Status status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        user.setStatus(status);
        userRepository.save(user);
    }





}







