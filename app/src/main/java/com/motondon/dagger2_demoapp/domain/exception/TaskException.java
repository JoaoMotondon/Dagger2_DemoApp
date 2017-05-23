package com.motondon.dagger2_demoapp.domain.exception;

public class TaskException extends Exception {

    public TaskException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
