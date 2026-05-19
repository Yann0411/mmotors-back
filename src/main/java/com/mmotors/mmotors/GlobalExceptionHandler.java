package com.mmotors.mmotors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> gererErreurValidation(MethodArgumentNotValidException ex) {
        List<String> erreurs = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(erreurs);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> gererErreurGenerale(Exception ex) {
        return ResponseEntity.internalServerError().body("Une erreur est survenue");
    }
}
