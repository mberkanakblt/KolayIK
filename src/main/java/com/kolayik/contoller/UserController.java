package com.kolayik.contoller;

import com.kolayik.config.JwtManager;
import com.kolayik.dto.request.AddRoleRequestDto;
import com.kolayik.dto.request.DoLoginRequestDto;
import com.kolayik.dto.request.DoRegisterRequestDto;
import com.kolayik.dto.request.ResetPasswordRequest;
import com.kolayik.dto.response.BaseResponse;
import com.kolayik.entity.User;

import com.kolayik.exception.ErrorType;
import com.kolayik.exception.KolayIkException;
import com.kolayik.service.UserRoleService;
import com.kolayik.service.UserService;
import com.kolayik.view.VwManager;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

}
