package com.umati.springboot.customexceptions;

public class ResourceNotFoundException extends Exception{

    public ResourceNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ResourceNotFoundException(String s) {
        super(s);
    }
}