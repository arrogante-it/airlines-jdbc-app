package com.airlines.jdbc.app.persistance.exception;

public class SqlOperationException extends RuntimeException {
    public SqlOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlOperationException(Throwable cause) {
        super(cause);
    }

    public SqlOperationException(String message) {
        super(message);
    }
}
