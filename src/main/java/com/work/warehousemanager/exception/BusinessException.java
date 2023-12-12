package com.work.warehousemanager.exception;

public class BusinessException extends RuntimeException{

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }
}
