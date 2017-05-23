package com.motondon.dagger2_demoapp.domain.exception;

public class UserException extends Exception {

    public UserException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
