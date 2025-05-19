package com.kolayik.contoller;

import com.kolayik.dto.request.AddMembershipRequestDto;
import com.kolayik.dto.response.BaseResponse;
import com.kolayik.entity.Membership;
import com.kolayik.service.MembershipService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kolayik.config.RestApis.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(MEMBERSHIP)
@CrossOrigin("*")
@SecurityRequirement(name = "bearerAuth")
public class MembershipController {
    private final MembershipService membershipService;

    @PostMapping("/add-membership")
    public ResponseEntity<BaseResponse<Boolean>> addMembership(@RequestBody AddMembershipRequestDto dto){
        membershipService.addMembership(dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                        .code(200)
                        .message("Success")
                        .data(true)
                .build());
    }
    @GetMapping("/get-all-membership")
    public ResponseEntity<BaseResponse<List<Membership>>> getAllMembership(){
        return ResponseEntity.ok(BaseResponse.<List<Membership>>builder()
                        .code(200)
                        .message("Success")
                        .data(membershipService.getAllMembership())
                .build());
    }

}
