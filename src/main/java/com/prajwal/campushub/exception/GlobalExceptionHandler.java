package com.prajwal.campushub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException ex){

        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationException(
            MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult() //contains all valida errors
                .getFieldErrors()  //get every field that failed
                .stream()//Converts the list into a Java Stream so we can process each error.
                .map(error -> error.getDefaultMessage())  //extarct onlt the message fro filederror to "ivalid email"
                .toList();  //collect all message into a List<String>

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}