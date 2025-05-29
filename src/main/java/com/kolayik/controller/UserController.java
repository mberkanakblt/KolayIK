
package com.kolayik.controller;

import com.kolayik.config.JwtManager;
import com.kolayik.dto.request.*;
import com.kolayik.dto.response.BaseResponse;
import com.kolayik.dto.response.ProfileResponseDto;
import com.kolayik.dto.response.UserNameResponse;
import com.kolayik.entity.Company;
import com.kolayik.entity.User;

import com.kolayik.exception.ErrorType;
import com.kolayik.exception.KolayIkException;
import com.kolayik.repository.UserRepository;
import com.kolayik.service.AllowManageService;
import com.kolayik.service.UserRoleService;
import com.kolayik.service.UserService;
import com.kolayik.utility.enums.Status;
import com.kolayik.view.VwManager;
import com.kolayik.view.VwPersonnel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kolayik.config.RestApis.USER;
import static com.kolayik.config.RestApis.*;
@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
@CrossOrigin("*")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final JwtManager jwtManager;
    private final AllowManageService allowManageService;
    private  final UserRepository userRepository;

    @PostMapping(DO_REGISTER)
    public ResponseEntity<BaseResponse<Boolean>> doRegister(@RequestBody @Valid DoRegisterRequestDto dto) {
        // Eğer kullanıcının şifreleri eşleşmiyor ise kayıt yapılmaz direkt hata dönülür.
        if (!dto.password().equals(dto.rePassword()))
            throw new KolayIkException(ErrorType.SIFREHATASI);
        userService.doRegister(dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .data(true)
                .message("Üyelik başarılı şekilde oluşturuldu.")
                .build());
    }

    @PostMapping(LOGIN)
    public ResponseEntity<BaseResponse<String>> doLogin(@RequestBody @Valid DoLoginRequestDto dto) {
        // email ve şifre yi vererek kullanıcını var olup olmadığını sorguluyorum.
        Optional<User> optionalKullanici = userService.findByEmailPassword(dto);
        if (optionalKullanici.isEmpty()) // böyle bir kullanıcı yok
            throw new KolayIkException(ErrorType.EMAIL_SIFRE_HATASI);
        return ResponseEntity.ok(BaseResponse.<String>builder()
                .code(200)
                .data(jwtManager.createToken(optionalKullanici.get().getId()))
                .message("Başaralı şekilde giriş yapıldı.")
                .build());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<BaseResponse<Boolean>> forgotPassword(@RequestParam String email) {
        userService.forgotPassword(email);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .message("E posta gonderildi")
                .data(true)
                .build());


    }

    @PostMapping("/reset-password")
    public ResponseEntity<BaseResponse<Boolean>> resetPassword(@RequestBody ResetPasswordRequest dto) {
        userService.resetPassword(dto.token(), dto.newPassword());
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .data(true)
                .message("Şifre başarıyla güncellendi.")
                .build());
    }


    @PostMapping("/add-role")
    public ResponseEntity<BaseResponse<Boolean>> addRole(@RequestBody AddRoleRequestDto dto) {
        userRoleService.addRole(dto.roleName(), dto.userId());
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .message("Ok")
                .data(true)
                .build());
    }

    @GetMapping("/get-user-name/{token}")
    public ResponseEntity<BaseResponse<String>> getUserName(@PathVariable String token) {
        Optional<Long> optionalUserId = jwtManager.validateToken(token);
        if (optionalUserId.isEmpty()) throw new KolayIkException(ErrorType.INVALID_TOKEN);
        Optional<User> optionalKullanici = userService.findByUserId(optionalUserId.get());
        if (optionalKullanici.isEmpty()) throw new KolayIkException(ErrorType.USER_NOT_FOUND);
        return ResponseEntity.ok(BaseResponse.<String>builder()
                .code(200)
                .data(optionalKullanici.get().getName())
                .message("Kullanıcı adı başarı ile getirildi.")
                .build());
    }

    @GetMapping(USERNAME_SURNAME + "/{token}")
    public ResponseEntity<BaseResponse<UserNameResponse>> getUserNameSurname(@PathVariable String token) {
        Optional<Long> optionalUserId = jwtManager.validateToken(token);
        if (optionalUserId.isEmpty()) {
            throw new KolayIkException(ErrorType.INVALID_TOKEN);
        }

        Optional<User> optionalKullanici = userService.findByUserId(optionalUserId.get());
        if (optionalKullanici.isEmpty()) {
            throw new KolayIkException(ErrorType.USER_NOT_FOUND);
        }

        User kullanici = optionalKullanici.get();
        UserNameResponse response = new UserNameResponse(kullanici.getName(), kullanici.getSurname());

        return ResponseEntity.ok(BaseResponse.<UserNameResponse>builder()
                .code(200)
                .data(response)
                .message("Kullanıcı adı ve soyadı başarı ile getirildi.")
                .build());
    }

    @GetMapping("/verify")
    public ResponseEntity<BaseResponse<String>> verifyEmail(@RequestParam String token) {
        Optional<User> userOptional = userService.findByToken(token);
        if (userOptional.isEmpty())
            throw new KolayIkException(ErrorType.INVALID_TOKEN);

        User user = userOptional.get();
        user.setEmailVerified(true);
        user.setVerificationToken(null);
        userService.save(user);

        return ResponseEntity.ok(BaseResponse.<String>builder()
                .code(200)
                .message("Email verified successfully.")
                .data("Verified")
                .build());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<User> findPersonnelById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/get-vw-manager")
    public ResponseEntity<BaseResponse<List<VwManager>>> getVwManager() {
        return ResponseEntity.ok(BaseResponse.<List<VwManager>>builder()
                .code(200)
                .message("Success")
                .data(userService.getVwManager())
                .build());
    }

    @GetMapping("/get-vw-personnel")
    public ResponseEntity<BaseResponse<List<VwPersonnel>>> getVwPersonnel() {
        return ResponseEntity.ok(BaseResponse.<List<VwPersonnel>>builder()
                .code(200)
                .message("Success")
                .data(userService.getVwPersonnel())
                .build());
    }

    @PostMapping(CREATE_PERSONNEL)
    public ResponseEntity<BaseResponse<Boolean>> createPersonnel(
            @RequestHeader Map<String, String> headers,
            @RequestBody @Valid CreatePersonnelDto createPersonnelDto) {

        System.out.println("Headers: " + headers);
        User user = userService.createPersonnel(createPersonnelDto);
        System.out.println("Created User: " + user);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .message("Personnel created successfully")
                .data(true)
                .build());
    }

    @PutMapping("/approved/{userId}")
    public ResponseEntity<BaseResponse<Boolean>> approvedUser(@PathVariable Long userId) {
        userService.approved(userId);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .message("Personnel approved successfully")
                .data(true)
                .build());
    }

    @PutMapping("/reject/{userId}")
    public ResponseEntity<BaseResponse<Boolean>> rejectManager(@PathVariable Long userId) {
        userService.reject(userId);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .message("Manager rejected successfully")
                .data(true)
                .build());
    }

    @GetMapping("/search")
    public List<PersonnelSearchResponseDto> searchPersonnel(@RequestParam String term) {
        if (term == null || term.trim().isEmpty()) {
            return List.of();
        }

        List<User> users = userRepository.findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrCompanyNameContainingIgnoreCase(
                term, term, term, term, term);

        return users.stream()
                .map(user -> new PersonnelSearchResponseDto(
                        user.getId(),
                        user.getName(),
                        user.getSurname(),
                        user.getAddress(),
                        user.getPhone(),
                        user.getEmail(),
                        user.getAvatar(),
                        user.getStatus().name(),
                        user.getCompanyName(),
                        mapToCompanyDto(user.getCompany())
                ))
                .collect(Collectors.toList());
    }


    // Örnek company mapleme metodu
    private CompanyDto mapToCompanyDto(Company company) {
        if (company == null) return null;

        // user alanı için örnek doldurma yapabilirsin ya da null bırakabilirsin
        return new CompanyDto(
                company.getId(),
                company.getName(),
                company.getAddress(),
                company.getPhone(),
                company.getStatus() != null ? company.getStatus().name() : null,
                company.getSector(),
                null // userDto eklenecekse buraya maplenmeli, yoksa null
        );
    }



    @GetMapping("/get-profile")
        public ResponseEntity<BaseResponse<ProfileResponseDto>> getProfile(
                @RequestParam("token") String token
        ) {
            Long userId = jwtManager.validateToken(token)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.UNAUTHORIZED, "Geçersiz token"
                    ));

            ProfileResponseDto dto = userService.getProfile(userId);
            return ResponseEntity.ok(
                    BaseResponse.<ProfileResponseDto>builder()
                            .code(200)
                            .data(dto)
                            .message("Profil bilgisi getirildi.")
                            .build()
            );
        }

        @PutMapping("/edit-profile")
        public ResponseEntity<BaseResponse<Boolean>> updateProfile(
                @RequestBody ProfileUpdateRequestDto dto
        ) {
            Long userId = jwtManager.validateToken(dto.token())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.UNAUTHORIZED, "Geçersiz token"
                    ));

            userService.updateProfile(dto, userId);
            return ResponseEntity.ok(
                    BaseResponse.<Boolean>builder()
                            .code(200)
                            .data(true)
                            .message("Profil başarıyla güncellendi.")
                            .build()
            );
        }


    @PutMapping("/update-personnel/{id}")
    public ResponseEntity<BaseResponse<Boolean>> updatePersonnel(
            @PathVariable Long id,
            @RequestBody @Valid PersonnelUpdateRequestDto dto,
            HttpServletRequest request) {

        // Authorization kontrolü

        userService.updatePersonnel(id, dto);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .data(true)
                .message("Personnel başarıyla güncellendi.")
                .build());
    }




    @GetMapping(GET_ALL_PERSONNEL)
        public ResponseEntity<List<PersonnelSearchResponseDto>> getAllPersonnel() {
            List<PersonnelSearchResponseDto> personnelList = userService.getAllPersonnel();
            return ResponseEntity.ok(personnelList);
        }


    @PutMapping("/update-status/{userId}")
    public ResponseEntity<Void> updateUserStatus(
            @PathVariable Long userId,
            @RequestBody Map<String, String> request) {
        String status = request.get("status");
        userService.updateStatus(userId, Status.valueOf(status));
        return ResponseEntity.ok().build();
    }



}







