package com.meetnow.meetnowbackend.global.error.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    int status;
    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
    }

}
