package com.lab.convertion.entity;

public class MyExceptions extends Throwable {
    String message;
    public MyExceptions(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
