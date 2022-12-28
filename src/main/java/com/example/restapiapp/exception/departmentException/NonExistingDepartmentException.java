package com.example.restapiapp.exception.departmentException;

import java.util.function.Supplier;

public class NonExistingDepartmentException extends Exception {
    public NonExistingDepartmentException() {
        super("The requested department does not exist");
    }
}
