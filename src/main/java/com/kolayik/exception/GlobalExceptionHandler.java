package com.kolayik.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> exceptionHandler(RuntimeException exception){
        return createErrorMessage(exception,ErrorType.INTERNAL_SERVER,HttpStatus.INTERNAL_SERVER_ERROR,null);
    }


    @ExceptionHandler(KolayIkException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> ECommerceException(KolayIkException exception){
        return createErrorMessage(exception,exception.getErrorType(),exception.getErrorType().getHttpStatus(),null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> methodArgumentNotValid(MethodArgumentNotValidException exception){
        List<String> fields = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(field -> {
            fields.add(field.getField()+ " : "+ field.getDefaultMessage());
        });
        return createErrorMessage(exception,ErrorType.BADREQUEST, HttpStatus.BAD_REQUEST,fields);
    }

    // Geçersiz argüman: 400 Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleIllegalArgument(IllegalArgumentException exception) {
        return createErrorMessage(exception, ErrorType.BADREQUEST, HttpStatus.BAD_REQUEST, null);
    }

    // Yetki hatası: 403 Forbidden
    @ExceptionHandler(SecurityException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleSecurity(SecurityException exception) {
        return createErrorMessage(exception, ErrorType.FORBIDDEN, HttpStatus.FORBIDDEN, null);
    }

    // Özel HTTP durum ve mesaj
    @ExceptionHandler(ResponseStatusException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleResponseStatus(ResponseStatusException ex) {
        // 1. HttpStatusCode al
        HttpStatusCode statusCode = ex.getStatusCode();
        // 2. Sayısal kodu HttpStatus'a dönüştür
        HttpStatus status = HttpStatus.valueOf(statusCode.value());
        String reason = ex.getReason() != null ? ex.getReason() : status.getReasonPhrase();

        ErrorMessage body = ErrorMessage.builder()
                .code(statusCode.value())
                .message(reason)
                .isSuccess(false)
                .fields(null)
                .build();

        return new ResponseEntity<>(body, status);
    }



    /**
     * Burada oluşan tüm hataların bir formatı ve response type ı olması gerekli. Tüm hatalar
     * bir noktadan log lanmalılar.
     */
    public ResponseEntity<ErrorMessage> createErrorMessage(Exception exception,ErrorType errorType, HttpStatus httpStatus, List<String> fields){
        // log.error("Exception caught in GlobalExceptionHandler", exception);
        log.error("Error message : {}", errorType.getMessage()+ " - Fields : " +fields);
        return new ResponseEntity<>(ErrorMessage.builder()
                .code(errorType.getCode())
                .fields(fields)
                .isSuccess(false)
                .message(errorType.getMessage())
                .build(),httpStatus);
    }


}
