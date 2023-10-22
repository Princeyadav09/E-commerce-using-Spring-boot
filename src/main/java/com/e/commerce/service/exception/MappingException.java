package com.e.commerce.service.exception;

public class MappingException extends RuntimeException{

    private final String mappingError;

    public MappingException(String mappingError) {
        this.mappingError = mappingError;
    }
}
