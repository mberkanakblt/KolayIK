package com.kolayik.controller;



import com.kolayik.dto.request.AllowRegisterRequestDto;
import com.kolayik.dto.request.RegisterShiftRequestDto;
import com.kolayik.dto.response.BaseResponse;
import com.kolayik.service.AllowService;
import com.kolayik.view.VwAllow;
import com.kolayik.view.VwShift;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.kolayik.config.RestApis.*;




@RestController
@RequiredArgsConstructor
@RequestMapping(ALLOW)
@CrossOrigin("*")
public class AllowController {
    private final AllowService allowService;


    @PostMapping(ALLOW_REGISTER)
    public ResponseEntity<BaseResponse<Boolean>> allowRegister(@RequestBody @Valid AllowRegisterRequestDto dto) {

        allowService.allowRegister(dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .data(true)
                .message("İzin başarılı şekilde oluşturuldu.")
                .build());


    }


    @PostMapping(ALLOW_UPDATE+"/{id}")
    public ResponseEntity<BaseResponse<Boolean>> allowUpdate(@RequestBody @Valid AllowRegisterRequestDto dto,@PathVariable Long id) {

        allowService.allowUpdate(dto,id);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .data(true)
                .message("İzin  başarılı şekilde  güncellendi.")
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

    @DeleteMapping(DELETE_ALLOW + "/{id}")
    public ResponseEntity<BaseResponse<Boolean>> deleteShiftsByUserId(@PathVariable Long id) {
        allowService.deleteAllow(id);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .data(true)
                .message("Vardiya(lar) başarılı şekilde silindi.")
                .build());
    }





}

