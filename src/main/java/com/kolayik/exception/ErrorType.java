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
    FORBIDDEN(4005, "Yetki hatası", HttpStatus.FORBIDDEN),
    INVALID_TOKEN(4999, "Geçersiz token bilgisi", HttpStatus.FORBIDDEN),
    USER_NOT_FOUND(2004,"Kullanici bulunamadı", INTERNAL_SERVER_ERROR),
    COMPANY_NOT_FOUND(2005, "Şirket bulunamadı", INTERNAL_SERVER_ERROR),
    EMAIL_NOT_FOUND(404,"E-posta sistemde kayıtlı değil.",INTERNAL_SERVER_ERROR),
    ALLOW_NOT_PENDING(408,"İZİN BEKLEMEDE DEĞİL .",INTERNAL_SERVER_ERROR),
    NAME_NOT_FOUND(405, "KİŞİ BULUNAMADI",INTERNAL_SERVER_ERROR),
    ALLOW_DATE_CONFLICT(407, "SEÇİLEN TARİHLER DOLUDUR",INTERNAL_SERVER_ERROR),
    INVALID_DATE_RANGE(406, "SEÇİLEN TARİHLER DOLUDUR",INTERNAL_SERVER_ERROR),
    INTERNAL_SERVER(5000, "Sunucuda beklenmeyen bir hata",INTERNAL_SERVER_ERROR),
    ASSIGNMENT_NOT_FOUND(3001, "Zimmet atanması bulunamadı", HttpStatus.NOT_FOUND);






    int code;
    String message;
    HttpStatus httpStatus;
}
