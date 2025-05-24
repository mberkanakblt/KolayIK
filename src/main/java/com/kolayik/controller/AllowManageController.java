package com.kolayik.controller;
import com.kolayik.config.JwtManager;
import com.kolayik.dto.request.AllowManageRegisterRequestDto;
import com.kolayik.dto.request.AllowStateUpdateRequestDto;
import com.kolayik.dto.response.BaseResponse;
import com.kolayik.entity.User;
import com.kolayik.exception.ErrorType;
import com.kolayik.exception.KolayIkException;
import com.kolayik.repository.AllowManageRepository;
import com.kolayik.service.AllowManageService;
import com.kolayik.service.UserService;
import com.kolayik.view.VwAllowManage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.kolayik.config.RestApis.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(ALLOWMANAGE)
@CrossOrigin("*")
public class AllowManageController {

    private final AllowManageService allowManageService;
    private final UserService userService;
    private final AllowManageRepository allowManageRepository;
    private final JwtManager jwtManager;


//    @PostMapping(ALLOW_MANAGE_REGISTER)
//    public ResponseEntity<BaseResponse<Boolean>> allowRegister(@RequestBody @Valid AllowManageRegisterRequestDto dto) {
////         if(!dto.user_id().equals(dto.user_id()))
////                    throw new KolayIkException(ErrorType.NAME_NOT_FOUND);
//        allowManageService.allowManageRegister(dto);
//        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
//                .code(200)
//                .data(true)
//                .message("İzin başarılı şekilde oluşturuldu.")
//                .build());
//
//
//    }
@PostMapping(ALLOW_MANAGE_REGISTER)
public ResponseEntity<BaseResponse<Boolean>> allowRegister(
        @RequestBody @Valid AllowManageRegisterRequestDto dto,
        HttpServletRequest request) {

    // Token'dan kullanıcı ID'si alınır
    String token = request.getHeader("Authorization").replace("Bearer ", "");
    Long userId = jwtManager.getIdFromToken(token); // → token içinden user ID çekiyoruz

    allowManageService.allowManageRegister(dto, userId);

    return ResponseEntity.ok(BaseResponse.<Boolean>builder()
            .code(200)
            .data(true)
            .message("İzin başarılı şekilde oluşturuldu.")
            .build());
}
//@PostMapping(ALLOW_MANAGE_REGISTER )
//public ResponseEntity<BaseResponse<Boolean>> allowRegister(
//        @RequestBody @Valid AllowManageRegisterRequestDto dto,
//        @PathVariable String token) {
//
//    allowManageService.allowManageRegister(dto, token);
//
//    return ResponseEntity.ok(BaseResponse.<Boolean>builder()
//            .code(200)
//            .data(true)
//            .message("İzin başarıyla kaydedildi")
//            .build());
//}

    @PostMapping(ALLOW_MANAGE_UPDATE)
    public ResponseEntity<BaseResponse<Boolean>> allowUpdate(@RequestBody @Valid AllowManageRegisterRequestDto dto,  HttpServletRequest request) {

        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Long userId = jwtManager.getIdFromToken(token);
        allowManageService.allowManageRegister(dto, userId);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .data(true)
                .message("İzin başarılı şekilde  kaydedildi.")
                .build());
    }


    @GetMapping(GET_REQUEST_ALLOW_MANAGE)
    public ResponseEntity<BaseResponse<List<VwAllowManage>>> getRequestAllow() {

        return ResponseEntity.ok(BaseResponse.<List<VwAllowManage>>builder()
                .code(200)
                .data(allowManageService.getRequestAllowsManage())
                .message("Talep edilen izinler başarı ile getirildi.")
                .build());
    }

    @GetMapping(GET_ALLOW_MANAGE_LIST)
    public ResponseEntity<BaseResponse<List<VwAllowManage>>> getAllAllow() {
        return ResponseEntity.ok(BaseResponse.<List<VwAllowManage>>builder()
                .code(200)
                .message("All allows found")
                .data(allowManageService.getAllAllowsManage())
                .build());
    }

    //  http://localhost:9090/dev/v1/allowmanage/approve'

   @PostMapping(ALLOW_MANAGE_APROVE)
   public ResponseEntity<BaseResponse<Boolean>> updateState(@RequestBody AllowStateUpdateRequestDto dto) {
       boolean success = allowManageService.updateAllowState(dto.id(), dto.allowstate());

       if (success) {
           return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                   .code(200)
                   .data(true)
                   .message("İzin başarılı şekilde kaydedildi.")
                   .build());
       } else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.<Boolean>builder()
                   .code(404)
                   .data(false)
                   .message("Belirtilen ID ile izin kaydı bulunamadı.")
                   .build());
       }
   }

}