package com.kolayik.service;

import com.kolayik.dto.request.DoLoginRequestDto;
import com.kolayik.dto.request.DoRegisterRequestDto;
import com.kolayik.entity.PasswordResetToken;
import com.kolayik.entity.User;
import com.kolayik.exception.ErrorType;
import com.kolayik.exception.KolayIkException;
import com.kolayik.repository.PasswordResetTokenRepository;
import com.kolayik.repository.UserRepository;
import com.kolayik.utility.enums.Role;
import com.kolayik.utility.enums.Status;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
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
        System.out.println("Saved user ID: " + user.getId());
        emailService.sendVerificationEmail(user.getEmail(), token);

    }
    public Optional<User> findByUserId(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findByNameAndSurname(String name, String surname) {
        return userRepository.findByNameAndSurname(name, surname);
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

    private Optional<User> findByEmail(String email) {
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
}

