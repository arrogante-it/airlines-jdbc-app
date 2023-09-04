package com.airlines.jdbc.app.exception;

public class SQLCustomException extends RuntimeException {
    public SQLCustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLCustomException(Throwable cause) {
        super(cause);
    }
}
