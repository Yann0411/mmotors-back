package com.mmotors.mmotors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> gererErreurValidation(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body("Erreur de validation des champs");


    }

    @ExceptionHandler(Exception.class)
         public ResponseEntity<?> gererErreurGenerale(Exception ex) {
        return ResponseEntity.internalServerError().body("Une erreur est survenue");
    }
}
