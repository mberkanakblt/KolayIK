package com.kolayik.controller;

import com.kolayik.config.JwtManager;
import com.kolayik.dto.request.AddMembershipRequestDto;
import com.kolayik.dto.request.BuyMembershipRequestDto;
import com.kolayik.dto.response.BaseResponse;
import com.kolayik.entity.Membership;
import com.kolayik.service.MembershipService;
import com.kolayik.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.kolayik.config.RestApis.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(MEMBERSHIP)
@CrossOrigin("*")
@SecurityRequirement(name = "bearerAuth")
public class MembershipController {
    private final MembershipService membershipService;
    private final JwtManager jwtManager;
    private final UserService userService;

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
    @PostMapping("/buy-membership")
    public ResponseEntity<BaseResponse<Boolean>> buyMembership(@RequestBody BuyMembershipRequestDto dto){
        Optional<Long> optionalUserId = jwtManager.validateToken(dto.token());
        membershipService.buyMembership(dto.membershipId(), optionalUserId.get());
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                        .code(200)
                        .message("Success")
                        .data(true)
                .build());
    }
    @PutMapping("/edit-membership")
    public ResponseEntity<BaseResponse<Boolean>> editMembership(@RequestBody AddMembershipRequestDto dto,Long membershipId){
        membershipService.editMembership(dto,membershipId);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .message("Success")
                .data(true)
                .build());


    }
    @DeleteMapping("/delete-membership")
    public ResponseEntity<BaseResponse<Boolean>> deleteMembership(Long membershipId){
        membershipService.deleteMembership(membershipId);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .message("Success")
                .data(true)
                .build());
    }

}
