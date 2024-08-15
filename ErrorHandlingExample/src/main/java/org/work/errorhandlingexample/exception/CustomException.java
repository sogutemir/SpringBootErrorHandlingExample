package org.work.errorhandlingexample.exception;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}