package com.airlines.jdbc.app.exception;

public class SQLOperationException extends RuntimeException {
    public SQLOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLOperationException(Throwable cause) {
        super(cause);
    }

    public SQLOperationException(String message) {
        super(message);
    }
}
