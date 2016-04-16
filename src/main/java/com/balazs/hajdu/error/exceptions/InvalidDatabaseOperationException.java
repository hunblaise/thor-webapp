package com.balazs.hajdu.error.exceptions;

/**
 * Encapsulates database related issues.
 *
 * @author Balazs Hajdu
 */
public class InvalidDatabaseOperationException extends Exception {

    public InvalidDatabaseOperationException(String message) {
        super(message);
    }

    public InvalidDatabaseOperationException(String message, Exception cause) {
        super(message, cause);
    }

}
