package com.balazs.hajdu.error.exceptions;

/**
 * Encapsulates search results related issues.
 *
 * @author Balazs Hajdu
 */
public class InvalidSearchResultException extends Exception {

    public InvalidSearchResultException(String message) {
        super(message);
    }

    public InvalidSearchResultException(String message, Exception cause) {
        super(message, cause);
    }

}
