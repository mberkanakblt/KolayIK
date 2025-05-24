package com.kolayik.controller;

import com.kolayik.config.JwtManager;
import com.kolayik.dto.request.RegisterShiftRequestDto;
import com.kolayik.dto.response.BaseResponse;

import com.kolayik.service.ShiftService;

import com.kolayik.view.VwAllowManage;
import com.kolayik.view.VwShift;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kolayik.config.RestApis.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(SHIFT)
@CrossOrigin("*")
public class ShiftController {
    private final ShiftService shiftService;
    private final JwtManager jwtManager;

    @PostMapping(ADD_SHIFT)
    public ResponseEntity<BaseResponse<Boolean>> ShiftRegister(@RequestBody @Valid RegisterShiftRequestDto dto){

        shiftService.doRegister(dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .data(true)
                .message("vardiya başarılı şekilde oluşturuldu.")
                .build());
    }

    @PostMapping(UPDATE_SHIFT+ "/{id}")
    public ResponseEntity<BaseResponse<Boolean>> ShiftUpdate(@RequestBody @Valid RegisterShiftRequestDto dto,@PathVariable Long id){

        shiftService.update(dto,id);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .data(true)
                .message("Vardiya başarılı şekilde güncellendi.")
                .build());
    }

    @DeleteMapping(DELETE_SHIFT + "/{id}")
    public ResponseEntity<BaseResponse<Boolean>> deleteShiftsByUserId(@PathVariable Long id) {
        shiftService.deleteShift(id);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .data(true)
                .message("Vardiya(lar) başarılı şekilde silindi.")
                .build());
    }

    @GetMapping(GET_SHIFT_LIST)
    public ResponseEntity<BaseResponse<List<VwShift>>> getAllShift() {
        return ResponseEntity.ok(BaseResponse.<List<VwShift>>builder()
                .code(200)
                .message("Tüm vardiya kayıtları getirildi.")
                .data(shiftService.getAllShift())
                .build());
    }


}
