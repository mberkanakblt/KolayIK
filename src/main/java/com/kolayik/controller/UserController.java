package com.kolayik.controller;

import com.kolayik.config.JwtManager;
import com.kolayik.dto.request.*;
import com.kolayik.dto.response.BaseResponse;
import com.kolayik.entity.User;

import com.kolayik.exception.ErrorType;
import com.kolayik.exception.KolayIkException;
import com.kolayik.repository.UserRepository;
import com.kolayik.service.UserRoleService;
import com.kolayik.service.UserService;
import com.kolayik.view.VwManager;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.kolayik.utility.enums.Status;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kolayik.dto.request.UpdatePersonnelDto;


import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private final UserRepository userRepository;

    @PostMapping(DO_REGISTER)
    public ResponseEntity<BaseResponse<Boolean>> doRegister(@RequestBody @Valid DoRegisterRequestDto dto){
        // Eğer kullanıcının şifreleri eşleşmiyor ise kayıt yapılmaz direkt hata dönülür.
        if(!dto.password().equals(dto.rePassword()))
            throw new KolayIkException(ErrorType.SIFREHATASI);
        userService.doRegister(dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                        .code(200)
                        .data(true)
                        .message("Üyelik başarılı şekilde oluşturuldu.")
                .build());
    }
    @PostMapping(LOGIN)
    public ResponseEntity<BaseResponse<String>> doLogin(@RequestBody @Valid DoLoginRequestDto dto){
        // email ve şifre yi vererek kullanıcını var olup olmadığını sorguluyorum.
        Optional<User> optionalKullanici = userService.findByEmailPassword(dto);
        if(optionalKullanici.isEmpty()) // böyle bir kullanıcı yok
            throw  new KolayIkException(ErrorType.EMAIL_SIFRE_HATASI);
        return ResponseEntity.ok(BaseResponse.<String>builder()
                        .code(200)
                        .data(jwtManager.createToken(optionalKullanici.get().getId()))
                        .message("Başaralı şekilde giriş yapıldı.")
                .build());
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<BaseResponse<Boolean>> forgotPassword(@RequestParam String email){
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
    public ResponseEntity<BaseResponse<Boolean>> addRole(@RequestBody AddRoleRequestDto dto){
        userRoleService.addRole(dto.roleName(), dto.userId());
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .message("Ok")
                .data(true)
                .build());
    }

    @GetMapping("/get-user-name/{token}")
    public ResponseEntity<BaseResponse<String>> getUserName(@PathVariable String token){
      Optional<Long> optionalUserId = jwtManager.validateToken(token);
      if(optionalUserId.isEmpty()) throw new KolayIkException(ErrorType.INVALID_TOKEN);
      Optional<User> optionalKullanici =  userService.findByUserId(optionalUserId.get());
      if(optionalKullanici.isEmpty()) throw new KolayIkException(ErrorType.USER_NOT_FOUND);
      return  ResponseEntity.ok(BaseResponse.<String>builder()
                      .code(200)
                      .data(optionalKullanici.get().getName())
                      .message("Kullanıcı adı başarı ile getirildi.")
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
    @GetMapping("/{id}")
    public ResponseEntity<User> findPersonnelById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
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

    @GetMapping(GET_ALL_PERSONNEL)
    public ResponseEntity<BaseResponse<List<User>>> getAllPersonnel() {
        List<User> personnelList = userService.getAllPersonnel();
        return ResponseEntity.ok(BaseResponse.<List<User>>builder()
                .code(200)
                .message("Personnel list fetched successfully")
                .data(personnelList)
                .build());
    }


    @DeleteMapping(DELETE_PERSONNEL + "/{userId}")
    public ResponseEntity<BaseResponse<Boolean>> deletePersonnelByUserId(@PathVariable Long userId) {
        try {
            userService.deletePersonnelByUserId(userId);
            return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                    .code(200)
                    .data(true)
                    .message("Personnel associated with user successfully deleted")
                    .build());
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.<Boolean>builder()
                    .code(404)
                    .data(false)
                    .message(ex.getMessage())
                    .build());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.<Boolean>builder()
                    .code(500)
                    .data(false)
                    .message("An unexpected error occurred")
                    .build());
        }

    }

    @PatchMapping(UPDATE_PERSONNEL_STATUS + "/{userId}")
    public ResponseEntity<BaseResponse<Boolean>> changePersonnelStatus(
            @PathVariable Long userId,
            @RequestParam Status status) {
        try {
            userService.changePersonnelStatus(userId, status);
            return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                    .code(200)
                    .message("Personnel status updated successfully")
                    .data(true)
                    .build());
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.<Boolean>builder()
                    .code(404)
                    .data(false)
                    .message(ex.getMessage())
                    .build());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.<Boolean>builder()
                    .code(500)
                    .data(false)
                    .message("An unexpected error occurred")
                    .build());
        }
    }
    @GetMapping("/get-vw-manager")
    public ResponseEntity<BaseResponse<List<VwManager>>> getVwManager(){
        return ResponseEntity.ok(BaseResponse.<List<VwManager>>builder()
                        .code(200)
                        .message("Success")
                        .data(userService.getVwManager())

                .build());
    }
    @PutMapping("/approved/{userId}")
    public ResponseEntity<BaseResponse<Boolean>> approvedUser(@PathVariable Long userId){
        userService.approved(userId);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .message("Success")
                .data(true)
                .build());
    }
    @PutMapping("/reject/{userId}")
    public ResponseEntity<BaseResponse<Boolean>> rejectManager(@PathVariable Long userId){
        userService.reject(userId);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .message("Success")
                .data(true)
                .build());
    }
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchPersonnel(@RequestParam String term) {
        List<User> results = userService.searchPersonnel(term);
        return ResponseEntity.ok(results);
    }

    @PatchMapping("/update-personnel-status/{id}")
    public ResponseEntity<?> updatePersonnel(
            @PathVariable Long id,
            @RequestBody UpdatePersonnelDto updatePersonnelDto) {
        try {
            userService.updatePersonnel(id, updatePersonnelDto);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage()));
        }
    }








}
