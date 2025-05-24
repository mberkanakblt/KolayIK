package com.kolayik.controller;

import com.kolayik.config.JwtManager;
import com.kolayik.dto.request.AddExpenseRequestDto;
import com.kolayik.dto.response.BaseResponse;
import com.kolayik.entity.Expense;
import com.kolayik.entity.User;
import com.kolayik.service.ExpenseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.kolayik.config.RestApis.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(EXPENSE)
@CrossOrigin("*")
@SecurityRequirement(name = "bearerAuth")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final JwtManager jwtManager;


    @PostMapping("/add-expense")
    public ResponseEntity<BaseResponse<Boolean>> addExpense(@RequestBody AddExpenseRequestDto dto){
        Optional<Long> optionalUserId = jwtManager.validateToken(dto.token());
        expenseService.addExpense(dto,optionalUserId.get());
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                        .code(200)
                        .message("Success")
                        .data(true)
                .build());


    }
    @GetMapping("/get-expense")
    public ResponseEntity<BaseResponse<List<Expense>>> getExpense(){
        return ResponseEntity.ok(BaseResponse.<List<Expense>>builder()
                        .code(200)
                        .message("Success")
                        .data(expenseService.getExpense())
                .build());
    }
    @PutMapping("/approved/{userId}")
    public ResponseEntity<BaseResponse<Boolean>> approvedExpense(@PathVariable Long userId) {
        expenseService.approved(userId);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .message("Approved successfully")
                .data(true)
                .build());
    }

    @PutMapping("/reject/{userId}")
    public ResponseEntity<BaseResponse<Boolean>> rejectExpense(@PathVariable Long userId) {
        expenseService.reject(userId);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .message("Rejected successfully")
                .data(true)
                .build());
    }
    @GetMapping("/get-my-expense")
    public ResponseEntity<BaseResponse<List<Expense>>> getMyExpense(String token){
        Optional<Long> optionalUserId = jwtManager.validateToken(token);
        expenseService.getMyExpense(optionalUserId.get());
        return ResponseEntity.ok(BaseResponse.<List<Expense>>builder()
                .code(200)
                .message("Success")
                .data(expenseService.getExpense())
                .build());


    }

}
