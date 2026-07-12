package com.prajwal.campushub.exception;

public class StudentNotFoundException extends RuntimeException {  //not extned Excpetion *****

    public StudentNotFoundException(String message) {
        super(message);
    }

}