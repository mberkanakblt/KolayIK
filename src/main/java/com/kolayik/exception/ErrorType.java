package com.kolayik.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorType {


    BADREQUEST(4002, "Girilen parametereler hatalıdır.",BAD_REQUEST),
    SIFREHATASI(4003, "Girilen şifreler bir biri ile uyuşumamaktadır.",BAD_REQUEST),
    EMAIL_SIFRE_HATASI(4004, "Kullanıcı adı ya da şifre hatalısır", BAD_REQUEST),
    INVALID_TOKEN(4999, "Geçersiz token bilgisi", FORBIDDEN),
    USER_NOT_FOUND(2004,"Kullanici bulunamadı", INTERNAL_SERVER_ERROR),
    EMAIL_NOT_FOUND(404,"E-posta sistemde kayıtlı değil.",INTERNAL_SERVER_ERROR),
    INTERNAL_SERVER(5000, "Sunucuda beklenmeyen bir hata",INTERNAL_SERVER_ERROR),
    EMAIL_ALREADY_EXISTS(4005, "Bu email adresi zaten kullanılmakta.", BAD_REQUEST);





    int code;
    String message;
    HttpStatus httpStatus;
}
