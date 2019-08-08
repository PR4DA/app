package com.gucci.mobileappws.exceptions;

public class UserServiceException extends RuntimeException {

    private static final long serialVersionUID = -8409044865458905735L;

    public UserServiceException(String message) {
        super(message);
    }
}
