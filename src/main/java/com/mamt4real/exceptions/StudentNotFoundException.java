package com.mamt4real.exceptions;

public class StudentNotFoundException extends Exception{

    public StudentNotFoundException(){
        this("Student Not found");
    }

    public StudentNotFoundException(long id) {
        this(String.format("No Student registered with id: %d", id));
    }

    public StudentNotFoundException(String message) {
        super(message);
    }
}
