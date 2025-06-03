package com.kolayik.controller;

import com.kolayik.config.JwtManager;
import com.kolayik.dto.request.AddCompanyRequestDto;
import com.kolayik.dto.response.BaseResponse;
import com.kolayik.dto.response.CompanyResponseDto;
import com.kolayik.entity.Company;
import com.kolayik.service.CompanyService;
import com.kolayik.utility.enums.Status;
import com.kolayik.view.VwCompany;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.kolayik.config.RestApis.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(COMPANY)
@CrossOrigin("*")
@SecurityRequirement(name = "bearerAuth")
public class CompanyController {
    private final CompanyService companyService;
    private final JwtManager jwtManager;


    @PostMapping("/add-company")
    public ResponseEntity<BaseResponse<Boolean>> addCompany(@RequestBody AddCompanyRequestDto dto){
        Optional<Long> optionalUserId = jwtManager.validateToken(dto.token());
        companyService.addCompany(dto,optionalUserId.get());
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .message("Success")
                .data(true)
                .build());
    }

//    @GetMapping("/get-all-company")
//    public ResponseEntity<BaseResponse<List<Company>>> getAllCompany(){
//        return ResponseEntity.ok(BaseResponse.<List<Company>>builder()
//                        .code(200)
//                        .message("Success")
//                        .data(companyService.getAllCompany())
//                .build());
//    }
@GetMapping("/get-all-company")
public ResponseEntity<BaseResponse<List<CompanyResponseDto>>> getAllCompany(){
    return ResponseEntity.ok(BaseResponse.<List<CompanyResponseDto>>builder()
            .code(200)
            .message("Success")
            .data(companyService.getAllCompany())
            .build());
}

    @GetMapping("/onay")
    public ResponseEntity<BaseResponse<List<Status>>> onay(String token){
        Optional<Long> optionalUserId = jwtManager.validateToken(token);
        return ResponseEntity.ok(BaseResponse.<List<Status>>builder()
                .code(200)
                .message("Success")
                .data(companyService.getOnay(optionalUserId.get()))
                .build());
    }
    @PutMapping("/approved/{companyId}")
    public ResponseEntity<BaseResponse<Boolean>> approvedCompany(@PathVariable Long companyId){
        companyService.approved(companyId);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                        .code(200)
                        .message("Success")
                        .data(true)
                .build());
    }
    @PutMapping("/reject/{companyId}")
    public ResponseEntity<BaseResponse<Boolean>> rejectCompany(@PathVariable Long companyId){
        companyService.reject(companyId);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .message("Success")
                .data(true)
                .build());
    }
    @PutMapping("/edit/{companyId}")
    public ResponseEntity<BaseResponse<Boolean>> editCompany(@PathVariable Long companyId,@RequestBody AddCompanyRequestDto dto){
        companyService.editCompany(companyId,dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                        .code(200)
                        .message("Success")
                        .data(true)
                .build());
    }
    @GetMapping("/get-view-company")
    public ResponseEntity<BaseResponse<List<VwCompany>>> getVwCompany(){
        return ResponseEntity.ok(BaseResponse.<List<VwCompany>>builder()
                        .code(200)
                        .message("Success")
                        .data(companyService.getVwCompany())
                .build());
    }

}
