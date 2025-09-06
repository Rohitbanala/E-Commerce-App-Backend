package com.ecommerce.project.exceptionhandler;

public class APIException extends RuntimeException{


    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }
}
