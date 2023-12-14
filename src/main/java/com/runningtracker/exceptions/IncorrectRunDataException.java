package com.runningtracker.exceptions;

public class IncorrectRunDataException extends RuntimeException {
    public IncorrectRunDataException(String errorMessage) {
        super(errorMessage);
    }
}
