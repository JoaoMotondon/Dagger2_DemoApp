package com.motondon.dagger2_demoapp.service;

public class EmailServiceException extends Exception {

    public EmailServiceException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
