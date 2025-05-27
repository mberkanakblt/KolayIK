package com.kolayik.service;

import com.kolayik.dto.request.AddExpenseRequestDto;
import com.kolayik.dto.response.ExpenseResponseDto;
import com.kolayik.entity.Company;
import com.kolayik.entity.Expense;
import com.kolayik.entity.User;
import com.kolayik.exception.ErrorType;
import com.kolayik.exception.KolayIkException;
import com.kolayik.repository.ExpenseRepository;
import com.kolayik.repository.UserRepository;
import com.kolayik.utility.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = fileStorageService.storeFile(file);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Dosya yüklenemedi: " + e.getMessage());
        }
    }

    public void addExpense(AddExpenseRequestDto dto, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Kullanıcı bulunamadı.");
        }
        Company company = optionalUser.get().getCompany();


        Expense expense = Expense.builder()
                .amount(dto.amount())
                .date(LocalDateTime.now())
                .description(dto.description())
                .userId(userId)
                .companyId(
                        company.getId())
                .fileUrl(dto.fileUrl())
                .status(Status.ASKIDA)
                .build();
        expenseRepository.save(expense);
    }

    public List<Expense> getExpense() {
        return expenseRepository.findAll();
    }

    public void approved(Long userId) {
        Expense expense = expenseRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Expense not found."));
        expense.setStatus(Status.AKTIF);
        expenseRepository.save(expense);


    }

    public void reject(Long userId) {
        Expense expense = expenseRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Expense not found."));
        expense.setStatus(Status.PASIF);
        expenseRepository.save(expense);
    }

    public List<Expense> getMyExpense(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Kullanıcı bulunamadı.");
        }
       return expenseRepository.findAllByUserId(userId);

    }

    public List<Expense> getCompanyExpense(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new KolayIkException(ErrorType.USER_NOT_FOUND));

        Company company = user.getCompany();
        if (company == null) {
            throw new RuntimeException("Company not found.");
        }
        return expenseRepository.findAllByCompanyId(company.getId());


    }

    public List<ExpenseResponseDto> getCompany1Expense(Long userId) {
        // Giriş yapan kullanıcıyı bul
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new KolayIkException(ErrorType.USER_NOT_FOUND));

        // Kullanıcının şirketini al
        Company company = user.getCompany();
        if (company == null) {
            throw new RuntimeException("Company not found.");
        }

        // Şirkete ait kullanıcıları al
        List<User> companyUsers = userRepository.findAllByCompanyId(company.getId());

        // Kullanıcı id'lerini listele
        List<Long> userIds = companyUsers.stream()
                .map(User::getId)
                .toList();

        // Bu kullanıcıların yaptığı harcamaları al
        List<Expense> expenses = expenseRepository.findAllByUserIdIn(userIds);

        // Map: Expense -> DTO
        return expenses.stream().map(expense -> {
            // Harcamayı yapan kullanıcıyı bul
            User expenseUser = companyUsers.stream()
                    .filter(u -> u.getId().equals(expense.getUserId()))
                    .findFirst()
                    .orElseThrow(() -> new KolayIkException(ErrorType.USER_NOT_FOUND));

            return new ExpenseResponseDto(
                    expense.getId(),
                    expense.getAmount(),
                    expense.getDescription(),
                    expense.getFileUrl(),
                    expense.getDate(),
                    expense.getStatus(),
                    expenseUser.getName()
            );
        }).toList();
    }
}
