package com.motondon.dagger2_demoapp.service;

public class TwitterServiceException extends Exception {

    public TwitterServiceException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
