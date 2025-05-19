package com.kolayik.contoller;



import com.kolayik.dto.request.AllowRegisterRequestDto;
import com.kolayik.dto.response.BaseResponse;
import com.kolayik.exception.ErrorType;
import com.kolayik.exception.KolayIkException;

import com.kolayik.repository.AllowRepository;

import com.kolayik.service.AllowService;
import com.kolayik.view.VwAllow;

import com.kolayik.view.VwComment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kolayik.config.RestApis.*;




@RestController
@RequiredArgsConstructor
@RequestMapping(ALLOW)
@CrossOrigin("*")
public class AllowController {
    private final AllowService allowService;


    @PostMapping(ALLOW_REGISTER)
    public ResponseEntity<BaseResponse<Boolean>> allowRegister(@RequestBody @Valid AllowRegisterRequestDto dto) {
//        if (!dto.allowtype().equalsIgnoreCase(dto.allowtype())) {
//            throw new KolayIkException(ErrorType.NAME_NOT_FOUND);
//        }

        allowService.allowRegister(dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .data(true)
                .message("İzin başarılı şekilde oluşturuldu.")
                .build());


    }


    @PostMapping(ALLOW_UPDATE)
    public ResponseEntity<BaseResponse<Boolean>> allowUpdate(@RequestBody @Valid AllowRegisterRequestDto dto) {

//        if (!dto.allowtype().equalsIgnoreCase(dto.allowtype())) {
//            throw new KolayIkException(ErrorType.NAME_NOT_FOUND);
//        }
        allowService.allowRegister(dto);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .data(true)
                .message("İzin başarılı şekilde  kaydedildi.")
                .build());
    }


    @GetMapping(GET_ALLOW_LIST)
    public ResponseEntity<BaseResponse<List<VwAllow>>> getAllAllow() {
        return ResponseEntity.ok(BaseResponse.<List<VwAllow>>builder()
                .code(200)
                .message("All allows found")
                .data(allowService.getAllAllowTypes())
                .build());


    }




}

