package com.nuramov.hw12_Web_Server.exceptions;

public class MyException extends RuntimeException{
    private String message;

    public MyException(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
