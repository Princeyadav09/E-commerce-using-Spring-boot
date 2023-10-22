package com.e.commerce.service.exception.global;

import com.e.commerce.service.exception.MappingException;
import com.e.commerce.service.exception.PasswordException;
import com.e.commerce.service.exception.QueryException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MappingException.class)
    public ResponseEntity<Object> HandleMappingException(MappingException ex) {
//        return ResponseUtils.getResponseEntity(new ExceptionResponse<>(400 , "Error Scheduling Job !!"), HttpStatus.BAD_REQUEST);
          return ResponseEntity.ok("error in mapping!");
    }

    @ExceptionHandler(QueryException.class)
    public ResponseEntity<Object> HandleDatabaseException(QueryException ex){
        return ResponseEntity.badRequest().body(new PasswordException(ex.getDatabaseError()));
    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<PasswordException> handlePasswordException(PasswordException exception) {
        return ResponseEntity.badRequest().body(new PasswordException(exception.getPasswordError()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> resp = new HashMap<>();
        ex.getConstraintViolations().forEach(error -> {
            String fieldName = error.getPropertyPath().toString();
            String message = error.getMessage();
            resp.put(fieldName, message);
        });
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }
}
