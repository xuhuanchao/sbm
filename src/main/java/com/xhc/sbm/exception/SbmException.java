package com.xhc.sbm.exception;

import lombok.Data;

@Data
public class SbmException extends RuntimeException {

    private int errorCode;
    private String errorMessage;

    public SbmException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
