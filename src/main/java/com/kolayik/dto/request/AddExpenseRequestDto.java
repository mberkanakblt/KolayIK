package com.kolayik.dto.request;

public record AddExpenseRequestDto(
        String token,
        Double amount,
        String description,
        String fileUrl
) {
}
