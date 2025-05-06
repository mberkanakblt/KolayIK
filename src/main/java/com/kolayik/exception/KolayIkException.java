package com.kolayik.exception;
import lombok.Getter;

@Getter
public class KolayIkException extends RuntimeException {
    private ErrorType errorType;
    public KolayIkException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType = errorType;
    }

}
