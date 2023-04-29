package com.mamt4real.exceptions;

public class RegisteredCoursesExceedLimitException  extends  Exception{
    public RegisteredCoursesExceedLimitException() {
        this("Registered courses exceed limit (7) !");
    }

    public RegisteredCoursesExceedLimitException(String message) {
        super(message);
    }
}
