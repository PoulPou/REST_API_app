package com.example.restapiapp.exception.departmentException;

public class NullDepartmentException extends Exception {
    public NullDepartmentException() {
        super("The requested department does not exist");
    }
}
