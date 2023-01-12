package com.meetnow.meetnowbackend.global.error.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    int status;
    ErrorCode errorCode;
    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.status = errorCode.getStatus();
    }

}
