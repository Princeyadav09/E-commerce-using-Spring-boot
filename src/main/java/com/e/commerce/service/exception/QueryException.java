package com.e.commerce.service.exception;

import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class QueryException extends RuntimeException{
    private final String databaseError;

    public QueryException(String databaseError) {
        this.databaseError = databaseError;
    }

}
